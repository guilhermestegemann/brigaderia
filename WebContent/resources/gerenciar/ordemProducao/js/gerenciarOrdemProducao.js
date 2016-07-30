BRIGADERIA.gerenciarOrdemProducao = new Object();

$(document).ready(function (){
	
	$("#subConteudo").text(""); //inicia div vazia
	
	BRIGADERIA.gerenciarOrdemProducao.lancarOrdemProducao = function() {
		$("#conteudo").load("resources/movimento/ordemProducao/ordemProducao.html", function (){
			BRIGADERIA.ordemProducao.exibirFormulario();
		});
	};
});