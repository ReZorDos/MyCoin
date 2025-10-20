package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.request.SignUpRequest;
import ru.kpfu.itis.dto.response.AuthResponse;
import ru.kpfu.itis.service.AuthService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private AuthService  authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        authService = (AuthService) config.getServletContext().getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }
        req.getRequestDispatcher("/jsp/sign-up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignUpRequest request = SignUpRequest.builder()
                .email(req.getParameter("email"))
                .nickname(req.getParameter("nickname"))
                .password(req.getParameter("password"))
                .build();
        AuthResponse authResponse = authService.signUp(request);
        if (!authResponse.isSuccess()) {
            req.getSession().setAttribute("errors", authResponse.getErrors());
            resp.sendRedirect("/sign-up");
        } else {
            req.getSession().setAttribute("errors", null);
            resp.sendRedirect("/sign-in");
        }
    }
}
