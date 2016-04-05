BRIGADERIA.gerenciarPedidoCompra = new Object();

$(document).ready( function () {
	
	BRIGADERIA.gerenciarPedidoCompra.lancarPedido = function() {
		$("#conteudo").load("resources/faturamento/pedidoCompra/pedidoCompra.html", function (){
		BRIGADERIA.pedidoCompra.exibirFormulario();
		});
	};
	
});