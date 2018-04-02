package top.linxz.jms.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String topicName = "topic-test";

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
        Destination destination = session.createTopic(topicName);

        //6
        MessageConsumer consumer = session.createConsumer(destination);

        //7监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //记得取消，不然连接耗尽
//        connection.close();
    }
}
