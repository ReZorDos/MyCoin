package ru.kpfu.itis.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<String> PRIVATE_MAPPING = List.of(
            "/profile",
            "/logout",
            "/create-expense",
            "/create-income",
            "/all-transactions",
            "/create-transaction/expense",
            "/create-transaction/income",
            "/expense-category/delete",
            "/income-category/delete",
            "/expense-category/update",
            "/income-category/update"
    );
    private static final List<String> NOT_ALLOWED_FOR_AUTHENTICATED_MAPPINGS = List.of("/sign-in", "/sign-up");
    private static final String DEFAULT_AUTH_REDIRECT = "/profile";
    private static final String DEFAULT_NO_AUTH_REDIRECT = "/sign-in";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        HttpServletResponse rs = (HttpServletResponse) servletResponse;

        String email = (String) rq.getSession(true).getAttribute("email");
        String mapping = rq.getRequestURI();

        if (Objects.isNull(email)) {
            if (PRIVATE_MAPPING.contains(mapping)) {
                rs.sendRedirect(DEFAULT_NO_AUTH_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            if (NOT_ALLOWED_FOR_AUTHENTICATED_MAPPINGS.contains(mapping)) {
                rs.sendRedirect(DEFAULT_AUTH_REDIRECT);
            } else {
                rq.setAttribute("email", email);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
