import lombok.SneakyThrows;
import org.testng.annotations.Test;
import ru.ancap.pkpt.api.server.Client;
import ru.ancap.pkpt.api.server.Server;
import ru.ancap.pkpt.api.util.FreePort;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestMessaging {

    @Test(threadPoolSize = 12, invocationCount = 12, timeOut = 1000)
    public void test() throws InterruptedException {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        int serverPort = new FreePort().get();
        this.server(serverPort);
        this.client(serverPort, queue);
        assert queue.take().equals("Host is fine!");
    }

    @SneakyThrows
    public void server(int port) {
        new Server(
                new ServerSocket(port),
                "secret-word",
                (author, message) -> {
                    if (message.getCommand().nextArgument("How are you?")) {
                        author.say("I'm fine!");
                    }
                }
        ).run();
    }

    @SneakyThrows
    public void client(int serverPort, BlockingQueue<String> queue) {
        new Client(
                new Socket("localhost", serverPort),
                "secret-word"
        ).ask(
                "How are you?",
                (reply) -> {
                    if (reply.nextArgument("I'm fine!")) {
                        queue.add("Host is fine!");
                        return;
                    }
                    if (reply.nextArgument("Things are bad...")) {
                        queue.add("Host is fine!");
                        return;
                    }
                    queue.add("Something happened");
                }
        );
    }

}
