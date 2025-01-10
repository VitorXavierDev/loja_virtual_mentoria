package vxs.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import vxs.lojavirtual.model.Usuario;
import vxs.lojavirtual.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Scheduled(initialDelay = 2000, fixedDelay = 86400000)
	//@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo" )/*vai rodar todos os dias às 11h da manhã horário de brasilia*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		 
		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		
		for (Usuario usuario : usuarios) {
			
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar sua senha, já passou 90 dias de validade.").append("<br/>");
			msg.append("troque sua senha a loja virtual do Xavier");
			
			serviceSendEmail.enviarEmailHtml("troca de senha", msg.toString(), usuario.getLogin());
			
			Thread.sleep(3000);
		}
	}
}
