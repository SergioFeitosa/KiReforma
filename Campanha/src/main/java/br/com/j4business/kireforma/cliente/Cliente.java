package br.com.j4business.kireforma.cliente;

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

import br.com.j4business.kireforma.campanha.Campanha;
import br.com.j4business.kireforma.time.Esquadrao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED )
@NoArgsConstructor

public class Cliente implements Serializable {

	private static final long serialVersionUID = 8618093653086244448L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter	
	private long id;
	@NotBlank
	@Getter @Setter	
	private String nome;
	@NotBlank
	@Getter @Setter	
	private String email;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Getter @Setter	
	private Date dataNasc;
	@NotNull
	@Getter @Setter	
	private Esquadrao esquadrao;

	@ManyToMany(cascade = CascadeType.ALL)
	@Getter @Setter	
	private Set<Campanha> campanha;

}
