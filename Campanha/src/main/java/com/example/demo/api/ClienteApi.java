package com.example.demo.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteApi {

	private int id;
	private String nome;
	private String email;
	private String dataNasc;
	private String esquadrao;

	public ClienteApi() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEsquadrao() {
		return esquadrao;
	}

	public void setEsquadrao(String esquadrao) {
		this.esquadrao = esquadrao;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", dataNasc=" + dataNasc + ", esquadrao="
				+ esquadrao + "]";
	}
}
