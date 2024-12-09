package entities;

import javax.swing.*;

public class CriarPessoa {
    public static Object criarPessoa(String tipo){
        String codigo = JOptionPane.showInputDialog("Digite o código do " + tipo + ":");
        String nome = JOptionPane.showInputDialog("Digite o nome do " + tipo + ":");
        String email = JOptionPane.showInputDialog("Digite o e-mail do " + tipo + ":");
        String materia = JOptionPane.showInputDialog("Digite a matéria do " + tipo + ":");

        int cod = Integer.parseInt(codigo);

        if (tipo.equals("Professor")) {
            return prof = new Professor(cod, nome, email, materia);
        } else if (tipo.equals("Aluno")) {
            return new Alunos(cod, nome, email, materia);
        } else {
            return null;
        }
    }
}
