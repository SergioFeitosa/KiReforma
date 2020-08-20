package br.com.j4business.kireforma.cliente;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
