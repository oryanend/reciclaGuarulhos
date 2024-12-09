package entities;

import javax.swing.*;

public class Alunos {
    private Integer id;
    private String nome;
    private String email;
    private String materia;

    public Alunos() {
    }

    public Alunos(Integer id, String nome, String email, String materia) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.materia = materia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Alunos{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", materia='" + materia + '\'' +
                '}';
    }
}
