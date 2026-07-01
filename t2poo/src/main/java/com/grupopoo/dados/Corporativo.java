package com.grupopoo.dados;

public class Corporativo extends Cliente{
    private String cnpj;
    private String nomeFantasia;

    public Corporativo(int numero, String nome, String email, String cnpj, String nomeFantasia){
        super(numero, nome, email);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }
    
    public Corporativo() {
        super(0, "", "");
    }

    public String getCnpj(){return cnpj;}
    public String getNomeFantasia(){return nomeFantasia;}

    public void setCnpj(String cnpj){this.cnpj = cnpj;}
    public void setNomeFantasia(String nomeFantasia){this.nomeFantasia = nomeFantasia;}

    @Override
    public String descrever(){
        return super.getNumero() + ";" + super.getNome() + ";" + super.getEmail() + ";" + cnpj + ";" + nomeFantasia;
    }
}
