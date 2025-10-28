package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.response.IncomeResponse;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.service.income.IncomeService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/income-category/update")
public class UpdateIncomeCategoryServlet extends HttpServlet {

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

        String uuid = req.getParameter("uuid");

        IncomeCategoryEntity category = incomeService.getCategoryById(UUID.fromString(uuid));
        req.setAttribute("category", category);

        String iconPath = getServletContext().getRealPath("/static/icons/income");
        List<String> icons = incomeService.getAvailableIcons(iconPath);
        req.setAttribute("availableIcons", icons);

        req.getRequestDispatcher("/jsp/edit-income.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        IncomeCategoryEntity income = IncomeCategoryEntity.builder()
                .name(req.getParameter("name"))
                .icon(req.getParameter("icon"))
                .build();

        IncomeResponse incomeResponse =  incomeService.updateIncomeCategory(uuid, income);

        if (!incomeResponse.isSuccess()) {
            req.getSession().setAttribute("errors", incomeResponse.getErrors());
            resp.sendRedirect("/income-category/update?uuid=" + uuid);
        } else {
            resp.sendRedirect("/profile");
        }
    }
}
