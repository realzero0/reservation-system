package bicycle.reservation.config;

import java.util.*;

import javax.servlet.*;

import org.springframework.web.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;
import org.springframework.web.filter.*;
import org.springframework.web.servlet.*;

public class WebInitializer implements WebApplicationInitializer {
	private static final String CONFIG_LOCATION = "bicycle.reservation.config";
	private static final String MAPPING_URL = "/";

	public WebInitializer() {

	}

	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = getContext();

		// encoding filter 설정
		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding",
				characterEncodingFilter);
		characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

		// Dispatcher servlet 설정
		servletContext.addListener(new ContextLoaderListener(context));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
				new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(MAPPING_URL);
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		return context;
	}
}