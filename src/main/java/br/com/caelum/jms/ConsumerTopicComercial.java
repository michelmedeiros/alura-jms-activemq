package br.com.caelum.jms;

import br.com.caelum.model.Pedido;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
@Component
public class ConsumerTopicComercial {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.setClientID("comercial");
        connection.start();
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Topic topic = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topic, "assinatura" );

        //Consumir de mensagens
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                ObjectMessage pedido = (ObjectMessage) message;
                try {
                    System.out.println("Recebendo msg: " + pedido.getObject());
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
