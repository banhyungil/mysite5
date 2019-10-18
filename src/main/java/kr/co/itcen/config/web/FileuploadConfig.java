package kr.co.itcen.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:kr/co/itcen/mysite/config/web/properties/multipart.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	Environment env;
	// MultipartResolver
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(env.getProperty("maxUploadSize", Integer.class));
		commonsMultipartResolver.setMaxInMemorySize(env.getProperty("maxInMemorySize",Integer.class));
		commonsMultipartResolver.setDefaultEncoding(env.getProperty("defaultEncoding"));
		
		return commonsMultipartResolver;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.
		addResourceHandler(env.getProperty("resourceMapping")).
		addResourceLocations("file:" + env.getProperty("uploadLocation"));
	}
	
	
	
}
