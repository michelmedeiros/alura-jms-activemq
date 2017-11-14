package br.com.caelum.jms;

import java.util.Enumeration;
import java.util.Scanner;

import javax.jms.*;
import javax.naming.InitialContext;

import org.springframework.stereotype.Component;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
@Component
public class ConsumerQueueDLQ {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Destination fila = (Destination) context.lookup("DLQ");
        MessageConsumer consumer = session.createConsumer(fila );
        //Consumir de mensagens
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println(message);
                    session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        new Scanner(System.in).nextLine();
        session.close();
        connection.close();
        context.close();
    }
}
