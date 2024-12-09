package com.example.reciclaguarulhos;

public class DadosFormulario {
    private String titulo;
    private String endereco;
    private String bairro;
    private String cep;
    private String descricao;

    public DadosFormulario(String titulo, String endereco, String bairro, String cep, String descricao) {
        this.titulo = titulo;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cep = cep;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
