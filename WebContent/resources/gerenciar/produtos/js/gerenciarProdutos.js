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
		if (valorBusca == "") {
			valorBusca = "null";
		}
		
		BRIGADERIA.gerenciarProdutos.exibirProdutos (undefined, valorBusca);
	};
	
	BRIGADERIA.gerenciarProdutos.exibirProdutos = function(listaDeProdutos, valorBusca) {
		
		BRIGADERIA.produtoService.listar({
			valorBusca : valorBusca,
			success : function (listaDeProdutos) {
				
				var html = "";
				
				for (var i = 0; i < listaDeProdutos.length; i++) {
					
					html += "<tr>"
					  + "<td>" + listaDeProdutos[i].codigo + "</td>"
					  + "<td>" + listaDeProdutos[i].descricao + "</td>"
					  + "<td>" + listaDeProdutos[i].estoque 
					  + "<td>" + "R$ " + listaDeProdutos[i].valorCusto + "</td>"
					  + "<td>" + "R$ " +listaDeProdutos[i].valorVenda + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarProdutos.editarProduto(" + listaDeProdutos[i].codigo + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarProdutos.deletarProduto(" + listaDeProdutos[i].codigo + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoProdutos tbody").html(html);
			
			},
			error : function(err) {
				console.log(err);
			} 
		});		   		
	};
	
	$("#buttonPesquisar").on('click', function(){
		BRIGADERIA.gerenciarProdutos.buscar();
	});
	
	BRIGADERIA.gerenciarProdutos.buscar();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});// fecha ready