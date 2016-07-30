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
		
	BRIGADERIA.ordemProducaoService.adicionar = function(newPerda) {
		cfg = {
			url: "rest/ordemProducao/adicionar",
			data: newPerda,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo('resources/gerenciar/ordemProducao/gerenciarOrdemProducao.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	/*		
	BRIGADERIA.ordemProducaoService.listar = function(cfg){
		cfg.url = "rest/perda/buscarPerda/" + cfg.dataInicio + "/" + cfg.dataFim;
		BRIGADERIA.ajax.get(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
		
	BRIGADERIA.ordemProducaoService.deletar = function(cfg) {
		cfg.url = "rest/perda/deletar/" + cfg.numero;
		
		BRIGADERIA.ajax.del(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	
	BRIGADERIA.ordemProducaoService.buscarPerdaPeloNumero = function(cfg) {
		cfg.url = "rest/perda/buscarPerdaPeloNumero/" + cfg.numero;
		BRIGADERIA.ajax.get(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	};
	*/
	
	BRIGADERIA.ordemProducaoService.listarPedidosImportacao = function(cfg) {
		cfg.url = "rest/ordemProducao/buscarPedidosImportacao";
		BRIGADERIA.ajax.get(BRIGADERIA.ordemProducaoService.defaultCfg(cfg));
	}
});