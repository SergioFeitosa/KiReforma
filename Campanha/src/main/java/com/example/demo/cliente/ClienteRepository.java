package com.example.demo.cliente;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.campanha.Campanha;
import com.example.demo.time.Esquadrao;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByCampanha(Campanha campanha); 

	List<Cliente> findByEmail(String email); 

	List<Cliente> findByNome(String nome); 
	
	List<Cliente> findByEsquadrao(Esquadrao esquadrao);

}
