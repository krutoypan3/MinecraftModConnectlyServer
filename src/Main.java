import sun.rmi.runtime.Log;

import javax.net.SocketFactory;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Main {
    private static ServerSocket ss;
    public static final String waiting = "Waiting for connection...";
    public static final String connection_recieved = "Recieved connection!";
    private static final int Port = 8090;

    public static final ArrayList<String> foodList = new ArrayList<>();
    public static final ArrayList<PrintWriter> writers = new ArrayList<>();

    public static void main(String[] args) {
        getFood();
        new ServerThread().start();
        Thread sockedThread = new Thread(() -> {
            try {
                ss = new ServerSocket(Port);
                System.out.println(waiting);
                System.out.println("Socket created!");

                while (true) {
                    Socket socket = ss.accept();
                    OutputStream sos = socket.getOutputStream();

                    PrintWriter writer = new PrintWriter(sos);
                    writers.add(writer);

                    System.out.println(connection_recieved);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        sockedThread.start();
    }

    private static void getFood(){
        try(FileReader reader = new FileReader("src/resources/words.txt"))
        {
            // читаем посимвольно
            int c;
            StringBuilder fruit = new StringBuilder();
            while((c=reader.read())!=-1){
                if ((char) c == '\n') {
                    foodList.add(fruit.toString());
                    fruit = new StringBuilder("");
                }
                else {
                    fruit.append((char) c);
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}