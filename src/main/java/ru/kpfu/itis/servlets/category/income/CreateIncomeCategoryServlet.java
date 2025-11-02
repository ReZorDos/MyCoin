package ru.kpfu.itis.servlets.category.income;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.dto.response.IncomeResponse;
import ru.kpfu.itis.service.income.IncomeService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/create-income")
public class CreateIncomeCategoryServlet extends HttpServlet {

    private IncomeService incomeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        incomeService = (IncomeService) config.getServletContext().getAttribute("incomeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }

        String iconPath = getServletContext().getRealPath("static/icons/income");
        List<String> icons = incomeService.getAvailableIcons(iconPath);
        req.setAttribute("availableIcons", icons);

        req.getRequestDispatcher("/jsp/income/create-income.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IncomeDto request = IncomeDto.builder()
                .name(req.getParameter("name"))
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .icon(req.getParameter("icon"))
                .build();

        IncomeResponse incomeResponse = incomeService.createIncomeCategory(request);

        if (!incomeResponse.isSuccess()) {
            req.getSession(false).setAttribute("errors", incomeResponse.getErrors());
            resp.sendRedirect("/create-income");
        } else {
            resp.sendRedirect("/profile");
        }
    }
}
