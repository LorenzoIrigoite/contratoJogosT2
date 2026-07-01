package com.grupopoo.dados;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "tipo"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Pix.class, name = "pix"),
  @JsonSubTypes.Type(value = CartaoCredito.class, name = "cartao")
})
public abstract class FormaPagamento {
    private int codigo;
    private int diaVencimento;

    public FormaPagamento(int codigo, int diaVencimento){
        this.codigo = codigo;
        this.diaVencimento = diaVencimento;
    }

    public int getCodigo(){return codigo;}
    public int getDiaVencimento(){return diaVencimento;}
    public void setDiaVencimento(int diaVencimento){this.diaVencimento = diaVencimento;}
    public void setCodigo(int codigo){this.codigo = codigo;}
    public abstract String descrever();
}
