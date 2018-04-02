package top.linxz.jms.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQConfiguration;

import javax.jms.*;

public class AppProducer {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String queueName = "queue-test";

    public static void main(String[] args) throws JMSException {
        //1
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //2
        Connection connection = connectionFactory.createConnection();

        //3
        connection.start();

        //4
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5
        Destination destination = session.createQueue(queueName);

        //6
        MessageProducer producer = session.createProducer(destination);

        //干活
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("test" + i);
            producer.send(textMessage);
            System.out.println("发送消息成功" + textMessage.getText() + "");
        }

        //记得取消，不然连接耗尽
        connection.close();
    }
}
