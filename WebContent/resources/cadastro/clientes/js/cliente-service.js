$(document).ready(function() {
	if(BRIGADERIA.clienteService == undefined){
		BRIGADERIA.clienteService = {};
	}
	
	BRIGADERIA.clienteService.defaultCfg = function(cfg) {
		var url = "rest/clientes";// url padrão
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
	
	BRIGADERIA.clienteService.listar = function(cfg){
		cfg.url = "rest/clientes/buscarClientes/" + cfg.valorBusca + "/" + cfg.ativo;
		BRIGADERIA.ajax.get(BRIGADERIA.clienteService.defaultCfg(cfg));
	};
	
	BRIGADERIA.clienteService.adicionar = function(cliente) {
		cfg = {
			url: "rest/clientes/adicionar",
			data: cliente,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/clientes/gerenciarClientes.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.post(BRIGADERIA.clienteService.defaultCfg(cfg));
	};
	
	BRIGADERIA.clienteService.atualizar = function (cliente) {
		cfg = {
			url: "rest/clientes/atualizar",
			data: cliente,
			success : function(sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/clientes/gerenciarClientes.html');
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		};
		BRIGADERIA.ajax.put(BRIGADERIA.clienteService.defaultCfg(cfg));
	}
	
	BRIGADERIA.clienteService.deletar = function(cfg) {
		cfg.url = "rest/clientes/deletar/" + cfg.codigo;
		
		BRIGADERIA.ajax.del(BRIGADERIA.clienteService.defaultCfg(cfg));
	};
	
	BRIGADERIA.clienteService.buscarClientePeloCodigo = function(cfg) {
		cfg.url = "rest/clientes/buscarClientePeloCodigo/" + cfg.codigo;
		BRIGADERIA.ajax.get(BRIGADERIA.clienteService.defaultCfg(cfg));
	};
});