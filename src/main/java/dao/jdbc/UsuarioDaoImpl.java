package dao.jdbc;

import dao.UsuarioDao;
import entities.Usuario;
import entities.CredencialAcceso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements UsuarioDao {

    @Override
    public void crear(Usuario usuario, Connection conn) throws Exception {
        String sql = "INSERT INTO usuario (nombre, email, eliminado) VALUES (?, ?, FALSE)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public Usuario leer(long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM usuario WHERE id = ? AND eliminado = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setEliminado(rs.getBoolean("eliminado"));

                // Cargar credencial asociada 1→1
                CredencialAcceso cred = obtenerCredencialPorUsuario(u.getId(), conn);
                u.setCredencial(cred);

                return u;
            }
            return null;
        }
    }

    @Override
    public List<Usuario> leerTodos(Connection conn) throws Exception {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario WHERE eliminado = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setEliminado(rs.getBoolean("eliminado"));

                // Cargar credencial asociada 1→1
                CredencialAcceso cred = obtenerCredencialPorUsuario(u.getId(), conn);
                u.setCredencial(cred);

                lista.add(u);
            }
        }

        return lista;
    }

    @Override
    public void actualizar(Usuario usuario, Connection conn) throws Exception {
        String sql = "UPDATE usuario SET nombre = ?, email = ? WHERE id = ? AND eliminado = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setLong(3, usuario.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws Exception {

        // 1) Eliminar credencial (baja lógica)
        String sqlCred = "UPDATE credencial_acceso SET eliminado = TRUE WHERE usuario_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlCred)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }

        // 2) Eliminar usuario (baja lógica)
        String sqlUser = "UPDATE usuario SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // ============================================================
    // MÉTODO PRIVADO PARA CARGAR LA CREDENCIAL POR usuario_id
    // ============================================================
    private CredencialAcceso obtenerCredencialPorUsuario(long userId, Connection conn) throws Exception {

        String sql = "SELECT id, username, password, eliminado, usuario_id " +
                "FROM credencial_acceso " +
                "WHERE usuario_id = ? AND eliminado = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CredencialAcceso c = new CredencialAcceso();
                c.setId(rs.getLong("id"));
                c.setUsername(rs.getString("username"));
                c.setPassword(rs.getString("password"));
                c.setEliminado(rs.getBoolean("eliminado"));
                c.setUsuarioId(rs.getLong("usuario_id")); // ✔ valor real, no userId fijo
                return c;
            }
        }

        return null;
    }
}
