package dao.jdbc;

import dao.CredencialAccesoDao;
import entities.CredencialAcceso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredencialAccesoDaoImpl implements CredencialAccesoDao {

    @Override
    public void crear(CredencialAcceso c, Connection conn) throws Exception {
        String sql = "INSERT INTO credencial_acceso (username, password, eliminado, usuario_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getUsername());
            ps.setString(2, c.getPassword());
            ps.setBoolean(3, c.isEliminado());
            ps.setLong(4, c.getUsuarioId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public CredencialAcceso leer(long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM credencial_acceso WHERE id = ? AND eliminado = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CredencialAcceso c = new CredencialAcceso();
                c.setId(rs.getLong("id"));
                c.setUsername(rs.getString("username"));
                c.setPassword(rs.getString("password"));
                c.setEliminado(rs.getBoolean("eliminado"));
                c.setUsuarioId(rs.getLong("usuario_id"));
                return c;
            }
        }
        return null;
    }

    @Override
    public List<CredencialAcceso> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM credencial_acceso WHERE eliminado = FALSE";

        List<CredencialAcceso> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CredencialAcceso c = new CredencialAcceso();
                c.setId(rs.getLong("id"));
                c.setUsername(rs.getString("username"));
                c.setPassword(rs.getString("password"));
                c.setEliminado(rs.getBoolean("eliminado"));
                c.setUsuarioId(rs.getLong("usuario_id"));
                lista.add(c);
            }
        }
        return lista;
    }

    @Override
    public void actualizar(CredencialAcceso c, Connection conn) throws Exception {
        String sql = "UPDATE credencial_acceso SET username = ?, password = ?, eliminado = ?, usuario_id = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getUsername());
            ps.setString(2, c.getPassword());
            ps.setBoolean(3, c.isEliminado());
            ps.setLong(4, c.getUsuarioId());
            ps.setLong(5, c.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws Exception {
        String sql = "UPDATE credencial_acceso SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
