BRIGADERIA.clientes = new Object();

$(document).ready(function() {
	
	BRIGADERIA.clientes.aplicarMask = function () {
		$('#rg').mask("0.000.000");
		$('#cpf').mask("000.000.000-00");
		$('#cep').mask("00000-000");
		$('.fone').mask("(00) 0000-0000");
	};
	
	
	$('#aniversario').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	
	BRIGADERIA.clientes.listarCidade = function (codCidade) { 
		BRIGADERIA.cidadeService.listar({
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
		BRIGADERIA.bairroService.listar({
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
		var html = "<input type='text' class='form-control' id='codigo' name='codigo' style='display:none' />";
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
				if (cliente.aniversario != null) {
					$("#aniversario").val(BRIGADERIA.convertData.dateToStr(cliente.aniversario));
				}
				$("#email").val(cliente.email);
				if (cliente.telefone != "0") {
					$("#telefone").val(cliente.telefone);
				}
				if (cliente.celular != "0") {
					$("#celular").val(cliente.celular);
				}
				$("#dataCadastro").val(BRIGADERIA.convertData.dateToStr(cliente.dataCadastro));
				$("#ativo").val(cliente.ativo);
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
		$('form input, form select').each(function(){newCliente[this.name]=this.value;});
		newCliente.cep = $("#cep").val().replace(/[^\d]+/g,'');
		newCliente.telefone = $("#telefone").val().replace(/[^\d]+/g,'');
		newCliente.celular = $("#celular").val().replace(/[^\d]+/g,'');
		newCliente.cpf = $("#cpf").val().replace(/[^\d]+/g,'');
		newCliente.rg = $("#rg").val().replace(/[^\d]+/g,'');
		newCliente.ativo = $("#ativo").val();
		if (newCliente.cpf != "") {
			var cpfValido = BRIGADERIA.validaCpf.validarCpf(newCliente.cpf);
			if (!cpfValido) {
				bootbox.alert("Cpf Inválido, por favor verifique!");
			}
		}
		if ((newCliente.cpf == "") || (cpfValido)) {
			retornoValida = BRIGADERIA.validaClientes.validar(newCliente);
			if (retornoValida == "") {
				if (newCliente.cidade == "") {
					newCliente.cidade = null;
				}
				if (newCliente.bairro == "") {
					newCliente.bairro = null;
				}
				if (newCliente.aniversario != "") {
					newCliente.aniversario = BRIGADERIA.convertData.strToDate(newCliente.aniversario);
				}
				BRIGADERIA.clienteService.adicionar(newCliente);
			}else{
				bootbox.alert("Favor verificar os campos obrigatórios: " + retornoValida);
			}
		}
	};
	
	BRIGADERIA.clientes.salvarEdicao = function () {
		
		var cliente = new Object();
		$('form input, form select').each(function(){cliente[this.name]=this.value;})
		cliente.cep = $("#cep").val().replace(/[^\d]+/g,'');
		cliente.telefone = $("#telefone").val().replace(/[^\d]+/g,'');
		cliente.celular = $("#celular").val().replace(/[^\d]+/g,'');
		cliente.cpf = $("#cpf").val().replace(/[^\d]+/g,'');
		cliente.rg = $("#rg").val().replace(/[^\d]+/g,'');
		cliente.ativo = $("#ativo").val();
		if (cliente.cpf != "") {
			var cpfValido = BRIGADERIA.validaCpf.validarCpf(cliente.cpf);
			if (!cpfValido) {
				bootbox.alert("Cpf Inválido, por favor verifique!");
			}
		}
		if ((cliente.cpf == "") || (cpfValido)) {
			var retornoValida = BRIGADERIA.validaClientes.validar(cliente);
			if (retornoValida == "") {
				if (cliente.aniversario != "") {
					cliente.aniversario = BRIGADERIA.convertData.strToDate(cliente.aniversario);
				}
				cliente.dataCadastro = BRIGADERIA.convertData.strToDate(cliente.dataCadastro);
				cliente.codigo = $("#codigo").val();
				BRIGADERIA.clienteService.atualizar(cliente);
			}else{
				bootbox.alert("Favor verificar os campos obrigatórios: " + retornoValida);
			}
		}
		
		
	};
	
	BRIGADERIA.clientes.aplicarMask();
});// fecha ready