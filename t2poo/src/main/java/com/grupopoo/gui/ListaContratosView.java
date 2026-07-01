package com.grupopoo.gui;

import com.grupopoo.dados.ContratoRepository;
import com.grupopoo.dados.Cliente;
import com.grupopoo.dados.Jogo;
import com.grupopoo.dados.FormaPagamento;
import com.grupopoo.dados.Contrato;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Lista de Contratos")
@Route(value = "listaContratos", layout = MainLayout.class)
public class ListaContratosView extends VerticalLayout {
    private ContratoRepository contratos;

    private final Grid<Contrato> gridContratos;

    H2 tituloPagina = new H2("Lista de Contratos");

    @Autowired
    public ListaContratosView (ContratoRepository contratos){
        this.contratos = contratos;

        gridContratos = new Grid<>(Contrato.class, false);
        gridContratos.addColumn(Contrato::getId).setHeader("Id");
        gridContratos.addColumn(Contrato::getPeriodo).setHeader("Período");
        gridContratos.addColumn(Contrato::getData).setHeader("Data");
        gridContratos.addColumn(Contrato::getCliente).setHeader("Cliente");
        gridContratos.addColumn(Contrato::getJogo).setHeader("Jogo");
        gridContratos.addColumn(Contrato::getFormaPagamento).setHeader("Forma de Pagamento");

        gridContratos.addComponentColumn(c -> {
            Button botaoRemover = new Button(VaadinIcon.TRASH.create());
            botaoRemover.addThemeVariants(ButtonVariant.LUMO_ERROR);
            botaoRemover.addClickListener(e -> modalRemocao(c));
            return botaoRemover;
        }).setHeader("Remover");


        if(contratos.size() == 0){
            Notification.show("Ainda não há nenhum contrato cadastrado.");
        } else {
            add(tituloPagina, gridContratos, new Hr());
            atualizarLista();
        }

    }

    private void atualizarLista() {
        gridContratos.setItems(contratos.getArrayList()); 
    }

    private void modalRemocao(Contrato contrato) {
        Dialog modal = new Dialog();
        modal.setHeaderTitle("Remover Contrato");
        modal.add(new Paragraph("Você tem certeza que deseja remover este contrato?"));

        Button confirmarRemocao = new Button("Sim, remover", e -> {
            try {
                contratos.removerContrato(contrato);
                atualizarLista();
                modal.close();
                modalMensagem("Contrato removido com sucesso!");
            } catch (Exception ex){
                Notification.show("Ocorreu um erro inesperado.");
            }
            
        });
        confirmarRemocao.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        Button fecharModal = new Button("Não", e -> modal.close());
        fecharModal.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        modal.getFooter().add(fecharModal, confirmarRemocao);
        modal.open();
    }

    public void modalMensagem(String mensagem){
        Dialog modal = new Dialog();
        modal.add(new H2(mensagem));

        Button fecharModal = new Button("Ok", e -> modal.close());
        fecharModal.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        modal.getFooter().add(fecharModal);
        
        modal.open();
    }

}
