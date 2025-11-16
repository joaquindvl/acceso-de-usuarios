package main;

import entities.Usuario;
import entities.CredencialAcceso;
import service.UsuarioService;
import service.CredencialAccesoService;

import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final Scanner sc = new Scanner(System.in);
    private final UsuarioService usuarioService = new UsuarioService();
    private final CredencialAccesoService credService = new CredencialAccesoService();

    public void iniciar() {

        String opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1) Gestión de Usuarios");
            System.out.println("2) Gestión de Credenciales");
            System.out.println("0) Salir");

            System.out.print("Seleccione opción: ");
            opcion = sc.nextLine().trim().toUpperCase();

            switch (opcion) {

                case "1":
                    menuUsuarios();
                    break;
                case "2":
                    menuCredenciales();
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (!opcion.equals("0"));
    }

    /* ========================================================
                      MENÚ USUARIO (A)
       ======================================================== */
    private void menuUsuarios() {
        String op;
        do {
            System.out.println("\n===== GESTIÓN DE USUARIOS =====");
            System.out.println("1) Crear Usuario");
            System.out.println("2) Leer por ID");
            System.out.println("3) Listar todos");
            System.out.println("4) Actualizar");
            System.out.println("5) Eliminar");
            System.out.println("6) Buscar por Email");
            System.out.println("0) Volver");

            System.out.print("Opción: ");
            op = sc.nextLine().trim().toUpperCase();

            try {
                switch (op) {
                    case "1":
                        crearUsuario();
                        break;
                    case "2":
                        leerUsuario();
                        break;
                    case "3":
                        listarUsuarios();
                        break;
                    case "4":
                        actualizarUsuario();
                        break;
                    case "5":
                        eliminarUsuario();
                        break;
                    case "6":
                        buscarUsuarioPorEmail();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

        } while (!op.equals("0"));
    }

    private void crearUsuario() throws Exception {
        System.out.println("\n--- Crear Usuario ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.println("=== Datos Credencial ===");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);

        CredencialAcceso c = new CredencialAcceso();
        c.setUsername(username);
        c.setPassword(password);

        u.setCredencial(c);

        usuarioService.insertar(u);

        System.out.println("✔ Usuario creado con ID: " + u.getId());
    }

    private void leerUsuario() throws Exception {
        long id = pedirId("ID Usuario");
        Usuario u = usuarioService.getById(id);

        if (u == null) {
            System.out.println("❌ Usuario no encontrado.");
            return;
        }

        System.out.println("Usuario encontrado: " + u);
    }

    private void listarUsuarios() throws Exception {
        System.out.println("\n--- Lista de Usuarios ---");
        List<Usuario> lista = usuarioService.getAll();
        lista.forEach(System.out::println);
    }

    private void actualizarUsuario() throws Exception {
        long id = pedirId("ID Usuario a actualizar");
        Usuario u = usuarioService.getById(id);

        if (u == null) {
            System.out.println("❌ Usuario no existe.");
            return;
        }

        System.out.println("Nombre actual: " + u.getNombre());
        System.out.print("Nuevo nombre (enter = no cambiar): ");
        String nuevoNombre = sc.nextLine().trim();
        if (!nuevoNombre.isBlank()) u.setNombre(nuevoNombre);

        System.out.println("Email actual: " + u.getEmail());
        System.out.print("Nuevo email (enter = no cambiar): ");
        String nuevoEmail = sc.nextLine().trim();
        if (!nuevoEmail.isBlank()) u.setEmail(nuevoEmail);

        usuarioService.actualizar(u);

        System.out.println("✔ Usuario actualizado.");
    }

    private void eliminarUsuario() throws Exception {
        long id = pedirId("ID Usuario a eliminar");
        usuarioService.eliminar(id);
        System.out.println("✔ Usuario eliminado (baja lógica).");
    }

    private void buscarUsuarioPorEmail() throws Exception {
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        List<Usuario> lista = usuarioService.getAll();
        boolean encontrado = false;

        for (Usuario u : lista) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("✔ Usuario encontrado: " + u);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("❌ No existe usuario con ese email.");
        }
    }

    /* ========================================================
                     MENÚ CREDENCIAL (B)
       ======================================================== */

    private void menuCredenciales() {
        String op;
        do {
            System.out.println("\n===== GESTIÓN DE CREDENCIALES =====");
            System.out.println("1) Crear");
            System.out.println("2) Leer por ID");
            System.out.println("3) Listar todos");
            System.out.println("4) Actualizar");
            System.out.println("5) Eliminar");
            System.out.println("0) Volver");

            System.out.print("Opción: ");
            op = sc.nextLine().trim().toUpperCase();

            try {
                switch (op) {
                    case "1":
                        crearCredencial();
                        break;
                    case "2":
                        leerCredencial();
                        break;
                    case "3":
                        listarCredenciales();
                        break;
                    case "4":
                        actualizarCredencial();
                        break;
                    case "5":
                        eliminarCredencial();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

        } while (!op.equals("0"));
    }

    private void crearCredencial() throws Exception {
        System.out.println("\n--- Crear Credencial ---");

        long userId = pedirId("ID Usuario existente para asociar");

        Usuario u = usuarioService.getById(userId);
        if (u == null) {
            System.out.println("❌ Usuario no existe.");
            return;
        }

        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        CredencialAcceso c = new CredencialAcceso();
        c.setUsername(username);
        c.setPassword(password);
        c.setUsuarioId(userId);

        credService.insertar(c);

        System.out.println("✔ Credencial creada.");
    }

    private void leerCredencial() throws Exception {
        long id = pedirId("ID Credencial");
        CredencialAcceso c = credService.getById(id);

        if (c == null) {
            System.out.println("❌ No existe credencial.");
            return;
        }

        System.out.println("Credencial encontrada: " + c);
    }

    private void listarCredenciales() throws Exception {
        System.out.println("\n--- Lista de Credenciales ---");
        List<CredencialAcceso> lista = credService.getAll();
        lista.forEach(System.out::println);
    }

    private void actualizarCredencial() throws Exception {
        long id = pedirId("ID Credencial a actualizar");
        CredencialAcceso c = credService.getById(id);

        if (c == null) {
            System.out.println("❌ No existe credencial.");
            return;
        }

        System.out.println("Username actual: " + c.getUsername());
        System.out.print("Nuevo username (enter = no cambiar): ");
        String un = sc.nextLine().trim();
        if (!un.isBlank()) c.setUsername(un);

        System.out.println("Password actual: " + c.getPassword());
        System.out.print("Nuevo password (enter = no cambiar): ");
        String pw = sc.nextLine().trim();
        if (!pw.isBlank()) c.setPassword(pw);

        credService.actualizar(c);

        System.out.println("✔ Credencial actualizada.");
    }

    private void eliminarCredencial() throws Exception {
        long id = pedirId("ID Credencial a eliminar");
        credService.eliminar(id);
        System.out.println("✔ Credencial eliminada (lógica).");
    }

    /* ========================================================
                     MÉTODO AUXILIAR DE VALIDACIÓN
       ======================================================== */

    private long pedirId(String etiqueta) {
        while (true) {
            try {
                System.out.print(etiqueta + ": ");
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ ID inválido. Debe ser numérico.");
            }
        }
    }
}