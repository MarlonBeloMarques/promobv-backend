package com.marlonmarqs.promobv.service;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.VerificaToken;
import com.marlonmarqs.promobv.domain.enums.TipoPerfil;
import com.marlonmarqs.promobv.dto.UsuarioNewDTO;
import com.marlonmarqs.promobv.dto.UsuarioUpdateDTO;
import com.marlonmarqs.promobv.event.OnRegistrationCompleteEvent;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.repository.VerificaTokenRepository;
import com.marlonmarqs.promobv.security.UserSS;
import com.marlonmarqs.promobv.service.exceptions.AuthorizationException;
import com.marlonmarqs.promobv.service.exceptions.BusinessRuleException;
import com.marlonmarqs.promobv.service.exceptions.DataIntegrityException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private VerificaTokenRepository tokenRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Optional<Usuario> find(Integer id) {
		
		UserSS user = UserService.authenticated();
		// faz a busca e verifica se não é nulo ou se não é admin e se o id é diferente do qual foi buscado
		if(user==null || !user.hasRole(TipoPerfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		
		Optional<Usuario> obj = repo.findById(id);
		
		obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Usuario.class.getName()));
		
		return obj;
	}
	
	public Usuario findByEmail(String email) throws ObjectNotFoundException {
		
		UserSS user = UserService.authenticated();
		if(user == null || !email.equals(user.getUsername())) { // se é nulo ou  não é adm e o email não é igual do usuario autenticado
			throw new AuthorizationException("Acesso negado");
		}
		
		Usuario obj = repo.findByEmail(email);
		
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId()
					+ ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
	
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	public Usuario insert(Usuario obj, HttpServletRequest request, Errors error) {
		
		try {
			obj.setId(null);
			obj = repo.save(obj);
			
			String appUrl = request.getRequestURL().toString();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(obj, request.getLocale(), appUrl));
		} catch (Exception e) {
			System.out.println(e);
		}
				return obj;
		
	}
	
	public String regitrationConfirm(String token) throws ObjectNotFoundException, BusinessRuleException {
		
		VerificaToken verificaToken = getVerificationToken(token);
		if(verificaToken == null) {
			throw new ObjectNotFoundException("Token não encontrado!");
		}
		
		Usuario user = verificaToken.getUser();
		Calendar cal = Calendar.getInstance();
		if((verificaToken.getDataDeValidade().getTime() - cal.getTime().getTime()) <= 0) {
			throw new BusinessRuleException("Token expirado!");
		}
		
		user.setAtivado(true);
		repo.save(user);
		
		return "Seu email foi validado!";
	}
	
	public void criaVerificacaoToken(final Usuario user, final String token) {
        final VerificaToken myToken = new VerificaToken(token, user);
        
        System.out.println(myToken.getToken());
        tokenRepository.save(myToken);
    }
	
	public VerificaToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
	
	public void delete(Integer id) throws DataIntegrityException {
		Optional<Usuario> obj = find(id);

		if(obj.get().getPromocoes().isEmpty()) {
			repo.deleteById(id);
		} else {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");	
		}
	}
	
	public Usuario update(Usuario obj) {
		Optional<Usuario> newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj.get());
	}
	
	public Usuario fromDTO(UsuarioUpdateDTO objDto) {
		return new Usuario(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone(), objDto.getDataDeNascimento());
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		return new Usuario (null, objDto.getApelido(), objDto.getEmail(), pe.encode(objDto.getSenha()));
	}
	
	private void updateData(Optional<Usuario> newObj, Usuario obj) {
		if(obj.getNome() != null)
			newObj.get().setNome(obj.getNome());
		if(obj.getCpf() != null)
			newObj.get().setCpf(obj.getCpf());
		if(obj.getDataDeNascimento() != null)
			newObj.get().setDataDeNascimento(obj.getDataDeNascimento());
		if(obj.getTelefone() != null)
			newObj.get().setTelefone(obj.getTelefone());
		if(obj.getDataDeNascimento() != null)
			newObj.get().setDataDeNascimento(obj.getDataDeNascimento());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) throws AuthorizationException {
		
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Usuario> obj = find(user.getId());

		//extrai jpg através do enviado pela requisição
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		//recorta
		jpgImage = imageService.cropSquare(jpgImage);
		//redimensionar
		jpgImage = imageService.resize(jpgImage, size);

		//definindo o nome do arquivo
		String fileName = prefix + user.getId() + ".jpg";
		
		URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
		obj.get().setUrlProfile(uri.toString());
		
		repo.save(obj.get());
				
		return uri;
	}
	
}
