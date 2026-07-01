package com.grupopoo.dados;

public class CartaoCredito extends FormaPagamento {
    private String numero;
    private String validade;
    private String nomeTitular;

    public CartaoCredito(int codigo, int diaVencimento, String numero, String nomeTitular, String dataValidade){
        super(codigo, diaVencimento);
        this.numero = numero;
        this.validade = dataValidade;
        this.nomeTitular = nomeTitular;
    }

    public CartaoCredito() {
        super(0, 0);
    }

    public String getNumero(){return numero;}
    public String getValidade(){return validade;}
    public String getNomeTitular(){return nomeTitular;}

    public void setNumero(String numero){this.numero = numero;}
    public void setValidade(String validade){this.validade = validade;}
    public void setNomeTitular(String nomeTitular){this.nomeTitular = nomeTitular;}

  
    public String descrever() {
        return super.getCodigo() + ";" + super.getDiaVencimento() + ";" + nomeTitular + ";" + numero
        + ";" + validade;
    }
    
}
