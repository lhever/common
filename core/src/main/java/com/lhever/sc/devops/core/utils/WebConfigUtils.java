package com.lhever.sc.devops.core.utils;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lhever.sc.devops.core.support.jackson.CustomDateDeSerializer;
import com.lhever.sc.devops.core.support.jackson.CustomDateSerializer;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author hehaoneng 2019/4/11 9:49
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2019/4/11 9:49
 * @modify by reason:{方法名}:{原因}
 */
public class WebConfigUtils {

    public static HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter() {

            @Override
            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                if (object != null && object instanceof String) {
                    OutputStream body = outputMessage.getBody();
                    body.write(StringUtils.getBytes((String) object));
                    body.flush();
                    IOUtils.closeQuietly(body);
                } else {
                    super.writeInternal(object, type, outputMessage);
                }

            }
        };

        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        objectMapper.setDateFormat(smt);


        //自定义日期序列化、反序列化类
        SimpleModule serializerModule = new SimpleModule("DateSerializer", PackageVersion.VERSION);
        serializerModule.addSerializer(Date.class, new CustomDateSerializer());
        serializerModule.addDeserializer(Date.class, new CustomDateDeSerializer());
        objectMapper.registerModule(serializerModule);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);


        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

}
