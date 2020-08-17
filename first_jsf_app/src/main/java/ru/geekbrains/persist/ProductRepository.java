package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.listener.AppBootstrapListener;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class ProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);
    @Inject
    private ServletContext context;

    private Connection connection;

    public ProductRepository(){

    }

    @PostConstruct
    public void init() throws SQLException {
        connection = (Connection) context.getAttribute("connection");

        createTableIfNotExists(connection);
        if(this.findAll().isEmpty()){
            logger.info("No products in DB. Add some 4.");
            this.insert(new Product(-1L,"Яблоко", "Красное", new BigDecimal(120)));
            this.insert(new Product(-1L,"Аапельсин", "Кислый",new BigDecimal(200)));
            this.insert(new Product(-1L,"Ананас", "Желтый", new BigDecimal(320)));
            this.insert(new Product(-1L,"Банан", "Спелый", new BigDecimal(60)));
        }
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into products(name, description, price) values (?, ?, ?);")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update products set name = ?, description = ?, price = ? where id = ?;")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setLong(4, product.getId());
            preparedStatement.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from products where id = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
    }
   // public Optional<Product> findById(long id) throws SQLException {
    public Product findById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select id, name, description, price from products where id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4));
                //return Optional.of(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4));
            }
        }
        return new Product(-1L,"","",new BigDecimal(0));
        //return Optional.empty();
    }

    public List<Product> findAll() throws SQLException {
        List<Product> productList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, name, description, price from products");

            while (resultSet.next()) {
                productList.add(new Product(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4) ));
            }
        }
        return productList;
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists products (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(25),\n" +
                    "    description varchar(25),\n" +
                    "    price decimal(10, 4) \n" +
                    ");");
        }
    }
}
