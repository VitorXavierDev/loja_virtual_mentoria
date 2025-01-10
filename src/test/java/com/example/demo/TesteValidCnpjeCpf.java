package com.example.demo;

import org.springframework.boot.test.context.SpringBootTest;

import vxs.lojavirtual.aplication.LojaVirtualMentoriaAplication;
import vxs.lojavirtual.util.ValidaCnpj;
import vxs.lojavirtual.util.ValidaCpf;

@SpringBootTest(classes = LojaVirtualMentoriaAplication.class)
public class TesteValidCnpjeCpf {

	
	public static void main(String[] args) {
		
		boolean isCnpj = ValidaCnpj.isCNPJ("20.927.756/0001-98");
		
		System.out.println("Cnpj Válido:" + isCnpj);
		
		boolean isCpf = ValidaCpf.isCPF("108.698.004-26");
		
		System.out.println("CPF Válido:" + isCpf);
		
	
		
	}
	
}
