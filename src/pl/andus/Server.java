package pl.andus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    String port;

    public Server() {
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        System.out.println("Write server port (Must be 5 numbers):");
        port = sc.nextLine();

        if (port.length() == 5) {
            try {
                serverSocket = new ServerSocket(Integer.parseInt(port));
                clientSocket = serverSocket.accept();
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

                Thread receive = new Thread(new Runnable() {
                    String msg;
                    @Override
                    public void run() {
                        try {
                            msg = in.readLine();
                            while (msg != null) {
                                System.out.println("Client: " + msg);
                                msg = in.readLine();
                            }

                            System.out.println("CLient disconnected!");

                            out.close();
                            clientSocket.close();
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                receive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Server();
        }
    }
}
