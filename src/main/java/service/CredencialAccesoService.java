package service;

import config.DatabaseConnection;
import dao.CredencialAccesoDao;
import dao.jdbc.CredencialAccesoDaoImpl;
import entities.CredencialAcceso;

import java.sql.Connection;
import java.util.List;

public class CredencialAccesoService implements GenericService<CredencialAcceso> {

    private final CredencialAccesoDao credDao = new CredencialAccesoDaoImpl();

    @Override
    public void insertar(CredencialAcceso c) throws Exception {

        validar(c);

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            credDao.crear(c, conn);

            conn.commit();

        } catch (Exception e) {
            throw new Exception("Error al insertar credencial: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(CredencialAcceso c) throws Exception {

        validar(c);

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            credDao.actualizar(c, conn);

            conn.commit();
        } catch (Exception e) {
            throw new Exception("Error al actualizar credencial: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            credDao.eliminar(id, conn);

            conn.commit();
        } catch (Exception e) {
            throw new Exception("Error al eliminar credencial: " + e.getMessage(), e);
        }
    }

    @Override
    public CredencialAcceso getById(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return credDao.leer(id, conn);
        }
    }

    @Override
    public List<CredencialAcceso> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return credDao.leerTodos(conn);
        }
    }

    private void validar(CredencialAcceso c) throws Exception {
        if (c.getUsername() == null || c.getUsername().isBlank()) {
            throw new Exception("El username es obligatorio");
        }
        if (c.getPassword() == null || c.getPassword().isBlank()) {
            throw new Exception("El password es obligatorio");
        }
        if (c.getUsuarioId() == null) {
            throw new Exception("Credencial debe tener usuarioId asociado.");
        }
    }
}
