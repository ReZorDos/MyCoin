package ru.kpfu.itis.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class ErrorsCleanFilter implements Filter {

    private static final List<String> ALLOWED_ERRORS = List.of(
            "/sign-in",
            "/sign-up",
            "/create-expense",
            "/create-income",
            "/expense-category/update");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;

        String mapping = rq.getRequestURI();
        if (!ALLOWED_ERRORS.contains(mapping)) {
            rq.getSession().setAttribute("errors", null);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
