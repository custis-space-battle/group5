import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Участник on 20.05.2017.
 */
public class Main {

    public static HashSet<String> hashSet = new HashSet<>();

    public static Point hit() {
        String str = null;
        do {
            str = randomCoordinate();
        } while (hashSet.contains(str) && hashSet.size() != 144);
        System.out.println("SIZE: " + hashSet.size());
        hashSet.add(str);
        return new Point(str.split(",")); //todo лол )
    }

    public static Point hitIfHitted(Point point) {
        System.out.println("input point" + point);

        if (RabbitConn.target != null) {
            if (RabbitConn.target.equals('y')) {
                if (!hashSet.contains((point.getX() - 1) + "," + point.getY())) {
                    hashSet.add((point.getX() - 1) + "," + point.getY());
                    System.out.println("returned point1" + new Point(point.getX() - 1, point.getY()));
                    return new Point(point.getX() - 1, point.getY());

                } else if ((!hashSet.contains(point.getX() + 1 + "," + point.getY()))) {
                    hashSet.add(point.getX() + 1 + "," + point.getY());
                    System.out.println("returned point3 " + new Point((point.getX() + 1), point.getY()));
                    return new Point((point.getX() + 1), point.getY());

                }
            }

            if (RabbitConn.target.equals('x')) {
                if ((!hashSet.contains(point.getX() + "," + (point.getY() + 1)))) {
                    hashSet.add((point.getX() + "," + (point.getY() + 1)));
                    System.out.println("returned point2 " + new Point(point.getX(), point.getY() + 1));
                    return new Point(point.getX(), point.getY() + 1);

                } else if ((!hashSet.contains(point.getX() + "," + (point.getY() - 1)))) {
                    hashSet.add((point.getX() + "," + (point.getY() - 1)));
                    System.out.println("returned point4 " + new Point((point.getX()), point.getY() - 1));
                    return new Point((point.getX()), point.getY() - 1);
                }
            }
        }

        if (!hashSet.contains((point.getX() - 1) + "," + point.getY())) {
            hashSet.add((point.getX() - 1) + "," + point.getY());
            System.out.println("returned point1" + new Point(point.getX() - 1, point.getY()));
            return new Point(point.getX() - 1, point.getY());

        } else if ((!hashSet.contains(point.getX() + "," + (point.getY() + 1)))) {
            hashSet.add((point.getX() + "," + (point.getY() + 1)));
            System.out.println("returned point2 " + new Point(point.getX(), point.getY() + 1));
            return new Point(point.getX(), point.getY() + 1);

        } else if ((!hashSet.contains(point.getX() + 1 + "," + point.getY()))) {
            hashSet.add(point.getX() + 1 + "," + point.getY());
            System.out.println("returned point3 " + new Point((point.getX() + 1), point.getY()));
            return new Point((point.getX() + 1), point.getY());

        } else if ((!hashSet.contains(point.getX() + "," + (point.getY() - 1)))) {
            hashSet.add((point.getX() + "," + (point.getY() - 1)));
            System.out.println("returned point4 " + new Point((point.getX()), point.getY() - 1));
            return new Point((point.getX()), point.getY() - 1);
        }
        return null;
    }


    private static String randomCoordinate() {
        Random random = new Random();
        int x = random.nextInt(10) + 1;
        int y = random.nextInt(10) + 1;
        return x + "," + y; //todo не оч хорошо :)
    }

    public static void main(String[] args) throws Exception {

        hashSet.add("1,1");
        for (int i = 0; i <= 11; i++) {
            hashSet.add(i + "," + 0);
            hashSet.add(i + "," + 11);
            hashSet.add(0 + "," + i);
            hashSet.add(11 + "," + i);
        }


        RabbitConn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Boolean isExist = true;
        while (true) {
            String temp = reader.readLine();
            if (temp.equals("exit")) {
                break;
            } else {
                if (temp.contains("fire")) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(hit());
                    }
                }
                RabbitConn.sendMessage(temp);
            }
        }

        reader.close();
    }
}
