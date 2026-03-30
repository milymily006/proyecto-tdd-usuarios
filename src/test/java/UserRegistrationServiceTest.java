import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationServiceTest {

    private UserRegistrationService service;

    @BeforeEach
    void setUp() {
        service = new UserRegistrationService();
    }

    // Test 1 — Registro exitoso con datos válidos
    @Test
    void testRegistroExitoso() {
        String result = service.register("juan123", "clave1234", "juan@email.com", 25);
        assertEquals("El usuario ha sido registrado correctamente", result);
    }
    // Test 2 — Nombre de usuario inválido (menos de 6 caracteres)
    @Test
    void testNombreUsuarioInvalido() {
        String result = service.register("abc", "clave1234", "juan@email.com", 25);
        assertEquals("El nombre de usuario no es válido", result);
    }
    // Test 3 — Contraseña inválida (sin números)
    @Test
    void testContrasenaInvalida() {
        String result = service.register("juan123", "sololetras", "juan@email.com", 25);
        assertEquals("La contraseña debe tener al menos 8 caracteres y contener letras y números", result);
    }
    // Test 4 — Correo inválido (sin '@')
    @Test
    void testCorreoInvalido() {
        String result = service.register("juan123", "clave1234", "correoinvalido", 25);
        assertEquals("Ingrese un correo electrónico válido", result);
    }



}