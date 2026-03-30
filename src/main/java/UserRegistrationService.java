public class UserRegistrationService {

    public String register(String username, String password, String email, Integer age) {

        if (username == null || username.isBlank() ||
                password == null || password.isBlank() ||
                email    == null || email.isBlank()    ||
                age      == null) {
            return "Debe completar todos los campos requeridos";
        }

        if (!username.matches("[a-zA-Z0-9]{6,12}")) {
            return "El nombre de usuario no es válido";
        }

        if (password.length() < 8 ||
                !password.matches(".*[a-zA-Z].*") ||
                !password.matches(".*[0-9].*")) {
            return "La contraseña debe tener al menos 8 caracteres y contener letras y números";
        }

        if (!email.contains("@") || email.length() < 8) {
            return "Ingrese un correo electrónico válido";
        }

        if (age < 18) {
            return "Debe ser mayor de edad para registrarse";
        }

        return "El usuario ha sido registrado correctamente";
    }
}