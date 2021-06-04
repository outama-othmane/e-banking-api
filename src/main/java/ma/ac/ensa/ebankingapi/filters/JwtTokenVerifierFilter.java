package ma.ac.ensa.ebankingapi.filters;

import com.google.common.base.Strings;
import io.jsonwebtoken.JwtException;
import ma.ac.ensa.ebankingapi.exception.InvalidCredentialsException;
import ma.ac.ensa.ebankingapi.exception.InvalidJwtTokenException;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtTokenVerifierFilter(JwtUtil jwtUtil,
                                  UserRepository userRepository,
                                  @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(Strings.isNullOrEmpty(authorizationHeader) || ! authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // The provided token
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            // The token subject
            Long userId = Long.parseLong(jwtUtil.extractSubject(token));

            // Getting the user
            User user = userRepository.findById(userId).get();

            if (! jwtUtil.validateToken(token ,user)) {
                throw new InvalidJwtTokenException();
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    null,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            resolver.resolveException(request, response, null, new InvalidJwtTokenException());
        }
    }
}
