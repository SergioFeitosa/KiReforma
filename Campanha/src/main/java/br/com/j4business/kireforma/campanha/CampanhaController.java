package br.com.j4business.kireforma.campanha;

import java.text.ParseException;
import java.util.HashMap;
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

import br.com.j4business.kireforma.cliente.Cliente;

@Controller
public class CampanhaController {

	@Autowired
	private CampanhaService campanhaService;


	@RequestMapping(value = "/editarCampanha")
	public ModelAndView editarCampanha(long id) {

		Campanha campanha = campanhaService.getCampanhaById(id);

		ModelAndView mv = new ModelAndView("campanha/editCampanha");
		mv.addObject("campanha", campanha);
		return mv;
	}
	

	@RequestMapping(value = "/cadastrarCampanha", method = RequestMethod.GET)
	public String form(@ModelAttribute Campanha campanha) {
		
		return "campanha/formCampanha";
	}

	// service implementado	
	@RequestMapping(value = "/cadastrarCampanha", method = RequestMethod.POST)
	public String form(@Valid Campanha campanha, BindingResult result, RedirectAttributes attributes) throws ParseException {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarCampanha";
		}
		
		try {
			campanhaService.cadastrarCampanha(campanha);			
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagem",e.getMessage());
			return "redirect:/cadastrarCampanha";
		}
		
		attributes.addFlashAttribute("mensagem", "Campanha incluida com sucesso");
		return "redirect:/cadastrarCampanha";
	}

	
	// service implementado
	@RequestMapping("/campanhas")
	public ModelAndView listaCampanhas() {
		ModelAndView mv = new ModelAndView("campanha/listCampanha");
		
		Iterable<Campanha> campanhas = campanhaService.getAllCampanhaNaoVencidas();
		
		mv.addObject("campanhas", campanhas);
		return mv;

	}

	// service implementado
	@RequestMapping(value = "/detalhesCampanha/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesCampanha(@PathVariable("id") long id, @ModelAttribute Cliente cliente) {

		
		Campanha campanha = campanhaService.getCampanhaById(id);
		
		ModelAndView mv = new ModelAndView("campanha/detalhesCampanha");
		mv.addObject("campanha", campanha);

		Map<String, Iterable<Cliente>> map = new HashMap<String, Iterable<Cliente>>();
		map = campanhaService.getClientesInscritos(campanha);
		
		mv.addObject("clienteInscrito", map.get("clienteInscrito"));
		mv.addObject("clienteNaoInscrito", map.get("clienteNaoInscrito"));

		return mv;
		
	}

	// service implementado
	@RequestMapping(value = "/detalhesCampanha/{id}", method = RequestMethod.POST)
	public String detalhesCampanhaPost(@PathVariable("id") long id, @Valid Cliente cliente, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/detalhesCampanha/{id}";
		}

		Campanha campanha = campanhaService.getCampanhaById(id);

		try {
			campanha = campanhaService.detalhesCampanha(id, cliente);			
		} catch (Exception e) {
			attributes.addFlashAttribute("Mensagem : " + e.getMessage());
		}

		try {
			campanhaService.saveCampanha(campanha);			
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagem", "Falha na atualização da Campanha. Verifique os campos");
		}

		attributes.addFlashAttribute("mensagem", "Cliente associado a Campanha com sucesso");
		return "redirect:/detalhesCampanha/{id}";
	}

	// service implementado
	@RequestMapping(value = "/deletarCampanha")
	public String deletarCampanha(long id, RedirectAttributes attributes) {

		Campanha campanha = campanhaService.getCampanhaById(id);

		campanhaService.deleteCampanha(campanha);			

		return "redirect:/campanhas";
	}

	// service implementado
	@RequestMapping(value = "/associarCampanha/{clienteId}/{campanhaId}")
	public String associarCampanha(@PathVariable("clienteId") long clienteId, @PathVariable("campanhaId") long campanhaId, RedirectAttributes attributes) throws Exception {

		try {
			campanhaService.associarCampanha(clienteId, campanhaId);
		} catch (Exception e) {
			throw new Exception("Mensagem : "+ e.getMessage());
		}
		
		return "redirect:/detalhesCampanha/{campanhaId}";
	}

	
	// service implementado
	@RequestMapping(value = "/desassociarCampanha/{clienteId}/{campanhaId}")
	public String desassociarCampanha(@PathVariable("clienteId") long clienteId, @PathVariable("campanhaId") long campanhaId, RedirectAttributes attributes) throws Exception {

		try {
			campanhaService.desassociarCampanha(clienteId, campanhaId);
		} catch (Exception e) {
			throw new Exception("Mensagem : "+ e.getMessage());
		}
		

		return "redirect:/detalhesCampanha/{campanhaId}";
	}

	
}
