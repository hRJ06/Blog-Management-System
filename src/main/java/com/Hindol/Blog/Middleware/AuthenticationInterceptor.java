package com.Hindol.Blog.Middleware;

import com.Hindol.Blog.Payload.TokenValidationResultDTO;
import com.Hindol.Blog.Util.JWTToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTToken jwtToken;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        try {
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String bearerToken = authorizationHeader.substring(7);
                TokenValidationResultDTO tokenValidationResultDTO = jwtToken.validateToken(bearerToken);
                if("Expired Token".equals(tokenValidationResultDTO.getResult()) || "Invalid Token".equals(tokenValidationResultDTO.getResult())) {
                    log.warn("Token validation failed: {}", tokenValidationResultDTO.getResult());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
                    return false;
                }
                else {
                    log.info("Token validation successful for user: {}", tokenValidationResultDTO.getEmail());
                    request.setAttribute("gmail",tokenValidationResultDTO.getEmail());
                    return true;
                }
            }
            else {
                log.warn("Authorization header is missing or does not start with 'Bearer'");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid Authorization header");
                return false;
            }
        }
        catch (Exception e) {
            log.error("Exception during token validation", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Log or handle postHandle if needed
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Log or handle afterCompletion if needed
    }
}
