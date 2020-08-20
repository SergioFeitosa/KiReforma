package br.com.j4busniess.kireforma.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.j4busniess.kireforma.cliente.ClienteController;

@Controller
public class JsonController {

	@Autowired
	ClienteController clienteController;
	
	@RequestMapping(value = "/testeJson", method = RequestMethod.GET)
	public ModelAndView JsonMain() {
		
		ModelAndView mv = new ModelAndView("json/formJson");
		
		return mv;
	}

	@RequestMapping(value = "/testeJson", method = RequestMethod.POST)
	public ModelAndView JsonMain(String nomeConsuming, String nomeResponding) {
		
		ModelAndView mv = new ModelAndView("/json/formJson");
		String resultNome = null;
		
		if (nomeConsuming != null ) {
			resultNome = clienteController.clienteConsumingJsonNomePrepare(nomeConsuming);
		}
		
		
		if (nomeResponding != null ) {
			resultNome = clienteController.clienteRespondingJsonNomePrepare(nomeResponding);
		}

		mv.addObject("resultJson", resultNome);

		return mv;
	}
}
