package pooh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Test consumer client for class Server.
 */
public class ClintGetTopic {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.write("Get/topic { topic : \"weather\", \"text\" : \"temperature +17 C\"}");
            out.newLine();
            out.flush();
            String str;
            do {
                str = in.readLine();
                System.out.println(str);
            } while (in.ready());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
