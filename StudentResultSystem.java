/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 
*/

package studentresultsystem;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StudentResultSystem {
    static final String DB_URL = "jdbc:mysql://localhost:3306/StudentDB";
    static final String USER = "root"; 
    static final String PASS = "afshanamim";
    public static void main(String[] args) {
        String[] studentNames = {"Afshana Mim", "Mimika Ferdous", "Sadia Priya", "Sharmin Rotna"};
        HashMap<Integer, Integer[]> studentMarks = new HashMap<>();
        studentMarks.put(1, new Integer[]{89, 90, 82}); 
        studentMarks.put(2, new Integer[]{85, 95, 90}); 
        studentMarks.put(3, new Integer[]{75, 99, 93});
        studentMarks.put(4, new Integer[]{95, 89, 87});
        System.out.println("Student Results");
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement clearStmt = conn.createStatement();
            clearStmt.executeUpdate("TRUNCATE TABLE Students");
            String insertSQL = "INSERT INTO Students (id, name, mark1, mark2, mark3, total, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            for (Map.Entry<Integer, Integer[]> entry : studentMarks.entrySet()) {
                int id = entry.getKey();
                Integer[] marks = entry.getValue();
                String name = studentNames[id - 1]; 
                int mark1 = marks[0];
                int mark2 = marks[1];
                int mark3 = marks[2];
                int total = mark1 + mark2 + mark3;
                String grade = calculateGrade(total);
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setInt(3, mark1);
                pstmt.setInt(4, mark2);
                pstmt.setInt(5, mark3);
                pstmt.setInt(6, total);
                pstmt.setString(7, grade);
                pstmt.executeUpdate();
                System.out.println("Inserting data for: " + name);
            }
            System.out.println("\n--- Fetching Records from Database ---");
            displayAllRecords(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static String calculateGrade(int totalMarks) {
        double percentage = (totalMarks / 300.0) * 100;
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }
    private static void displayAllRecords(Connection conn) throws SQLException {
        String query = "SELECT * FROM Students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.printf("%-5s | %-15s | %-5s | %-5s | %-5s | %-5s | %-5s%n", 
        "ID", "Name", "Mark1", "Mark2", "Mark3", "Total", "Grade");
        System.out.println("-------------------------------------------------------------------");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int m1 = rs.getInt("mark1");
            int m2 = rs.getInt("mark2");
            int m3 = rs.getInt("mark3");
            int total = rs.getInt("total");
            String grade = rs.getString("grade");
            System.out.printf("%-5d | %-15s | %-5d | %-5d | %-5d | %-5d | %-5s%n", 
                       id, name, m1, m2, m3, total, grade);
        }
    }
}
