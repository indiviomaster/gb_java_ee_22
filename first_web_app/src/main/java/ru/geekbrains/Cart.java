package  ru.geekbrains;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Cart", urlPatterns="/cart")
public class Cart extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Корзина</h1>");
        resp.getWriter().println("<a href='./main'> Главная");
        resp.getWriter().println("<a href='./catalog'> Каталог");
        resp.getWriter().println("<a href='./product'> Товар");
        resp.getWriter().println("<a href='./cart'> Корзина");
        resp.getWriter().println("<a href='./order'> Заказ");
    }
}