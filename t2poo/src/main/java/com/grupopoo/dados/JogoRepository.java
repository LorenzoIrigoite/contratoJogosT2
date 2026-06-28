package com.grupopoo.dados;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class JogoRepository{
    ArrayList<Jogo> jogos;

    public JogoRepository(){
        jogos = new ArrayList<Jogo>();
    }

    public void adicionarJogo(Jogo j){
        jogos.add(j);
    }

    public Jogo encontrarJogoCodigo(int codigo){
        for (Jogo j : jogos){
            if (codigo == j.getCodigo()) 
                return j;
        }
        return null;
    }

    public ArrayList<Jogo> encontrarJogoCategoria(Categoria categoria){
        ArrayList<Jogo> aux = new ArrayList<Jogo>();
        for (Jogo j : jogos){
            if (j.getCategoria() == categoria) 
                aux.add(j);
        }

        return aux;
    }

    public ArrayList<Jogo> listarJogosAno(int ano){
        ArrayList<Jogo> aux = new ArrayList<Jogo>();
        for (Jogo j : jogos){
            if (j.getAno() == ano) 
                aux.add(j);
        }
        return aux;
    }

    public ArrayList<Jogo> jogosAcimaValorMinuto(double valorMinuto){
        ArrayList<Jogo> aux = new ArrayList<Jogo>();
        for (Jogo j : jogos){
            if (j.getValorDiaria() >= valorMinuto) 
                aux.add(j);
        }
        return aux;
    }

    public ArrayList<Jogo> jogosAbaixoValorMinuto(double valorMinuto){
        ArrayList<Jogo> aux = new ArrayList<Jogo>();
        for (Jogo j : jogos){
            if (j.getValorDiaria() <= valorMinuto) 
                aux.add(j);
        }
        return aux;
    }

    public ArrayList<Jogo> listarJogos(){
        ArrayList<Jogo> aux = new ArrayList<Jogo>();
        for (Jogo j : jogos){
            aux.add(j);
        }
        return aux;
    }

    public boolean removerJogo(Jogo jogo){
        for (Jogo j : jogos){
            if (jogo == j){
                jogos.remove(j);
                return true;
            }
        }
        return false;
    }

}
