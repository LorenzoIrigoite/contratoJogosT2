package com.grupopoo.gui;

import javax.security.auth.callback.TextInputCallback;

import com.grupopoo.Main;
import com.grupopoo.app.ACMESpiele;


import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home")
@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
        H1 titulo = new H1("ACMESpiele - Gerenciamento de Jogos");

        Paragraph infos = new Paragraph("Selecione a opção desejada:");

        Button telaCadastroClientes = new Button("Cadastro de clientes");
        Button telaCadastroJogos = new Button("Cadastro de jogos");
        Button telaCadastroContratos = new Button("Cadastro de contratos");
        Button telaGerDados = new Button("Importar/Exportar Dados");

        telaCadastroClientes.addClickListener(e -> UI.getCurrent().navigate("cadastroClientes"));
        telaCadastroJogos.addClickListener(e -> UI.getCurrent().navigate("cadastroJogos"));
        telaCadastroContratos.addClickListener(e -> UI.getCurrent().navigate("cadastroContratos"));
        telaGerDados.addClickListener(e -> UI.getCurrent().navigate("gerenciamentoDados"));

        HorizontalLayout botoesLayout = new HorizontalLayout(
            telaCadastroClientes, telaCadastroContratos, telaCadastroJogos, telaGerDados                                   
        );

        add(titulo);
        add(infos);
        add(botoesLayout);
    }
}