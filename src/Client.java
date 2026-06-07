/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the Student Info Server.");
            while (true) {
                System.out.print("Enter Student ID (or enter -1 to exit): ");
                int id = scanner.nextInt();
                scanner.nextLine();
                dos.writeInt(id);
                if (id == -1) break;
                System.out.print("Enter Student Name: ");
                String name = scanner.nextLine();
                dos.writeUTF(name);
                System.out.print("How many subjects/marks are you entering?: ");
                int numMarks = scanner.nextInt();
                dos.writeInt(numMarks);
                for (int i = 0; i < numMarks; i++) {
                    System.out.print("Enter mark for subject " + (i + 1) + ": ");
                    dos.writeInt(scanner.nextInt());
                }
                dos.flush();
                System.out.println(">> Data sent to server successfully!\n");
            }
        } catch (IOException e) {
            System.err.println("Client Error (Make sure the server is running first!): " + e.getMessage());
        }
    }
}