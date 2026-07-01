package com.grupopoo.dados;

public class Jogo {
    private int codigo;
    private String nome;
    private int ano;
    private double valorDiario;
    private Categoria categoria;

    public Jogo(int codigo, String nome, int ano, double valorDiario, Categoria categoria){
        this.codigo = codigo;
        this.nome = nome;
        this.ano = ano;
        this.valorDiario = valorDiario;
        this.categoria = categoria;
    }

    public Jogo() {}

    public int getCodigo(){return codigo;}
    public String getNome() {return nome;}
    public int getAno(){return ano;}
    public double getValorDiario(){return valorDiario;}
    public Categoria getCategoria(){return categoria;}
    
    public String descrever(){
        return codigo + ";" + nome + ";" + ano + ";" + valorDiario + ";" + categoria.getNome();
    }
}
