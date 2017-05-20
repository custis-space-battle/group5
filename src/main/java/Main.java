import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Участник on 20.05.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        RabbitConn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Boolean isExist = true;
        while (isExist) {
            String temp = reader.readLine();
            if (temp.equals("exit")) {
                isExist = false;
            } else {
                RabbitConn.sendMessage(temp);
            }
        }
    }
}
