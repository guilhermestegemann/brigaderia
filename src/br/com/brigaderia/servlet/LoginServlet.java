package br.com.brigaderia.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.cripto.CodificaBase64;
import br.com.brigaderia.cripto.CodificaMD5;
import br.com.brigaderia.jdbc.JDBCUsuarioDAO;
import br.com.brigaderia.jdbcinterface.UsuarioDAO;
import br.com.brigaderia.objetos.Usuario;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try{
			
			String context = request.getContextPath();
			//System.out.println(context);
			
			// Se a acao for login
			if(request.getParameter("acao").equals("login")) {
				
				// Recupera o login e senha do usuario
				String usuario = request.getParameter("usuario");
				String senhabase64 = request.getParameter("senha");
				System.out.println("Enviada  "+senhabase64);
				// Descriptografa senha base64
				String senha = null;
				CodificaBase64 base64 = new CodificaBase64();
				senha = base64.decrypBase64(senhabase64);
				System.out.println("decript   "+senha);
				// Criptografa senha para MD5
				String senhamd5 = null;
				CodificaMD5 md5 = new CodificaMD5();
				senhamd5 = md5.convertToMD5(senha);
				
				
				System.out.println(" md5  "+senhamd5);
				
				// Abre conexao com bd
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();

				UsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
				Usuario user = jdbcUsuario.buscar(usuario);
				conec.fecharConexao();
				
				if(usuario.equals(user.getEmail()) &&
						senhamd5.equals(user.getSenha())) {
								
					HttpSession sessao = request.getSession();
					
					// setando um atributo da sessao
					sessao.setAttribute("login", request.getParameter("usuario"));
					sessao.setAttribute("id", user.getId());
					
					// como obtive sucesso, chamo a página principal
					response.sendRedirect(context+"/resources/index.html");
					
				} else {
					
					//request.getSession().setAttribute("msg", "Usuario ou senha incorretos!!!");
					request.setAttribute("msg", "Usuario ou senha incorretos!!!");
					System.out.println("Usuario ou senha incorretos!");
					
					//response.sendRedirect(context+"/index.jsp?erro=1");
					RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?erro=1");
					rd.forward(request, response);
				}
				
			}else if(request.getParameter("acao").equals("logout")) {
				HttpSession sessao = request.getSession();
				sessao.invalidate();
				response.sendRedirect(context+"/index.jsp");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
