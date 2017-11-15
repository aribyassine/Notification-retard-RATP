package controllers.authentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthenticationFilter implements Filter {
    private String loginPage = "";
    private List<String> excludedURLs;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.loginPage = filterConfig.getInitParameter("loginPage");

        String[] excluded = filterConfig.getInitParameter("excludedURLs").split(";");
        excludedURLs = new ArrayList<>();
        Collections.addAll(excludedURLs, excluded);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean isExcludedURL = false;
        for (String excludedURL : excludedURLs) {
            if (request.getRequestURL().indexOf(excludedURL) > -1) {
                isExcludedURL = true;
                break;
            }
        }
        System.out.println(request.getRequestURL());

        if (isExcludedURL) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("username") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
