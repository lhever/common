package com.lhever.sc.devops.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * <p>支持HTTP、HTTPS请求GET、POST调用</p>
 *
 * @author dingzhongcheng 2019/11/18 19:39
 * @version V1.0
 */
@Slf4j
public class HttpClientUtils {
    private static final int NUM_OF_RETRIES = 3;

    private static HttpClient client = null;

    // 连接超时
    public static final Integer DEFAULT_CONNECTION_TIME_OUT = 3000;

    // 读取超时
    public static final Integer DEFAULT_SOCKET_TIME_OUT = 10000;

    public static final String DEFAULT_CHAR_SET = "UTF-8";

    static {
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount > NUM_OF_RETRIES) {
                    return false;
                }
                if (exception instanceof UnknownHostException
                        || exception instanceof ConnectTimeoutException
                        || exception instanceof InterruptedIOException
                        || exception instanceof SSLException) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException
                        || exception instanceof SocketException
                        || exception instanceof ConnectException) {
                    return true;
                }
                HttpClientContext clientContext = HttpClientContext
                        .adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(40);
        cm.setDefaultMaxPerRoute(20);
        client = HttpClients.custom().setConnectionManager(cm).setRetryHandler(handler).build();
    }


    public static String doPostDefaultSecurity(String url, String jsonText, Map<String, String> header, Map<String, String> resHeader) throws Exception {
        return TryWithLocalStrategy.tryDoPost(0, url, jsonText, null, header, resHeader);
    }

    public static String doGetDefaultSecurity(String url, Map<String, String> header) throws Exception {
        return TryWithLocalStrategy.tryDoGet(0, url, null, header);
    }

    /**
     * <p>支持HTTP、HTTPS POST请求</p>
     * @param url
     * @param jsonText
     * @param key
     * @param header
     * @param resHeader
     * @return
     * @throws Exception
     */
    private static String doPost(String url, String jsonText, String key, Map<String, String> header, Map<String, String> resHeader) throws Exception {
        HttpClient client = null;
        InputStream in = null;
        HttpPost post = new HttpPost(url);
        try {
            if (jsonText != null && !jsonText.isEmpty()) {
                StringEntity entity = new StringEntity(jsonText, ContentType.APPLICATION_JSON);
                post.setEntity(entity);
            }
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_SOCKET_TIME_OUT);
            post.setConfig(customReqConf.build());
            HttpResponse res = null;
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client;
                res = client.execute(post);
            }
            if (resHeader != null) {
                Header[] headers = res.getAllHeaders();
                if (headers != null) {
                    for (Header h : headers) {
                        resHeader.put(h.getName(), h.getValue());
                    }
                }
            }
            in = res.getEntity().getContent();
            String result = org.apache.commons.io.IOUtils.toString(in, DEFAULT_CHAR_SET);
            return result;
        } catch (Exception e) {
            log.error("doPost", "http error", e);
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            post.releaseConnection();
            if (url.startsWith("https")  && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * <p>支持HTTP、HTTPS GET请求</p>
     * @param url
     * @param key
     * @param header
     * @return
     * @throws Exception
     */
    private static String doGet(String url, String key, Map<String, String> header) throws Exception {
        HttpClient client = null;
        InputStream in = null;
        HttpGet get = new HttpGet(url);
        try {
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
            customReqConf.setSocketTimeout(DEFAULT_SOCKET_TIME_OUT);
            get.setConfig(customReqConf.build());
            HttpResponse res = null;
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                // 执行 Http 请求.
                client = HttpClientUtils.client;
                res = client.execute(get);
            }
            in = res.getEntity().getContent();
            String result = IOUtils.toString(in, DEFAULT_CHAR_SET);
            return result;
        } catch (Exception e) {
            // 加这步为了能统一记分布式日志，失败的情况
            log.error("doGet", "http error", e);
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            get.releaseConnection();
            if (url.startsWith("https") && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     *
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    if (executionCount > NUM_OF_RETRIES) {
                        return false;
                    }
                    if (exception instanceof UnknownHostException
                            || exception instanceof ConnectTimeoutException
                            || exception instanceof InterruptedIOException
                            || exception instanceof SSLException) {
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException
                            || exception instanceof SocketException
                            || exception instanceof ConnectException) {
                        return true;
                    }
                    HttpClientContext clientContext = HttpClientContext
                            .adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };
            return HttpClients.custom().setSSLSocketFactory(sslsf).setRetryHandler(handler).build();
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }

    /**
     * 寻址完善策略
     */
    static class TryWithLocalStrategy {
        /**
         * 连接超时尝试新连接 POST
         *
         * @param count
         * @param jsonText
         * @param url
         * @return
         * @throws Exception
         */
        static String tryDoPost(int count, String url, String jsonText, String key, Map<String, String> header, Map<String, String> resHeader) throws Exception {
            String result = "";
            try {
                result = doPost(url, jsonText, key, header, resHeader);
            } catch (Exception e) {
                //重试
                if (e instanceof ConnectTimeoutException && count == 0) {
                    return tryDoPost(1, url, jsonText, key, header, resHeader);
                } else {
                    throw e;
                }
            }
            return result;
        }

        /**
         * 连接超时尝试新连接 GET
         *
         * @param count
         * @param url
         * @param key
         * @param header
         * @return
         * @throws Exception
         */
        static String tryDoGet(int count, String url, String key, Map<String, String> header) throws Exception {
            String result = "";
            try {
                result = doGet(url, key, header);
            } catch (Exception e) {
                //重试
                if (e instanceof ConnectTimeoutException && count == 0) {
                    return tryDoGet(1, url, key, header);
                } else {
                    throw e;
                }
            }
            return result;
        }

    }
}
