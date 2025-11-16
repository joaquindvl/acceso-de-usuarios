package main;

import config.DatabaseConnection;
import dao.jdbc.UsuarioDaoImpl;
import dao.jdbc.CredencialAccesoDaoImpl;
import entities.Usuario;
import entities.CredencialAcceso;

import java.sql.Connection;

public class TestDao {

    public static void main(String[] args) {

        UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
        CredencialAccesoDaoImpl credDao = new CredencialAccesoDaoImpl();

        try (Connection conn = DatabaseConnection.getConnection()) {

            // IMPORTANTE: transacción externa
            conn.setAutoCommit(false);

            // 1. Crear usuario
            Usuario u = new Usuario();
            u.setNombre("Test DAO");
            u.setEmail("dao@example.com");
            u.setEliminado(false);

            usuarioDao.crear(u, conn);

            System.out.println("Usuario creado con ID: " + u.getId());


            // 2. Crear credencial asociada
            CredencialAcceso c = new CredencialAcceso();
            c.setUsername("userdao");
            c.setPassword("pass123");
            c.setEliminado(false);
            c.setUsuarioId(u.getId());  // FK

            credDao.crear(c, conn);

            System.out.println("Credencial creada con ID: " + c.getId());


            // 3. Leer usuario
            Usuario leido = usuarioDao.leer(u.getId(), conn);
            System.out.println("Leído: " + leido);


            // 4. Leer credencial
            CredencialAcceso leida = credDao.leer(c.getId(), conn);
            System.out.println("Credencial leída: " + leida);


            // 5. Actualizar usuario
            u.setNombre("Test DAO Updated");
            usuarioDao.actualizar(u, conn);
            System.out.println("Usuario actualizado.");


            // 6. Baja lógica
            usuarioDao.eliminar(u.getId(), conn);
            System.out.println("Usuario eliminado lógicamente.");

            conn.commit();
            System.out.println("TRANSACCIÓN COMMIT ✔");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
