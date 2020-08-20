package com.example.demo.cliente;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.campanha.Campanha;
import com.example.demo.campanha.CampanhaService;
import com.example.demo.time.Esquadrao;

@Service
public class ClienteService {

	@Autowired
	private CampanhaService campanhaService;

	@Autowired
	private ClienteRepository clienteRepository;


	public List<Cliente> getClienteByNome(String nome) {
		
		List<Cliente> clientes = clienteRepository.findByNome(nome);

		return clientes;
	}


	
	public List<Cliente> getAllList() {

		List<Cliente> clientes = clienteRepository.findAll();
		//Iterable<Cliente> clientes = repositoryJson.findAll();

		return clientes;
	}

	public Iterable<Cliente> getAll() {

		Iterable<Cliente> clientes = clienteRepository.findAll();
		//Iterable<Cliente> clientes = repositoryJson.findAll();

		return clientes;
	}

	public Iterable<Cliente> getClienteByEsquadrao(Esquadrao esquadrao) {

		Iterable<Cliente> clientes = clienteRepository.findByEsquadrao(esquadrao);

		return clientes;
	}
	
	
	public Iterable<Cliente> getClienteByCampanha(Campanha campanha) {

		Iterable<Cliente> clientes = clienteRepository.findByCampanha(campanha);

		return clientes;
	}
	
	public Iterable<Cliente> getClienteByEmail(Cliente cliente) {

		Iterable<Cliente> clientes = clienteRepository.findByEmail(cliente.getEmail());

		return clientes;
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
		
		
		cliente = clienteRepository.save(cliente);

		return cliente;
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
			throw new Exception ("Cliente já cadastrado.");
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
		
		List<Campanha> campanhaInscritoList = new ArrayList<Campanha>(); ;
		List<Campanha> campanhaNaoInscritoList = new ArrayList<Campanha>(); ;
		
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
