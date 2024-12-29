package vxs.lojavirtual.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"Acesso negado\", \"message\": \"" + accessDeniedException.getMessage() + "\"}");
		
	}

}
