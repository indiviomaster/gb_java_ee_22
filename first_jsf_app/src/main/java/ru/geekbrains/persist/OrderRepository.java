package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    @Inject
    private ServletContext context;

    private Connection connection;

    public OrderRepository() {
    }


    @PostConstruct
    public void init() throws SQLException {
        connection = (Connection) context.getAttribute("connection");
        createTableIfNotExists(connection);
        if(this.findAll().isEmpty()){
            logger.info("No orders in DB. Add some 4.");
            this.insert(new Order(-1L,"Михаил", "Тува", new BigDecimal(120)));
            this.insert(new Order(-1L,"Николай", "Крассное",new BigDecimal(200)));
            this.insert(new Order(-1L,"Вера", "СПб", new BigDecimal(320)));
            this.insert(new Order(-1L,"Ольга", "Москва", new BigDecimal(60)));
        }
    }





    public void insert(Order order) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into orders(client, description, price) values ( ?, ?,?);")) {
            preparedStatement.setString(1, order.getClient());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setBigDecimal(3, order.getPrice());
            preparedStatement.execute();
        }
    }

    public void update(Order order) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update orders set client = ?, description = ?, price = ? where id = ?;")) {
            preparedStatement.setString(1, order.getClient());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setBigDecimal(3, order.getPrice());
            preparedStatement.setLong(4, order.getId());
            preparedStatement.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from orders where id = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
    }
   // public Optional<Order> findById(long id) throws SQLException {
    public Order findById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select id, client, description, price from orders where id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Order(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),resultSet.getBigDecimal(4));
                //return Optional.of(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3), resultSet.getBigDecimal(3));
            }
        }
        return new Order(-1L,"","",new BigDecimal(0));
        //return Optional.empty();
    }

    public List<Order> findAll() throws SQLException {
        List<Order> productList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, client, description, price from orders ");

            while (resultSet.next()) {
                productList.add(new Order(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4) ));
            }
        }
        return productList;
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists orders (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    client varchar(25),\n" +
                    "    description varchar(25),\n" +
                    "    price decimal(10, 4) \n" +
                    ");");
        }
    }
}
