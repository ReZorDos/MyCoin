package ru.kpfu.itis.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.config.JdbcConfig;
import ru.kpfu.itis.repository.*;
import ru.kpfu.itis.repository.impl.*;
import ru.kpfu.itis.service.analyze.AnalyzeService;
import ru.kpfu.itis.service.analyze.impl.AnalyzeServiceImpl;
import ru.kpfu.itis.service.auth.AuthDataValidationService;
import ru.kpfu.itis.service.auth.AuthService;
import ru.kpfu.itis.service.auth.PasswordEncoder;
import ru.kpfu.itis.service.auth.impl.AuthServiceImpl;
import ru.kpfu.itis.service.auth.impl.BCryptPasswordEncoder;
import ru.kpfu.itis.service.auth.impl.RegexpAuthDataValidationService;
import ru.kpfu.itis.service.chart.ChartService;
import ru.kpfu.itis.service.chart.impl.ChartServiceImpl;
import ru.kpfu.itis.service.expense.ExpenseDataValidationService;
import ru.kpfu.itis.service.expense.ExpenseService;
import ru.kpfu.itis.service.expense.impl.ExpenseServiceImpl;
import ru.kpfu.itis.service.expense.impl.RegexpExpenseValidationService;
import ru.kpfu.itis.service.income.IncomeDataValidation;
import ru.kpfu.itis.service.income.IncomeService;
import ru.kpfu.itis.service.income.impl.IncomeServiceImpl;
import ru.kpfu.itis.service.income.impl.RegexpIncomeValidationService;
import ru.kpfu.itis.service.transaction.TransactionDataValidation;
import ru.kpfu.itis.service.transaction.TransactionService;
import ru.kpfu.itis.service.transaction.impl.RegexpTransactionValidationService;
import ru.kpfu.itis.service.transaction.impl.TransactionServiceImpl;
import ru.kpfu.itis.service.user.UserService;
import ru.kpfu.itis.service.user.impl.UserServiceImpl;

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
        ExpenseDataValidationService validationExpenseService = new RegexpExpenseValidationService();

        ExpenseService expenseService = new ExpenseServiceImpl(expenseRepository, validationExpenseService);
        context.setAttribute("expenseService", expenseService);

        IncomeCategoryRepository incomeRepository = new IncomeCategoryRepositoryImpl(JdbcConfig.getJdbcTemplate());
        IncomeDataValidation validationIncomeService = new RegexpIncomeValidationService();

        IncomeService incomeService = new IncomeServiceImpl(incomeRepository, validationIncomeService);
        context.setAttribute("incomeService", incomeService);

        TransactionRepository transactionRepository = new TransactionRepositoryImpl(JdbcConfig.getJdbcTemplate());
        TransactionDataValidation validationTransactionService = new RegexpTransactionValidationService();

        TransactionService transactionService = new TransactionServiceImpl(transactionRepository, validationTransactionService);
        context.setAttribute("transactionService", transactionService);

        UserService userService = new UserServiceImpl(userRepository);
        context.setAttribute("userService", userService);

        ChartRepository chartRepository = new ChartRepositoryImpl(JdbcConfig.getJdbcTemplate());
        ChartService chartService = new ChartServiceImpl(chartRepository);
        context.setAttribute("chartService", chartService);

        AnalyzeRepository analyzeRepository = new AnalyzeRepositoryImpl(JdbcConfig.getJdbcTemplate(), userRepository);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeRepository);
        context.setAttribute("analyzeService", analyzeService);
    }
}
