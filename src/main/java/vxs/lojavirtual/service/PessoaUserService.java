package vxs.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vxs.lojavirtual.model.PessoaFisica;
import vxs.lojavirtual.model.PessoaJuridica;
import vxs.lojavirtual.model.Usuario;
import vxs.lojavirtual.repository.PessoaFisicaRepository;
import vxs.lojavirtual.repository.PessoaRepository;
import vxs.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {

		for (int i = 0; i < juridica.getEnderecos().size(); i++) {
			juridica.getEnderecos().get(i).setPessoa(juridica);
			juridica.getEnderecos().get(i).setEmpresa(juridica);
		}

		juridica = pessoaRepository.save(juridica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());

		if (usuarioPj == null) {

			String constraint = usuarioRepository.consultaConstraintAcesso();

			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}

			usuarioPj = new Usuario();

			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa_id(juridica);
			usuarioPj.setPessoa(juridica);
			usuarioPj.setLogin(juridica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCrpt = new BCryptPasswordEncoder().encode(senha);

			usuarioPj.setSenha(senhaCrpt);

			usuarioPj = usuarioRepository.save(usuarioPj);

			usuarioRepository.insereAcessoUser(usuarioPj.getId());
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");
			
			StringBuilder messagemHtml = new StringBuilder();
			
			messagemHtml.append("<b> segue abaixo seu dados de acesso para a loja virtual</b>");
			messagemHtml.append("<b>Login </b>"+juridica.getEmail()+"<br/>");
			messagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			messagemHtml.append("Obrigado!");
	

			try {
				serviceSendEmail.enviarEmailHtml("Acesso gerado para loja virtual", messagemHtml.toString(),
						juridica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return juridica;

	}
	
	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {

		for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
			pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
			//pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);
		}

		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

		if (usuarioPj == null) {

			String constraint = usuarioRepository.consultaConstraintAcesso();

			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}

			usuarioPj = new Usuario();

			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa_id(pessoaFisica.getEmpresa_id());
			usuarioPj.setPessoa(pessoaFisica);
			usuarioPj.setLogin(pessoaFisica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCrpt = new BCryptPasswordEncoder().encode(senha);

			usuarioPj.setSenha(senhaCrpt);

			usuarioPj = usuarioRepository.save(usuarioPj);

			usuarioRepository.insereAcessoUser(usuarioPj.getId());

			
			StringBuilder messagemHtml = new StringBuilder();
			
			messagemHtml.append("<b> segue abaixo seu dados de acesso para a loja virtual</b>");
			messagemHtml.append("<b>Login </b>"+pessoaFisica.getEmail()+"<br/>");
			messagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			messagemHtml.append("Obrigado!");
	

			try {
				serviceSendEmail.enviarEmailHtml("Acesso gerado para loja virtual", messagemHtml.toString(),
						pessoaFisica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pessoaFisica;

	}

}
