package server;

import java.io.*;
import java.net.*;
import static server.Cookie.getRandomCookie;

public class Server {
    public static void main(String[] args) throws NumberFormatException, IOException {
        if (args.length < 2) {
            System.out.println("Insufficient arguments, exiting program");
            System.exit(1);
        }

        System.out.println(">>> Server started");
        String serverPort = args[0];
        String cookieFile = args[1];
        
        try (ServerSocket server = new ServerSocket(Integer.parseInt(serverPort))) {
            System.out.println(">>> Server listening on port " + serverPort);
            Cookie.init(cookieFile);
            while (true) { 
                Socket socket = server.accept();
                System.out.println(">>> Client connected");
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                while (true) { 
                    String message = dis.readUTF();
                    System.out.println(">>> Client message: " + message);

                    if (message.equals("exit")) {
                        break;
                    } else if (message.equals("get-cookie")) {
                        String randomCookie = getRandomCookie();
                        dos.writeUTF("cookie_text: " + randomCookie);
                        System.out.println(">>> Sent cookie response to client");
                    } else {
                        dos.writeUTF(">>> Invalid command");
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
