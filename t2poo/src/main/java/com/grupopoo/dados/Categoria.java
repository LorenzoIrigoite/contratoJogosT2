package com.grupopoo.dados;

public enum Categoria {
    AVENTURA("AVENTURA"),
    ESTRATEGIA("ESTRATEGIA"),
    CORRIDA("CORRIDA");

    private String nome;

    private Categoria (String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
