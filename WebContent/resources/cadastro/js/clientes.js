BRIGADERIA.clientes = new Object();

$(document).ready(function() {
	
	BRIGADERIA.clientes.aplicarMask = function () {
		$('#rg').mask("9.999.999");
		$('#cpf').mask("999.999.999-99");
		$('#cep').mask("99999-999");
		$('#telefone').mask("(99) 9999-9999");
		$('#celular').mask("(99) 9999-9999");
	};
	
	
	$('#aniversario').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	
	BRIGADERIA.clientes.listarCidade = function (codCidade) { 
		BRIGADERIA.cidade.listar({
			success: function(data) {
				var html = "";
				
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].nome + " - " + data[i].estado.uf + "</option>";
				}
				$("#cidade").append(html);
				if (codCidade != null) {
					$("#cidade").val(codCidade);
				}
			}
		});
	};
	
	BRIGADERIA.clientes.listarBairro = function (codBairro) {
		BRIGADERIA.bairro.listar({
			success: function(data) {
				var html = "";
				
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].nome + "</option>";
				}
				$("#bairro").append(html);
				if (codBairro != null) {
					$("#bairro").val(codBairro);
				}
			}
		});
	};

	BRIGADERIA.clientes.exibirEdicao = function (codigo) {
		var html = "<input type='text' class='form-control' id='codigo' name='codigo' />";
		$("#conteudo").append(html);
		BRIGADERIA.clienteService.buscarClientePeloCodigo({
			codigo : codigo,
			async: false,
			success : function (cliente) {
				
				$("#codigo").val(cliente.codigo);
				$("#nome").val(cliente.nome);
				$("#rg").val(cliente.rg);
				$("#cpf").val(cliente.cpf);
				$("#endereco").val(cliente.endereco);
				$("#numero").val(cliente.numero);
				$("#complemento").val(cliente.complemento);
				if (cliente.cep != "0") {
					$("#cep").val(cliente.cep);
				}
				BRIGADERIA.clientes.listarCidade(cliente.cidade);
				BRIGADERIA.clientes.listarBairro(cliente.bairro);
				$("#aniversario").val(BRIGADERIA.convertData.dateToStr(cliente.aniversario));
				$("#email").val(cliente.email);
				if (cliente.telefone != "0") {
					$("#telefone").val(cliente.telefone);
				}
				if (cliente.celular != "0") {
					$("#celular").val(cliente.celular);
				}
				$("#dataCadastro").val(BRIGADERIA.convertData.dateToStr(cliente.dataCadastro));
				$("#btnConfirmar").attr("onclick", "BRIGADERIA.clientes.salvarEdicao()");
				$("h1").text("Edição de Clientes");
				
				BRIGADERIA.clientes.aplicarMask();
			}
		});	
	};
	
	BRIGADERIA.clientes.exibirFormulario = function () {
		BRIGADERIA.clientes.listarCidade();
		BRIGADERIA.clientes.listarBairro();
	};
	
	BRIGADERIA.clientes.adicionar = function () {
		
		var newCliente = new Object();
		$('form input, form select').each(function(){newCliente[this.name]=this.value;})
		newCliente.cep = $("#cep").val().replace(/[^\d]+/g,'');
		newCliente.telefone = $("#telefone").val().replace(/[^\d]+/g,'');
		newCliente.celular = $("#celular").val().replace(/[^\d]+/g,'');
		newCliente.aniversario = BRIGADERIA.convertData.strToDate(newCliente.aniversario);
		BRIGADERIA.clienteService.adicionar(newCliente);
	};
	
	BRIGADERIA.clientes.salvarEdicao = function () {
		
		var cliente = new Object();
		$('form input, form select').each(function(){cliente[this.name]=this.value;})
		cliente.cep = $("#cep").val().replace(/[^\d]+/g,'');
		cliente.telefone = $("#telefone").val().replace(/[^\d]+/g,'');
		cliente.celular = $("#celular").val().replace(/[^\d]+/g,'');
		cliente.aniversario = BRIGADERIA.convertData.strToDate(cliente.aniversario);
		cliente.dataCadastro = BRIGADERIA.convertData.strToDate(cliente.dataCadastro);
		cliente.codigo = $("#codigo").val();
		BRIGADERIA.clienteService.atualizar(cliente);
	};
	
	BRIGADERIA.clientes.aplicarMask();
});// fecha ready