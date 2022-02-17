package com.egencia.webapp.myapp.config;

import com.egencia.library.commons.web.serviceclient.EgenciaServiceRestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.DeviceWebArgumentResolver;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

@Configuration
@EnableCaching
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ServletWebArgumentResolverAdapter(new DeviceWebArgumentResolver()));
        argumentResolvers.add(new SitePreferenceHandlerMethodArgumentResolver());
    }

    @Bean
    public RestTemplate restTemplate(EgenciaServiceRestTemplateBuilder egenciaServiceRestTemplateBuilder) {
        return egenciaServiceRestTemplateBuilder.usingServiceToken().build();
    }

    /**
     * Resources mapping
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
    }
}
