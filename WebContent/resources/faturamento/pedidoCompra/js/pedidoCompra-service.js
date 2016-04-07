$(document).ready(function() {
	if(BRIGADERIA.pedidoCompraService == undefined){
		BRIGADERIA.pedidoCompraService = {};
	}
	
	BRIGADERIA.pedidoCompraService.defaultCfg = function(cfg) {
		var url = "rest/pedidoCompra";// url padrão
		if(cfg && cfg.url){// se cfg nao for undefined e tiver url
			url = cfg.url;// altera a url pra url que veio no objeto cfg
		}
		var data = undefined;
		if(cfg && cfg.data){//se cfg nao for undefined e tiver data
			data = cfg.data;// altera data pra data que veio no objeto cfg
		}
		return {
			'url': url,//retorna url
			'data' : data,//retorna data
			success: function(data){
				if (cfg && cfg.success) {
					cfg.success(data);//se tiver cfg e success, executa a função do success
				}
			},
			error: function(error){
				if (cfg && cfg.error) {
					cfg.error(error);//se tiver cdg e error, executa a função do error
				}
			}
		};
	};
	
	
	BRIGADERIA.pedidoCompraService.listarProdutos = function(cfg) {
		cfg.url = "rest/pedidoCompra/listarProdutos";
		BRIGADERIA.ajax.get(BRIGADERIA.pedidoCompraService.defaultCfg(cfg));
	};
	
	BRIGADERIA.pedidoCompraService.adicionar = function(newPedido) {
		cfg = {
			url: "rest/pedidoCompra/adicionar",
			data: newPedido,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/pedidoCompra/gerenciarPedidoCompra.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.pedidoCompraService.defaultCfg(cfg));
	};
});