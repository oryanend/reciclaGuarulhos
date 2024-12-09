package application;

import entities.Alunos;
import entities.Professor;

import javax.swing.*;

import static entities.CriarPessoa.criarPessoa;

public class Program {
    public static void main(String[] args) {
        criarPessoa("Professor");

        System.out.println();

        criarPessoa("Aluno");

        System.out.println();
    }
}
