package com.lhever.sc.devops.core.support.IdCard;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lhever.sc.devops.core.utils.StringUtils;

import java.io.IOException;

/**
 * @author liuyabin6 2019/11/22 22:04
 * @version V1.0
 */
public class IdCardSerializerConvert extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {


        if (!StringUtils.isEmpty(s)&&s!=null) {
            jsonGenerator.writeString(idEncrypt(s));
        }else{
            jsonGenerator.writeString(s);
        }
    }

    public static   String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

}
