package  ru.geekbrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Order;
import ru.geekbrains.persist.OrderRepository;
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

@WebServlet(name="Order", urlPatterns={"/order"})
public class OrderServlet extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(OrderServlet.class);
    private OrderRepository orderRepository;

    @Override
    public void init() throws ServletException {
        orderRepository = (OrderRepository) getServletContext().getAttribute("orderRepository");
        if(orderRepository == null){
            throw new ServletException("OrderRepository not initialised");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Order page   ->"+req.getServletPath() + "  " + req.getPathInfo() );

        if(req.getServletPath().equals("/order")&&req.getPathInfo()==null){
            try {
                req.setAttribute("order",orderRepository.findAll());
                logger.info("redirect order");
                getServletContext().getRequestDispatcher("/WEB-INF/orders.jsp").forward(req,resp);
            } catch (SQLException ex) {

                throw new IllegalStateException();
            }
        }else if(req.getServletPath().equals("/order")&&req.getPathInfo().equals("/new_order")){
            req.setAttribute("order", new Order());
            getServletContext().getRequestDispatcher("/WEB-INF/order.jsp").forward(req,resp);
        }
        else if(req.getServletPath().equals("/order")&&req.getPathInfo().equals("/edit_order")){
            String id = req.getParameter("id");

            try {
// если Optional
//                Optional<Order> opt=orderRepository.findById(Long.parseLong(id));
//                if(opt.isPresent()){
//                    req.setAttribute("order", opt.get());
//                }else{
//                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//                    return;
//                }

                req.setAttribute("order", orderRepository.findById(Long.parseLong(id)));
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/order.jsp").forward(req,resp);


        }else if(req.getServletPath().equals("/order")&&req.getPathInfo().equals("/delete_order")){
            try {
                orderRepository.delete(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(getServletContext().getContextPath());
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getServletPath().equals("/order/order_post")){
            try {
                String strId = req.getParameter("id");
                if(strId.isEmpty()){

                    orderRepository.insert(new Order(req.getParameter("client"), req.getParameter("description"), new BigDecimal(req.getParameter("price"))));
                    resp.sendRedirect(getServletContext().getContextPath());
                }else{
                    orderRepository.update(new Order(Long.parseLong(req.getParameter("id")),req.getParameter("client"), req.getParameter("description"), new BigDecimal(req.getParameter("price"))));
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