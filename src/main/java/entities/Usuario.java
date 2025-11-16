package entities;

public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private boolean eliminado;

    // Relación 1→1 unidireccional
    private CredencialAcceso credencial;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String email, boolean eliminado, CredencialAcceso credencial) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.eliminado = eliminado;
        this.credencial = credencial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public CredencialAcceso getCredencial() {
        return credencial;
    }

    public void setCredencial(CredencialAcceso credencial) {
        this.credencial = credencial;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", eliminado=" + eliminado +
                ", credencial=" + credencial +
                '}';
    }
}
