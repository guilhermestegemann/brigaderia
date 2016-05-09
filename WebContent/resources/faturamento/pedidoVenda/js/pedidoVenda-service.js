$(document).ready(function() {
	if(BRIGADERIA.pedidoVendaService == undefined){
		BRIGADERIA.pedidoVendaService = {};
	}
	
	BRIGADERIA.pedidoVendaService.defaultCfg = function(cfg) {
		var url = "rest/pedidoVenda";// url padrão
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
	
	BRIGADERIA.pedidoVendaService.listarProdutos = function(cfg) {
		cfg.url = "rest/pedidoVenda/listarProdutos";
		BRIGADERIA.ajax.get(BRIGADERIA.pedidoVendaService.defaultCfg(cfg));
	};
	
	BRIGADERIA.pedidoVendaService.listarClientes = function(cfg) {
		cfg.url = "rest/pedidoVenda/listarClientes";
		BRIGADERIA.ajax.get(BRIGADERIA.pedidoVendaService.defaultCfg(cfg));
	};
	
	BRIGADERIA.pedidoVendaService.adicionar = function(newPedido) {
		cfg = {
			url: "rest/pedidoVenda/adicionar",
			data: newPedido,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/pedidoVenda/gerenciarPedidoVenda.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.pedidoVendaService.defaultCfg(cfg));
	};
	/*
	BRIGADERIA.pedidoVendaService.listar = function(cfg){
		cfg.url = "rest/pedidoVenda/buscarPedidoCompra/" + cfg.dataInicio + "/" + cfg.dataFim;
		BRIGADERIA.ajax.get(BRIGADERIA.pedidoVendaService.defaultCfg(cfg));
	};
	
	BRIGADERIA.pedidoVendaService.deletar = function(cfg) {
		cfg.url = "rest/pedidoVenda/deletar/" + cfg.numero;
		
		BRIGADERIA.ajax.del(BRIGADERIA.pedidoVendaService.defaultCfg(cfg));
	};
	
	BRIGADERIA.pedidoVendaService.buscarPedidoPeloNumero = function(cfg) {
		cfg.url = "rest/pedidoVenda/buscarPedidoPeloNumero/" + cfg.numero;
		BRIGADERIA.ajax.get(BRIGADERIA.pedidoVendaService.defaultCfg(cfg));
	};*/
});