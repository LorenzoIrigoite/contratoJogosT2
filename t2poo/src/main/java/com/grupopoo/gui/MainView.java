package com.grupopoo.gui;

import javax.security.auth.callback.TextInputCallback;

import com.grupopoo.Main;
import com.grupopoo.app.ACMESpiele;


import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home")
@Route (value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {
    public MainView() {
        H1 titulo = new H1("ACMESpiele - Gerenciamento de Jogos");

        Paragraph infos = new Paragraph("Selecione a opção desejada:");

        Button botaoCadastroClientes = new Button("Cadastro de clientes");
        Button botaoCadastroJogos = new Button("Cadastro de jogos");
        Button botaoCadastroContratos = new Button("Cadastro de contratos");
        Button botaoGerDados = new Button("Importar/Exportar Dados");

        botaoCadastroClientes.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoCadastroJogos.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoCadastroContratos.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoGerDados.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        botaoCadastroClientes.addClickListener(e -> UI.getCurrent().navigate("cadastroClientes"));
        botaoCadastroJogos.addClickListener(e -> UI.getCurrent().navigate("cadastroJogos"));
        botaoCadastroContratos.addClickListener(e -> UI.getCurrent().navigate("cadastroContratos"));
        botaoGerDados.addClickListener(e -> UI.getCurrent().navigate("gerenciamentoDados"));

        HorizontalLayout botoesLayout = new HorizontalLayout(
            botaoCadastroClientes, botaoCadastroContratos, botaoCadastroJogos, botaoGerDados                                   
        );

        add(titulo, infos, botoesLayout);
    }
}