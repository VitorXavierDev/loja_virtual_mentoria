package vxs.lojavirtual.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

}
