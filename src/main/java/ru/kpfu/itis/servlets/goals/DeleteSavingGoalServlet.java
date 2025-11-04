package ru.kpfu.itis.servlets.goals;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.service.goals.SavingGoalService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/saving-goal/delete")
public class DeleteSavingGoalServlet extends HttpServlet {

    private SavingGoalService savingGoalService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        savingGoalService = (SavingGoalService) config.getServletContext().getAttribute("savingGoalService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        boolean deleted = savingGoalService.deleteSavingGoal(UUID.fromString(uuid));
        resp.sendRedirect("/profile");
    }
}
