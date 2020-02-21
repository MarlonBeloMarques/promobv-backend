package com.marlonmarqs.promobv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marlonmarqs.promobv.domain.Notificacao;
import com.marlonmarqs.promobv.domain.Promocao;
import com.marlonmarqs.promobv.domain.Usuario;
import com.marlonmarqs.promobv.domain.enums.TipoNotificacao;
import com.marlonmarqs.promobv.dto.NotificacaoNewDTO;
import com.marlonmarqs.promobv.repository.NotificacaoRepository;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.service.exceptions.DataIntegrityException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository repo;
	
	@Autowired
	private PromocaoRepository promocaoRepository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private PromocaoService promocaoService;
	
	@Autowired
	private UsuarioService userService;
	
	private Optional<Notificacao> find(Integer id) {
		Optional<Notificacao> obj = repo.findById(id);
		
		obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
				+ ", Tipo: " + Notificacao.class.getName()));
		
		return obj;
	}
	
	public Page<Notificacao> findPage(Integer idUser, Integer page, Integer linesPerPage, String orderBy, String direction){
		List<Notificacao> objs = new ArrayList<>();
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Promocao> promocoes = promocaoService.findAllUser(idUser);
		for (Promocao promocao : promocoes) {
		 	objs.addAll(repo.findByPromocao(promocao));
		}
				
		return new PageImpl<>(objs, pageRequest, objs.size());
	}
	
	public Notificacao insert(Notificacao obj) {
		obj.setId(null);
		obj = repo.save(obj);
		userRepository.save(obj.getUsuario());
		promocaoRepository.save(obj.getPromocao());
		return obj;
	}
	
	public void remove(Integer id) {
		Optional<Notificacao> obj = find(id);
		
		repo.delete(obj.get());
	}
	
	public Notificacao fromDTO(NotificacaoNewDTO objDto) {
		Optional<Usuario> user = userService.find(objDto.getIdUsuario());
		Optional<Promocao> promo = promocaoService.find(objDto.getIdPromocao());
		
		return new Notificacao(null, objDto.getData(), objDto.getHora(), promo.get(), user.get(), TipoNotificacao.toEnum(objDto.getTipo()));
	}
}
