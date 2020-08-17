package ru.geekbrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="About", urlPatterns={"/about","/about/"})
public class About extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(About.class);

    @Override
    public void init() throws ServletException {    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(".... do Get About page"+req.getServletPath());

        if(req.getServletPath().equals("/about")) {
            getServletContext().getRequestDispatcher("/WEB-INF/about.jsp").forward(req, resp);
        }
        logger.info(".... after do Get About page");

    }
}