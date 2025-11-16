package entities;

public class CredencialAcceso {

    private Long id;
    private String username;
    private String password;
    private boolean eliminado;

    // NECESARIO para la FK en la base de datos
    private Long usuarioId;

    public CredencialAcceso() {}

    public CredencialAcceso(Long id, String username, String password, boolean eliminado, Long usuarioId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.eliminado = eliminado;
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "CredencialAcceso{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", eliminado=" + eliminado +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
