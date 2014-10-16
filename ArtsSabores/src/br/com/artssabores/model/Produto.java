package br.com.artssabores.model;

import java.io.Serializable;

public class Produto implements Serializable{
	
	/**
	 * POJO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;

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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}
}
