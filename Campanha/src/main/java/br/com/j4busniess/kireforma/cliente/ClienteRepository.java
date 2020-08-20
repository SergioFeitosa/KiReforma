package br.com.j4busniess.kireforma.cliente;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.j4busniess.kireforma.campanha.Campanha;
import br.com.j4busniess.kireforma.time.Esquadrao;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByCampanha(Campanha campanha); 

	List<Cliente> findByEmail(String email); 

	List<Cliente> findByNome(String nome); 
	
	List<Cliente> findByEsquadrao(Esquadrao esquadrao);

}
