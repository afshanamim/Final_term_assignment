/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        HashMap<Integer, Integer[]> studentMarks = new HashMap<>();
        String[] studentNames = new String[100];
        int studentCount = 0;
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("Server is running and waiting for a client connection on port 8888...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected successfully!\n");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                try {
                    int id = dis.readInt();
                    if (id == -1) {
                        System.out.println("\nClient has disconnected. Shutting down server.");
                        break; 
                    }
                    String name = dis.readUTF();
                    int numMarks = dis.readInt();
                    Integer[] marks = new Integer[numMarks];
                    for (int i = 0; i < numMarks; i++) {
                        marks[i] = dis.readInt();
                    }
                    studentMarks.put(id, marks);
                    if (studentCount < studentNames.length) {
                        studentNames[studentCount] = name;
                        studentCount++;
                    }
                    System.out.println("--- New Student Record Received ---");
                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Marks: " + Arrays.toString(studentMarks.get(id)));
                    System.out.println("-----------------------------------\n");

                } catch (EOFException e) {
                    System.out.println("\nClient disconnected unexpectedly.");
                    break; 
                }
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }
}
