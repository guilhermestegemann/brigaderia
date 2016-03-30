BRIGADERIA.gerenciarProdutos = new Object();

$(document).ready(function() {
	
	$("#subConteudo").text("");
	
	BRIGADERIA.gerenciarProdutos.cadastrarProduto = function() {
		$("#conteudo").load("resources/cadastro/produtos/produtos.html", function (){
		BRIGADERIA.produtos.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarProdutos.buscar = function () {
		var valorBusca = $("#buscaProduto").val();
		var ativo = $("#filtroAtivo").val();
		if (valorBusca == "") {
			valorBusca = "null";
		}
		
		BRIGADERIA.gerenciarProdutos.exibirProdutos (undefined, valorBusca, ativo);
	};
	
	BRIGADERIA.gerenciarProdutos.exibirProdutos = function(listaDeProdutos, valorBusca, ativo) {
		BRIGADERIA.produtoService.listar({
			valorBusca : valorBusca,
			ativo: ativo,
			success : function (listaDeProdutos) {
				
				var html = "";
				
				for (var i = 0; i < listaDeProdutos.length; i++) {
					
					html += "<tr>"
					  + "<td>" + listaDeProdutos[i].codigoProduto + "</td>"
					  + "<td>" + listaDeProdutos[i].descricao + "</td>"
					  + "<td>" + listaDeProdutos[i].estoque 
					  + "<td>" + "R$ " + listaDeProdutos[i].valorCusto + "</td>"
					  + "<td>" + "R$ " +listaDeProdutos[i].valorVenda + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarProdutos.editarProduto(" + listaDeProdutos[i].codigoProduto + "," + listaDeProdutos.tipoItem + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarProdutos.deletarProduto(" + listaDeProdutos[i].codigo + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoProdutos tbody").html(html);
			
			},
			error : function(err) {
				bootbox.alert(err.responseText);
			} 
		});		   		
	};
	
	BRIGADERIA.gerenciarProdutos.editarProduto = function (codigoProduto, tipoItem) {
		$("#conteudo").load("resources/cadastro/produtos/produtos.html", function (){
			BRIGADERIA.produtos.exibirEdicao(codigoProduto, tipoItem);
		});	
	};
	
	$("#buttonPesquisar").on('click', function(){
		BRIGADERIA.gerenciarProdutos.buscar();
	});
	
	BRIGADERIA.gerenciarProdutos.buscar();
	
	$("#filtroAtivo").on('change',function(){
		BRIGADERIA.gerenciarProdutos.buscar();
	});
});// fecha ready