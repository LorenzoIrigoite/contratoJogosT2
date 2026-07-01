package com.grupopoo.dados;

import java.util.ArrayList;
import java.util.Comparator;
import org.springframework.stereotype.Repository;

import com.grupopoo.app.ValorReservadoException;

@Repository
public class ClienteRepository {
    private ArrayList <Cliente> clientes;

    public ClienteRepository(){
        clientes = new ArrayList<Cliente>();
    }

    //INSERIR, ALTERAR E REMOVER
    public void adicionarCliente(Cliente c) {
        if (c.getNumero() == 99999) {
            throw new ValorReservadoException("Número do cliente inválido. O código 99999 é reservado pelo sistema.");
        }
        
        clientes.add(c);
        clientes.sort(Comparator.comparingInt(Cliente::getNumero));
    }

    public boolean removerCliente(Cliente cliente){
        for (Cliente c : clientes){
            if (cliente == c){
                clientes.remove(c);
                return true;
            }
        }
        return false;
    }

    public void alterarDadosCliente(Cliente cliente, int numero){
        //criar um cliente auxiliar na APP e copiar as informações dele para o cliente que desejo alterar
        //criar tratamento de excessões
        Cliente c = encontrarClienteNumero(numero);
        c.setNome(cliente.getNome());
        c.setEmail(cliente.getEmail());

        if (cliente instanceof Corporativo corporativo){
            Corporativo atualizar = (Corporativo)c;

            atualizar.setCnpj(corporativo.getCnpj());
            atualizar.setNomeFantasia(corporativo.getNomeFantasia());
        } else {
            Individual individual = (Individual)cliente;
            Individual atualizar = (Individual)c;

            atualizar.setCpf(individual.getCpf());
        }
    }

    //CONSULTAS
    public Cliente encontrarClienteNumero(int numero){
        for (Cliente c : clientes){
            if (numero == c.getNumero()) 
                return c;
        }
        return null;
    }

    public Cliente encontrarClienteNome(String nome){
        for (Cliente c : clientes){
            if (nome.equals(c.getNome())) 
                return c;
        }
        return null;
    }

    public Cliente encontrarClienteEmail(String email){
        for (Cliente c : clientes){
            if (email.equals(c.getEmail())) 
                return c;
        }
        return null;
    }

    public Cliente encontrarClienteCpf(String cpf){
        Individual cliente;
        for (Cliente c : clientes){
            if (c instanceof Individual){
                cliente = (Individual) c;
                if (cpf.equals(cliente.getCpf())) 
                    return c;
            }
        }
        return null;
    }

    public Cliente encontrarClienteCnpj(String cnpj){
        Corporativo cliente;
        for (Cliente c : clientes){
            if (c instanceof Corporativo){
                cliente = (Corporativo) c;
                if (cnpj.equals(cliente.getCnpj())) 
                    return c;
            }
        }
        return null;
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> aux = new ArrayList<Cliente>();
        for (Cliente cliente : clientes){
            aux.add(cliente);
        }
        return aux;
    }

    public ArrayList<Cliente> getArrayList(){
        return clientes;
    }

    public int size(){
        int qtd = 0;
        for (Cliente c : clientes){
            qtd++;
        }
        return qtd;
    }
}
