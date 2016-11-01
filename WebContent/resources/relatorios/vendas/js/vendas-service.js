$(document).ready(function() {
	if(BRIGADERIA.vendasService == undefined){
		BRIGADERIA.vendasService = {};
	}
	
	BRIGADERIA.vendasService.defaultCfg = function(cfg) {
		var url = "rest/vendas";// url padrão
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
	
	BRIGADERIA.vendasService.listarClientes = function(cfg) {
		cfg.url = "rest/vendas/listarClientes";
		BRIGADERIA.ajax.get(BRIGADERIA.vendasService.defaultCfg(cfg));
	};
	
		BRIGADERIA.vendasService.listarProdutos = function(cfg) {
		cfg.url = "rest/vendas/listarProdutos";
		BRIGADERIA.ajax.get(BRIGADERIA.vendasService.defaultCfg(cfg));
	};
	
	BRIGADERIA.vendasService.gerarPorProduto = function(cfg){
		cfg.url = "rest/vendas/gerarPorProduto/"+cfg.dataInicio+"/"+cfg.dataFim+"/"+cfg.produto+"/"+cfg.numRegistro;
		BRIGADERIA.ajax.get(BRIGADERIA.vendasService.defaultCfg(cfg));
	};
	
	BRIGADERIA.vendasService.gerarPorCliente = function(cfg){
		cfg.url = "rest/vendas/gerarPorCliente/"+cfg.dataInicio+"/"+cfg.dataFim+"/"+cfg.cliente+"/"+cfg.numRegistro;
		BRIGADERIA.ajax.get(BRIGADERIA.vendasService.defaultCfg(cfg));
	};
	
});