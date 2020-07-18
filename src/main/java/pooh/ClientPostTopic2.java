package pooh;

import java.io.*;
import java.net.Socket;

/**
 * Test producer client for class StartServer.
 */
public class ClientPostTopic2 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
             OutputStream out = socket.getOutputStream()) {
            out.write("Post/topic { topic : \"weather\", \"text\" : \"temperature +30 C\"}".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
