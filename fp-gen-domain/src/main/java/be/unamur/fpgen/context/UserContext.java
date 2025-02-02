package be.unamur.fpgen.context;

/**
 * This class is used to store the user context.
 * Used by the interceptor to store email, token, trigram
 */
public class UserContext {

    private String token;
    private String email;
    private String trigram;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrigram() {
        return trigram;
    }

    public void setTrigram(String trigram) {
        this.trigram = trigram;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
