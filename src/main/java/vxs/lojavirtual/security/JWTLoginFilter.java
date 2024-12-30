package vxs.lojavirtual.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vxs.lojavirtual.model.Usuario;
import vxs.lojavirtual.repository.UsuarioRepository;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	 private final JWTTokenAutenticacaoService jwtService;
	
	
	  public JWTLoginFilter(String url, AuthenticationManager authenticationManager, JWTTokenAutenticacaoService jwtService) {
	        super(new AntPathRequestMatcher(url));
	        setAuthenticationManager(authenticationManager);
	        this.jwtService = jwtService;
	    }


	
	
	/*Retornar o usuário ao processar a autenticação*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		/*Obter usuário*/
		Usuario user =  new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		
		
		/*retorna user com  login e senha*/
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	 @Override
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	                                            Authentication authResult) throws IOException {
	        try {
				jwtService.addAuthentication(response, authResult.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	 @Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		 response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");

		    if (failed instanceof BadCredentialsException) {
		        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		        response.getWriter().write("{\"error\": \"Credenciais inválidas\", \"message\": \"Usuário ou senha estão incorretos.\"}");
		    } else if (failed instanceof UsernameNotFoundException) {
		        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		        response.getWriter().write("{\"error\": \"Usuário não encontrado\", \"message\": \"O usuário fornecido não existe.\"}");
		    } else {
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().write("{\"error\": \"Erro de autenticação\", \"message\": \"" + failed.getMessage() + "\"}");
		    }

		    // Log para o servidor
		    logger.error("Erro de autenticação: " + failed.getMessage(), failed);
	}

}
