$(document).ready(function() {
	if(BRIGADERIA.perdaService == undefined){
		BRIGADERIA.perdaService = {};
	}
	
	BRIGADERIA.perdaService.defaultCfg = function(cfg) {
		var url = "rest/perda";// url padrão
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
	
	BRIGADERIA.perdaService.listarProdutos = function(cfg) {
		cfg.url = "rest/perda/listarProdutos";
		BRIGADERIA.ajax.get(BRIGADERIA.perdaService.defaultCfg(cfg));
	};
		
	BRIGADERIA.perdaService.adicionar = function(newPerda) {
		cfg = {
			url: "rest/perda/adicionar",
			data: newPerda,
			success : function (sucesso) {
				bootbox.alert(sucesso.replace("\n","<br>"));
				carregarConteudo ('resources/gerenciar/perda/gerenciarPerdas.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.perdaService.defaultCfg(cfg));
	};
		
	BRIGADERIA.perdaService.listar = function(cfg){
		cfg.url = "rest/perda/buscarPerda/" + cfg.dataInicio + "/" + cfg.dataFim;
		BRIGADERIA.ajax.get(BRIGADERIA.perdaService.defaultCfg(cfg));
	};
	/*	
	BRIGADERIA.perdaService.deletar = function(cfg) {
		cfg.url = "rest/perda/deletar/" + cfg.numero;
		
		BRIGADERIA.ajax.del(BRIGADERIA.perdaService.defaultCfg(cfg));
	};
	
	BRIGADERIA.perdaService.buscarPedidoPeloNumero = function(cfg) {
		cfg.url = "rest/perda/buscarPedidoPeloNumero/" + cfg.numero;
		BRIGADERIA.ajax.get(BRIGADERIA.perdaService.defaultCfg(cfg));
	};
	*/
});