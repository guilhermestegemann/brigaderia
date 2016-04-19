package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCFichaTecnicaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.FichaTecnica;
import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.ItemFichaTecnicaVO;
import br.com.brigaderia.validacoes.ValidaFichaTecnica;


public class FichaTecnicaService {
	
	public List<IngredientesVO> buscarIngredientes() throws SQLException  {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarIngredientes();
		}finally {
			conec.fecharConexao();
		}
	}
	
	public void adicionar(FichaTecnica fichaTecnica) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
			ValidaFichaTecnica validaFichatecnica = new ValidaFichaTecnica();
			validaFichatecnica.validar(fichaTecnica);
			int codFichaTecnica = jdbcFichaTecnica.adicionar(fichaTecnica);
			List<ItemFichaTecnicaVO> listIngredientes = new ArrayList<>();
			listIngredientes =	fichaTecnica.getIngredientes();
			
			for(int i = 0; i < listIngredientes.size(); i++) {
				jdbcFichaTecnica.adicionarIngredientes(codFichaTecnica, listIngredientes.get(i).getCodigo(), listIngredientes.get(i).getQtde());
			}
			
		}finally{
			conec.fecharConexao();
		}
	}
	
	public FichaTecnica buscarFichaTecnicaPeloCodigoProduto (int codigoProduto) throws SQLException{
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
			FichaTecnica fichaTecnica = jdbcFichaTecnica.buscarPeloCodigoProduto(codigoProduto);
			
			return fichaTecnica;
		}finally{
			conec.fecharConexao();
		}	
	}
	
	public void atualizarFichaTecnica (FichaTecnica fichaTecnica) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
			ValidaFichaTecnica validaFichatecnica = new ValidaFichaTecnica();
			validaFichatecnica.validar(fichaTecnica);
			jdbcFichaTecnica.atualizar(fichaTecnica);
			jdbcFichaTecnica.deletarIngredientes(fichaTecnica.getCodigoFichaTecnica());
			List<ItemFichaTecnicaVO> listIngredientes = new ArrayList<>();
			listIngredientes =	fichaTecnica.getIngredientes();
			
			for(int i = 0; i < listIngredientes.size(); i++) {
				jdbcFichaTecnica.adicionarIngredientes(fichaTecnica.getCodigoFichaTecnica(), listIngredientes.get(i).getCodigo(), listIngredientes.get(i).getQtde());
			}
		}finally{
			conec.fecharConexao();
		}
	}
}


