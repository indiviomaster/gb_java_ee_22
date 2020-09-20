package ru.geekbrains;

import ru.geekbrains.service.ProductRepr;
import ru.geekbrains.service.ProductServiceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EjbClient {
    public static void main(String[] args) throws IOException, NamingException, ExecutionException, InterruptedException {
        Context context =createInitialContext();
        ProductServiceRemote serviceRemote = (ProductServiceRemote) context.lookup("ejb:/first_jsf_app/ProductServiceImpl!ru.geekbrains.ProductServiceRemote");
        serviceRemote.findAll().forEach(todo-> System.out.println(todo.getId()+": "+todo.getName()));

        /*Future<ProductRepr> future = serviceRemote.findByIdAsync(1L);
        System.out.println(future.get());*/
    }

    private static Context createInitialContext() throws NamingException, IOException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
