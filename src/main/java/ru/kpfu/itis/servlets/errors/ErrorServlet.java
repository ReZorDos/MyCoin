package ru.kpfu.itis.servlets.errors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleError(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");

        req.setAttribute("statusCode", statusCode);
        req.setAttribute("requestUri", requestUri);
        req.setAttribute("errorMessage", throwable != null ? throwable.getMessage() : "Unknown error");

        req.getRequestDispatcher("/jsp/error/error.jsp").forward(req, resp);
    }
}