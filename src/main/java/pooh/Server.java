package pooh;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private volatile static Storage storage = new Storage();
    private static ExecutorService service = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public static void main(String[] args) throws InterruptedException {
        try (ServerSocket server = new ServerSocket(5555)) {
            while (!service.isTerminated()) {
                Socket socket = server.accept();
                service.execute(
                        () -> {
                            try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                 var out = socket.getOutputStream()) {
                                StringBuilder sb = new StringBuilder();
                                while (in.ready()) {
                                    sb.append(in.readLine());
                                    System.out.println(sb.toString());
                                }
                                String answer = action(sb);
                                out.write(answer.getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String action(StringBuilder sb) {
        String rsl = "";
        String line = sb.toString();
        if (line.contains("Post/queue")) {
            storage.addToQueue(
                    line.split(":")[1].split(",")[0],
                    line.split(":")[2]
            );
        } else if (line.contains("Get/queue")) {
            rsl = storage.getFromQueue(
                    line.split(":")[1].split(",")[0]
            );
        } else if (line.contains("Post/topic")) {
            storage.postTopic(
                    line.split(":")[1].split(",")[0],
                    line.split(":")[2]
            );
        } else if (line.contains("Get/topic")) {
            rsl = storage.getFromTopic(
                    line.split(":")[1].split(",")[0]
            );
        }
        return rsl;
    }
}
