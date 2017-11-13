package br.com.caelum.jms;

import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.InitialContext;

/**
 * Created by Michel Medeiros on 07/11/2017.
 */
@Component
public class ProducerTopic {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topic = (Destination) context.lookup("loja");

        MessageProducer producer = session.createProducer(topic);

        for(int i =1; i <= 5; i++) {
            //Definindo propriedades de seletor
            Message message = session.createTextMessage("<pedido><id>" + i +"</id><ebook>true</ebook></pedido>");
            message.setBooleanProperty("ebook", true);

            //Sem definir propriedades
//            Message message = session.createTextMessage("<pedido><id>" + i +"</id></pedido>");

            producer.send(topic, message);
            System.out.println("Send to queue"  + message);
        }

        session.close();
        connection.close();
        context.close();
    }
}
