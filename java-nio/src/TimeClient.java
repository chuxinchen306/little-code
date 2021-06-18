/**
 * @author: chenchuxin
 * @create: 2021-06-15 17:10
 **/
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        TimeClientHandler timeClientHandler = new TimeClientHandler("127.0.0.1", port);
        new Thread(timeClientHandler, "TimeClient-001").start();
        new Thread(new ClientWriter(timeClientHandler), "ClientWriter-002").start();
    }
}
