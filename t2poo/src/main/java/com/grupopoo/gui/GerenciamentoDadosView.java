package com.grupopoo.gui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.grupopoo.services.Persistencia;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle ("Gerenciamento de Dados")
@Route(value = "gerenciamentoDados", layout = MainLayout.class)
public class GerenciamentoDadosView extends VerticalLayout{
    private final Persistencia persistencia;

    @Autowired
    public GerenciamentoDadosView(Persistencia persistencia){
        this.persistencia = persistencia;

        H2 tituloPagina = new H2("Gerenciamento de Arquivos JSON");
        Paragraph descricao = new Paragraph("Salve os dados atuais do sistema em arquivos JSON ou restaure backups anteriores.");

        Button botaoSalvar = new Button("Salvar Dados (Exportar)", VaadinIcon.DOWNLOAD_ALT.create());
        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoSalvar.addClickListener(e -> {
            try {
                persistencia.salvarDados();
                Notification.show("Dados salvos com sucesso!");
            } catch (Exception ex) {
                Notification.show("Erro ao tentar exportar os dados: " + ex.getMessage());
            }
        });

        Button botaoCarregar = new Button("Carregar Dados (Importar)", VaadinIcon.UPLOAD_ALT.create());
        botaoCarregar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        botaoCarregar.addClickListener(e -> {
            try {
                persistencia.carregarDados();
                Notification.show("Dados carregados com sucesso!");
            } catch (Exception ex) {
                Notification.show("Erro ao tentar carregar os dados: " + ex.getMessage());
            }
        });

        HorizontalLayout containerBotoes = new HorizontalLayout(botaoSalvar, botaoCarregar);
        containerBotoes.setSpacing(true);

        add(tituloPagina, descricao, containerBotoes, new Hr());
    }
}