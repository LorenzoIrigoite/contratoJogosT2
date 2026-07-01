package com.grupopoo.dados;

import java.util.ArrayList;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoRepository {
    ArrayList<Contrato> contratos;

    public ContratoRepository(){
        contratos = new ArrayList<Contrato>();
    }

    public int getTamanho(){return contratos.size();}

    public void adicionarContrato(Contrato c){
        contratos.add(c);
    }

    public Contrato encontrarContratoId(int id){
        for (Contrato c : contratos){
            if (c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public double valorMinutoTotalCliente(Cliente cliente){
        double valor = 0;
        for(Contrato c : contratos){
            if (c.getCliente() == cliente){
                valor+=c.getJogo().getValorDiario();
            }
        }
        return valor;
    }

    public Cliente encontraMaiorCliente(){
        Cliente maiorCliente = contratos.get(0).getCliente(), clienteComparacao;
        double maiorValor = valorMinutoTotalCliente(maiorCliente), comparacao = 0;

        for(int i = 1; i < contratos.size(); i++){
            clienteComparacao = contratos.get(i).getCliente();
            comparacao = valorMinutoTotalCliente(clienteComparacao);
            if (comparacao > maiorValor){
                maiorCliente = clienteComparacao;
                maiorValor = comparacao;
            }
        }

        return maiorCliente;
    }

    public ArrayList<Contrato> listarContratos(){
        ArrayList<Contrato> aux = new ArrayList<Contrato>();
        for (Contrato c : contratos){
            aux.add(c);
        }
        return aux;
    }

    public ArrayList<Contrato> getArrayList(){
        return contratos;
    }

    public boolean jogoDisponivel(Jogo j, LocalDate dataDesejada) {
        for (Contrato c : contratos) {
            if (c.getJogo().getCodigo() == j.getCodigo()) {
                LocalDate dataInicioExistente = c.getData();
                LocalDate dataTerminoExistente = c.getData().plusDays(c.getPeriodo());

                boolean conflitaInicio = !dataDesejada.isBefore(dataInicioExistente);
                boolean conflitaFim = !dataDesejada.isAfter(dataTerminoExistente);

                if (conflitaInicio && conflitaFim) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Contrato> removerContratosPorJogo(Jogo jogo){
        ArrayList<Contrato> contratosRemovidos = new ArrayList<Contrato>();
        for (Contrato c : contratos){
            if (jogo == c.getJogo()){
                contratosRemovidos.add(c);
            }
        }

        for (Contrato c : contratosRemovidos){
            contratos.remove(c);
        }

        return contratosRemovidos;
    }

    public boolean removerContrato(Contrato contrato){
        for(Contrato c : contratos){
            if (c == contrato){
                contratos.remove(c);
                return true;
            }
        }
        return false;
    }

    public int size(){
        int qtd = 0;
        for (Contrato c : contratos){
            qtd++;
        }
        return qtd;
    }
}
