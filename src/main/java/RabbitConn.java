import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Участник on 20.05.2017.
 */
public class RabbitConn {

    private static Channel channel;

    private final static String QUEUE = "group5";
    private final static String TO_QUEUE = "to_group5";

    public static void connect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://group5:iJ6QFl@91.241.45.69/debug");
        Connection conn = factory.newConnection();

        channel = conn.createChannel();
        channel.queueDeclare(QUEUE, false, false, true, null);
        channel.queueBind(QUEUE, QUEUE, QUEUE);

        channel.queueDeclare(TO_QUEUE, false, false, true, null);
        channel.queueBind(TO_QUEUE, TO_QUEUE, TO_QUEUE);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    parseMsg(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        channel.basicConsume(TO_QUEUE, true, consumer);

    }

    public static void sendMessage(String message) throws IOException {
        channel.basicPublish(QUEUE, QUEUE, null, message.getBytes());
    }

    public static void parseMsg(String msg) throws InterruptedException, IOException {

        if (msg.contains("fire result: HIT:")) {
            Pattern pattern = Pattern.compile("(\\d),(\\d)");
            Matcher matcher = pattern.matcher(msg);
            if(matcher.find()) {
                Point p = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                System.out.println(p);
            }

//            new Thread(() -> {
//                try {
//                    Thread.sleep(5000);
////                    String hit = // метод
////                    System.out.println(hit);
////                    sendMessage(hit);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
        }

        if (msg.contains("fire result: MISS:")) {
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    String hit = Main.hit().toString();
                    System.out.println(hit);
                    sendMessage(hit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        if (msg.contains("fire result: KILL")) {
            System.out.println("KILL");
        }
    }


}
