BRIGADERIA.produtos = new Object();

$(document).ready(function() {

	$("#subConteudo").text(""); //inicia div vazia
	
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
	

});// fecha ready







