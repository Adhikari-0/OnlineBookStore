package com.onlineBookStore.authapi.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.onlineBookStore.authapi.services.JwtService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final HandlerExceptionResolver handlerExceptionResolver;

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
			HandlerExceptionResolver handlerExceptionResolver) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	private String extractTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("OnlineBookStore".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = extractTokenFromCookies(request);

		if (jwt != null) {
			try {
				String username = jwtService.extractUsername(jwt);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					if (jwtService.isTokenValid(jwt, userDetails)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}
			} catch (Exception e) {
				handlerExceptionResolver.resolveException(request, response, null, e);
			}
		}

		filterChain.doFilter(request, response);
	}
}
