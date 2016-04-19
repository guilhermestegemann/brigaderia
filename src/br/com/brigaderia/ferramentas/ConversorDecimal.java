package br.com.brigaderia.ferramentas;

import java.text.DecimalFormat;

public class ConversorDecimal {
	
	public String convertDoubleString (double valor) {
		DecimalFormat df = new DecimalFormat("0.00");
		String totalStr = df.format(valor);
		String[] part = totalStr.split(",");
		String totalAjustado = part[0] + "." + part[1];
		return totalAjustado;
	}
}