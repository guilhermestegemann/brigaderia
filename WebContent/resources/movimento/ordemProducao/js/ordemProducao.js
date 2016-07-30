BRIGADERIA.ordemProducao = new Object();

$(document).ready(function (){
	
	var produtos;
	var produtoArray = []; //utilizado ao inserir produtos no formulario.
	
	
	$("#produtos").on('change',function(){
		for (var i = 0; i < produtos.length; i++) {
			if (produtos[i].codigoProduto == $("#produtos").val()) {
				$("#unEstoque").val(produtos[i].unEstoque);
			}else if ($("#produtos").val() == ""){
				$("#unEstoque").val("");
			}
		}
	});
	
	BRIGADERIA.ordemProducao.listarProdutos = function () { 
		BRIGADERIA.ordemProducaoService.listarProdutos({
			success: function(data) {
				var html = "";
				produtos = data;//atribui ao objeto global o valor retornado da lista. Utilizado pra edição/inserção de itens da Ordem
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigoProduto + "'>" + data[i].descricao + "</option>";
				}
				$("#produtos").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.ordemProducao.exibirFormulario = function () {
		BRIGADERIA.ordemProducao.listarProdutos();
	};
	
	BRIGADERIA.ordemProducao.incluirProduto = function () {
		var expNumeros = /^[0-9]+$/;
		if ($("#produtos").val() == "") {
			bootbox.alert("Selecione o Produto!");
		}else if (!expNumeros.test($("#qtdeProduto").val().replace(",","").replace(".",""))) {
			bootbox.alert("Quantida inválida.");
		}else{
			var html = "";
			var descricao = "";
			var unEstoque = "";
			
			for (var i = 0; i < produtos.length; i++) {
		
				if ((produtos[i].codigoProduto == $("#produtos").val()) && (unEstoque == "")) {
					unEstoque = produtos[i].unEstoque;
					descricao = produtos[i].descricao; 
				}
			}
			
			if (unEstoque != "") {
				html =  "<tr class='itemPerda'>"
						  + "<td >" + $("#produtos").val() + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + $("#qtdeProduto").val().replace(",",".") + "</td>"
						  + "<td>" + unEstoque + "</td>"
						  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.ordemProducao.editarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#qtdeProduto").val().replace(",",".")) + "," + "\"" + unEstoque + "\"" + ")' aria-hidden='true'></i></a>"
						  	  + "<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.ordemProducao.deletarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + ")' aria-hidden='true'></i></a></td>"
					  + "</tr>";
				
				var prod = {
						codigoProduto: $("#produtos").val(),
						qtde: $("#qtdeProduto").val()
				} ;
				
				produtoArray.push(prod);
				$("#itensOrdemProducao tbody").append(html);
				$("#produtos option:selected").remove();
				$("#qtdeProduto").val("");
				$("#unEstoque").val("");
			}
		}
	};
	
	BRIGADERIA.ordemProducao.editarProduto = function (handler, codigo, descricao, qtde, unEstoque) {
		
		$("#qtdeProduto").val(parseFloat(qtde));
		$("#unEstoque").val(unEstoque);
		
		for (var i = 0; i < produtoArray.length; i++) {
			if (produtoArray[i].codigoProduto == codigo) {
				produtoArray.splice(i, 1);
			}
		}
		
		$(handler).closest('tr').remove();//exclui o tr mais proximo.
		window.event.preventDefault();
		$('#produtos').append('<option value="' + codigo + '" selected="unselected">' + descricao + '</option>');
	};
	
	BRIGADERIA.ordemProducao.deletarProduto = function (handler, codigo, descricao) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o produto?", 
			callback: function(confirma){
				if (confirma) {
					for (var i = 0; i < produtoArray.length; i++) {
						if (produtoArray[i].codigoProduto == codigo) {
							produtoArray.splice(i, 1);
						}
					}
					
					$(handler).closest('tr').remove();//exclui o tr mais proximo.
					//window.event.preventDefault();
					$('#produtos').append('<option value="' + codigo + '">' + descricao + '</option>');
				}
			}
		});
	};
	
	BRIGADERIA.ordemProducao.adicionar = function () {
		
		var newOrdemProducao = {
				itemOrdemProducao : produtoArray
		};
		if (newOrdemProducao.itemOrdemProducao == "") {
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.ordemProducaoService.adicionar(newOrdemProducao);
		}
	};
	
	BRIGADERIA.ordemProducao.importarPedido = function(){
		BRIGADERIA.ordemProducaoService.listarPedidosImportacao ({
			success : function (listaPedido) {
				console.log(listaPedido);
			},
			error : function(error)  {
				console.log(error);
			}
		})
	};
	
});