package main;

import config.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Conexión establecida correctamente.");
        } catch (Exception e) {
            System.out.println("Error de conexión:");
            e.printStackTrace();
        }
    }
}
