BRIGADERIA.fichaTecnica = new Object();

$(document).ready(function(){
	
	var ingredientes;
	
	BRIGADERIA.fichaTecnica.listarIngredientes = function () { 
		BRIGADERIA.fichaTecnicaService.listarIngredientes({
			success: function(data) {
				var html = "";
				ingredientes = data;

				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].descricao + "</option>";
				}
				$("#ingrediente").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.fichaTecnica.exibirFormulario = function () {
		BRIGADERIA.fichaTecnica.listarIngredientes();
	};
	
	BRIGADERIA.fichaTecnica.incluirIngrediente = function () {
		
		if ($("#ingrediente").val() == "") {
			bootbox.alert("Selecione o Ingrediente!");
		}else if (($("#qtdeIngrediente").val() == "") || ($("#qtdeIngrediente").val() <= 0)) {
			bootbox.alert("Quantida inválida.");
		}else{
			var html = "";
			var codigo = $("#ingrediente").val();
			var descricao = "";
			var un = "";
			var qtde = $("#qtdeIngrediente").val();
			
			for (var i = 0; i < ingredientes.length; i++) {
				if ((ingredientes[i].codigo == codigo) && (un == "")) {
					un = ingredientes[i].un;
					descricao = ingredientes[i].descricao;
				}
			}
			if (un != "") {
				html =  "<tr class='ingredientes'>"
						  + "<td >" + codigo + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + un + "</td>"
						  + "<td>" + qtde + "</td>"
					  + "</tr>";
				$("#ingredientesFichaTecnica tbody").append(html);
				$("#ingrediente option:selected").remove();
				$("#qtdeIngrediente").val("");
			}
		}
	};
	
	BRIGADERIA.fichaTecnica.adicionar = function() {
		var newData = {
			produto:{
				tipoItem: $("#tipoItem").val(),
				descricao: $("#descricao").val(),
				qtdeEntrada: $("#qtdeEntrada").val(),
				unEntrada: $("#unEntrada").val(),
				valorCusto: $("#valorCusto").val(),
				estoque: $("#estoque").val(),
				unEstoque: $("#unEstoque").val(),
				margem: $("#margem").val(),
				valorVenda: $("#valorVenda").val(),
				dataCadastro: $("#dataCadastro").val(),
				ativo: $("#ativo").val(),	
			}, 
			fichaTecnica:{
				custoTotal: $("#custoTotal").val(),
				qtdeProduto: $("#qtdeProduto").val(),
				procedimento: $("#procedimento").val(),
				ingredientes: []
			}
		};
		
		$("#ingredientesFichaTecnica tbody .ingredientes").each(function(){
			
			var ingrediente = {
					codigo: $("td",this).first().text(),
					qtde: $("td",this).last().text()
			};
			newData.fichaTecnica.ingredientes.push(ingrediente);
		});
		newData.produto = BRIGADERIA.produtos.ajustarCampos(newData.produto);
		retornoValida = BRIGADERIA.validaProdutos.validar(newData.produto);
		if (retornoValida != "") {
			bootbox.alert("Favor verificar os seguintes campos: " + retornoValida);
		}else{
			retornoValida = BRIGADERIA.validaFichaTecnica.validar(newData.fichaTecnica);
			if (retornoValida == "") {
				BRIGADERIA.produtoFichaTecnicaService.adicionar(newData);
			}else{
				bootbox.alert("Favor verificar os seguintes campos: " + retornoValida);
			}
		}
	};
	
	
	
	
});//fecha ready

















