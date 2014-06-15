package com.hantsylabs.restexample.springmvc.config;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hantsylabs.restexample.springmvc.Constants;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
@EnableWebMvc
@ComponentScan(
    basePackageClasses = { Constants.class },
    includeFilters = { 
        @Filter(type = FilterType.ANNOTATION, value = {RestController.class, ControllerAdvice.class })
    }
)
public class WebConfig extends SpringDataWebConfiguration {

	private static final Logger logger = LoggerFactory
			.getLogger(WebConfig.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// super.addViewControllers(registry);
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorParameter(false);
		configurer.favorPathExtension(false);
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2Converter = new MappingJackson2HttpMessageConverter();
		jackson2Converter.setSupportedMediaTypes(Arrays
				.asList(MediaType.APPLICATION_JSON));
		jackson2Converter.setObjectMapper(objectMapper());
		converters.add(jackson2Converter);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		return objectMapper;
	}
        
        
//        @Bean
//        public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver(){
//            return new ExceptionHandlerExceptionResolver();
//        }

}
