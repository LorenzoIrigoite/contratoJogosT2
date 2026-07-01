package com.grupopoo.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grupopoo.dados.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class Persistencia {

    private final ClienteRepository clientes;
    private final JogoRepository jogos;
    private final ContratoRepository contratos;
    private final FormaPagamentoRepository formasPagamento;
    private final ObjectMapper mapper;

    //arquivos de referência
    private final String ARQUIVO_CLIENTES = "clientes.json";
    private final String ARQUIVO_JOGOS = "jogos.json";
    private final String ARQUIVO_CONTRATOS = "contratos.json";
    private final String ARQUIVO_FORMAS_PAGAMENTO = "formasPagamento.json";

    @Autowired
    public Persistencia(ClienteRepository clientes, JogoRepository jogos, ContratoRepository contratos,
                        FormaPagamentoRepository formasPagamento) {
        this.clientes = clientes;
        this.jogos = jogos;
        this.contratos = contratos;
        this.formasPagamento = formasPagamento;
        
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public void salvarDados() throws IOException {
        // grava os ArrayLists e o Map nos arquivos
        mapper.writeValue(new File(ARQUIVO_CLIENTES), clientes.listarClientes());
        mapper.writeValue(new File(ARQUIVO_JOGOS), jogos.getArrayList()); 
        mapper.writeValue(new File(ARQUIVO_CONTRATOS), contratos.getArrayList());
        mapper.writeValue(new File(ARQUIVO_FORMAS_PAGAMENTO), formasPagamento.getMap());
    }

    public void carregarDados() throws IOException {
        File fClientes = new File(ARQUIVO_CLIENTES);
        File fJogos = new File(ARQUIVO_JOGOS);
        File fFormasPagamento = new File(ARQUIVO_FORMAS_PAGAMENTO);
        File fContratos = new File(ARQUIVO_CONTRATOS);

        // carregamento dos Clientes com validações de limpeza e prevenção de duplicatas
        if (fClientes.exists()) {
            clientes.listarClientes().clear();
            Cliente[] clientesCarregados = mapper.readValue(fClientes, Cliente[].class);
            for (Cliente c : clientesCarregados) {
                clientes.adicionarCliente(c);
            }
        }

        // carregamento dos Jogos
        if (fJogos.exists()) {
            jogos.getArrayList().clear(); 
            Jogo[] jogosCarregados = mapper.readValue(fJogos, Jogo[].class);
            for (Jogo j : jogosCarregados) {
                jogos.adicionarJogo(j); 
            }
        }

        // carregamento das Formas de Pagamento
        if (fFormasPagamento.exists()) {
            formasPagamento.getMap().clear();
            Map<Integer, List<FormaPagamento>> formasCarregadas = mapper.readValue(fFormasPagamento, 
                    new TypeReference<Map<Integer, List<FormaPagamento>>>() {});
            
            formasPagamento.getMap().putAll(formasCarregadas);
        }

        // carregamento dos contratos e reestruturação dos vínculos
        if (fContratos.exists()) {
            contratos.getArrayList().clear();
            Contrato[] contratosCarregados = mapper.readValue(fContratos, Contrato[].class);
            for (Contrato c : contratosCarregados) {
                Cliente clienteReal = clientes.encontrarClienteNumero(c.getCliente().getNumero());
                Jogo jogoReal = jogos.encontrarJogoCodigo(c.getJogo().getCodigo()); 
                
                if (clienteReal != null && jogoReal != null) {
                    Contrato contratoValido = new Contrato(c.getId(), c.getPeriodo(), clienteReal, jogoReal, c.getData(), c.getFormaPagamento());
                    contratos.adicionarContrato(contratoValido);
                }
            }
        }
    }
}