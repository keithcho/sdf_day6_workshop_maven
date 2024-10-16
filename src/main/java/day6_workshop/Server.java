package day6_workshop;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static day6_workshop.Cookie.getRandomCookie;

public class Server {
    public static void main(String[] args) throws NumberFormatException, IOException {
        if (args.length < 2) {
            System.out.println("Insufficient arguments, exiting program");
            System.exit(1);
        }

        System.out.println(">>> Server started");
        String serverPort = args[0];
        String cookieFile = args[1];
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        
        try (ServerSocket server = new ServerSocket(Integer.parseInt(serverPort))) {
            System.out.println(">>> Server listening on port " + serverPort);
            Cookie.init(cookieFile);
            while (true) { 
                Socket socket = server.accept();
                System.out.println(">>> Client connected");
                
                ClientHandler worker = new ClientHandler(socket);
                threadPool.submit(worker);
            }
        } catch (Exception e) {
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);) {
        
            while (true) { 
                String message = dis.readUTF();
                System.out.println(">>> Client message: " + message);

                if (message.equals("exit")) {
                    System.exit(0);
                } else if (message.equals("get-cookie")) {
                    String randomCookie = getRandomCookie();
                    dos.writeUTF("cookie-text:" + randomCookie);
                    System.out.println(">>> Sent cookie response to client");
                } else {
                    dos.writeUTF(">>> Invalid command");
                }
            }
        } catch (IOException e) {

        }
    }
}
