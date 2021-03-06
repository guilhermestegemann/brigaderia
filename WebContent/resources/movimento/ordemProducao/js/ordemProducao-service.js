$(document).ready(function() {
	if(BRIGADERIA.ordemProducaoService == undefined){
		BRIGADERIA.ordemProducaoService = {};
	}
	
	BRIGADERIA.ordemProducaoService.defaultCfg = function(cfg) {
		var url = "rest/ordemProducao";// url padrão
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
	
	BRIGADERIA.ordemProducaoService.listarProdutos = function(cfg) {
		cfg.url = "rest/ordemProducao/listarProdutos";
		BRIGADERIA.ajax.get(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.listar = function(cfg){
		cfg.url = "rest/ordemProducao/buscarOrdemProducao/" + cfg.dataInicio + "/" + cfg.dataFim + "/" + cfg.status;
		BRIGADERIA.ajax.get(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
		
	BRIGADERIA.ordemProducaoService.adicionar = function(newPerda) {
		cfg = {
			url: "rest/ordemProducao/adicionar",
			data: newPerda,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo('gerenciar/ordemProducao/gerenciarOrdemProducao.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.deletarOrdem = function(cfg) {
		cfg.url = "rest/ordemProducao/deletarOrdem/" + cfg.numero;
		
		BRIGADERIA.ajax.del(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.buscarOrdemPeloNumero = function(cfg) {
		cfg.url = "rest/ordemProducao/buscarOrdemPeloNumero/" + cfg.numero;
		BRIGADERIA.ajax.get(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.editarOrdemProducao = function(ordemProducao) {
		cfg = {
			url : "rest/ordemProducao/editarOrdemProducao",
			data : ordemProducao,
			success : function(sucesso){
				bootbox.alert(sucesso);
				carregarConteudo('gerenciar/ordemProducao/gerenciarOrdemProducao.html');
			},
			error: function(err){
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.put(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.iniciarProducao = function(cfg) {
		cfg.url = "rest/ordemProducao/iniciarProducao/"+ cfg.numero;
		BRIGADERIA.ajax.put(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.cancelarInicio = function(cfg) {
		cfg.url = "rest/ordemProducao/cancelarInicio/"+ cfg.numero;
		BRIGADERIA.ajax.put(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.cancelarFinalizada = function(cfg) {
		cfg.url = "rest/ordemProducao/cancelarFinalizada/"+ cfg.numero;
		BRIGADERIA.ajax.put(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.finalizarProducao = function(cfg) {
		cfg.url = "rest/ordemProducao/finalizarProducao/"+ cfg.numero;
		BRIGADERIA.ajax.put(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	

});