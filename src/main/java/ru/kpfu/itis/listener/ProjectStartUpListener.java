package ru.kpfu.itis.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.config.JdbcConfig;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.repository.IncomeCategoryRepository;
import ru.kpfu.itis.repository.TransactionRepository;
import ru.kpfu.itis.repository.UserRepository;
import ru.kpfu.itis.repository.impl.ExpenseCategoryRepositoryImpl;
import ru.kpfu.itis.repository.impl.IncomeCategoryRepositoryImpl;
import ru.kpfu.itis.repository.impl.TransactionRepositoryImpl;
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
import ru.kpfu.itis.service.income.IncomeDataValidation;
import ru.kpfu.itis.service.income.impl.IncomeServiceImpl;
import ru.kpfu.itis.service.income.impl.RegexpIncomeValidationService;
import ru.kpfu.itis.service.transaction.TransactionDataValidation;
import ru.kpfu.itis.service.transaction.TransactionService;
import ru.kpfu.itis.service.transaction.impl.RegexpTransactionValidationService;
import ru.kpfu.itis.service.transaction.impl.TransactionServiceImpl;

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
        ExpenseDataValidationService validationExpenseService = new RegexpExpenseValidationService();

        ExpenseService expenseService = new ExpenseServiceImpl(expenseRepository, validationExpenseService);
        context.setAttribute("expenseService", expenseService);

        IncomeCategoryRepository incomeRepository = new IncomeCategoryRepositoryImpl(JdbcConfig.getJdbcTemplate());
        context.setAttribute("incomeRepository", incomeRepository);
        IncomeDataValidation validationIncomeService = new RegexpIncomeValidationService();

        IncomeServiceImpl incomeService = new IncomeServiceImpl(incomeRepository, validationIncomeService);
        context.setAttribute("incomeService", incomeService);

        TransactionRepository transactionRepository = new TransactionRepositoryImpl(JdbcConfig.getJdbcTemplate());
        TransactionDataValidation validationTransactionService = new RegexpTransactionValidationService();

        TransactionService transactionService = new TransactionServiceImpl(transactionRepository, validationTransactionService);
        context.setAttribute("transactionService", transactionService);

    }
}
