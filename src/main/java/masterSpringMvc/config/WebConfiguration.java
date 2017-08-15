package masterSpringMvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import masterSpringMvc.date.ChinaDateFormatter;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Locale;

@EnableSwagger2
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {


    /**
     * 格式转化器,在接受表单的时候转换格式
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class,new ChinaDateFormatter());
        super.addFormatters(registry);
    }

    /**
     *
     * @param properties
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(WebMvcProperties properties){
        return new SessionLocaleResolver();
    }


    /**
     * 配置locale 设置方式
     * @return
     */
    @Bean
    public LocaleChangeInterceptor LocaleChangeInterceptor(){

        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return  localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(LocaleChangeInterceptor());
    }

    /**
     * 增加错误页面，对于servlet级别的错误，重定向到/uploadError
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return container -> {
            container.addErrorPages(new ErrorPage(MultipartException.class,"/uploadError"));
        };
    }

    /**
     * 需要设置tomcat 的 maxSwallowSize 参数以解决 ，渲染了错误页面后，连接被重置的问题
     * @return
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory containerFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol)) {
                    ((AbstractHttp11Protocol) connector.getProtocolHandler()).setMaxSwallowSize(-1);
                }
            }
        };
    }

    /**
     * 配置是否需要使用矩阵参数，
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        UrlPathHelper urlPathHelper = new UrlPathHelper();
        //默认情况会异常url后面分号后的内容eg: profile;var1=value,value2
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }

    /**
     * 配置jackson 序列化 date，不使用时间戳
     * @param builder
     * @return
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder){
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

        return objectMapper;
    }

    /**
     * 配置swagger 只显示url为/api的文档
     * @return
     */
    @Bean
    public Docket userApi(){
        return new Docket(DocumentationType.SWAGGER_2).select().paths(path->path.startsWith("/api/")).build();
    }

    /**
     * 设置contentType
     * 1.按客户端发送的Accept头信息
     * 2.借助类似于“?format=json”这样的参数
     * 3.借助路径扩展民，如/myResource.json 或 myResource.xml
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(true).useJaf(false)
//                .favorParameter(true).parameterName("mediaType")
//                .ignoreAcceptHeader(true).
//                defaultContentType(MediaType.APPLICATION_JSON).
//                mediaType("xml", MediaType.APPLICATION_XML).
//                mediaType("json", MediaType.APPLICATION_JSON);
    }

}
