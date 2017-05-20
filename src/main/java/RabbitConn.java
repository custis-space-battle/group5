import com.rabbitmq.client.*;
import org.omg.PortableServer.POA;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Участник on 20.05.2017.
 */
public class RabbitConn {

    private static Channel channel;

    private final static String QUEUE = "group5";
    private final static String TO_QUEUE = "to_group5";

    private static boolean needKill = false;

    public static final HashSet<Point> ship = new HashSet<>();
    public static Point lastPoint;

    public static final long mills = 3000;

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
            needKill = true;
            Thread thread = new Thread() {
                public void run() {
                    try {
                        Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
                        Matcher matcher = pattern.matcher(msg);
                        Point point = null;
                        if (matcher.find()) {
                            point = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                        }
                        Thread.sleep(mills);
                        Point hitted = Main.hitIfHitted(point);
                        System.out.println(hitted.toString());
                        ship.add(point);
//                        Main.hashSet.add(point);
                        lastPoint = point;
                        sendMessage(hitted.toString());
//                        System.out.println("point to HIT: " + hitted);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }


        if (msg.contains("fire result: MISS:")) {
            if (needKill) {
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
                            Matcher matcher = pattern.matcher(msg);
                            Point point = null;
                            if (matcher.find()) {
                                point = new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                            }
                            Thread.sleep(mills);
                            Point hitted = Main.hitIfHitted(lastPoint);
                            System.out.println(hitted.toString());
                            sendMessage(hitted.toString());
                            System.out.println("HITTIN POINT" + hitted);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            } else {
                new Thread(() -> {
                    try {
                        Thread.sleep(mills);
                        String hit = Main.hit().toString();
                        System.out.println(hit);
                        sendMessage(hit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }

        if (msg.contains("fire result: KILL")) {
            needKill = false;
            if (ship.size() != 0) {
                for (Point sheepPoint : ship) {
                    int x1 = sheepPoint.getX() + 1;
                    int x_1 = sheepPoint.getX() - 1;
                    int y1 = sheepPoint.getY() + 1;
                    int y_1 = sheepPoint.getY() - 1;
                    Main.hashSet.add(x1 + "," + sheepPoint.getY());
                    Main.hashSet.add(x1 + "," + y1);
                    Main.hashSet.add(x1 + "," + y_1);
                    Main.hashSet.add(x_1 + "," + sheepPoint.getY());
                    Main.hashSet.add(x_1 + "," + y1);
                    Main.hashSet.add(x_1 + "," + y_1);
                    Main.hashSet.add(sheepPoint.getX() + "," + y1);
                    Main.hashSet.add(sheepPoint.getX() + "," + y_1);
                }
                ship.clear();

                new Thread(() -> {
                    try {
                        Thread.sleep(mills);
                        String hit = Main.hit().toString();
                        System.out.println(hit);
                        sendMessage(hit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }


}
