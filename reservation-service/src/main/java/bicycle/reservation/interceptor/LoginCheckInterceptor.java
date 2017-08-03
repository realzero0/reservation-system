package bicycle.reservation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bicycle.reservation.domain.Users;



public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(); 
       
        //System.out.println("path : " + path);
       
        if (session.getAttribute("user") == null) {
        	request.getSession().setAttribute("returnUrl", request.getRequestURL());
			response.sendRedirect("/login");
            return false;
        	
        } 
        return true;
        
    }
}