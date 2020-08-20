package br.com.j4business.kireforma.cliente;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.j4business.kireforma.campanha.Campanha;
import br.com.j4business.kireforma.time.Esquadrao;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByCampanha(Campanha campanha); 

	List<Cliente> findByEmail(String email); 

	List<Cliente> findByNome(String nome); 
	
	List<Cliente> findByEsquadrao(Esquadrao esquadrao);

}
