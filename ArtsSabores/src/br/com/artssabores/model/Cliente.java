package br.com.artssabores.model;

import java.io.Serializable;
import java.util.Calendar;


public class Cliente {

	private Long id;

	private String nome;
	private String email;
	private String endereco;
	private Calendar dataDeCadastro;
	private byte[] foto;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(String nome, String endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
