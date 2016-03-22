$(document).ready(function() {
	if(BRIGADERIA.produtoService == undefined){
		BRIGADERIA.produtoService = {};
	}
	
	BRIGADERIA.produtoService.defaultCfg = function(cfg) {
		var url = "rest/produtos";// url padrão
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
	
	
	
	BRIGADERIA.produtoService.adicionar = function(produto) {
		cfg = {
			url: "rest/produtos/adicionar",
			data: produto,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/produtos/gerenciarProdutos.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.produtoService.defaultCfg(cfg));
	};
});