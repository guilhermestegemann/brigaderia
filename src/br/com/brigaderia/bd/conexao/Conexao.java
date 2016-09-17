package br.com.brigaderia.bd.conexao;

import java.sql.Connection;

public class Conexao {
	
	private Connection conexao;
	//M�todo que abre a conex�o com o banco de dados
	public Connection abrirConexao() {
		try{
			//instru��o que identifica o tipo de driver utulizado para a conex�o como banco de dados.
			Class.forName("org.gjt.mm.mysql.Driver");
			//Notem o endere�amento feito do servidor de banco de dados e driver.
			conexao = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/brigaderia", "root", "root");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}
	//M�todo para fechar a conex�o com o banco de dados.
	public void fecharConexao() {
		try{
			conexao.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}