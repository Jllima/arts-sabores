package br.com.artssabores.model;

import java.io.Serializable;

public class Pedido implements Serializable{
	
	
	private Long id;

	private Cliente cliente;

	private Cesta cesta;
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}
	
	public Pedido(Cliente cliente, Cesta cesta) {
		this.cliente = cliente;
		this.cesta = cesta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cesta getCesta() {
		return cesta;
	}

	public void setCesta(Cesta cesta) {
		this.cesta = cesta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
