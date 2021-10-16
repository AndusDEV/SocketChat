package pl.andus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    String ip;
    String port;

    public Client() {
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        System.out.println("Write IP of server:");
        ip = sc.nextLine();
        System.out.println("Write port of server:");
        port = sc.nextLine();

        if (!ip.isEmpty() && !port.isEmpty()) {
            try {
                clientSocket = new Socket(ip, Integer.parseInt(port));
                out = new PrintWriter(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Thread sender = new Thread(new Runnable() {
                    String msg;
                    @Override
                    public void run() {
                        while(true) {
                            msg = sc.nextLine();
                            out.println(msg);
                            out.flush();
                        }
                    }
                });
                sender.start();

                Thread receiver = new Thread(new Runnable() {
                    String msg;
                    @Override
                    public void run() {
                        try {
                            msg = in.readLine();
                            while(msg != null) {
                                System.out.println("Server: " + msg);
                                msg = in.readLine();
                            }
                            System.out.println("Server out of service");
                            out.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                receiver.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Client();
        }
    }
}
