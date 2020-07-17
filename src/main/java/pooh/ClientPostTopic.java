package pooh;

import java.io.OutputStream;
import java.net.Socket;

public class ClientPostTopic {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
            OutputStream out = socket.getOutputStream()) {
            out.write("Post/topic { topic : \"weather\", \"text\" : \"temperature +18 C\"}".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
