$(document).ready(function() {
	if(BRIGADERIA.bairroService == undefined){
		BRIGADERIA.bairroService = {};
	}
	
	BRIGADERIA.bairroService.defaultCfg = function(cfg) {
		var url = "rest/bairros";// url padrão
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
	
	BRIGADERIA.bairroService.listar = function(cfg){
		cfg.url = "rest/bairros/buscar";
		BRIGADERIA.ajax.get(BRIGADERIA.bairroService.defaultCfg(cfg));
	};
});