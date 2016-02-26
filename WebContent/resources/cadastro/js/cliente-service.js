$(document).ready(function() {
	if(BRIGADERIA.clienteService == undefined){
		BRIGADERIA.clienteService = {};
	}
	
	BRIGADERIA.clienteService.defaultCfg = function(cfg) {
		var url = "rest/clientes";
		if(cfg && cfg.url){
			url = cfg.url;
		}
		var data = undefined;
		if(cfg && cfg.data){
			data = cfg.data;
		}
		return {
			'url': url,
			'data' : data,
			success: function(data){
				if (cfg && cfg.success) {
					cfg.success(data);
				}
			},
			error: function(error){
				if (cfg && cfg.error) {
					cfg.error(error);
				}
			}
		};
	};
	
	BRIGADERIA.clienteService.listar = function(cfg){
		cfg.url = "rest/clientes/buscarClientes/" + cfg.valorBusca;
		BRIGADERIA.ajax.get(BRIGADERIA.clienteService.defaultCfg(cfg));
	};
	
	BRIGADERIA.clienteService.adicionar = function(cliente) {
		cfg = {
			url: "rest/clientes/adicionar",
			data: cliente,
			success : function (sucesso) {
				bootbox.alert(sucesso);
				carregarConteudo ('resources/gerenciar/gerenciarClientes.html');
			},
			error : function (err) {
				bootbox.alert(err.ResponseText);
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
				carregarConteudo ('resources/gerenciar/gerenciarClientes.html');
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