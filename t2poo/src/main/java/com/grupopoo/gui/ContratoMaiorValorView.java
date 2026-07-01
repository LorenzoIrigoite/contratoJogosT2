package com.grupopoo.gui;

import com.grupopoo.dados.Contrato;
import com.grupopoo.dados.ContratoRepository;
import com.grupopoo.dados.Pix;
import com.grupopoo.dados.CartaoCredito;

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

@PageTitle("Contrato com Maior Valor Final")
@Route(value = "contratoMaiorValor", layout = MainLayout.class)
public class ContratoMaiorValorView extends VerticalLayout {

    H2 tituloPagina = new H2("Contrato(s) com Maior Valor Final");
    private final Grid<Contrato> gridContratos;

    @Autowired
    public ContratoMaiorValorView(ContratoRepository contratosRepository) {
        gridContratos = new Grid<>(Contrato.class, false);

        gridContratos.addColumn(Contrato::getId).setHeader("ID Contrato");
        gridContratos.addColumn(Contrato::getPeriodo).setHeader("Período (Dias)");
        gridContratos.addColumn(Contrato::getData).setHeader("Data");
        gridContratos.addColumn(c -> c.getCliente() != null ? c.getCliente().getNome() + " (ID: " + c.getCliente().getNumero() + ")" : "N/A").setHeader("Cliente");
        gridContratos.addColumn(c -> c.getJogo() != null ? c.getJogo().getNome() : "N/A").setHeader("Jogo");
        
        gridContratos.addColumn(c -> {
            if (c.getFormaPagamento() == null) return "Não informada";
            return (c.getFormaPagamento() instanceof Pix) ? "PIX" : "Cartão de Crédito";
        }).setHeader("Forma de Pagamento");

        gridContratos.addColumn(c -> String.format("R$ %.2f", calcularValorFinal(c))).setHeader("Valor Final");
        gridContratos.getColumns().forEach(col -> col.setAutoWidth(true));

        if (contratosRepository.getTamanho() == 0) {
            Notification notificacao = Notification.show("Erro: Não há nenhum contrato cadastrado no sistema.");
            notificacao.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            double maiorValorContrato = contratosRepository.listarContratos().stream()
                    .mapToDouble(this::calcularValorFinal)
                    .max()
                    .orElse(0.0);

            List<Contrato> contratosFiltrados = contratosRepository.listarContratos().stream()
                    .filter(c -> calcularValorFinal(c) == maiorValorContrato)
                    .collect(Collectors.toList());

            gridContratos.setItems(contratosFiltrados);
            add(tituloPagina, gridContratos, new Hr());
        }
    }
    
    private double calcularValorFinal(Contrato c) {
        if (c.getJogo() != null) {
            return c.getPeriodo() * c.getJogo().getValorDiario();
        }
        return 0.0;
    }
}