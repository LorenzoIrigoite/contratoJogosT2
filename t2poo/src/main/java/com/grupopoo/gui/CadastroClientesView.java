package com.grupopoo.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cadastro de Clientes")
@Route("cadastroClientes")
public class CadastroClientesView extends VerticalLayout {
    
    HorizontalLayout cabecalho = new HorizontalLayout();
    HorizontalLayout linhaBotoes = new HorizontalLayout();
    VerticalLayout linhaTextPessoaFisica = new VerticalLayout();
    VerticalLayout linhaTextCnpj = new VerticalLayout();

    
    H1 titulo = new H1("Cadastrar");
    TextField campoNome = new TextField("Nome");
    TextField campoEmail = new TextField("Email");
    TextField campoCpf = new TextField("Cpf");
    TextField campoCnpj = new TextField("Cnpj");
    TextField campoNomeFantasia = new TextField("Nome Fantasia");

    Button botaoCancel = new Button("Cancelar");
    Button botaoConfirm = new Button("Confirmar");

    public CadastroClientesView() {
        
        cabecalho.setWidthFull();
        cabecalho.setJustifyContentMode(JustifyContentMode.BETWEEN);
        cabecalho.setAlignItems(Alignment.CENTER);
       
        cabecalho.add(titulo);
        
     
        linhaBotoes.add(botaoCancel, botaoConfirm);
        linhaTextCnpj.add(campoNome, campoEmail, campoCnpj, campoNomeFantasia);
        linhaTextPessoaFisica.add(campoNome, campoCpf, campoEmail);
      

    }

    
}