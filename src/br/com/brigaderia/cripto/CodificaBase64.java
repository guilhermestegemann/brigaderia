package br.com.brigaderia.cripto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class CodificaBase64 {
	
	public String decrypBase64(String base64) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException{
	
		String senha = new String(DatatypeConverter.parseBase64Binary(base64), "UTF-8");

		//System.out.println(senha);
		//System.out.println(base64);
		
		return senha;
	}

}
