package  ru.geekbrains;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="MainPage", urlPatterns="/main")
public class MainPage extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Главная</h1>");
        //resp.getWriter().println("<a href='first_web_app/main'> Главная </a> <a href='first_web_app/catalog'> Катаог </a>");
        resp.getWriter().println("<a href='./main'> Главная");
        resp.getWriter().println("<a href='./catalog'> Каталог");
        resp.getWriter().println("<a href='./product'> Товар");
        resp.getWriter().println("<a href='./cart'> Корзина");
        resp.getWriter().println("<a href='./order'> Заказ");

    }
}