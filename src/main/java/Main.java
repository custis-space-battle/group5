/**
 * Created by Участник on 20.05.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        RabbitConn.connect();
        RabbitConn.sendMessage("start:bot");
    }
}
