package fs19.java.backend.config;

import fs19.java.backend.application.UserDetailsServiceImpl;
import fs19.java.backend.presentation.shared.exception.AuthenticationNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

/**
 * JWT request filter
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtValidator jwtValidator;

    public JwtAuthFilter(UserDetailsServiceImpl userDetailsService, JwtValidator jwtValidator) {
        this.userDetailsService = userDetailsService;
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Allow specific endpoints without authentication
            String requestURI = request.getRequestURI();
            if (requestURI.startsWith("/swagger-ui/") ||
                    requestURI.startsWith("/v3/api-docs") ||
                    requestURI.startsWith("/swagger-resources") ||
                    requestURI.startsWith("/webjars/")) {
                filterChain.doFilter(request, response);
                return;
            } else if (requestURI.startsWith("/api/v1/invitation/")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = request.getHeader("Authorization");
            String workspaceId = request.getHeader("workspaceId");
            String username = null;
            if (token != null) {
                username = jwtValidator.extractUserEmail(token);
                logger.info("Extracted username from token: {}", username);
            }
            if (token == null) {
                logger.info("No token found in request");
                filterChain.doFilter(request, response);
                return;
            }
            if ((username != null && workspaceId != null) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUserNameAndWorkspaceId(username, UUID.fromString(workspaceId));
                handleUserInfo(request, token, userDetails, workspaceId, username);
            } else if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                handleUserInfo(request, token, userDetails, workspaceId, username);
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            logger.error("Access denied: {}", e.getMessage());
            response.getWriter().write(e.getMessage());
            throw new AuthenticationNotFoundException(e.getMessage());
        } catch (IOException e) {
            logger.error("IO Exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleUserInfo(HttpServletRequest request, String token, UserDetails userDetails, String workspaceId, String username) {
        if (jwtValidator.isTokenValid(token, userDetails, workspaceId)) {
            logger.info("Token is valid for user: {}", username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetailsService.loadUserByUsername(username), userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            logger.info("Authentication set for user: {}", username);
        } else {
            logger.error("Invalid token for user: {}", username);
        }
    }

    /**
     * Get the signature
     *
     * @param jwt
     * @return
     */
    public String getSignature(String jwt) {
        String[] jwtParts = jwt.split("\\.");
        return jwtParts[1];
    }
}