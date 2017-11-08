package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
public class Consumer {
    public static void main(String[] args) throws NamingException, JMSException {
        /*
         */


        InitialContext initialContext = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) initialContext.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);
        Message receive = consumer.receive();
        System.out.println("Recebendo mensagem: " + receive);
        connection.close();
        initialContext.close();
    }
}
