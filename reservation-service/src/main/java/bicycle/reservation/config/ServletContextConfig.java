package bicycle.reservation.config;

import bicycle.reservation.interceptor.LoggingHandlerInterceptor;
import bicycle.reservation.interceptor.LoginCheckInterceptor;
import bicycle.reservation.security.AuthUserWebArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"bicycle.reservation.controller"})
public class ServletContextConfig extends WebMvcConfigurerAdapter {

	@Value("${app.file.max.size}")
	private Integer maxFileSize;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(maxFileSize); // 1024 * 1024 * 10 = 10MB
        return multipartResolver;
    }
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new LoggingHandlerInterceptor()).addPathPatterns("/booked/**").addPathPatterns("/exhibition/**/reserve");
	    registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/booked/**").addPathPatterns("/exhibition/**/reserve");
	    super.addInterceptors(registry);
    }

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new AuthUserWebArgumentResolver());
	}


}
