package  ru.geekbrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Cart", urlPatterns="/cart")
public class CartServlet extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(ProductControllerServlet.class);

    @Override
    public void init() throws ServletException {    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(".... do Get Cart page"+req.getServletPath());

        if(req.getServletPath().equals("/cart")){
            getServletContext().getRequestDispatcher("/WEB-INF/cart.jsp").forward(req,resp);
        }

        logger.info(".... after do Get Cart page");


    }
}