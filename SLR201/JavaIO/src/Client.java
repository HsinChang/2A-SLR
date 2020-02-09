import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static final String IP_ADDR = "localhost";
    public static final int PORT = 8081;

    public static void main(String[] args) {
        while (true) {
            Socket socket = null;
            try {

                socket = new Socket(IP_ADDR, PORT);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.print("Veuillez saisir la message:\t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(str);

                String ret = input.readUTF();
                System.out.println("Message répondu: " + ret);

                out.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
