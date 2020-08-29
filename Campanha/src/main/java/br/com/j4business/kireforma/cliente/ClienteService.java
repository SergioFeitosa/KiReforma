package br.com.j4business.kireforma.cliente;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import br.com.j4business.kireforma.campanha.Campanha;
import br.com.j4business.kireforma.campanha.CampanhaService;
import br.com.j4business.kireforma.time.Esquadrao;

@Service
public class ClienteService {

	@Autowired
	private CampanhaService campanhaService;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> getClienteByNome(String nome) {

		return ((List<Cliente>) clienteRepository
				.findByNome(nome))
				.stream()
				.collect(Collectors.toList());
	}

	public List<Cliente> getAllList() {
		return ((List<Cliente>) clienteRepository
				.findAll())
				.stream()
				.collect(Collectors.toList());
	}

	public Iterable<Cliente> getAll() {
		
		List<Cliente> clienteList = this.getAllList();

		Collection<Cliente> ClienteCollection = clienteList;
		
	    Iterable<Cliente> clienteIterable = ClienteCollection;
		
		return clienteIterable;
	}

	public Iterable<Cliente> getClienteByEsquadrao(Esquadrao esquadrao) {

		return ((List<Cliente>) clienteRepository
				.findByEsquadrao(esquadrao))
				.stream()
				.collect(Collectors.toList());

	}

	public Iterable<Cliente> getClienteByCampanha(Campanha campanha) {

		return ((List<Cliente>) clienteRepository
				.findByCampanha(campanha))
				.stream()
				.collect(Collectors.toList());
	}

	public Iterable<Cliente> getClienteByEmail(Cliente cliente) {

		return ((List<Cliente>) clienteRepository
				.findByEmail(cliente.getEmail())
				.stream()
				.collect(Collectors.toList()));
	}

	public String deleteCliente(Cliente cliente) {

		clienteRepository.delete(cliente);

		return null;
	}

	public Cliente getClienteById(long id) {

		Optional<Cliente> optionalEntity = clienteRepository.findById(id);
		Cliente cliente = optionalEntity.get();

		return cliente;
	}

	public Cliente saveCliente(Cliente cliente) {

		return clienteRepository.save(cliente);
	}

	public Cliente cadastrarCliente(Cliente cliente) throws Exception {

		ZoneId defaultZoneId = ZoneId.systemDefault();

		LocalDate localDateDataNasc = Instant.ofEpochMilli(cliente.getDataNasc().getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();

		Date dataNascTimeZero = Date.from(localDateDataNasc.atStartOfDay(defaultZoneId).toInstant());

		if (dataNascTimeZero.after(new Date())) {
			throw new Exception("Mensagem : " + "Data de Nascimento inválida. Verifique os campos");
		}

		Iterable<Cliente> clientesCadastro = this.getClienteByEmail(cliente);

		if (clientesCadastro.iterator().hasNext()) {
			throw new Exception("Cliente já cadastrado.");
		}

		try {
			cliente = this.saveCliente(cliente);
		} catch (Exception e) {
			throw new Exception("mensagem : Falha na atualização do Cliente. Verifique os campos");
		}

		try {
			this.atualizarCampanha(cliente);
		} catch (Exception e) {
			throw new Exception("mensagem : Falha na atualização do Cliente. Verifique os campos");
		}

		return cliente;
	}

	public Map<String, Iterable<Campanha>> getCampanhasInscritos(long id) {

		Map<String, Iterable<Campanha>> map = new HashMap<>();

		Cliente cliente = this.getClienteById(id);

		ModelAndView mv = new ModelAndView("cliente/detalhesCliente");
		mv.addObject("cliente", cliente);

		Iterable<Campanha> campanhas = campanhaService.getCampanhaByEsquadrao(cliente.getEsquadrao());

		List<Campanha> campanhaInscritoList = new ArrayList<Campanha>();
		;
		List<Campanha> campanhaNaoInscritoList = new ArrayList<Campanha>();
		;

		for (Campanha campanha3 : campanhas) {
			if (campanha3.getCliente().contains(cliente)) {
				campanhaInscritoList.add(campanha3);
			} else {
				campanhaNaoInscritoList.add(campanha3);
			}

		}

		Iterable<Campanha> campanhaInscrito = campanhaInscritoList;
		Iterable<Campanha> campanhaNaoInscrito = campanhaNaoInscritoList;

		map.put("campanhaInscrito", campanhaInscrito);
		map.put("campanhaNaoInscrito", campanhaNaoInscrito);

		return map;
	}

	private void atualizarCampanha(Cliente cliente) throws Exception {

		Iterable<Campanha> campanhas = campanhaService.getCampanhaByEsquadrao(cliente.getEsquadrao());

		for (Campanha campanha : campanhas) {

			if (campanha.getCliente() != null) {
				campanha.getCliente().add(cliente);
			} else {
				campanha.setCliente(new HashSet<Cliente>());
				campanha.getCliente().add(cliente);
			}

			try {
				campanhaService.saveCampanha(campanha);
			} catch (Exception e) {
				throw new Exception("mensagem : Falha na atualização do Cliente. Verifique os campos");
			}

			if (cliente.getCampanha() != null) {
				cliente.getCampanha().add(campanha);
			} else {
				cliente.setCampanha(new HashSet<Campanha>());
				cliente.getCampanha().add(campanha);
			}

			try {
				cliente = this.saveCliente(cliente);
			} catch (Exception e) {
				throw new Exception("mensagem : Falha na atualização do Cliente. Verifique os campos");
			}
		}
	}

}
