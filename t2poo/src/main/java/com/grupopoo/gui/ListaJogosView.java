package com.grupopoo.gui;

import com.grupopoo.dados.Jogo;
import com.grupopoo.dados.JogoRepository;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Lista de Jogos")
@Route(value = "listaJogos", layout = MainLayout.class)
public class ListaJogosView extends VerticalLayout {
    private JogoRepository jogos;

    private final Grid<Jogo> gridJogos;

    H2 tituloPagina = new H2("Lista de Jogos");

    @Autowired
    public ListaJogosView (JogoRepository jogos){
        this.jogos = jogos;

        gridJogos = new Grid<>(Jogo.class);
        gridJogos.setItems(jogos.getArrayList());
        gridJogos.setColumns("codigo", "nome", "ano", "valorDiario", "categoria");

        if(jogos.size() == 0){
            Notification.show("Ainda não há nenhum jogo cadastrado.");
        } else {
            add(tituloPagina, gridJogos);
            add(new Hr());
        }


    }
}
