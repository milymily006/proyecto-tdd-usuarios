public class UserRegistrationService {

    public String register(String username, String password, String email, Integer age) {

        if (username == null || username.isBlank() ||
                password == null || password.isBlank() ||
                email == null || email.isBlank() ||
                age == null) {
            return "Debe completar todos los campos requeridos";
        }

        return username;
    }
}