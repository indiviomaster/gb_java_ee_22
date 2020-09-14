import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.ProductServiceWs;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/first_jsf_app/ProductService/ProductServiceImpl?wsdl");
        ProductService productService = new ProductService(url);
        ProductServiceWs port = productService.getProductServiceImplPort();
        port.findAll().forEach(todo-> System.out.println(todo.getId()+": "+todo.getName()));
    }
}
