package vxs.lojavirtual.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;

/*Criar autenticação e retornar autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {

	/*Token de validade*/
	private static final long EXPIRATION_TIME = 999999999;
	
	/*Chave de senha ´para juntar com o JWT*/
	private static final String SECRET = "ss/*****";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/*Gera o token e da a resposta par ao cliente o com JWT*/
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		//String JWT = Jwts.builder()./*Chama o gerador de token*/
			//	setSubject(username) /*Adiciona o user*/
				//.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				//.signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*tEMPO DE EXPIRAÇÃO*/

		 Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
		 
		  // Gera o JWT
        String JWT = Jwts.builder()
                .claim("sub", username)  // Usando claim para definir o  "subject"
                .claim("exp", new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Define a expiração
                .signWith(secretKey)  // Usando a chave secreta
                .compact();  // Gera o token JWT
		
		/*Ex: Bearer 934938084093jaosljla*/
		String token = TOKEN_PREFIX + " " + JWT;
		
		/*Dá resposta para tela e para o cliente, navegador, aplicativo, javascript, outra chamadajava*/
		response.addHeader(HEADER_STRING, token);
		
		
		/*Usado para ver no postman para teste*/
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
		
	}
	
}
