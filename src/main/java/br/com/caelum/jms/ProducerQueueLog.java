package br.com.caelum.jms;

import br.com.caelum.model.Pedido;
import br.com.caelum.model.PedidoFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
@Component
public class ProducerQueueLog {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = (Destination) context.lookup("log");

        MessageProducer producer = session.createProducer(queue);
        Message message = session.createTextMessage("INFO | ID:lnxcit017578-14333-1510652729757-1:1:1:1 Exception while processing message: ID:lnxcit017578-23459-1510652804420-1:1:1:1:1");
        producer.send(message, DeliveryMode.NON_PERSISTENT, 3, 80000);
        System.out.println("Send to queue"  + message);

        session.close();
        connection.close();
        context.close();
    }
}
