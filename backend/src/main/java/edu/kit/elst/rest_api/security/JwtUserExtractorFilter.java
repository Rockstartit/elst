package edu.kit.elst.rest_api.security;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.UserId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtUserExtractorFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Jwt jwt = extractJwtFromHeader(request);
        String userId = extractUserId(jwt);

        log.debug("Recognized user {}", userId);
        initializeCurrentUserContext(userId);

        filterChain.doFilter(request, response);
    }

    private Jwt extractJwtFromHeader(HttpServletRequest request) {
        final String TOKEN_PREFIX = "Bearer ";
        final String HEADER_STRING = "Authorization";

        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new MissingTokenException("Authentication token could not be found in header");
        }

        final String authToken = header.replace(TOKEN_PREFIX, "");

        return jwtDecoder.decode(authToken);
    }

    private String extractUserId(Jwt jwt) {
        final String USER_ID_CLAIM = "sub";

        return jwt.getClaim(USER_ID_CLAIM);
    }

    private void initializeCurrentUserContext(String userId) {
        UserId aUserId = new UserId(userId);

        UserContext.setUserId(aUserId);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }
}
