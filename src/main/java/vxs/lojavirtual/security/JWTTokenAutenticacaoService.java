package vxs.lojavirtual.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vxs.lojavirtual.aplication.ApplicationContextLoad;
import vxs.lojavirtual.model.Usuario;
import vxs.lojavirtual.repository.UsuarioRepository;

/*Criar autenticação e retornar autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {

	/* Token de validade */
	private static final long EXPIRATION_TIME = 999999999;

	/* Chave de senha ´para juntar com o JWT */
	private static final String SECRET = "ss/1546a4s65d456as4d56a74s98d7a9s87d98as7d98sa789d";

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	private final UsuarioRepository usuarioRepository;

	// Chave gerada a partir do SECRET
	private final Key secretKey;

	public JWTTokenAutenticacaoService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
		this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes()); // Gera uma chave segura
	}

	/* Gera o token e da a resposta par ao cliente o com JWT */
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		// Gera o JWT
		String JWT = Jwts.builder().claim("sub", username) // Usando claim para definir o "subject"
				.claim("exp", new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Define a expiração
				.signWith(secretKey) // Usando a chave secreta
				.compact(); // Gera o token JWT

		/* Ex: Bearer 934938084093jaosljla */
		String token = TOKEN_PREFIX + " " + JWT;

		/*
		 * Dá resposta para tela e para o cliente, navegador, aplicativo, javascript,
		 * outra chamadajava
		 */
		response.addHeader(HEADER_STRING, token);

		/* Usado para ver no postman para teste */
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	/* Retorna o usuário validado com token ou caso nao seja valido retona null */
	public org.springframework.security.core.Authentication getAuthetication(HttpServletRequest request,
			HttpServletResponse response) {

		String token = request.getHeader(HEADER_STRING);

		try {
			if (token != null && token.startsWith(TOKEN_PREFIX)) {

				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

				Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(tokenLimpo)
						.getBody();

				String username = claims.getSubject();

				if (username != null) {

					Usuario usuario = usuarioRepository.findUserByLogin(username);

					if (usuario != null) {
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(), null,
								usuario.getAuthorities());
					} else { 
						 throw new UsernameNotFoundException("Usuário não encontrado para o token fornecido.");
					} 
				} else {
					throw new IllegalArgumentException("Token inválido. Subject está vazio.");
				}
			} else {
				 throw new IllegalArgumentException("Token não fornecido ou formato inválido.");
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro na autenticação: " + e.getMessage(), e);
			
		}
	
	}


	/* Fazendo liberação contra erro de COrs no navegador */
	private void liberacaoCors(HttpServletResponse response) {

		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}

	}

}
