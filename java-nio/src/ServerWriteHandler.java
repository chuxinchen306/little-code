import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author: chenchuxin
 * @create: 2021-06-17 17:44
 **/
public class ServerWriteHandler implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private MultiplexterTimeServer multiplexterTimeServer;

    public ServerWriteHandler(MultiplexterTimeServer multiplexterTimeServer) {
        this.multiplexterTimeServer = multiplexterTimeServer;
    }

    @Override
    public void run() {
        while (!multiplexterTimeServer.isStop()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (SelectionKey selectionKey : multiplexterTimeServer.getSelector().keys()) {
                try {
                    if (selectionKey.channel() instanceof SocketChannel) {

                        doWrite((SocketChannel) selectionKey.channel());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MultiplexterTimeServer getMultiplexterTimeServer() {
        return multiplexterTimeServer;
    }

    public void setMultiplexterTimeServer(MultiplexterTimeServer multiplexterTimeServer) {
        this.multiplexterTimeServer = multiplexterTimeServer;
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        String s = "";
        while (!s.equals("q")) {

            s = scanner.nextLine();
            if (!s.equals("q")) {
                byte[] bytes = s.getBytes();
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            }
        }


    }
}
