package com.example.demo.api;

import com.example.demo.cliente.Cliente;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ClienteConsumingJson {

	static String webService = "http://localhost:3001/cliente";
	static int codigoSucesso = 200;

	public static Cliente buscaClienteNome(String nome) throws Exception {
		String urlParaChamada = webService; // + nome + "/json";

		try {
			URL url = new URL(urlParaChamada);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

			if (conexao.getResponseCode() != codigoSucesso)
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			String jsonEmString = Util.converteJsonEmString(resposta);

			Gson gson = new Gson();
			Cliente cliente = gson.fromJson(jsonEmString, Cliente.class);

			return cliente;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	public static String buscaClienteAll() throws Exception {
		String urlParaChamada = webService;

		try {
			URL url = new URL(urlParaChamada);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

			if (conexao.getResponseCode() != codigoSucesso)
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			String jsonEmString = Util.converteJsonEmString(resposta);

			return jsonEmString;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}
}