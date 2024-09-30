package be.unamur.fpgen.context;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = ThreadLocal.withInitial(UserContext::new);

    public static UserContext getContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}
