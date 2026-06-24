package com.grupopoo.dados;

public class CartaoCredito extends FormaPagamento {
    private String numero;
    private String validade;

    public CartaoCredito(int codigo, int diaVencimento, String numero, String nomeTitular, String dataValidade){
        super(codigo, diaVencimento);
        this.numero = numero;
        this.validade = validade;
    }

    public String getNumero(){return numero;}
    public String getValidade(){return validade;}

    public void setNumero(String numero){this.numero = numero;}
    public void setValidade(String validade){this.validade = validade;}

  
    public String descrever() {
        return getNumero() + ";" + getValidade() + ";" + numero + ";" + validade;
    }
    
}
