package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RegistraTratadorNaFila {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		
		QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		QueueConnection connection = factory.createQueueConnection();
		
		Queue fila = (Queue) context.lookup("gerador");
		QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueReceiver receiver = session.createReceiver(fila);
		receiver.setMessageListener(new TratadorDeMensagem());
		
		connection.start();
		
		Scanner teclado = new Scanner(System.in);
		System.out.println("Tratador esperando as mensagens na fila JMS");
		
		teclado.nextLine();
		
		teclado.close();
		connection.close();
		
	}

}
