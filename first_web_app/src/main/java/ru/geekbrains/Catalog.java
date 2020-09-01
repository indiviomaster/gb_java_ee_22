package  ru.geekbrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Catalog", urlPatterns={"/catalog","/catalog/"})
public class Catalog extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(ProductControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(".... do Get catalog page"+req.getServletPath());
        if(req.getServletPath().equals("/catalog")||req.getServletPath().equals("/catalog/")){
        getServletContext().getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req,resp);
        }
    }
}