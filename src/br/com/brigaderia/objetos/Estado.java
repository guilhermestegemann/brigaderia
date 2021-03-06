package br.com.brigaderia.objetos;

import java.io.Serializable;

public class Estado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	
	private int codigo;
	private String nome;
	private String uf;
	
	public Estado(int codigo, String nome, String uf) {
		this.codigo = codigo;
		this.nome = nome;
		this.uf = uf;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
}