package com.grupopoo.dados;

public class Individual extends Cliente{
    private String cpf;

    public Individual(int numero, String nome, String email, String cpf){
        super(numero, nome, email);
        this.cpf = cpf;
    }

    public String getCpf(){return cpf;}
    
    public void setCpf(String cpf){this.cpf = cpf;}

    @Override
    public String descrever(){
        return super.getNumero() + ";" + super.getNome() + ";" + super.getEmail() + ";" + cpf;
    }
}
