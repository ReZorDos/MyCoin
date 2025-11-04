package ru.kpfu.itis.servlets.goals;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.categories.SavingGoalDto;
import ru.kpfu.itis.dto.response.SavingGoalResponse;
import ru.kpfu.itis.service.goals.SavingGoalService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/create-saving-goal")
public class CreateSavingGoalServlet extends HttpServlet {

    private SavingGoalService savingGoalService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        savingGoalService = (SavingGoalService) config.getServletContext().getAttribute("savingGoalService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }

        req.getRequestDispatcher("/jsp/saving-goal/create-saving-goal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SavingGoalDto request = SavingGoalDto.builder()
                .name(req.getParameter("name"))
                .title(req.getParameter("title"))
                .total_amount(Double.parseDouble(req.getParameter("total_amount")))
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .build();

        SavingGoalResponse savingGoalResponse = savingGoalService.createSavingGoal(request);

        if (!savingGoalResponse.isSuccess()) {
            req.getSession(false).setAttribute("errors", savingGoalResponse.getErrors());
            resp.sendRedirect("/create-saving-goal");
        } else {
            resp.sendRedirect("/profile");
        }
    }
}
