BRIGADERIA.perda = new Object();

$(document).ready( function () {
	
	var ingredientes;
	var ingredienteArray = []; //utilizado ao inserir ingrediente no formulário
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$("#ingredientes").on('change',function(){
		for (var i = 0; i < ingredientes.length; i++) {
			if (ingredientes[i].codigoProduto == $("#ingredientes").val()) {
				$("#unEstoque").val(ingredientes[i].unEstoque);
				$("#valorCusto").val(ingredientes[i].valorCusto);
			}
			if ($("#valorCusto").val() != "") {
				$("#totalItemPerda").val($("#valorCusto").val().replace(",",".") * ($("#qtdeIngrediente").val().replace(",",".")));
			}
			if ($("#totalItemPerda").val() != "") {
				$("#totalItemPerda").val(parseFloat($("#totalItemPerda").val()).toFixed(2));
			}
		}
		
	});
	
	$("#qtdeIngrediente").on('blur', function(){
		if ($("#valorCusto").val() != "") {
			$("#totalItemPerda").val($("#valorCusto").val().replace(",",".") * ($("#qtdeIngrediente").val().replace(",",".")));
		}
		if ($("#totalItemPerda").val() != "") {
			$("#totalItemPerda").val(parseFloat($("#totalItemPerda").val()).toFixed(2));
		}
	});
	
	$("#valorCusto").on('blur', function(){
		if ($("#valorCusto").val() != "") {
			$("#totalItemPerda").val(parseFloat($("#valorCusto").val().replace(",",".") * $("#qtdeIngrediente").val().replace(",",".")).toFixed(2));
		}
		if ($("#valorCusto").val().replace(",",".") > 0) {
			$("#valorCusto").val(parseFloat($("#valorCusto").val().replace(",",".")).toFixed(2));
		}
	});

	BRIGADERIA.perda.listarProdutos = function () { 
		BRIGADERIA.perdaService.listarProdutos({
			success: function(data) {
				var html = "";
				ingredientes = data;//atribui ao objeto global o valor retornado da lista. Utilizado pra edição/inserção de itens de pedido
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigoProduto + "'>" + data[i].descricao + "</option>";
				}
				$("#ingredientes").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.perda.exibirFormulario = function () {
		BRIGADERIA.perda.listarProdutos();
		$("#totalPerda").val("0.00");
	};
	
	BRIGADERIA.perda.incluirProduto = function () {
		var expNumeros = /^[0-9]+$/;
		if ($("#ingredientes").val() == "") {
			bootbox.alert("Selecione o Produto!");
		}else if (!expNumeros.test($("#qtdeIngrediente").val().replace(",","").replace(".",""))) {
			bootbox.alert("Quantida inválida.");
		}else if (($("#unitario").val() == "") || ($("#unitario").val() <= 0)) {
			bootbox.alert("Unitário inválido.");
		}else{
			var html = "";
			var descricao = "";
			var unEstoque = "";
			
			for (var i = 0; i < ingredientes.length; i++) {
		
				if ((ingredientes[i].codigoProduto == $("#ingredientes").val()) && (unEstoque == "")) {
					unEstoque = ingredientes[i].unEstoque;
					descricao = ingredientes[i].descricao; 
				}
			}
			
			if (unEstoque != "") {
				html =  "<tr class='itemPerda'>"
						  + "<td >" + $("#ingredientes").val() + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + unEstoque + "</td>"
						  + "<td>" + $("#qtdeIngrediente").val().replace(",",".") + "</td>"
						  + "<td>" + parseFloat($("#valorCusto").val().replace(",",".")).toFixed(2) + "</td>"
						  + "<td>" + parseFloat($("#totalItemPerda").val()).toFixed(2) + "</td>"
						  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.perda.editarProduto(this"+ "," + $("#ingredientes").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#qtdeIngrediente").val().replace(",",".")) + "," + "\"" + unEstoque + "\"" + "," + parseFloat($("#valorCusto").val()) + "," + parseFloat($("#totalItemPerda").val()) +")' aria-hidden='true'></i></a>"
						  	  + "<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.perda.deletarProduto(this"+ "," + $("#ingredientes").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#totalItemPerda").val()) +")' aria-hidden='true'></i></a></td>"
					  + "</tr>";
				$("#totalPerda").val(parseFloat(parseFloat($("#totalItemPerda").val()) + parseFloat($("#totalPerda").val())).toFixed(2)) // soma o total do pedido.
				var prod = {
						codigoProduto: $("#ingredientes").val(),
						qtde: $("#qtdeIngrediente").val(),
						unitario: $("#valorCusto").val(),
						total: $("#totalItemPerda").val()
				} ;
				
				ingredienteArray.push(prod);
				$("#itensPerda tbody").append(html);
				$("#ingredientes option:selected").remove();
				$("#qtdeIngrediente").val("");
				$("#valorCusto").val("");
				$("#totalItemPerda").val("");
				$("#unEstoque").val("");
			}
		}
	};
	
	BRIGADERIA.perda.editarProduto = function (handler, codigo, descricao, qtde, unEstoque, valorCusto, totalItem) {
		
		$("#qtdeIngrediente").val(parseFloat(qtde));
		$("#unEstoque").val(unEstoque);
		$("#valorCusto").val(parseFloat(valorCusto));
		$("#totalItemPerda").val(totalItem);
		
		for (var i = 0; i < ingredienteArray.length; i++) {
			if (ingredienteArray[i].codigoProduto == codigo) {
				ingredienteArray.splice(i, 1);
			}
		}
		
		$(handler).closest('tr').remove();//exclui o tr mais proximo.
		window.event.preventDefault();
		$('#ingredientes').append('<option value="' + codigo + '" selected="unselected">' + descricao + '</option>');
		$("#totalPerda").val(parseFloat(parseFloat($("#totalPerda").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
	};
	
	BRIGADERIA.perda.deletarProduto = function (handler, codigo, descricao, totalItem) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o item?", 
			callback: function(confirma){
				if (confirma) {
					for (var i = 0; i < ingredienteArray.length; i++) {
						if (ingredienteArray[i].codigoProduto == codigo) {
							ingredienteArray.splice(i, 1);
						}
					}
					
					$(handler).closest('tr').remove();//exclui o tr mais proximo.
					//window.event.preventDefault();
					$('#ingredientes').append('<option value="' + codigo + '">' + descricao + '</option>');
					$("#totalPerda").val(parseFloat(parseFloat($("#totalPerda").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
				}
			}
		});
		
		
	};
		
	BRIGADERIA.perda.adicionar = function() {
		
		var newPerda = {
			total : $("#totalPerda").val(),
			itemPerda: ingredienteArray
		};
		if (newPerda.itemPerda == "") {
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.perdaService.adicionar(newPerda);
		}
	};
		
	BRIGADERIA.perda.exibirEdicao = function(numero) {
		BRIGADERIA.perdaService.buscarPerdaPeloNumero({
			numero : numero,
			async: false,
			success : function (perda) {
				$("#numero").val(perda.numero);
				$("#data").val(BRIGADERIA.convertData.dateToStr(perda.data));
				$("#totalPerda").val(parseFloat(perda.total).toFixed(2));
				itemPerda = {};
				itemPerda = perda.itemPerda;
				console.log(itemPerda);
				for (var i = 0; i < itemPerda.length; i++ ) {
					var html = ""
						html =  "<tr class='itemPerda'>"
							  + "<td >" + itemPerda[i].codigoProduto + "</td>"
							  + "<td>" + itemPerda[i].descricao + "</td>"
							  + "<td>" + itemPerda[i].unEstoque + "</td>"
							  + "<td>" + itemPerda[i].qtde + "</td>"
							  + "<td>" + parseFloat(itemPerda[i].unitario).toFixed(2) + "</td>"
							  + "<td>" + itemPerda[i].total + "</td>"
						  + "</tr>";		
						
						$("#itensPerda tbody").append(html);
				}
			}
		});	
	};
});