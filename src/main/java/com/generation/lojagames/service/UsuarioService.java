package com.generation.lojagames.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.Usuario;
import com.generation.lojagames.model.UsuarioLogin;
import com.generation.lojagames.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario){
		
		Period idade = Period.between(usuario.getDataNascimento(), LocalDate.now());

		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent() || idade.getYears() < 18)
			return Optional.empty();
		
		if(usuario.getFoto().isBlank()) 
			usuario.setFoto("https://i.imgur.com/IhQhKMH.jpg");
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	/*public Optional<UsuarioLogin> verificarFoto(Optional<UsuarioLogin> usuarioLogin){
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuario.get().getFoto().isEmpty()) {
			return 	usuarioLogin.;

		}
	}*/
	
	public String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
		
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = usuarioRepository.findById(usuario.getId());
			if((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário já existe", null);
				
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			return Optional.of(usuarioRepository.save(usuario));
		}
		return Optional.empty();
	}
	
	
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuario.isPresent()) {
			
			usuarioLogin.get().setId(usuario.get().getId());
			
			usuarioLogin.get().setNome(usuario.get().getNome());
			
			usuarioLogin.get().setFoto(usuario.get().getFoto());
			
			usuarioLogin.get().setSenha(usuario.get().getSenha());
			
			usuarioLogin.get().setDataNascimento(usuario.get().getDataNascimento());

			usuarioLogin.get().setToken(gerarBasicToken(usuario.get().getUsuario(), usuario.get().getSenha()));
			
			return usuarioLogin;
		}
		return Optional.empty();
	}
	
	public String gerarBasicToken(String usuario, String senha) {
		
		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);
		
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada,senhaBanco);
	}
}
