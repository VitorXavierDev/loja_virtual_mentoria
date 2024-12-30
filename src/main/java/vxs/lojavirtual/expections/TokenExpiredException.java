package vxs.lojavirtual.expections;

public class TokenExpiredException extends TokenValidationException  {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenExpiredException(String message) {
	        super(message + "token expirado");
	    }
}
