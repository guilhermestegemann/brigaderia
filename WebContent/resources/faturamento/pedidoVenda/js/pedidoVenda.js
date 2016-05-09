BRIGADERIA.pedidoVenda = new Object();

$(document).ready( function () {
	
	var produtos;
	var produtoArray = []; //utilizado ao inserir produtos no formulário
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$("#produtos").on('change',function(){
		for (var i = 0; i < produtos.length; i++) {
			if (produtos[i].codigoProduto == $("#produtos").val()) {
				$("#unEstoque").val(produtos[i].unEstoque);
				$("#unitario").val(parseFloat(produtos[i].valorVenda).toFixed(2));
				if ($("#unitario").val() != "") {
					$("#totalItemPedidoVenda").val($("#unitario").val().replace(",",".") * ($("#qtdeProduto").val().replace(",",".")));
				}
				if ($("#totalItemPedidoVenda").val() != "") {
					$("#totalItemPedidoVenda").val(parseFloat($("#totalItemPedidoVenda").val()).toFixed(2));
				}
			}
		}
		
	});
	
	$("#qtdeProduto").on('blur', function(){
		if ($("#unitario").val() != "") {
			$("#totalItemPedidoVenda").val($("#unitario").val().replace(",",".") * ($("#qtdeProduto").val().replace(",",".")));
		}
		if ($("#totalItemPedidoVenda").val() != "") {
			$("#totalItemPedidoVenda").val(parseFloat($("#totalItemPedidoVenda").val()).toFixed(2));
		}
	});
	
	$("#unitario").on('blur', function(){
		if ($("#unitario").val() != "") {
			$("#totalItemPedidoVenda").val(parseFloat($("#unitario").val().replace(",",".") * $("#qtdeProduto").val().replace(",",".")).toFixed(2));
		}
		if ($("#unitario").val().replace(",",".") > 0) {
			$("#unitario").val(parseFloat($("#unitario").val().replace(",",".")).toFixed(2));
		}
	});

	
	BRIGADERIA.pedidoVenda.listarProdutos = function () { 
		BRIGADERIA.pedidoVendaService.listarProdutos({
			success: function(data) {
				var html = "";
				produtos = data;//atribui ao objeto global o valor retornado da lista. Utilizado pra edição/inserção de itens de pedido
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
	
	BRIGADERIA.pedidoVenda.listarClientes = function () { 
		BRIGADERIA.pedidoVendaService.listarClientes({
			success: function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].nome + "</option>";
				}
				$("#clientes").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.pedidoVenda.exibirFormulario = function () {
		BRIGADERIA.pedidoVenda.listarProdutos();
		BRIGADERIA.pedidoVenda.listarClientes();
		$("#totalPedidoVenda").val("0.00");
	};
	
	BRIGADERIA.pedidoVenda.incluirProduto = function () {
		var expNumeros = /^[0-9]+$/;
		if ($("#produtos").val() == "") {
			bootbox.alert("Selecione o Produto!");
		}else if (!expNumeros.test($("#qtdeProduto").val().replace(",","").replace(".",""))) {
			bootbox.alert("Quantida inválida.");
		}else if (($("#unitario").val() == "") || ($("#unitario").val() <= 0)) {
			bootbox.alert("Unitário inválido.");
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
				html =  "<tr class='itemPedidoVenda'>"
						  + "<td >" + $("#produtos").val() + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + unEstoque + "</td>"
						  + "<td>" + $("#qtdeProduto").val().replace(",",".") + "</td>"
						  + "<td>" + parseFloat($("#unitario").val().replace(",",".")).toFixed(2) + "</td>"
						  + "<td>" + $("#totalItemPedidoVenda").val() + "</td>"
						  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.pedidoVenda.editarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#qtdeProduto").val().replace(",",".")) + "," + "\"" + unEstoque + "\"" + "," + parseFloat($("#unitario").val()) + "," + parseFloat($("#totalItemPedidoVenda").val()) +")' aria-hidden='true'></i></a>"
						  	  + "<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.pedidoVenda.deletarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#totalItemPedidoVenda").val()) +")' aria-hidden='true'></i></a></td>"
					  + "</tr>";
				$("#totalPedidoVenda").val(parseFloat(parseFloat($("#totalItemPedidoVenda").val()) + parseFloat($("#totalPedidoVenda").val())).toFixed(2)) // soma o total do pedido.
				var prod = {
						codigoProduto: $("#produtos").val(),
						qtde: $("#qtdeProduto").val(),
						unitario: $("#unitario").val(),
						total: $("#totalItemPedidoVenda").val()
				} ;
				
				produtoArray.push(prod);
				$("#itensPedidoVenda tbody").append(html);
				$("#produtos option:selected").remove();
				$("#qtdeProduto").val("");
				$("#unitario").val("");
				$("#totalItemPedidoVenda").val("");
				$("#unEstoque").val("");
			}
		}
	};
	
	BRIGADERIA.pedidoVenda.editarProduto = function (handler, codigo, descricao, qtde, unEstoque, unitario, totalItem) {
		debugger;
		$("#qtdeProduto").val(parseFloat(qtde));
		$("#unEstoque").val(unEstoque);
		$("#unitario").val(parseFloat(unitario));
		$("#totalItemPedidoVenda").val(totalItem);
		
		for (var i = 0; i < produtoArray.length; i++) {
			if (produtoArray[i].codigoProduto == codigo) {
				produtoArray.splice(i, 1);
			}
		}
		
		$(handler).closest('tr').remove();//exclui o tr mais proximo.
		window.event.preventDefault();
		$('#produtos').append('<option value="' + codigo + '" selected="unselected">' + descricao + '</option>');
		$("#totalPedidoVenda").val(parseFloat(parseFloat($("#totalPedidoVenda").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
	};
	
	BRIGADERIA.pedidoVenda.deletarProduto = function (handler, codigo, descricao, totalItem) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o Produto?", 
			callback: function(confirma){
				if (confirma == true) {
					for (var i = 0; i < produtoArray.length; i++) {
						if (produtoArray[i].codigoProduto == codigo) {
							produtoArray.splice(i, 1);
						}
					}
					
					$(handler).closest('tr').remove();//exclui o tr mais proximo.
					window.event.preventDefault();
					$('#produtos').append('<option value="' + codigo + '">' + descricao + '</option>');
					$("#totalPedidoVenda").val(parseFloat(parseFloat($("#totalPedidoVenda").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
				}
			}
		});
		
		
	};
	
	BRIGADERIA.pedidoVenda.adicionar = function() {
		
		var newPedido = {
			cliente : $("#clientes").val(),
			total : $("#totalPedidoVenda").val(),
			faturado : "N",
			produzido : "N",
			cancelado : "N",
			itemPedidoVenda: produtoArray
		};
		if (newPedido.itemPedidoCompra == "") {
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.pedidoVendaService.adicionar(newPedido);
		}
	};
	/*
	BRIGADERIA.pedidoCompra.exibirEdicao = function(numero) {
		BRIGADERIA.pedidoCompraService.buscarPedidoPeloNumero({
			numero : numero,
			async: false,
			success : function (pedido) {
				$("#numero").val(pedido.numero);
				$("#data").val(BRIGADERIA.convertData.dateToStr(pedido.data));
				$("#totalPedidoCompra").val(parseFloat(pedido.total).toFixed(2));
				itemPedido = {};
				itemPedido = pedido.itemPedidoCompra;
				
				for (var i = 0; i < itemPedido.length; i++ ) {
					var html = ""
						html =  "<tr class='itemPedidoCompra'>"
							  + "<td >" + itemPedido[i].codigoProduto + "</td>"
							  + "<td>" + itemPedido[i].descricao + "</td>"
							  + "<td>" + itemPedido[i].unEntrada + "</td>"
							  + "<td>" + itemPedido[i].qtde + "</td>"
							  + "<td>" + parseFloat(itemPedido[i].unitario).toFixed(2) + "</td>"
							  + "<td>" + itemPedido[i].total + "</td>"
						  + "</tr>";		
						
						$("#itensPedidoCompra tbody").append(html);
				}
			}
		});	
	};*/
});