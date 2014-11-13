package br.com.artssabores.model;

import java.io.Serializable;

public class Cesta implements Serializable{
	Long uid;
	String id;
	String nome;
	String descricao;
	Double preco;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long id) {
		this.uid = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
