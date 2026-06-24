package com.grupopoo.dados;

import java.util.ArrayList;

public class ClienteRepository {
    private ArrayList <Cliente> clientes;

    public ClienteRepository(){
        clientes = new ArrayList<Cliente>();
    }

    //INSERIR, ALTERAR E REMOVER
    public void adicionarCliente(Cliente c){
        clientes.add(c);
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
        clientes.get(numero).setNome(cliente.getNome());
        clientes.get(numero).setEmail(cliente.getEmail());

        if (cliente instanceof Corporativo){
            Corporativo c = (Corporativo)cliente;
            Corporativo atualizar = (Corporativo)clientes.get(numero);

            atualizar.setCnpj(c.getCnpj());
            atualizar.setNomeFantasia(c.getNomeFantasia());
        } else {
            Individual c = (Individual)cliente;
            Individual atualizar = (Individual)clientes.get(numero);

            atualizar.setCpf(c.getCpf());
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
}
