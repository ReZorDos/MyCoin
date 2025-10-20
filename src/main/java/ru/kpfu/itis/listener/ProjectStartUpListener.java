package ru.kpfu.itis.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.config.JdbcConfig;
import ru.kpfu.itis.repository.UserRepository;
import ru.kpfu.itis.repository.impl.UserRepositoryImpl;
import ru.kpfu.itis.service.AuthDataValidationService;
import ru.kpfu.itis.service.AuthService;
import ru.kpfu.itis.service.PasswordEncoder;
import ru.kpfu.itis.service.impl.AuthServiceImpl;
import ru.kpfu.itis.service.impl.BCryptPasswordEncoder;
import ru.kpfu.itis.service.impl.RegexpAuthDataValidationService;

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
    }
}
