package com.netand.avocado.authorization.listener.restful.configuration;

import com.netand.avocado.authorization.listener.restful.intercepter.LoggingInterceptor;
import com.netand.avocado.authorization.model.converter.GrantTypeConverter;
import com.netand.avocado.authorization.model.converter.ResponseTypesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    public WebMvcConfiguration( @Autowired LoggingInterceptor loggingInterceptor ) {

        this.loggingInterceptor = loggingInterceptor;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxRequestSize( DataSize.ofMegabytes( 20 ) );
        factory.setMaxFileSize( DataSize.ofMegabytes( 20 ) );
        factory.setFileSizeThreshold( DataSize.ofBytes( 0 ) );
        return factory.createMultipartConfig();
    }

    @Bean( name = "multipartResolver" )
    public StandardServletMultipartResolver resolver() {

        return new StandardServletMultipartResolver();
    }

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {

        registry.addInterceptor( loggingInterceptor )
                .addPathPatterns( "/**" )
//                .excludePathPatterns(
//                        ApiPaths.ServiceManagement.GetVersion + "/**"
//                )
                .order( 0 );
    }

    @Override
    public void addCorsMappings( CorsRegistry registry ) {

        registry.addMapping( "/**" )
                .allowedOrigins( "*" )
                .allowedMethods( "*" )
                .allowedHeaders( "*" )
                .allowCredentials( true );
    }

    @Override
    public void addFormatters( FormatterRegistry registry ) {

	    registry.addConverter( new ResponseTypesConverter() );
	    registry.addConverter( new GrantTypeConverter() );

        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat( true );
        registrar.registerFormatters( registry );
    }

	@Override
	public void configurePathMatch( PathMatchConfigurer configurer) {

		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setUrlDecode(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}
}
