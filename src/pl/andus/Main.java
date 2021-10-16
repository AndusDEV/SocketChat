package pl.andus;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome!");
        System.out.println("Please select what you want to use:");
        System.out.println("client OR server");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("client")) {
            new Client();
        } else if (answer.equalsIgnoreCase("server")) {
            new Server();
        }
    }
}
