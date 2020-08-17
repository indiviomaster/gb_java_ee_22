package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppBootstrapListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppBootstrapListener.class);
    //инициализация приложения
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("******Initializing application******");

        ServletContext servletContext = servletContextEvent.getServletContext();
        String JDBCConnectionString = servletContext.getInitParameter("jdbcConnectionString");
        String username = servletContext.getInitParameter("username");
        String password = servletContext.getInitParameter("password");

        try {
            Connection dbConnection = DriverManager.getConnection(JDBCConnectionString, username, password);
            servletContext.setAttribute("connection", dbConnection);
        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("connection");
        try{
            if (connection != null && connection.isClosed()){
                connection.close();
            }

        } catch(SQLException exc)        {
            logger.error("", exc);
        }
    }
}