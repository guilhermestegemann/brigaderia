BRIGADERIA.produtos = new Object();

$(document).ready(function() {

	$("#subConteudo").text(""); //inicia div vazia
	
	BRIGADERIA.produtos.aplicarMask = function () {
		$(".money").mask('000000000000000,00', {reverse: true});
		 $('#margem').mask('##0,00%', {reverse: true});
	};
	
	BRIGADERIA.produtos.listarTipoItem = function (codTipo) { 
		BRIGADERIA.tipoItem.listar({
			success: function(data) {
				var html = "";
				
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].tipo + "</option>";
				}
				$("#tipoItem").append(html);
				if (codTipo != null) {
					$("#tipoItem").val(codTipo);
				}
			}
		});
	};
	
	BRIGADERIA.produtos.exibirFormulario = function () {
		BRIGADERIA.produtos.listarTipoItem();
	};
	
	$("#tipoItem").on('change',function(){
		if ($("#tipoItem").val() == 1) {
			$("#btnSalvarProduto").hide();
			$("#btnCancelarProduto").hide();
			$("#subConteudo").load("resources/cadastro/fichaTecnica/fichaTecnica.html", function (){
				BRIGADERIA.fichaTecnica.exibirFormulario();
			});
		}else{
			$("#subConteudo").text("");
			$("#btnSalvarProduto").show();
			$("#btnCancelarProduto").show();
		}
	});
	
	BRIGADERIA.produtos.adicionar = function() {
		var newProduto = new Object();
		$('form input, form select').each(function(){newProduto[this.name]=this.value;});
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
				
				BRIGADERIA.produtos.aplicarMask();
				$("#btnSalvarProduto").attr("onclick", "BRIGADERIA.produtos.salvarEdicao()");
				$("#conteudo h1").text("Edição de Produtos");
				
				if (produto.tipoItem == "1") {
					$("#btnSalvarProduto").hide();
					$("#btnCancelarProduto").hide();
					$("#subConteudo").load("resources/cadastro/fichaTecnica/fichaTecnica.html", function (){
						BRIGADERIA.fichaTecnica.exibirFormulario("Edição");
					});
				}else{// Só lista tipoItem se for Ingrediente ou Embalagem
					BRIGADERIA.produtos.listarTipoItem(produto.tipoItem)
				}
			}
		});	
	};
	
	
	BRIGADERIA.produtos.salvarEdicao = function() {
		var produto = new Object();
		$('form input, form select').each(function(){produto[this.name]=this.value;});
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




