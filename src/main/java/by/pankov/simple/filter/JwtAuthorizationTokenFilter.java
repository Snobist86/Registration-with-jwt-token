package by.pankov.simple.filter;

import by.pankov.simple.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    @Autowired
    public JwtAuthorizationTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String requestHeader = request.getHeader(tokenHeader);
        if(StringUtils.isEmpty(requestHeader)){
            log.warn("Request does not contain a Jwt token");
        }
        String authToken = requestHeader;
        if (StringUtils.isNotEmpty(requestHeader) && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }

        if ((StringUtils.isNotEmpty(authToken) && SecurityContextHolder.getContext().getAuthentication() == null)
                && tokenService.isTokenValid(authToken)){
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(tokenService.getEmail(authToken), null);
            authentication.setAuthenticated(true);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
