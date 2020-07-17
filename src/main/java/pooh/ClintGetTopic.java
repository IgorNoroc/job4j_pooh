package pooh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClintGetTopic {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
             var out = socket.getOutputStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.write("Get/topic { topic : \"weather\", \"text\" : \"temperature +17 C\"}".getBytes());
            String line = in.readLine();
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
