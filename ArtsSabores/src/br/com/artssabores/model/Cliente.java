package br.com.artssabores.model;

import java.io.Serializable;
import java.util.Calendar;

public class Cliente {

	private Long id;
	private String uid;
	private String nome;
	private String email;
	private String endereco;
	private String senha;
	private Calendar dataDeCadastro;
	private byte[] foto;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(String nome, String email, String senha, String endereco) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.endereco = endereco;
	}

	public Cliente(Long id, String nome, String email, String endereco) {
		this.id = id;
		this.nome = nome;
		this.email = email;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Calendar getDataDeCadastro() {
		return dataDeCadastro;
	}

	public void setDataDeCadastro(Calendar dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		
		return "UID: "+this.uid+" NOME: "+this.nome+" EMAIL: "+this.email;	
				}
}
