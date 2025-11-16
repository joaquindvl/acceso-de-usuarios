package service;

import config.DatabaseConnection;

import dao.UsuarioDao;
import dao.CredencialAccesoDao;

import dao.jdbc.UsuarioDaoImpl;
import dao.jdbc.CredencialAccesoDaoImpl;

import entities.Usuario;
import entities.CredencialAcceso;

import java.sql.Connection;
import java.util.List;

public class UsuarioService implements GenericService<Usuario> {

    private final UsuarioDao usuarioDao = new UsuarioDaoImpl();
    private final CredencialAccesoDao credDao = new CredencialAccesoDaoImpl();

    @Override
    public void insertar(Usuario u) throws Exception {

        validar(u);

        if (u.getCredencial() == null)
            throw new Exception("El usuario debe traer una credencial asociada para crearse.");

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            // 1 → Crear usuario
            usuarioDao.crear(u, conn);

            // 2 → Configurar FK en credencial
            u.getCredencial().setUsuarioId(u.getId());

            // 3 → Verificar que el usuario no tenga otra credencial (regla 1→1)
            List<CredencialAcceso> existentes = credDao.leerTodos(conn);
            for (CredencialAcceso c : existentes) {
                if (c.getUsuarioId() == u.getId()) {
                    conn.rollback();
                    throw new Exception("El usuario ya tiene una credencial. Regla 1→1 violada.");
                }
            }

            // 4 → Crear credencial
            credDao.crear(u.getCredencial(), conn);

            conn.commit();

        } catch (Exception e) {
            throw new Exception("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Usuario u) throws Exception {

        validar(u);

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            usuarioDao.actualizar(u, conn);

            conn.commit();

        } catch (Exception e) {
            throw new Exception("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            usuarioDao.eliminar(id, conn);

            conn.commit();

        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return usuarioDao.leer(id, conn);
        }
    }

    @Override
    public List<Usuario> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return usuarioDao.leerTodos(conn);
        }
    }

    private void validar(Usuario u) throws Exception {
        if (u.getNombre() == null || u.getNombre().isBlank()) {
            throw new Exception("El nombre es obligatorio.");
        }
        if (u.getEmail() == null || u.getEmail().isBlank()) {
            throw new Exception("El email es obligatorio.");
        }
    }
}
