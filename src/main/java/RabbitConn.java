import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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


        String message = "start:bot";
        channel.basicPublish(QUEUE, QUEUE, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }


}
