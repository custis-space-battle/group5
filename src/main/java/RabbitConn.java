import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Участник on 20.05.2017.
 */
public class RabbitConn {

    private final static String QUEUE = "group5";

    public static void connect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://group5:iJ6QFl@91.241.45.69/debug");
        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE, false, false, true, null);
        channel.queueBind(QUEUE, QUEUE, QUEUE);


        String message = "start:BOT";
        channel.basicPublish(QUEUE, QUEUE, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        channel.basicConsume("to_group5", true, consumer);

    }
}
