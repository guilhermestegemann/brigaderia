BRIGADERIA.pedidoCompra = new Object();

$(document).ready( function () {
	
	var produtos;
	var produtoArray = []; //utilizado ao inserir ingrediente no formulário
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$("#produtos").on('change',function(){
		for (var i = 0; i < produtos.length; i++) {
			if (produtos[i].codigoProduto == $("#produtos").val()) {
				$("#unEntrada").val(produtos[i].unEntrada);
			}
		}
		
	});
	
	$("#qtdeProduto").on('blur', function(){
		if ($("#unitario").val() != "") {
			$("#totalItemPedidoCompra").val($("#unitario").val().replace(",",".") * ($("#qtdeProduto").val().replace(",",".")));
		}
		if ($("#totalItemPedidoCompra").val() != "") {
			$("#totalItemPedidoCompra").val(parseFloat($("#totalItemPedidoCompra").val()).toFixed(2));
		}
	});
	
	$("#unitario").on('blur', function(){
		if ($("#unitario").val() != "") {
			$("#totalItemPedidoCompra").val(parseFloat($("#unitario").val().replace(",",".") * $("#qtdeProduto").val().replace(",",".")).toFixed(2));
		}
		if ($("#unitario").val().replace(",",".") > 0) {
			$("#unitario").val(parseFloat($("#unitario").val().replace(",",".")).toFixed(2));
		}
	});

	
	BRIGADERIA.pedidoCompra.listarProdutos = function () { 
		BRIGADERIA.pedidoCompraService.listarProdutos({
			success: function(data) {
				var html = "";
				produtos = data;

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
	
	BRIGADERIA.pedidoCompra.exibirFormulario = function () {
		BRIGADERIA.pedidoCompra.listarProdutos();
		$("#totalPedidoCompra").val("0.00");
	};
	
	BRIGADERIA.pedidoCompra.incluirProduto = function () {
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
			var unEntrada = "";
			
			for (var i = 0; i < produtos.length; i++) {
		
				if ((produtos[i].codigoProduto == $("#produtos").val()) && (unEntrada == "")) {
					unEntrada = produtos[i].unEntrada;
					descricao = produtos[i].descricao; 
				}
			}
			
			if (unEntrada != "") {
				html =  "<tr class='itemPedidoCompra'>"
						  + "<td >" + $("#produtos").val() + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + unEntrada + "</td>"
						  + "<td>" + $("#qtdeProduto").val().replace(",",".") + "</td>"
						  + "<td>" + parseFloat($("#unitario").val().replace(",",".")).toFixed(2) + "</td>"
						  + "<td>" + $("#totalItemPedidoCompra").val() + "</td>"
						  + "<td><a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.pedidoCompra.deletarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#qtdeProduto").val().replace(",",".")) + "," + "\"" + unEntrada + "\"" + "," + parseFloat($("#unitario").val()) + "," + parseFloat($("#totalItemPedidoCompra").val()) +")' aria-hidden='true'></i></a>"
					  + "</tr>";
				$("#totalPedidoCompra").val(parseFloat(parseFloat($("#totalItemPedidoCompra").val()) + parseFloat($("#totalPedidoCompra").val())).toFixed(2)) // soma o total do pedido.
				var prod = {
						codigoProduto: $("#produtos").val(),
						qtde: $("#qtdeProduto").val(),
						unitario: $("#unitario").val(),
						total: $("#totalItemPedidoCompra").val()
				} ;
				
				produtoArray.push(prod);
				$("#itensPedidoCompra tbody").append(html);
				$("#produtos option:selected").remove();
				$("#qtdeProduto").val("");
				$("#unitario").val("");
				$("#totalItemPedidoCompra").val("");
				$("#unEntrada").val("");
			}
		}
	};
	
	BRIGADERIA.pedidoCompra.deletarProduto = function (handler, codigo, descricao, qtde, unEntrada, unitario, totalItem) {
		$("#qtdeProduto").val(parseFloat(qtde));
		$("#unEntrada").val(unEntrada);
		$("#unitario").val(parseFloat(unitario));
		$("#totalItemPedidoCompra").val(totalItem);
		for (var i = 0; i < produtoArray.length; i++) {
			if (produtoArray[i].codigoProduto == codigo) {
				produtoArray.splice(i, 1);
			}
		}
		var tr = $(handler).closest('tr');
		tr.remove();  
		window.event.preventDefault();
		$('#produtos').append('<option value="' + codigo + '" selected="selected">' + descricao + '</option>');
		$("#totalPedidoCompra").val(parseFloat(parseFloat($("#totalPedidoCompra").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
	};
	
	BRIGADERIA.pedidoCompra.adicionar = function() {
		var newPedido = {
			total : $("#totalPedidoCompra").val(),
			itemPedidoCompra: produtoArray
		};
		if (newPedido.itemPedidoCompra == "") {
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.pedidoCompraService.adicionar(newPedido);
		}
	};
});