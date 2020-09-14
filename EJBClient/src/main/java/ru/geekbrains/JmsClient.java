package ru.geekbrains;

import ru.geekbrains.service.ProductRepr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.print.attribute.standard.Destination;
import javax.resource.cci.ConnectionFactory;
import java.util.Properties;

public class JmsClient {
    public static void main(String[] args) throws Exception {
       /* Context context =createInitialContext();
        ConnectionFactory connectionFactory =(ConnectionFactory)  context.lookup("jms/RemoteConnectionFactory");
        JMSContext jmsContext = connectionFactory.createContext("jmsGuestUser","123");
        Destination destination = (Destination) context.lookup("jms/queue/productQueue");
        JMSProducer jmsProducer = jmsContext.createProducer();
        ObjectMessage objectMessage = jmsContext.createObjectMessage(new ProductRepr(null,"JMs product",""));
        jmsProducer.setProperty("action","create").send(destination,objectMessage);*/

    }

    private static Context createInitialContext() throws Exception {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
