package br.com.cadastro.modelo;

import java.io.Serializable;

public class Aluno implements Serializable{
	
	private Long id;
	private String nome;
	private String telefone;
	private String endereco;
	private String site;
	private Double nota;
	private String caminhoFoto;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}
	public String getCaminhoFoto() {
		return caminhoFoto;
	}
	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId(){
		return this.id;
	}
	
	@Override
	public String toString() {
		return id + " - " + nome;
	}
	

}
