$(document).ready(function() {
	if(BRIGADERIA.cidadeService == undefined){
		BRIGADERIA.cidadeService = {};
	}
	
	BRIGADERIA.cidadeService.defaultCfg = function(cfg) {
		var url = "rest/cidades";// url padrão
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
	
	BRIGADERIA.cidadeService.listar = function(cfg){
		cfg.url = "rest/cidades/buscar";
		BRIGADERIA.ajax.get(BRIGADERIA.cidadeService.defaultCfg(cfg));
	};
});