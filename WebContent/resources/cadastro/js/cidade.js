BRIGADERIA.cidade = new Object();

$(document).ready(function() {
	BRIGADERIA.cidade = {
		listar : function(cfg) {
			BRIGADERIA.ajax.get({
				url : "rest/cidades/buscar",
				success : function(listaDeCidade) {
					if (cfg && cfg.success) {
						cfg.success(listaDeCidade);
					}
				},
				error : function(error) {
					if (cfg && cfg.error) {
						cfg.error(error);
					}
				}
			});
		}
	};	
});