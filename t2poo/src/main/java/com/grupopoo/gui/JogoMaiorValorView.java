package com.grupopoo.gui;

import com.grupopoo.dados.Jogo;
import com.grupopoo.dados.JogoRepository;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Jogo com Maior Valor Diário")
@Route(value = "jogoMaiorValor", layout = MainLayout.class)
public class JogoMaiorValorView extends VerticalLayout {

    H2 tituloPagina = new H2("Jogo(s) com Maior Valor Diário");
    private final Grid<Jogo> gridJogos;

    @Autowired
    public JogoMaiorValorView(JogoRepository jogosRepository) {

        gridJogos = new Grid<>(Jogo.class);
        gridJogos.setColumns("codigo", "nome", "ano", "valorDiario", "categoria");
        gridJogos.getColumns().forEach(col -> col.setAutoWidth(true));

        if (jogosRepository.getArrayList() == null || jogosRepository.getArrayList().isEmpty()) {
            Notification notificacao = Notification.show("Erro: Não há nenhum jogo cadastrado no sistema.");
            notificacao.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            double maiorValor = jogosRepository.getArrayList().stream()
                    .mapToDouble(Jogo::getValorDiario)
                    .max()
                    .orElse(0.0);

            // pra nao empatar
            List<Jogo> jogosFiltrados = jogosRepository.getArrayList().stream()
                    .filter(j -> j.getValorDiario() == maiorValor)
                    .collect(Collectors.toList());

            gridJogos.setItems(jogosFiltrados);
            add(tituloPagina, gridJogos, new Hr());
        }
    }
}
