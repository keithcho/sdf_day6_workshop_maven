package client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Insufficient arguments, exiting program");
            return;
        }

        String[] addrArray = args[0].split(":");
        if (addrArray.length != 2) {
            System.err.println("Invalid server address format. Use <server:port>");
            return;
        }
        String hostName = addrArray[0];
        int portNo;
        try {
            portNo = Integer.parseInt(addrArray[1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number.");
            return;
        }

        try (Socket socket = new Socket(hostName, portNo)) {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            Console console = System.console();
            while (true) {
                String userInput = console.readLine("Enter a command: ");
                dos.writeUTF(userInput);
                dos.flush();

                String serverResponse = dis.readUTF();
                System.out.println(serverResponse);
            }
        } catch (Exception e) {
            
        }
    }
}
