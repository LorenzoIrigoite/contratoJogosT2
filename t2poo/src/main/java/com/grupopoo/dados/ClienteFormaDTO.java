package com.grupopoo.dados;

import java.util.*;

public class ClienteFormaDTO {
    private int cod;
    private List<FormaPagamento> bancoFormas;

    public ClienteFormaDTO(int cod, List<FormaPagamento> bancoFormas){
        this.cod = cod;
        this.bancoFormas = bancoFormas;
    }


    public int getCod() {
        return this.cod;
    }

    public List<FormaPagamento> getBancoFormas() {
        return this.bancoFormas;
    }

}
