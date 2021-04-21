package ua.training.CruiseLineSpring.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			if(checkFilterExceptions(request)) {
				filterChain.doFilter(request, response);
				return;
			}
			if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
				authenticateUser(request, jwt);
				filterChain.doFilter(request, response);
				return;
			}
			throw new IllegalArgumentException("Auth token is null or empty");
		} catch (NullPointerException | IllegalArgumentException | JwtException e) {
			response.setStatus(401);
			response.getWriter().write("401");
		} 
    }

	private boolean checkFilterExceptions(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return checkRefreshTokenException(url) || checkLoginException(url) || checkSignupException(url) || checkSignoffException(url);
	}

	private boolean checkRefreshTokenException(String url) {
		return url.contains("refresh/token");
	}

	private boolean checkLoginException(String url) {
		return url.contains("login");
	}

	private boolean checkSignupException(String url) {
		return url.contains("singup");
	}
	
	private boolean checkSignoffException(String url) {
		return url.contains("logout");
	}
	private void authenticateUser(HttpServletRequest request, String jwt) {
		String username = jwtProvider.getUsernameFromJWT(jwt);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}