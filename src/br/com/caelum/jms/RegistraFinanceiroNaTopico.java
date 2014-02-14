package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RegistraFinanceiroNaTopico {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		
		TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
		TopicConnection connection = factory.createTopicConnection();
		connection.setClientID("Financeiro");
		
		Topic topic = (Topic) context.lookup("livraria");
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		TopicSubscriber subscriber = session.createDurableSubscriber(topic, "AssinaturaDeNotas");
		subscriber.setMessageListener(new TratadorDeMensagem());
		
		connection.start();
		
		Scanner teclado = new Scanner(System.in);
		System.out.println("Financeiro esperando as mensagens na fila JMS");
		
		teclado.nextLine();
		
		teclado.close();
		connection.close();
		
	}

}
