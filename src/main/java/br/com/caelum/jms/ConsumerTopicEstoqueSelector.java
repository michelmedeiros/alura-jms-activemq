package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.*;
import javax.naming.InitialContext;

import org.springframework.stereotype.Component;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
@Component
public class ConsumerTopicEstoqueSelector {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.setClientID("estoque-selector");
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Topic topic = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topic, "assinatura-selector", "ebook=false OR ebook is null", false);

        //Consumir de mensagens
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("Recebendo msg: " + textMessage.getText());
                    message.acknowledge();
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
