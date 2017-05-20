import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Участник on 20.05.2017.
 */
public class Main {

    public static String hit() {

        Random random = new Random();
        int x = random.nextInt(10) + 1;
        int y = random.nextInt(10) + 1;


        System.out.println(x + " " + y);

        return null;

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
                    while (true) hit();
                }
                RabbitConn.sendMessage(temp);
            }
        }


        reader.close();
    }
}
