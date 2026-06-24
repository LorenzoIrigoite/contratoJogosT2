package com.grupopoo.dados;

public abstract class Cliente {
    private int numero;
    private String nome;
    private String email;

    public Cliente(int numero, String nome, String email){
        this.numero = numero;
        this.nome = nome;
        this.email = email;
    }

    public int getNumero(){return numero;}
    public String getNome(){return nome;}
    public String getEmail(){return email;}

    public void setNome(String nome){this.nome = nome;}
    public void setEmail(String email){this.email = email;}

    public abstract String descrever();
}
