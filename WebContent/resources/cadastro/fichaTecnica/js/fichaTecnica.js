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
	
	BRIGADERIA.fichaTecnica.exibirFormulario = function (modo) {
		BRIGADERIA.fichaTecnica.listarIngredientes();
		if (modo == "Edição") {
			BRIGADERIA.fichaTecnica.exibirEdicao();
		}
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
						  + "<td><a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.fichaTecnica.deletarIngrediente(this"+ "," + codigo + "," + "\"" + descricao + "\"" +")' aria-hidden='true'></i></a>"
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
	
	BRIGADERIA.fichaTecnica.exibirEdicao = function () {
		var html = "<input type='text' class='form-control' id='codigoFichaTecnica' name='codigoFichaTecnica' style='display:none' />";
		$("#subConteudo").append(html);
		BRIGADERIA.fichaTecnicaService.buscarFichaTecnicaPeloCodigoProduto({
			codigoProduto : $("#codigoProduto").val(),
			async: false,
			success : function (fichaTecnica) {
				$("#codigoFichaTecnica").val(fichaTecnica.codigoFichaTecnica);
				$("#custoTotal").val(fichaTecnica.custoTotal);
				$("#qtdeProduto").val(fichaTecnica.qtdeProduto);
				$("#procedimento").val(fichaTecnica.procedimento);
				ingredientesVO = {};
				ingredientesVO = fichaTecnica.ingredientes;
				for (var i = 0; i < ingredientesVO.length; i++) {
					html = "";
					html =  "<tr class='ingredientes'>"
						  + "<td>" + ingredientesVO[i].codigo + "</td>"
						  + "<td>" + ingredientesVO[i].descricao + "</td>"
						  + "<td>" + ingredientesVO[i].un + "</td>"
						  + "<td>" + ingredientesVO[i].qtde + "</td>"
						  + "<td><a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.fichaTecnica.deletarIngrediente(this"+ "," + ingredientesVO[i].codigo + "," + "\"" + ingredientesVO[i].descricao + "\"" +")' aria-hidden='true'></i></a>"
					  + "</tr>";
					$("#ingredientesFichaTecnica tbody").append(html);
					$("#ingrediente option[value='"+ ingredientesVO[i].codigo +"']").remove();
				}
				$("#tipoItemField").text("");
				$("#tipoItemField").html('<label for="tipoItemInput">*Tipo Item</label><input type="text" class="form-control float" id="tipoItemInput" name="tipoItemInput" readonly/>');
				$("#tipoItemInput").val("Produto");
				$("#subConteudo h1").text("Edição de FichaTecnica");
				$("#btnSalvarFichaTecnicaProduto").attr("onclick", "BRIGADERIA.fichaTecnica.salvarEdicao()");
			}
		});	
	}
	

	BRIGADERIA.fichaTecnica.deletarIngrediente = function (handler, codigo, descricao) {
		var tr = $(handler).closest('tr');
		tr.remove();  
		window.event.preventDefault();
		$('#ingrediente').append('<option value="' + codigo + '" selected="selected">' + descricao + '</option>');
	};
	
	BRIGADERIA.fichaTecnica.salvarEdicao = function() {
		var dataEdit = {
				produto:{
					codigoProduto: $("#codigoProduto").val(),
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
					codigoFichaTecnica: $("#codigoFichaTecnica").val(),
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
			dataEdit.fichaTecnica.ingredientes.push(ingrediente);
		});
		
		dataEdit.produto = BRIGADERIA.produtos.ajustarCampos(dataEdit.produto);
		retornoValida = BRIGADERIA.validaProdutos.validar(dataEdit.produto);
		if (retornoValida != "") {
			bootbox.alert("Favor verificar os seguintes campos: " + retornoValida);
		}else{
			retornoValida = BRIGADERIA.validaFichaTecnica.validar(dataEdit.fichaTecnica);
			if (retornoValida == "") {
				dataEdit.produto.dataCadastro = BRIGADERIA.convertData.strToDate(dataEdit.produto.dataCadastro);
				BRIGADERIA.produtoFichaTecnicaService.atualizar(dataEdit);
			}else{
				bootbox.alert("Favor verificar os seguintes campos: " + retornoValida);
			}
		}
	};

	
	
	
});//fecha ready

















