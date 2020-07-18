package pooh;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MultiThreading SocketServer.
 * task : Техническое задание - проект "Pooh JMS" [#318329].
 */
public class Server {
    private final Queues queues = new Queues();
    private final Topics topics = new Topics();
    private final ExecutorService service = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * When create instance of this class,
     * creating new ServerSocket.
     *
     * @param port port.
     */
    public Server(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                service.execute(
                        () -> {
                            System.out.println(Thread.currentThread().getName() + " was connected");
                            try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                 var out = socket.getOutputStream()) {
                                String str;
                                String answer = "";
                                do {
                                    str = in.readLine();
                                    if (containsRequest(str)) {
                                        answer = action(str);
                                    }
                                } while (in.ready());
                                out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
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

    /**
     * Checking if request contains command.
     *
     * @param str line.
     * @return true or false.
     */
    private boolean containsRequest(String str) {
        return str.contains("Post/queue")
                || str.contains("Get/queue")
                || str.contains("Post/topic")
                || str.contains("Get/topic");
    }

    /**
     * Request processing
     *
     * @param request request.
     * @return text or nothing.
     */
    private String action(String request) {
        String rsl = "";
        if (request.contains("Post/queue")) {
            queues.addToQueue(
                    request.split(":")[1].split(",")[0],
                    request.split(":")[2]
            );
        } else if (request.contains("Get/queue")) {
            rsl = queues.getFromQueue(
                    request.split(":")[1].split(",")[0]
            );
        } else if (request.contains("Post/topic")) {
            topics.postTopic(
                    request.split(":")[1].split(",")[0],
                    request.split(":")[2]
            );
        } else if (request.contains("Get/topic")) {
            rsl = topics.getFromTopic(
                    request.split(":")[1].split(",")[0]
            );
        }
        return rsl;
    }
}
