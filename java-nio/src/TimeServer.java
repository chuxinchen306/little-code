import java.io.IOException;

/**
 * @author: chenchuxin
 * @create: 2021-06-15 11:24
 **/
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException numberFormatException) {

            }
        }
        MultiplexterTimeServer multiplexterTimeServer = new MultiplexterTimeServer(port);
        // 读线程
        new Thread(multiplexterTimeServer, "Nio-MultiplexterTimeServer-001").start();
        ServerWriteHandler serverWriteHandler = new ServerWriteHandler(multiplexterTimeServer);
        // 写线程
        new Thread(serverWriteHandler, "NIO-writeServer-001").start();
    }
}
