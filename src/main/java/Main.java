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
        } while (hashSet.contains(str) && hashSet.size() != 100);
        hashSet.add(str);
        return new Point(str.split(";")); //todo лол )
    }

    public static Point hitIfHitted() {
        String str = null;
        do {
            str = randomCoordinate();
        } while (hashSet.contains(str) && hashSet.size() != 100);
        hashSet.add(str);
        return new Point(str.split(";")); //todo лол )
    }


    private static String randomCoordinate() {
        Random random = new Random();
        int x = random.nextInt(10) + 1;
        int y = random.nextInt(10) + 1;
        return x + ";" + y; //todo не оч хорошо :)
    }

    public static void main(String[] args) throws Exception {

        hashSet.add("1,1");
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
