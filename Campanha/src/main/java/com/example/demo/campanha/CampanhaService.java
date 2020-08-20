package com.example.demo.campanha;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cliente.Cliente;
import com.example.demo.cliente.ClienteService;
import com.example.demo.time.Esquadrao;

@Service
public class CampanhaService {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private CampanhaRepository campanhaRepository;

	public <T> ArrayList<T> getCollectionFromIteralbe(Iterable<T> itr) {

		// Create an empty Collection to hold the result
		ArrayList<T> cltn = new ArrayList<T>();

		itr.forEach(cltn::add);

		// Return the converted collection
		return cltn;
	}

	public String addCampanha() {

		return null;
	}

	public Iterable<Campanha> getCampanhaByCliente(Cliente cliente) {

		Iterable<Campanha> campanhas = campanhaRepository.findByCliente(cliente);

		return campanhas;
	}

	public Iterable<Campanha> getCampanhaByNome(String nome) {

		Iterable<Campanha> campanhas = campanhaRepository.findByNome(nome);

		return campanhas;
	}

	public Iterable<Campanha> getCampanhaByEsquadrao(Esquadrao esquadrao) {

		Iterable<Campanha> campanhas = campanhaRepository.findByEsquadrao(esquadrao);

		return campanhas;
	}

	public Campanha saveCampanha(Campanha campanha) {

		campanhaRepository.save(campanha);

		return campanha;
	}

	public Iterable<Campanha> getAllCampanha() {

		Iterable<Campanha> campanhas = campanhaRepository.findAll();

		return campanhas;
	}

	public Iterable<Campanha> getAllCampanhaNaoVencidas() {

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date dataTermino = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		Iterable<Campanha> campanhas = campanhaRepository.findAllNaoVencidas(dataTermino);

		return campanhas;
	}

	public Campanha detalhesCampanha(long id, Cliente cliente) throws Exception {

		Campanha campanha = this.getCampanhaById(id);

		if (cliente.getCampanha() != null) {
			cliente.getCampanha().add(campanha);
		} else {
			cliente.setCampanha(new HashSet<Campanha>());
			cliente.getCampanha().add(campanha);
		}

		cliente = clienteService.saveCliente(cliente);

		if (campanha.getCliente() != null) {
			campanha.getCliente().add(cliente);
		} else {
			campanha.setCliente(new HashSet<Cliente>());
			campanha.getCliente().add(cliente);
		}
		try {
			this.saveCampanha(campanha);
		} catch (Exception e) {
			throw new Exception("mensagem: Falha na atualização da Campanha. Verifique os campos");
		}

		return campanha;
	}

	public String cadastrarCampanha(Campanha campanha) throws Exception {

		this.validaNome(campanha);

		this.validaDatas(campanha);

		this.atualizaClientes(campanha);

		campanhaRepository.save(campanha);

		return null;
	}

	public String deleteCampanha(Campanha campanha) {

		campanhaRepository.delete(campanha);

		return null;
	}

	public Campanha getCampanhaById(long id) {

		Optional<Campanha> optionalEntity = campanhaRepository.findById(id);
		Campanha campanha = optionalEntity.get();

		return campanha;
	}

	public void associarCampanha(long clienteId, long campanhaId) throws Exception {

		Cliente cliente = clienteService.getClienteById(clienteId);

		Campanha campanha = this.getCampanhaById(campanhaId);

		if (cliente.getCampanha() != null) {
			cliente.getCampanha().add(campanha);
		} else {
			cliente.setCampanha(new HashSet<Campanha>());
			cliente.getCampanha().add(campanha);
		}

		cliente = clienteService.saveCliente(cliente);

		if (campanha.getCliente() != null) {
			campanha.getCliente().add(cliente);
		} else {
			campanha.setCliente(new HashSet<Cliente>());
			campanha.getCliente().add(cliente);
		}

		try {
			this.saveCampanha(campanha);
		} catch (Exception e) {
			throw new Exception("mensagem: Falha na associação da Campanha. Verifique os campos");
		}

	}

	public void desassociarCampanha(long clienteId, long campanhaId) throws Exception {

		Cliente cliente = clienteService.getClienteById(clienteId);

		Campanha campanha = this.getCampanhaById(campanhaId);

		if (cliente.getCampanha() != null) {
			cliente.getCampanha().remove(campanha);
		} else {
			cliente.setCampanha(new HashSet<Campanha>());
			cliente.getCampanha().remove(campanha);
		}

		cliente = clienteService.saveCliente(cliente);

		if (campanha.getCliente() != null) {
			campanha.getCliente().remove(cliente);
		} else {
			campanha.setCliente(new HashSet<Cliente>());
			campanha.getCliente().remove(cliente);
		}

		try {
			this.saveCampanha(campanha);
		} catch (Exception e) {
			throw new Exception("mensagem: Falha na exclusão da associação da Campanha. Verifique os campos");
		}

	}

	public Map<String, Iterable<Cliente>> getClientesInscritos(Campanha campanha) {

		Map<String, Iterable<Cliente>> map = new HashMap<>();

		Iterable<Cliente> cliente = clienteService.getClienteByEsquadrao(campanha.getEsquadrao());

		List<Cliente> clienteInscritoList = new ArrayList<Cliente>();
		List<Cliente> clienteNaoInscritoList = new ArrayList<Cliente>();

		for (Cliente cliente2 : cliente) {
			if (cliente2.getCampanha().contains(campanha)) {
				clienteInscritoList.add(cliente2);
			} else {
				clienteNaoInscritoList.add(cliente2);
			}

		}

		Iterable<Cliente> clienteInscrito = clienteInscritoList;
		Iterable<Cliente> clienteNaoInscrito = clienteNaoInscritoList;

		map.put("clienteInscrito", clienteInscrito);
		map.put("clienteNaoInscrito", clienteNaoInscrito);

		return map;
	}

	private void atualizaClientes(Campanha campanha) throws Exception {

		Iterable<Cliente> clientes = clienteService.getClienteByEsquadrao(campanha.getEsquadrao());

		for (Cliente cliente : clientes) {

			if (campanha.getCliente() != null) {
				campanha.getCliente().add(cliente);
			} else {
				campanha.setCliente(new HashSet<Cliente>());
				campanha.getCliente().add(cliente);
			}

			campanha = this.saveCampanha(campanha);

			if (cliente.getCampanha() != null) {
				cliente.getCampanha().add(campanha);
			} else {
				cliente.setCampanha(new HashSet<Campanha>());
				cliente.getCampanha().add(campanha);
			}

			clienteService.saveCliente(cliente);

		}

	}

	private void validaNome(Campanha campanha) throws Exception {
		Iterable<Campanha> campanhasX = this.getCampanhaByNome(campanha.getNome());

		if (campanhasX.iterator().hasNext()) {
			throw new Exception("Mensagem : " + "Nome de Campanha já existente. Verifique os campos");
		}

	}

	private void validaDatas(Campanha campanha) throws Exception {

		ZoneId defaultZoneId = ZoneId.systemDefault();

		LocalDate localDateDataInicio = Instant.ofEpochMilli(campanha.getDataInicio().getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();

		LocalDate localDateDataTermino = Instant.ofEpochMilli(campanha.getDataTermino().getTime())
				.atZone(ZoneId.systemDefault()).toLocalDate();

		Date dataInicioTimeZero = Date.from(localDateDataInicio.atStartOfDay(defaultZoneId).toInstant());
		Date dataTerminoTimeZero = Date.from(localDateDataTermino.atStartOfDay(defaultZoneId).toInstant());
		Date dataTerminoAuxiliar = Date.from(localDateDataTermino.atStartOfDay(defaultZoneId).toInstant());

		if (dataTerminoTimeZero.before(dataInicioTimeZero)) {
			throw new Exception("Mensagem : " + "Período de vigência inválido. Verifique os campos");
		}

		if (dataTerminoTimeZero.before(new Date())) {
			throw new Exception("Mensagem : " + "Período de vigência vencido. Verifique os campos");
		}

		Iterable<Campanha> campanhas = campanhaRepository.findAllMesmaVigencia(dataInicioTimeZero, dataTerminoTimeZero);

		ArrayList<Campanha> cn = getCollectionFromIteralbe(campanhas);

		cn.sort(Comparator.comparing(o -> o.getDataTermino()));

		for (Iterator<Campanha> iterator = cn.iterator(); iterator.hasNext();) {
			Campanha campanha2 = (Campanha) iterator.next();

			if (!campanha2.getDataTermino().after(campanha.getDataTermino())) {

				LocalDate localDateDataTermino2 = Instant.ofEpochMilli(campanha2.getDataTermino().getTime())
						.atZone(ZoneId.systemDefault()).toLocalDate();
				localDateDataTermino2 = localDateDataTermino2.plusDays(1);
				Date dataTerminoTimeZero2 = Date.from(localDateDataTermino2.atStartOfDay(defaultZoneId).toInstant());
				campanha2.setDataTermino(dataTerminoTimeZero2);
				dataTerminoAuxiliar = dataTerminoTimeZero2;

				if (campanha2.getDataTermino().equals(campanha.getDataTermino())
						|| campanha2.getDataTermino().equals(dataTerminoAuxiliar)) {

					localDateDataTermino2 = Instant.ofEpochMilli(campanha2.getDataTermino().getTime())
							.atZone(ZoneId.systemDefault()).toLocalDate();
					localDateDataTermino2 = localDateDataTermino2.plusDays(1);
					dataTerminoTimeZero2 = Date.from(localDateDataTermino2.atStartOfDay(defaultZoneId).toInstant());
					campanha2.setDataTermino(dataTerminoTimeZero2);
					dataTerminoAuxiliar = dataTerminoTimeZero2;
				}
				campanhaRepository.save(campanha2);
			}
		}
	}
}
