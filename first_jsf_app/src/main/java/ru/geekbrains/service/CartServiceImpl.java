package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.ProductRepository;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
public class CartServiceImpl implements CartService {
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private List<ProductRepr> productReprList = new ArrayList<>();


    @Override
    public void add(ProductRepr productRepr) {

        productReprList.add(productRepr);
        logger.info(productReprList.toString());
    }

    @Override
    public List<ProductRepr> getAllProducts() {

        return productReprList;
    }
}
