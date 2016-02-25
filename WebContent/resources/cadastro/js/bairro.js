BRIGADERIA.bairro = new Object();

$(document).ready(function() {
	BRIGADERIA.bairro = {
		listar : function(cfg) {
			BRIGADERIA.ajax.get({
				url : "rest/bairros/buscar",
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