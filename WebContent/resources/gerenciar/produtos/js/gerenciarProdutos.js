BRIGADERIA.gerenciarProdutos = new Object();

$(document).ready(function() {
	
	$("#subConteudo").text("");
	
	BRIGADERIA.gerenciarProdutos.cadastrarProduto = function() {
		$("#conteudo").load("resources/cadastro/produtos/produtos.html", function (){
		BRIGADERIA.produtos.exibirFormulario();
		});
	};
});// fecha ready