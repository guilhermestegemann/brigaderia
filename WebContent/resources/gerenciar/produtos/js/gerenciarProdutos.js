BRIGADERIA.gerenciarProdutos = new Object();

$(document).ready(function() {
	
	$("#subConteudo").text("");
	
	BRIGADERIA.gerenciarProdutos.cadastrarProduto = function() {
		$("#conteudo").load("resources/cadastro/produtos/produtos.html", function (){
		BRIGADERIA.produtos.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarProdutos.buscar = function () {
		var tipoItem = $("#tipoItemFiltro").val();
		var ativo = $("#filtroAtivo").val();
		var valorBusca = $("#buscaProduto").val();
		
		if (valorBusca == "") {
			valorBusca = "null";
		}
		
		BRIGADERIA.gerenciarProdutos.exibirProdutos (undefined, valorBusca, ativo, tipoItem);
	};
	
	BRIGADERIA.gerenciarProdutos.exibirProdutos = function(listaDeProdutos, valorBusca, ativo, tipoItem) {
		BRIGADERIA.produtoService.listar({
			valorBusca : valorBusca,
			ativo: ativo,
			tipoItem: tipoItem,
			success : function (listaDeProdutos) {
				
				var html = "";
				
				for (var i = 0; i < listaDeProdutos.length; i++) {
					
					html += "<tr>"
					  + "<td>" + listaDeProdutos[i].codigoProduto + "</td>"
					  + "<td>" + listaDeProdutos[i].descricao + "</td>"
					  + "<td>" + listaDeProdutos[i].estoque 
					  + "<td>" + "R$ " + listaDeProdutos[i].valorCusto + "</td>"
					  + "<td>" + "R$ " +listaDeProdutos[i].valorVenda + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarProdutos.editarProduto(" + listaDeProdutos[i].codigoProduto + "," + listaDeProdutos[i].tipoItem + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarProdutos.deletarProduto(" + listaDeProdutos[i].codigoProduto + "," + listaDeProdutos[i].tipoItem + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoProdutos tbody").html(html);
			
			},
			error : function(err) {
				bootbox.alert(err.responseText);
			} 
		});		   		
	};
	
	BRIGADERIA.gerenciarProdutos.deletarProduto = function (codigo, tipoItem) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o Produto?", 
			callback: function(confirma){
				if (confirma == true) {
					BRIGADERIA.produtoService.deletar({
						codigo : codigo,
						tipoItem : tipoItem,
						success: function (successo) {
							bootbox.alert(successo);
							BRIGADERIA.gerenciarProdutos.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					})
				}
			}
		});
	}
	
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
	
	$("#tipoItemFiltro").on('change',function(){
		BRIGADERIA.gerenciarProdutos.buscar();
	});
	
	BRIGADERIA.produtos.listarTipoItem("#tipoItemFiltro");
	
	
	
});// fecha ready