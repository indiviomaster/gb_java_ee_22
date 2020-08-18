package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.listener.AppBootstrapListener;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "ProductControllerServlet", urlPatterns = {"","/"})
public class ProductControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductControllerServlet.class);

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if(productRepository == null){
            throw new ServletException("ProductRepository not initialised");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Index product page");

        if(req.getServletPath().equals("/")){
            try {
                req.setAttribute("products",productRepository.findAll());
                logger.info("redirect index");
                getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req,resp);
            } catch (SQLException ex) {

               throw new IllegalStateException();
            }
        }else if(req.getServletPath().equals("/new_product")){
            req.setAttribute("product", new Product());
            getServletContext().getRequestDispatcher("/WEB-INF/product.jsp").forward(req,resp);
        }
        else if(req.getServletPath().equals("/edit_product")){
            String id = req.getParameter("id");

            try {
// если Optional
//                Optional<Product> opt=productRepository.findById(Long.parseLong(id));
//                if(opt.isPresent()){
//                    req.setAttribute("product", opt.get());
//                }else{
//                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//                    return;
//                }

                req.setAttribute("product", productRepository.findById(Long.parseLong(id)));
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/product.jsp").forward(req,resp);


        }else if(req.getServletPath().equals("/delete_product")){
            try {
                productRepository.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(getServletContext().getContextPath());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getServletPath().equals("/product_post")){
            try {
                String strId = req.getParameter("id");
                if(strId.isEmpty()){

                    productRepository.insert(new Product(req.getParameter("name"), req.getParameter("description"), new BigDecimal(req.getParameter("price"))));
                    resp.sendRedirect(getServletContext().getContextPath());
                }else{
                    productRepository.update(new Product(Long.parseLong(req.getParameter("id")),req.getParameter("name"), req.getParameter("description"), new BigDecimal(req.getParameter("price"))));
                    resp.sendRedirect(getServletContext().getContextPath());
                }
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}


