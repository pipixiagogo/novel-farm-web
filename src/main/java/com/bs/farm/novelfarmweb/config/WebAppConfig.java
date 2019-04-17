package com.bs.farm.novelfarmweb.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bs.farm.novelfarmweb.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//应用配置类
@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {
//    @Autowired
//    private LoginInterceptor loginInterceptor;



    @Value("${swagger.show}")
    private boolean swaggerShow;


    //文件上传的bean 配置上传文件的大小
    @Bean
    public MultipartConfigElement multipartConfigElement(){

        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大上传
        factory.setMaxFileSize("2MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("2MB");
        return factory.createMultipartConfig();
    }

    //添加自定义的拦截器
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(loginInterceptor)
//               .addPathPatterns("/**")
//                .excludePathPatterns("/user/login","/user/ccode","/test","test1")
//                .excludePathPatterns("/Auth","/acl_verify","/verify","/firmware/download","/config/verifyDomain","/Cert")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/configuration/**");
//    }


    //配置消息的转换器  to convert HTTP requests and responses
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //继承默认配置
        //如objects can be automatically converted to JSON
        super.configureMessageConverters(converters);

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat
        );
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //处理字符串, 避免直接返回字符串的时候被添加了引号
        StringHttpMessageConverter smc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        smc.setWriteAcceptCharset(false);
        converters.add(smc);
        converters.add(fastConverter);
    }




    //处理静态文件
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //日期格式化组件
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(addNewConvert());
    }
    //转换器
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                if(StringUtil.isEmpty(source)){
                    return date;
                }
                try {
                    date = sdf.parse((String) source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }
}
