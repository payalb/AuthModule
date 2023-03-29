package com.java;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = false)
@EnableWebMvc
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/users/**")).build();
	}
	@Bean
	  public InternalResourceViewResolver defaultViewResolver() {
	    return new InternalResourceViewResolver();
	  }
	
	 @Bean
	    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
	        return new BeanPostProcessor() {

	            @Override
	            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
	                if (bean instanceof WebMvcRequestHandlerProvider ) {
	                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
	                }
	                return bean;
	            }

	            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
	                List<T> copy = mappings.stream()
	                    .filter(mapping -> mapping.getPatternParser() == null)
	                    .collect(Collectors.toList());
	                mappings.clear();
	                mappings.addAll(copy);
	            }

	            @SuppressWarnings("unchecked")
	            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
	                try {
	                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
	                    field.setAccessible(true);
	                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
	                } catch (IllegalArgumentException | IllegalAccessException e) {
	                    throw new IllegalStateException(e);
	                }
	            }
	        };
	 }
}
