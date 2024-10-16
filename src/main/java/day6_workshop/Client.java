package day6_workshop;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
                if (serverResponse.contains("cookie-text")) {
                    String[] responseArr = serverResponse.split(":");
                    if (responseArr.length > 1) {
                        System.out.println("Cookie from the server: " + responseArr[1]);
                    } else {
                        System.err.println("Invalid cookie format received from server.");
                    }
                } else {
                    System.err.println(serverResponse);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
