package br.com.j4busniess.kireforma.cliente;

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

import br.com.j4busniess.kireforma.campanha.Campanha;
import br.com.j4busniess.kireforma.time.Esquadrao;

@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 8618093653086244448L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataNasc;
	@NotNull
	private Esquadrao esquadrao;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Campanha> campanha;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Esquadrao getEsquadrao() {
		return esquadrao;
	}

	public void setEsquadrao(Esquadrao esquadrao) {
		this.esquadrao = esquadrao;
	}

	public Set<Campanha> getCampanha() {
		return campanha;
	}

	public void setCampanha(Set<Campanha> campanha) {
		this.campanha = campanha;
	}

}
