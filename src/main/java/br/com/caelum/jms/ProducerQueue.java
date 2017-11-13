package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.*;
import javax.naming.InitialContext;

import org.springframework.stereotype.Component;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
@Component
public class ProducerQueue {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");

        MessageProducer producer = session.createProducer(fila);

        for(int i =1; i <= 1000; i++) {
            Message message = session.createTextMessage("<pedido><id>" + i +"</id></pedido>");
            producer.send(fila, message);
            System.out.println("Send to queue"  + message);
        }

        session.close();
        connection.close();
        context.close();
    }
}
