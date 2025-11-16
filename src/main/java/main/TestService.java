package main;

import entities.CredencialAcceso;
import entities.Usuario;
import service.UsuarioService;
import service.CredencialAccesoService;

public class TestService {

    public static void main(String[] args) {

        UsuarioService usuarioService = new UsuarioService();
        CredencialAccesoService credService = new CredencialAccesoService();

        try {
            System.out.println("\n===== TEST 1: Crear usuario con credencial =====");

            Usuario u = new Usuario();
            u.setNombre("Service User");
            u.setEmail("service@example.com");

            CredencialAcceso c = new CredencialAcceso();
            c.setUsername("serviceUser");
            c.setPassword("abc123");

            u.setCredencial(c);

            usuarioService.insertar(u);

            System.out.println("✔ Usuario + Credencial creados (ID usuario: " + u.getId() + ")");

            System.out.println("\n===== TEST 2: Leer usuario =====");
            Usuario leido = usuarioService.getById(u.getId());
            System.out.println("Leído → " + leido);

            System.out.println("\n===== TEST 3: Actualizar usuario =====");
            leido.setNombre("Service User UPDATED");
            usuarioService.actualizar(leido);
            System.out.println("✔ Usuario actualizado correctamente");

            System.out.println("\n===== TEST 4: Regla 1→1 (Debe fallar creando otra credencial) =====");
            try {
                CredencialAcceso c2 = new CredencialAcceso();
                c2.setUsername("otroUser");
                c2.setPassword("xyz456");
                c2.setUsuarioId(u.getId());

                credService.insertar(c2);  // Esto NO debería permitirse
                System.out.println("❌ ERROR: Se permitió violar la regla 1→1");
            } catch (Exception e) {
                System.out.println("✔ Regla 1→1 respetada (No se permitió segunda credencial)");
            }

            System.out.println("\n===== TEST 5: getAll() usuarios =====");
            usuarioService.getAll().forEach(System.out::println);

            System.out.println("\n===== TEST 6: Eliminar usuario =====");
            usuarioService.eliminar(u.getId());
            System.out.println("✔ Usuario eliminado (baja lógica)");

        } catch (Exception e) {
            System.out.println("❌ Error general en prueba:");
            e.printStackTrace();
        }
    }
}
