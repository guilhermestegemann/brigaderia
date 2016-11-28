package br.com.brigaderia.cripto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CodificaMD5 {
	
	public String convertToMD5(String senha) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException{
	
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(senha.getBytes(), 0, senha.length());
		String senhaMD5 = new BigInteger(1, digest.digest()).toString(16);
		//System.out.println(senhaMD5);
		return senhaMD5;
	}

}
