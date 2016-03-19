$(document).ready(function() {
	if(BRIGADERIA.produtoFichaTecnicaService == undefined){
		BRIGADERIA.produtoFichaTecnicaService = {};
	}
	
	BRIGADERIA.produtoFichaTecnicaService.defaultCfg = function(cfg) {
		var url = "rest/produtoFichaTecnica";// url padrão 
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
					cfg.error(error);//se tiver cfg e error, executa a função do error
				}
			}
		};
	};
	
	BRIGADERIA.produtoFichaTecnicaService.adicionar = function(newData) {
		cfg = {
			url: "rest/produtoFichaTecnica/adicionar",
			data: newData,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/produtos/gerenciarProdutos.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.produtoFichaTecnicaService.defaultCfg(cfg));
	};
});