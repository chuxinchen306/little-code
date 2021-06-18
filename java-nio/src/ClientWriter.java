import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author: chenchuxin
 * @create: 2021-06-18 10:00
 **/
public class ClientWriter implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private TimeClientHandler timeClientHandler;

    public ClientWriter(TimeClientHandler timeClientHandler) {
        this.timeClientHandler = timeClientHandler;
    }

    @Override
    public void run() {
        while (!timeClientHandler.isStop()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                doWrite(timeClientHandler.getSocketChannel());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public TimeClientHandler getTimeClientHandler() {
        return timeClientHandler;
    }

    public void setTimeClientHandler(TimeClientHandler timeClientHandler) {
        this.timeClientHandler = timeClientHandler;
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        String s = "";
        while (!s.equals("q")) {


            s = scanner.nextLine();
            if (!s.equals("q")) {
                byte[] req = s.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                writeBuffer.put(req);
                writeBuffer.flip();
                socketChannel.write(writeBuffer);
            }
        }


    }
}
