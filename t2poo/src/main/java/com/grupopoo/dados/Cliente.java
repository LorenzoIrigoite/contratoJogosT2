package com.grupopoo.dados;

//biblioteca para notificar ao JSON a diferença das classes que devem ser instanciadas
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "tipo"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Individual.class, name = "individual"),
  @JsonSubTypes.Type(value = Corporativo.class, name = "corporativo")
})
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
