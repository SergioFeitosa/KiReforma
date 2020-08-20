package br.com.j4busniess.kireforma.cliente;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.j4busniess.kireforma.campanha.Campanha;
import br.com.j4busniess.kireforma.campanha.CampanhaService;

@Controller
public class ClienteAPIController {


	@Autowired
	private CampanhaService campanhaService;

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/editarCliente")
	public ModelAndView editarCliente(long id) {

		Cliente cliente = clienteService.getClienteById(id);

		ModelAndView mv = new ModelAndView("cliente/editCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}

	@RequestMapping(value = "/deletarCliente")
	public String deletarCliente(long id, RedirectAttributes attributes) {

		Cliente cliente = clienteService.getClienteById(id);
		try {
			clienteService.deleteCliente(cliente);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/clientes";
	}

	// service implementado
	@RequestMapping(value = "/editarCliente", method = RequestMethod.POST)
	public ModelAndView edit(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			ModelAndView mv = new ModelAndView("redirect:/editarCliente");
			mv.addObject("cliente", cliente);
			return mv;
		}

		try {
			clienteService.saveCliente(cliente);
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagem", "Falha na atualização da Campanha. Verifique os campos");
		}

		attributes.addFlashAttribute("mensagem", "Cliente alterado com sucesso");
		ModelAndView mv = new ModelAndView("redirect:/clientes");
		return mv;
	}

	@RequestMapping("/clienteConsumingJsonNome/{nome}")
	public ModelAndView clienteConsumingJsonNome(@PathVariable("nome") String nome) {

		String cliente = this.clienteConsumingJsonNomePrepare(nome);
		ModelAndView mv = new ModelAndView("json/formJson");
		mv.addObject("cliente", cliente);
		return mv;
	}

	
	public String clienteConsumingJsonNomePrepare(String nome) {

		String cliente = null;
		try {
			cliente = this.clienteRespondingJsonNomePrepare(nome);
		} catch (Exception e) {
		}
		return cliente;

	}

	@RequestMapping("/clienteRespondingJsonNome/{nome}")
	public ModelAndView clienteRespondingJsonNome(@PathVariable("nome") String nome) {

		String cliente = this.clienteRespondingJsonNomePrepare(nome);
		ModelAndView mv = new ModelAndView("json/formJson");
		mv.addObject("cliente", cliente);
		return mv;
	}

	
	public String clienteRespondingJsonNomePrepare(String nome) {

		String clientesJson = "";
		List<Cliente> clientes = clienteService.getClienteByNome(nome);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			clientesJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientes);
			//System.out.println(clientesJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clientesJson;
	}

	
	@RequestMapping("/clienteRespondingJsonAll")
	public ModelAndView clienteRespondingJsonAll() {

		String cliente = this.clienteRespondingJsonAllPrepare();
		ModelAndView mv = new ModelAndView("json/formJson");
		mv.addObject("cliente", cliente);
		return mv;
	}

	
	
	public String clienteRespondingJsonAllPrepare() {

		String clientesJson = "";
		List<Cliente> clientes = clienteService.getAllList();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			clientesJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientes);
			//System.out.println(clientesJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clientesJson;
	}

	// service implementado
	@RequestMapping("/clientes")
	public ModelAndView listaClientes() {
		ModelAndView mv = new ModelAndView("cliente/listCliente");

		Iterable<Cliente> clientes = clienteService.getAll();

		mv.addObject("clientes", clientes);
		return mv;

	}

	@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.GET)
	public String form(@ModelAttribute Cliente cliente) {

		return "cliente/formCliente";
	}

	// service implementado
	@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
	public ModelAndView form(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes)
			throws ParseException {

		ModelAndView mv = new ModelAndView("redirect:/cadastrarCliente");

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			mv.addObject("cliente", cliente);
			return mv;
		}

		try {
			clienteService.cadastrarCliente(cliente);
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagem", e.getMessage());
			mv.addObject("cliente", cliente);
			return mv;
		}

		attributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso");
		mv.addObject("cliente", cliente);
		return mv;
	}

	// service implementado
	@RequestMapping(value = "/detalhesCliente/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesCliente(@PathVariable("id") long id, @ModelAttribute Campanha campanha) {

		Cliente cliente = clienteService.getClienteById(id);

		ModelAndView mv = new ModelAndView("cliente/detalhesCliente");
		mv.addObject("cliente", cliente);

		Map<String, Iterable<Campanha>> map = new HashMap<String, Iterable<Campanha>>();
		map = clienteService.getCampanhasInscritos(id);

		mv.addObject("campanhaInscrito", map.get("campanhaInscrito"));
		mv.addObject("campanhaNaoInscrito", map.get("campanhaNaoInscrito"));

		return mv;

	}

	// service implementado
	@RequestMapping(value = "/detalhesCliente/{id}", method = RequestMethod.POST)
	public String detalhesCampanhaPost(@PathVariable("id") long id, @Valid Campanha campanha, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/detalhesCliente/{id}";
		}

		Cliente cliente = clienteService.getClienteById(id);

		cliente = clienteService.saveCliente(cliente);

		if (campanha.getCliente() != null) {
			campanha.getCliente().add(cliente);
		} else {
			campanha.setCliente(new HashSet<Cliente>());
			campanha.getCliente().add(cliente);
		}

		try {
			campanhaService.saveCampanha(campanha);
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagem", "Falha na atualização da Campanha. Verifique os campos");
		}

		attributes.addFlashAttribute("mensagem", "Cliente associado a Campanha com sucesso");
		return "redirect:/detalhesCliente/{id}";
	}

	// service implementado
	@RequestMapping(value = "/associarCliente/{clienteId}/{campanhaId}")
	public String associarCampanha(@PathVariable("clienteId") long clienteId,
			@PathVariable("campanhaId") long campanhaId, RedirectAttributes attributes) {

		Cliente cliente = clienteService.getClienteById(clienteId);

		Campanha campanha = campanhaService.getCampanhaById(campanhaId);

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

		campanha = campanhaService.saveCampanha(campanha);

		return "redirect:/detalhesCliente/{clienteId}";
	}

	// service implementado
	@RequestMapping(value = "/desassociarCliente/{clienteId}/{campanhaId}")
	public String desassociarCampanha(@PathVariable("clienteId") long clienteId,
			@PathVariable("campanhaId") long campanhaId, RedirectAttributes attributes) {

		Cliente cliente = clienteService.getClienteById(clienteId);

		Campanha campanha = campanhaService.getCampanhaById(campanhaId);

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

		campanha = campanhaService.saveCampanha(campanha);

		return "redirect:/detalhesCliente/{clienteId}";
	}

}
