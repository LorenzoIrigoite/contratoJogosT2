package com.grupopoo.dados;

public class Pix extends FormaPagamento {
    private String chave;

    public Pix(int codigo, int diaVencimento, String chave){
        super(codigo, diaVencimento);
        this.chave = chave;
    }
    
    public Pix() {
        super(0, 0);
    }

    public String getChave(){return chave;}
    public void setChave(String chave){this.chave = chave;}

    public String descrever() {
        return  chave;
    }
    
}
