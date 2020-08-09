package  ru.geekbrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Product", urlPatterns="/product")
public class ProductServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(".... do Get Order page"+req.getServletPath());

        if(req.getServletPath().equals("/order")){
            getServletContext().getRequestDispatcher("/WEB-INF/product.jsp").forward(req,resp);
        }
    }
}