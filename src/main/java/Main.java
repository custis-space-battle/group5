import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Участник on 20.05.2017.
 */
public class Main {

    public static HashSet<String> hashSet = new HashSet<>();

    public static String hit() {

        String str = null;
        do {
            str = randomCoordinate();
        } while (hashSet.contains(str) && hashSet.size() != 100);

        hashSet.add(str);
        return str;
    }

    private static String randomCoordinate() {
        Random random = new Random();
        int x = random.nextInt(2) + 1;
        int y = random.nextInt(2) + 1;
        return x + "," + y;
    }



    public static void main(String[] args) throws Exception {

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
                        hit();
                    }
                    System.out.println(hashSet);
                }
                RabbitConn.sendMessage(temp);
            }
        }


        reader.close();
    }
}
