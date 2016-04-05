BRIGADERIA.pedidoCompra = new Object();

$(document).ready( function () {
	
	
	var produtos;
	var produtoArray = []; //utilizado ao inserir ingrediente no formulário
	
	BRIGADERIA.pedidoCompra.listarProdutos = function () { 
		BRIGADERIA.pedidoCompraService.listarProdutos({
			success: function(data) {
				var html = "";
				produtos = data;

				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].descricao + "</option>";
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
	};
	
});