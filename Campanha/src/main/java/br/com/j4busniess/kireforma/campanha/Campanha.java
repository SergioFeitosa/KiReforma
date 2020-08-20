package br.com.j4busniess.kireforma.campanha;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4busniess.kireforma.cliente.Cliente;
import br.com.j4busniess.kireforma.time.Esquadrao;

@Entity
public class Campanha implements Serializable {

	private static final long serialVersionUID = 1577875995263833134L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank
	private String nome;
	@NotNull
	private Esquadrao esquadrao;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInicio;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataTermino;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Cliente> cliente;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Esquadrao getEsquadrao() {
		return esquadrao;
	}

	public void setEsquadrao(Esquadrao esquadrao) {
		this.esquadrao = esquadrao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Set<Cliente> getCliente() {
		return cliente;
	}

	public void setCliente(Set<Cliente> cliente) {
		this.cliente = cliente;
	}

}
