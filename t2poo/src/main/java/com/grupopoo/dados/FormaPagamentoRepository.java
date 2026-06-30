package com.grupopoo.dados;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FormaPagamentoRepository {
    private Map<Integer, List<FormaPagamento>> bancoFormas;
    private ClienteFormaDTO clienteDTO;

    public FormaPagamentoRepository() {
        bancoFormas = new HashMap<>();
    }

    public void addClienteFormaPagamento(FormaPagamento forma, Cliente cliente) {
        int cod = cliente.getNumero();
        if (!bancoFormas.containsKey(cod)) {
            bancoFormas.put(cod, new ArrayList<>());
        }
        bancoFormas.get(cod).add(forma);
    }

    public ClienteFormaDTO formaPorIdCliente(int id) {
        ClienteFormaDTO clienteDTO;
        for (int auxId : bancoFormas.keySet()) {
            if (id == auxId) {
                List<FormaPagamento> metodosPagamento = bancoFormas.get(id);
                    clienteDTO = new ClienteFormaDTO(id, metodosPagamento);
                     return clienteDTO;
            }
        }
        return null;
    }

}
