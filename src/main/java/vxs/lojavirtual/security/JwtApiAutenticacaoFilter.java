package vxs.lojavirtual.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtApiAutenticacaoFilter extends OncePerRequestFilter{

	private final JWTTokenAutenticacaoService jwtService;

    public JwtApiAutenticacaoFilter(JWTTokenAutenticacaoService jwtService) {
        this.jwtService = jwtService;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		  // Ignorar a rota /login
	    if ("/login".equals(request.getRequestURI())) {
	        filterChain.doFilter(request, response);
	        return;
	    }
	    
	    String token = request.getHeader("Authorization");
	    
	    if (token == null || !token.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response); // Continua sem autenticar
	        return;
	    }
	    
	    String tokenLimpo = token.replace("Bearer ", "").trim();
		
		  Authentication authentication = jwtService.getAuthetication(request, response);

	        // Define a autenticação no contexto do Spring Security
	        if (authentication != null) {
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        // Prossegue com o restante do filtro
	        filterChain.doFilter(request, response);
		
	}

}
