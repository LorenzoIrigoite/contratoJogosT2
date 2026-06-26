package com.grupopoo.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cadastro")
@Route("cadastro")
public class CadastroGeralView extends VerticalLayout {

    private Select<String> seletorTipoCadastro = new Select<>();
    private Select<String> seletorTipoCategoria = new Select<>();

    HorizontalLayout cabecalho = new HorizontalLayout();
    HorizontalLayout linhaBotoes = new HorizontalLayout();
    VerticalLayout linhaTextPessoaFisica = new VerticalLayout();
    VerticalLayout linhaTextCnpj = new VerticalLayout();
    VerticalLayout linhaTextJogos = new VerticalLayout();

    Button botaoCancel = new Button("Cancelar");
    Button botaoConfirm = new Button("Confirmar");

    public CadastroGeralView() {
        
    seletorTipoCadastro.setLabel("O que você deseja cadastrar?");
    seletorTipoCadastro.setPlaceholder("Selecione uma opção...");
    seletorTipoCadastro.setItems("Jogos", "Clientes CPF", "Clientes CNPJ");
    seletorTipoCadastro.setWidth("300px");

    seletorTipoCategoria.setLabel("Categoria");
    seletorTipoCategoria.setPlaceholder("Selecione uma categoria:");
    seletorTipoCategoria.setItems("AVENTURA", "ESTRATEGIA", "CORRIDA");
    seletorTipoCategoria.setWidth("300px");

    //aqui constroi os campos de cada casdastro e deixa eles invisiveis
    formCadastroEmpresa();
    formCadastroJogo();
    formCadastroPessoaFisica();
    ocultarFormularios();


    seletorTipoCadastro.addValueChangeListener(e -> {
        ocultarFormularios();
        String opcaoSelecionada = e.getValue();

        if("Jogos".equals(opcaoSelecionada)){
            linhaTextJogos.setVisible(true);
        }
        else if("Clientes CPF".equals(opcaoSelecionada)){
            linhaTextPessoaFisica.setVisible(true);
        }
        else if("Clientes CNPJ".equals(opcaoSelecionada)){
            linhaTextCnpj.setVisible(true);
        }
    });

        cabecalho.setWidthFull();
        cabecalho.setJustifyContentMode(JustifyContentMode.BETWEEN);
        cabecalho.setAlignItems(Alignment.CENTER);
       
        
        linhaBotoes.add(botaoCancel, botaoConfirm);
        add(cabecalho,seletorTipoCadastro,linhaTextCnpj,linhaTextJogos,linhaTextPessoaFisica);
    }

    // metodos que montam os formularios, pra deixar mais limpo.

    public void formCadastroJogo() {

        TextField campoNomeJogo = new TextField("Nome");
        TextField campoAno = new TextField("Ano");
        TextField campoValorDiario = new TextField("Valor");
        Button botaoCadastrar = new Button("Cadastrar Jogo");
        botaoCadastrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        linhaTextJogos.add(new H1("Cadastrar Jogo"), campoNomeJogo, campoAno, campoValorDiario, botaoCadastrar);
    }

    public void formCadastroPessoaFisica() {
        TextField campoNomePessoa = new TextField("Nome");
        TextField campoEmail = new TextField("Email");
        TextField campoCpf = new TextField("Cpf");
        Button botaoCadastrar = new Button("Cadastrar Cliente");
        botaoCadastrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        linhaTextPessoaFisica.add(new H1("Cadastrar Cliente"), campoNomePessoa, campoEmail, campoCpf, botaoCadastrar);
    }

    public void formCadastroEmpresa() {
        TextField campoNomePessoa = new TextField("Nome");
        TextField campoEmail = new TextField("Email");
        TextField campoCnpj = new TextField("Cnpj");
        TextField campoNomeFantasia = new TextField("Nome Fantasia");
        Button botaoCadastrar = new Button("Cadastrar Empresa");
        botaoCadastrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        linhaTextCnpj.add(new H1("Cadastrar Empresa"), campoNomePessoa, campoEmail, campoCnpj, campoNomeFantasia,
                botaoCadastrar);
    }

    public void ocultarFormularios() {
        linhaTextCnpj.setVisible(false);
        linhaTextJogos.setVisible(false);
        linhaTextPessoaFisica.setVisible(false);
    }
}