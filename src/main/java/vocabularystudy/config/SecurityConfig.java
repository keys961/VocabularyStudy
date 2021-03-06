package vocabularystudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter
{
    public static final String SESSION_KEY = "user_info";

    @Bean
    public HandlerInterceptorAdapter securityInterceptor()
    {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        InterceptorRegistration registration = registry.addInterceptor(securityInterceptor());
        registration.addPathPatterns("/learn/**");
        registration.addPathPatterns("/plan/**");
        registration.addPathPatterns("/learnedHistory/**");
        registration.addPathPatterns("/revision/**");
        registration.addPathPatterns("/test/**");
        registration.addPathPatterns("/collection/**");
        registration.addPathPatterns("/user/logout/**");
        registration.addPathPatterns("/user/password/**");
        registration.addPathPatterns("/user/modifyProfile/**");
        registration.addPathPatterns("/user/profile/**");
    }

    private static class SecurityInterceptor extends HandlerInterceptorAdapter
    {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
        {
            HttpSession session = request.getSession();

            if(session.getAttribute(SESSION_KEY) == null)
            {
                response.sendRedirect("/user/login/");//to login
                return false;
            }

            return true;
        }
    }
}
