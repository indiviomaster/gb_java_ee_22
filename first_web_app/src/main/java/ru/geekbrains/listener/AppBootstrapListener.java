package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Order;
import ru.geekbrains.persist.OrderRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppBootstrapListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppBootstrapListener.class);
    //инициализация приложения
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("*****Initializing application******");

        ServletContext servletContext = servletContextEvent.getServletContext();
        String JDBCConnectionString = servletContext.getInitParameter("jdbcConnectionString");
        String username = servletContext.getInitParameter("username");
        String password = servletContext.getInitParameter("password");

        try {
            Connection dbConnection = DriverManager.getConnection(JDBCConnectionString, username, password);

            servletContext.setAttribute("connection", dbConnection);

            logger.info("*****Product repo ******");
            ProductRepository productRepository = new ProductRepository(dbConnection);
            OrderRepository orderRepository = new OrderRepository(dbConnection);
            //CartRepository cartRepository = new CartRepository(dbConnection);

            //создаем записи если база пустая
            if(productRepository.findAll().isEmpty()){
                logger.info("No products in DB. Add some 4.");
                productRepository.insert(new Product(-1L,"Яблоко", "Красное", new BigDecimal(120)));
                productRepository.insert(new Product(-1L,"Аапельсин", "Кислый",new BigDecimal(200)));
                productRepository.insert(new Product(-1L,"Ананас", "Желтый", new BigDecimal(320)));
                productRepository.insert(new Product(-1L,"Банан", "Спелый", new BigDecimal(60)));
            }

            logger.info("*****Order repo ******");
            if(orderRepository.findAll().isEmpty()){
                logger.info("No orders in DB. Add some 4.");
                orderRepository.insert(new Order(-1L,"Михаил", "Тува", new BigDecimal(120)));
                orderRepository.insert(new Order(-1L,"Николай", "Крассное",new BigDecimal(200)));
                orderRepository.insert(new Order(-1L,"Вера", "СПб", new BigDecimal(320)));
                orderRepository.insert(new Order(-1L,"Ольга", "Москва", new BigDecimal(60)));
            }

            servletContext.setAttribute("productRepository", productRepository);
            servletContext.setAttribute("orderRepository", orderRepository);

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