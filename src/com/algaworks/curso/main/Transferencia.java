package com.algaworks.curso.main;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.algaworks.curso.modelo.ContaCorrente;

public class Transferencia {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlPU");
		EntityManager em = emf.createEntityManager();

		Scanner entrada = new Scanner(System.in);
		ContaCorrente conta1 = em.find(ContaCorrente.class, 1L);
		if (conta1 == null) {
			System.out.print("Digite o saldo inicial da conta 1: ");
			Double saldoInicialConta1 = entrada.nextDouble();
			conta1 = new ContaCorrente();
			conta1.setSaldo(saldoInicialConta1);
		}
		
		ContaCorrente conta2 = em.find(ContaCorrente.class, 2L);
		if (conta2 == null) {
			System.out.print("Digite o saldo incial da conta 2: ");
			Double saldoInicialConta2 = entrada.nextDouble();
			conta2 = new ContaCorrente();
			conta2.setSaldo(saldoInicialConta2);
		}
		
		em.getTransaction().begin();
		em.persist(conta1);
		em.persist(conta2);
		em.getTransaction().commit();
		
		System.out.println("Saldo da conta1: " + conta1.getSaldo() + ". Saldo conta2: " + conta2.getSaldo());
		
		System.out.println("---------------------");
		System.out.print("Digite o valor a retirar da conta 1 e depositar na conta 2: ");
		Double valorTransferencia = entrada.nextDouble();
		
		em.getTransaction().begin();
		conta1.setSaldo(conta1.getSaldo() - valorTransferencia);
		conta2.setSaldo(conta2.getSaldo() + valorTransferencia);
		
		if (conta1.getSaldo() > 0) {
			em.getTransaction().commit();
			System.out.println("Transfer�ncia realizada com sucesso!");
			System.out.println("Saldo da conta1: " + conta1.getSaldo() + ". Saldo conta2: " + conta2.getSaldo());
		} else {
			em.getTransaction().rollback();
			System.err.println("Transfer�ncia n�o realizada, saldo insuficiente!");
		}
	}

}
