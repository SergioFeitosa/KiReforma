package br.com.j4business.kireforma.cliente;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.kireforma.campanha.Campanha;

@RestController
public class ClienteAPIController {


	@Autowired
	private ClienteService clienteService;

	
	// service implementado
	@GetMapping("/api/clientes")
	public ResponseEntity<Iterable<Cliente>> listaAPIClientes() {

		Iterable<Cliente> clientes = clienteService.getAll();

		return ResponseEntity.status(HttpStatus.OK).body(clientes);

	}

	// service implementado
	@GetMapping("/api/clientes/{id}")
	public ResponseEntity<Cliente> listaAPIClientesById(@PathVariable("id") long id) {

		Cliente cliente = clienteService.getClienteById(id);

		return ResponseEntity.status(HttpStatus.OK).body(cliente);

	}

	// service implementado
	@PostMapping("/api/clientes")
	public ResponseEntity<Iterable<Cliente>> CriaAPIClientes(@RequestBody Cliente cliente) {

		try {
			clienteService.cadastrarCliente(cliente);
		} catch (Exception e) {
		}
		
		Iterable<Cliente> clientes = clienteService.getAll();

		return ResponseEntity.status(HttpStatus.OK).body(clientes);

	}

	@PutMapping("/api/clientes/{id}")
	public ResponseEntity<Cliente> editarAPICliente(@PathVariable("id") long id,@RequestBody Cliente cliente) {

		Cliente clienteCad = clienteService.getClienteById(id);
		
		clienteCad.setNome(cliente.getNome());
		clienteCad.setEmail(cliente.getEmail());
		clienteCad.setDataNasc(cliente.getDataNasc());
		clienteCad.setEsquadrao(cliente.getEsquadrao());
		clienteCad.setCampanha(cliente.getCampanha());

		clienteService.saveCliente(clienteCad);

		return ResponseEntity.status(HttpStatus.OK).body(clienteCad);
	}

	@DeleteMapping("/api/clientes/{id}")
	public ResponseEntity<Cliente> deletarAPICliente(@PathVariable("id") long id) {

		Cliente cliente = clienteService.getClienteById(id);
		try {
			clienteService.deleteCliente(cliente);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}


}
