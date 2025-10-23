package ru.kpfu.itis.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.config.JdbcConfig;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.repository.UserRepository;
import ru.kpfu.itis.repository.impl.ExpenseCategoryRepositoryImpl;
import ru.kpfu.itis.repository.impl.UserRepositoryImpl;
import ru.kpfu.itis.service.auth.AuthDataValidationService;
import ru.kpfu.itis.service.auth.AuthService;
import ru.kpfu.itis.service.auth.PasswordEncoder;
import ru.kpfu.itis.service.auth.impl.AuthServiceImpl;
import ru.kpfu.itis.service.auth.impl.BCryptPasswordEncoder;
import ru.kpfu.itis.service.auth.impl.RegexpAuthDataValidationService;
import ru.kpfu.itis.service.expense.ExpenseDataValidationService;
import ru.kpfu.itis.service.expense.ExpenseService;
import ru.kpfu.itis.service.expense.impl.ExpenseServiceImpl;
import ru.kpfu.itis.service.expense.impl.RegexpExpenseValidationService;

@WebListener
public class ProjectStartUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserRepository userRepository = new UserRepositoryImpl(JdbcConfig.getJdbcTemplate());
        AuthDataValidationService authDataValidationService = new RegexpAuthDataValidationService();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        AuthService authService = new AuthServiceImpl(userRepository, authDataValidationService, passwordEncoder);
        context.setAttribute("authService", authService);

        ExpenseCategoryRepository expenseRepository = new ExpenseCategoryRepositoryImpl(JdbcConfig.getJdbcTemplate());
        context.setAttribute("expenseRepository", expenseRepository);
        ExpenseDataValidationService validationService = new RegexpExpenseValidationService();

        ExpenseService expenseService = new ExpenseServiceImpl(expenseRepository, validationService);
        context.setAttribute("expenseService", expenseService);
    }
}
