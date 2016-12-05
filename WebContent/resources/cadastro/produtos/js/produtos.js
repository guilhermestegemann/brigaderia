BRIGADERIA.produtos = new Object();

$(document).ready(function() {

	$("#subConteudo").text(""); //inicia div vazia
	
	BRIGADERIA.produtos.exibirFormulario = function () {
		BRIGADERIA.produtos.listarTipoItem("#tipoItem");
	};
	
	$("#tipoItem").on('change',function(){
		if ($("#tipoItem").val() == 1) {
			$("#dataCadastro").val("");
			$("#btnSalvarProduto").hide();
			$("#btnCancelarProduto").hide();
			$("#subConteudo").load("cadastro/fichaTecnica/fichaTecnica.html", function (){
				BRIGADERIA.fichaTecnica.exibirFormulario();
			});
		}else{
			$("#subConteudo").text("");
			$("#btnSalvarProduto").show();
			$("#btnCancelarProduto").show();
		}
	});
	
	$("#margem").on('blur', function(){
		if ($("#valorCusto").val() > 0) {
			$("#valorVenda").val($("#valorCusto").val() * (1+($("#margem").val().replace(",","."))/100));
		}
		if ($("#valorVenda").val() > 0) {
			$("#valorVenda").val(parseFloat($("#valorVenda").val()).toFixed(2));
		}
	});
	
	$("#valorVenda").on('blur', function(){
		if ($("#valorCusto").val() > 0) {
			$("#margem").val((($("#valorVenda").val().replace(",",".") / $("#valorCusto").val())-1)*100);
		}else if ($("#valorCusto").val() == 0) {
			$("#margem").val(0);
		}	
		$("#margem").val(parseFloat($("#margem").val()).toFixed(2));
	});
	
	
	BRIGADERIA.produtos.aplicarMask = function () {
		$("#valorCusto").mask('000000000000000,00', {reverse: true});
	};
	
	
	
	BRIGADERIA.produtos.listarTipoItem = function (idHtml) { 
		BRIGADERIA.tipoItemService.listar({
			success: function(data) {
				var html = "";
				
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].tipo + "</option>";
				}
				$(idHtml).append(html);
			}
		});
	};
	
	BRIGADERIA.produtos.montarProduto = function() {
		return produto = {
				tipoItem: $("#tipoItem").val(),
				descricao: $("#descricao").val(),
				unEntrada: $("#unEntrada").val(),
				qtdeEntrada: $("#qtdeEntrada").val(),
				estoque: $("#estoque").val(),
				unEstoque: $("#unEstoque").val(),
				valorCusto: $("#valorCusto").val(),
				margem: $("#margem").val(),
				valorVenda: $("#valorVenda").val(),
				dataCadastro: $("#dataCadastro").val(),
				ativo: $("#ativo").val(),
		};
	};
	
	BRIGADERIA.produtos.adicionar = function() {
		var newProduto = new Object();
		newProduto = BRIGADERIA.produtos.montarProduto();
		var retornoValida = BRIGADERIA.validaProdutos.validar(newProduto);
		if (retornoValida == "") {
			BRIGADERIA.produtoService.adicionar(BRIGADERIA.produtos.ajustarCampos(newProduto));
		}else{
			bootbox.alert("Favor verificar os seguintes campos: " + retornoValida);
		}
		
	};
	
	BRIGADERIA.produtos.exibirEdicao = function (codigo, tipoItem) {
		
		var html = "<input type='text' class='form-control' id='codigoProduto' name='codigoProduto' style='display:none' />";
		$("#conteudo").append(html);
		BRIGADERIA.produtoService.buscarProdutoPeloCodigo({
			codigo : codigo,
			async: false,
			success : function (produto) {
				
				$("#codigoProduto").val(produto.codigoProduto);
				$("#descricao").val(produto.descricao);
				$("#qtdeEntrada").val(produto.qtdeEntrada);
				$("#unEntrada").val(produto.unEntrada);
				$("#valorCusto").val(produto.valorCusto);
				$("#estoque").val(produto.estoque);
				$("#unEstoque").val(produto.unEstoque);
				$("#margem").val(produto.margem);
				$("#valorVenda").val(produto.valorVenda);
				$("#dataCadastro").val(BRIGADERIA.convertData.dateToStr(produto.dataCadastro));
				$("#ativo").val(produto.ativo);
				$("#tipoItemField").text("");
				BRIGADERIA.produtos.aplicarMask();
				$("#btnSalvarProduto").attr("onclick", "BRIGADERIA.produtos.salvarEdicao()");
				$("#conteudo h1").text("Edição de Produtos");
				$("#tipoItemField").text("");
				$("#tipoItemField").html('<label for="tipoItemInput">*Tipo Item</label><input type="text" class="form-control float" id="tipoItemInput" name="tipoItemInput" readonly/>');
				if (produto.tipoItem == "1") {
					$("#btnSalvarProduto").hide();
					$("#btnCancelarProduto").hide();
					$("#subConteudo").load("cadastro/fichaTecnica/fichaTecnica.html", function (){//passar id da div como parametro
						BRIGADERIA.fichaTecnica.exibirFormulario("Edição");//passar funcao de callback como parametro;
					});
				}else if (produto.tipoItem == "2"){
					$("#tipoItemInput").val("Ingrediente");
				}else{
					$("#tipoItemInput").val("Embalagem");
				}
			}
		});	
	};
	
	
	BRIGADERIA.produtos.salvarEdicao = function() {
		var produto = new Object();
		produto = BRIGADERIA.produtos.montarProduto();
		var retornoValida = BRIGADERIA.validaProdutos.validar(produto);
		if (retornoValida == "") {
			produto.dataCadastro = BRIGADERIA.convertData.strToDate(produto.dataCadastro);
			produto.codigoProduto = $("#codigoProduto").val();
			BRIGADERIA.produtoService.atualizar(BRIGADERIA.produtos.ajustarCampos(produto));
		}else{
			bootbox.alert("Favor verificar os seguintes campos: " + retornoValida);
		}
		
	};
	
	BRIGADERIA.produtos.aplicarMask();
	
	BRIGADERIA.produtos.ajustarCampos = function(produto) {
		produto.qtdeEntrada = $("#qtdeEntrada").val().replace(",",".");
		produto.valorCusto = $("#valorCusto").val().replace(",",".");
		produto.estoque = $("#estoque").val().replace(",",".");
		produto.margem = $("#margem").val().replace(",",".").replace("%","");
		produto.valorVenda = $("#valorVenda").val().replace(",",".");
		return produto;
	};
	
	

	

});// fecha ready




