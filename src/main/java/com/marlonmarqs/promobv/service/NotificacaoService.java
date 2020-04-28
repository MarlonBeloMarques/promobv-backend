package com.marlonmarqs.promobv.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.marlonmarqs.promobv.dto.NotificacaoDTO;
import com.marlonmarqs.promobv.dto.NotificacaoNewDTO;
import com.marlonmarqs.promobv.repository.NotificacaoRepository;
import com.marlonmarqs.promobv.repository.PromocaoRepository;
import com.marlonmarqs.promobv.repository.UsuarioRepository;
import com.marlonmarqs.promobv.service.exceptions.BusinessRuleException;
import com.marlonmarqs.promobv.service.exceptions.ObjectNotFoundException;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository repo;
	
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
	
	public Page<NotificacaoDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		List<NotificacaoDTO> objs = new ArrayList<>();
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Promocao> promocoes = promocaoService.findAllUser();
		for (Promocao promocao : promocoes) {
		 	objs.addAll(repo.findByPromocao(promocao).stream().map(obj -> new NotificacaoDTO(obj)).collect(Collectors.toList()));
		}
				
		return new PageImpl<>(objs, pageRequest, objs.size());
	}
	
	public Notificacao interact(Notificacao obj) throws BusinessRuleException {	
		
		List<Notificacao> objs = obj.getPromocao().getNotificacoes();
		
		if(obj.getTipo() == 1) {
			for (Notificacao notificacao : objs) {
				if(notificacao.getUsuario().getId() == obj.getUsuario().getId()) {
					remove(notificacao.getId());
					throw new BusinessRuleException("Notificação removida");
				}
			}
		} else if(obj.getTipo() == 2) {
			for (Notificacao notificacao : objs) {
				if(notificacao.getUsuario().getId() == obj.getUsuario().getId()) {
					throw new BusinessRuleException("Você já denunciou essa promoção.");
				}
			}
		}
		
		obj.setId(null);
		obj = repo.save(obj);
		
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
	
	public List<String> disableCheckDenuncia(Integer idUser) {
		String msg = "A publicação foi desativada para análise: ";
		
		List<Promocao> objs = promocaoService.findAllUser();
		List<String> messages = new ArrayList<>();
		
		for (Promocao promocao : objs) {
			if(promocao.getNotificacoes().stream().map(obj -> obj.getTipo().compareTo(2)).count() >= 10) {
				promocaoService.disable(promocao.getId());
				messages.add(msg + promocao.getTitulo());
			}
		}
		
		return messages.isEmpty() ? Arrays.asList("Não há publicações denunciadas") : messages;
	}
	
	public List<String> disableCheckDenuncia() {
		String msg = "A publicação foi desativada para análise:";
		
		List<Promocao> objs = promocaoService.findAll();
		List<String> messages = new ArrayList<>();
		
		for (Promocao promocao : objs) {
			if(promocao.getNotificacoes().stream().map(obj -> obj.getTipo().compareTo(2)).count() >= 10) {
				promocaoService.disable(promocao.getId());
				messages.add(msg + 
						" (Titulo: " + promocao.getTitulo() +
						", Id: " + promocao.getId() + ")");
			}
		}
		
		return messages.isEmpty() ? Arrays.asList("Não há publicações denunciadas") : messages;
	}
}
