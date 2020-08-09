package ru.geekbrains.persist;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private final Connection connection;

    public CartRepository(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists(connection);
    }

    public void insert(Order order) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into carts(client, description, price) values ( ?, ?,?);")) {
            preparedStatement.setString(1, order.getClient());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setBigDecimal(3, order.getPrice());
            preparedStatement.execute();
        }
    }

    public void update(Order order) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update carts set client = ?, description = ?, price = ? where id = ?;")) {
            preparedStatement.setString(1, order.getClient());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setBigDecimal(3, order.getPrice());
            preparedStatement.setLong(4, order.getId());
            preparedStatement.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from carts where id = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
    }
   // public Optional<Order> findById(long id) throws SQLException {
    public Order findById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select id, client, description, price from carts where id = ?")) {
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
            ResultSet resultSet = statement.executeQuery("select id, client, description, price from carts ");

            while (resultSet.next()) {
                productList.add(new Order(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4) ));
            }
        }
        return productList;
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists carts (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    client varchar(25),\n" +
                    "    description varchar(25),\n" +
                    "    price decimal(10, 4) \n" +
                    ");");
        }
    }
}
