$(document).ready(function() {
	if(BRIGADERIA.graficoService == undefined){
		BRIGADERIA.graficoService = {};
	}
	
	BRIGADERIA.graficoService.defaultCfg = function(cfg) {
		var url = "rest/graficos";// url padrão
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
	
	BRIGADERIA.graficoService.vendaAnual = function(cfg) {
		cfg.url = "rest/graficos/vendaAnual";
		BRIGADERIA.ajax.get(BRIGADERIA.graficoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.graficoService.porProduto = function(cfg) {
		cfg.url = "rest/graficos/porProduto/"+cfg.dataInicio+"/"+cfg.dataFim+"/"+cfg.orderBy+"/"+cfg.limit;
		BRIGADERIA.ajax.get(BRIGADERIA.graficoService.defaultCfg(cfg));
	}
	
});