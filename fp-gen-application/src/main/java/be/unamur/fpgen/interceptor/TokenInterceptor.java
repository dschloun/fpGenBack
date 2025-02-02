package be.unamur.fpgen.interceptor;

import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.context.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Interceptor to extract the token from the Authorization header and set the user context.
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String payload = new String(Base64.getDecoder().decode(parts[1]));
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> payloadMap = mapper.readValue(payload, Map.class);
                String email = (String) payloadMap.get("email");
                UserContext userContext = UserContextHolder.getContext();
                userContext.setEmail(email);
                userContext.setTrigram((String) payloadMap.get("preferred_username"));
                userContext.setToken(token);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }
}