package ru.kpfu.itis.servlets.goals;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.response.SavingGoalResponse;
import ru.kpfu.itis.model.SavingGoalEntity;
import ru.kpfu.itis.service.goals.SavingGoalService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/saving-goal/update")
public class UpdateSavingGoalServlet extends HttpServlet {

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

        String uuid = req.getParameter("uuid");

        SavingGoalEntity category = savingGoalService.getSavingGoalById(
                (UUID) req.getSession(false).getAttribute("userId"),
                UUID.fromString(uuid)
        );
        req.setAttribute("savingGoal", category);

        req.getRequestDispatcher("/jsp/saving-goal/edit-saving-goal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        SavingGoalEntity savingGoal = SavingGoalEntity.builder()
                .name(req.getParameter("name"))
                .title(req.getParameter("title"))
                .total_amount(Double.parseDouble(req.getParameter("total_amount")))
                .build();

        SavingGoalResponse savingGoalResponse = savingGoalService.updateSavingGoal(uuid, savingGoal, (UUID) req.getSession(false).getAttribute("userId"));

        if (!savingGoalResponse.isSuccess()) {
            req.getSession().setAttribute("errors", savingGoalResponse.getErrors());
            resp.sendRedirect("/saving-goal/update?uuid=" + uuid);
        } else {
            resp.sendRedirect("/profile");
        }
    }
}
