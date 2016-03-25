$(document).ready(function() {
	if(BRIGADERIA.fichaTecnicaService == undefined){
		BRIGADERIA.fichaTecnicaService = {};
	}
	
	BRIGADERIA.fichaTecnicaService.defaultCfg = function(cfg) {
		var url = "rest/fichatecnica";// url padrão
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
	
	BRIGADERIA.fichaTecnicaService.listarIngredientes = function(cfg) {
		cfg.url = "rest/fichaTecnica/listarIngredientes";
		BRIGADERIA.ajax.get(BRIGADERIA.fichaTecnicaService.defaultCfg(cfg));
	};
	
	BRIGADERIA.fichaTecnicaService.buscarFichaTecnicaPeloCodigoProduto = function(cfg) {
		cfg.url = "rest/fichaTecnica/buscarFichaTecnicaPeloCodigoProduto/" + cfg.codigoProduto;
		BRIGADERIA.ajax.get(BRIGADERIA.fichaTecnicaService.defaultCfg(cfg));
	};
});