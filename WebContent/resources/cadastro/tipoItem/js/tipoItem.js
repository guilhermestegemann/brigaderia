BRIGADERIA.tipoItem = new Object();

$(document).ready(function() {
	BRIGADERIA.tipoItem = {
		listar : function(cfg) {
			BRIGADERIA.ajax.get({
				url : "rest/tipoitem/buscar",
				success : function(listaTipoItem) {
					if (cfg && cfg.success) {
						cfg.success(listaTipoItem);
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