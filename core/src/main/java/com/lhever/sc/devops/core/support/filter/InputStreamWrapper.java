package com.lhever.sc.devops.core.support.filter;

import com.lhever.sc.devops.core.utils.WebServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputStreamWrapper extends HttpServletRequestWrapper {
    private final static Logger log = LoggerFactory.getLogger(InputStreamWrapper.class);
    private final byte[] body; // 报文
    final static int BUFFER_SIZE = 512;
    private boolean finished = false;

    public InputStreamWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //WebServletUtils.setEncoding(request, UpmConstants.CHARSET_UTF8);
        ServletInputStream inputStream = request.getInputStream();
        body = WebServletUtils.toBytesAndClose(inputStream); //读取输入流
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body); //再封装数据
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                int data = bais.read();
                if (data == -1) {
                    InputStreamWrapper.this.finished = true;
                }


                return data;
            }

            @Override
            public boolean isFinished() {
                return InputStreamWrapper.this.finished;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) { //在这里配置读取监听  可以使用这个方法限制读取次数
            }

            @Override
            public int available() throws IOException {
                return bais.available();
            }


            @Override
            public void close() throws IOException {
                super.close();
                bais.close();
            }
        };
    }


}

