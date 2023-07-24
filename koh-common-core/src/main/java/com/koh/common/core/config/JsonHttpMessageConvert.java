package com.koh.common.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.koh.common.core.utils.StringUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kohlarnhin
 * @create 2022/7/19 9:37
 */
@Configuration
public class JsonHttpMessageConvert {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        List<SerializerFeature> list = new ArrayList<>();
        list.add(SerializerFeature.PrettyFormat);
        list.add(SerializerFeature.WriteMapNullValue);
        list.add(SerializerFeature.WriteNullStringAsEmpty);
        list.add(SerializerFeature.WriteNullListAsEmpty);
        list.add(SerializerFeature.QuoteFieldNames);
        list.add(SerializerFeature.WriteDateUseDateFormat);
        list.add(SerializerFeature.DisableCircularReferenceDetect);
        list.add(SerializerFeature.WriteBigDecimalAsPlain);

        //升级最新版本需加=============================================================
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);

        fastJsonConfig.setSerializerFeatures(list.toArray(new SerializerFeature[list.size()]));
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        fastJsonConfig.setSerializeFilters(new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {
                if ((StringUtils.endsWith(name, "Id") || StringUtils.equals(name, "id")) && value != null
                        && value.getClass() == Long.class) {
                    return String.valueOf(value);
                }
                return value;
            }
        });
        return new HttpMessageConverters(converter);
    }
}
