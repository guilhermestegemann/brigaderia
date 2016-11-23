package br.com.brigaderia.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroConexao implements Filter {

	private String contextPath;
	
	public void destroy() {
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.contextPath = filterConfig.getServletContext().getContextPath();
	}
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain chain) 
					throws IOException, ServletException{
		
		/*
		 * O método getContextPath é responsavel por retornar o contexto
		 * da URL que realizou a requisiçao. 
		 */
		//String context = request.getServletContext().getContextPath();
		
		
		try{
			/*
			 * O metodo getSession é responsavel por pegar a sessao ativa.
			 * Aqui foi necessario fazer um casting pois o objeto request 
			 * � do tipo ServletRequest e nao HttpServletRequest como no 
			 * Servlet onde voce utiliza o m�todo em questao sem o uso 
			 * do casting.
			 */
			HttpSession session = ((HttpServletRequest) request).getSession();
			
			String usuario = null;
			
			if(session != null){
				usuario = (String) session.getAttribute("login");
			}
			if(usuario == null){
				/*
				 * Aqui esta sendo setado um atributo na sessao para que depois
				 * possamos exibir uma mensagem ao usuario.
				 */
				session.setAttribute("msg", "Você não está logado no sistema!");
				//System.out.println(session.getAttribute("msg"));
				/*
				 * Utilizamos o metodo sendRedirect que altera a URL do navegador
				 * para posicionar o usuario na tela de login, que neste caso � a
				 * p�gina index.html. Note que não precisamos utilizar o recurso 
				 * "../../" para informar o caminho da p�gina index.html, a variavel
				 * do contexto ja posiciona no inicio da URL.
				 */
				
				//((HttpServletResponse) response).sendRedirect(contextPath+"/login.html");
				
			}else{
				/*
				 * Caso exista um usuario valido (diferente de nulo) envia a requisi��o
				 * para a pagina que se deseja acessar, ou seja, permite o acesso, 
				 * deixa passar.
				 */
				chain.doFilter(request, response);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
