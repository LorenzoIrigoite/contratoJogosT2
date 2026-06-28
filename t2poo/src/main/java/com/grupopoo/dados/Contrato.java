package com.grupopoo.dados;
import java.time.LocalDate;

public class Contrato {
    private int id;
    private int periodo;
    private LocalDate data;
    private Cliente cliente;
    private Jogo jogo;
    private FormaPagamento formaPagamento;

    public Contrato(int id, int periodo, Cliente cliente, Jogo jogo, LocalDate data){
        this.id = id;
        this.periodo = periodo;
        this.cliente = cliente;
        this.jogo = jogo;
        this.data = data;
    }

    public Contrato(int id, int periodo, Cliente cliente, Jogo jogo, LocalDate data, FormaPagamento formaPagamento){
        this.id = id;
        this.periodo = periodo;
        this.cliente = cliente;
        this.jogo = jogo;
        this.data = data;
        this.formaPagamento = formaPagamento;
    }

    public int getId(){return id;}
    public int getPeriodo(){return periodo;}
    public Cliente getCliente(){return cliente;}
    public Jogo getJogo(){return jogo;}
    public LocalDate getData(){return data;}

    public double calculaValorFinal(){
        double valor = jogo.getValorDiaria();

        //regra das categorias
        if (jogo.getCategoria() == Categoria.AVENTURA){
            valor += (jogo.getValorDiaria() * 0.05);
        } else if (jogo.getCategoria() == Categoria.CORRIDA){
            valor += (jogo.getValorDiaria() * 0.15);
        } else {
            valor += (jogo.getValorDiaria() * 0.1);
        }

        //regra das formas de pagamento
        if (formaPagamento instanceof Pix){
            valor += (jogo.getValorDiaria() * 0.05);
        } else {
            valor += (jogo.getValorDiaria() * 0.05);
        }

        return valor;
    }

    public String descrever(){
        return id + ";" + periodo + ";" + cliente.getNumero() + ";" + jogo.getCodigo();
    }

}
