package br.com.brigaderia.exception;



public class QtdeProdutoFichaTecnicaInválida extends BrigaderiaException{

	private static final long serialVersionUID = 1L;
	
	
	public QtdeProdutoFichaTecnicaInválida(){
		super("Quantidade da Ficha Técnica deve ser maior que zero!");
	}
	public QtdeProdutoFichaTecnicaInválida(String msg){
		super(msg);
	}
	public QtdeProdutoFichaTecnicaInválida(Throwable t){
		super(t);
	}
	

}