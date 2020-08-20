package br.com.j4business.kireforma.campanha;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.kireforma.cliente.Cliente;
import br.com.j4business.kireforma.time.Esquadrao;

@Repository
public interface CampanhaRepository extends CrudRepository<Campanha, Long>{

	Iterable<Campanha> findByCliente(Cliente cliente); 

	Iterable<Campanha> findByNome(String nome); 

	@Query("SELECT campanha FROM Campanha campanha WHERE  campanha.dataTermino >= :dataTermino" )
	Iterable<Campanha> findAllNaoVencidas(@Param("dataTermino") Date dataTermino);
	
	@Query("SELECT campanha FROM Campanha campanha WHERE  campanha.dataInicio >= :dataInicioTimeZero and campanha.dataTermino <= :dataTerminoTimeZero order by campanha.dataTermino desc ")
	Iterable<Campanha> findAllMesmaVigencia(@Param("dataInicioTimeZero") Date dataInicioTimeZero,@Param("dataTerminoTimeZero") Date dataTerminoTimeZero);
	
	Iterable<Campanha> findByEsquadrao(Esquadrao esquadrao);
	
}
