import java.io.*;
import java.net.Socket;
import java.util.Random;

public class ServerThread extends Thread {

    final Random random = new Random();

    public void run() {
        try {
            while (true) {
                String currentFruit = Main.foodList.get(random.nextInt(Main.foodList.size()));
                if (!Main.writers.isEmpty()) {
                    PrintWriter writer = Main.writers.get(random.nextInt(Main.writers.size()));
                    writer.println(currentFruit);
                    System.out.println("Message sent to random user");
                    writer.flush();
                }
                sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}