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

}