package com.grupopoo.gui;

import java.time.LocalDate;
import java.util.Locale;

import com.grupopoo.dados.CartaoCredito;
import com.grupopoo.dados.Cliente;
import com.grupopoo.dados.ClienteRepository;
import com.grupopoo.dados.FormaPagamento;
import com.grupopoo.dados.FormaPagamentoRepository;
import com.grupopoo.dados.Pix;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Cadastro Formas de Pagamento")
@Route(value = "cadastroFormasPagamento", layout = MainLayout.class)
public class CadastroFormaPagamentoView extends VerticalLayout {

    private FormaPagamentoRepository formasPagamento;
    private ClienteRepository clientes;
    private Cliente clienteSelecionado;

    private RadioButtonGroup<String> radioFormaPagamento = new RadioButtonGroup<>();

    private TextField campoCod = new TextField("Código:");
    private TextField campoDiaVencimento = new TextField("Dia de vencimento:");
    private TextField campoChavePix = new TextField("Chave PIX:");
    private TextField campoNumeroCartao = new TextField("Número do Cartão:");
    private TextField campoNomeTitularCartao = new TextField("Nome do Titular:");
    private DatePicker campoDataValidadeCartao = new DatePicker("Vencimento do cartão:");
    private Button botaoSalvar = new Button("Cadastrar Forma de Pagamento");
    private final H2 tituloPagina = new H2("Lista de clientes");

    private FormLayout formCamposDinamicos = new FormLayout();
    private final Grid<Cliente> gridCliente;

    @Autowired
    public CadastroFormaPagamentoView(FormaPagamentoRepository formasPagamento, ClienteRepository clientes) {
        this.formasPagamento = formasPagamento;
        this.clientes = clientes; 

       
        formCamposDinamicos.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formCamposDinamicos.setMaxWidth("200px");

        radioFormaPagamento.setLabel("Forma de Pagamento:");
        radioFormaPagamento.setItems("Cartão de crédito", "PIX");

        radioFormaPagamento.addValueChangeListener(event -> {
            atualizarCamposPagamento(event.getValue());
        });

        campoDataValidadeCartao.setLocale(new Locale("pt", "BR"));
        campoDataValidadeCartao.setPlaceholder("Selecione a data");

        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoSalvar.addClickListener(e -> salvarFormaPagamento());

        
        gridCliente = new Grid<>(Cliente.class, false);
        gridCliente.addColumn(Cliente::getNumero).setHeader("Número").setAutoWidth(true);
        gridCliente.addColumn(Cliente::getNome).setHeader("Nome").setAutoWidth(true);
        gridCliente.addColumn(Cliente::getEmail).setHeader("E-mail").setAutoWidth(true);   
        gridCliente.setItems(clientes.listarClientes());
        gridCliente.asSingleSelect().addValueChangeListener(event -> {
            clienteSelecionado = event.getValue();
            if (clienteSelecionado != null) {
                Notification.show("Cliente selecionado: " + clienteSelecionado.getNome());
            }
        });

        add(tituloPagina, gridCliente, radioFormaPagamento, formCamposDinamicos, botaoSalvar);
    }

    public void atualizarCamposPagamento(String tipoPagamento) {
        formCamposDinamicos.removeAll();

        if (tipoPagamento == null)
            return;

        formCamposDinamicos.add(campoCod, campoDiaVencimento);
        campoCod.setRequiredIndicatorVisible(true);
        campoDiaVencimento.setRequiredIndicatorVisible(true);

        if (tipoPagamento.equals("PIX")) {
            formCamposDinamicos.add(campoChavePix);
            campoChavePix.setRequiredIndicatorVisible(true);

        } else if (tipoPagamento.equals("Cartão de crédito")) {
            formCamposDinamicos.add(campoNumeroCartao, campoNomeTitularCartao, campoDataValidadeCartao);
            campoNumeroCartao.setRequiredIndicatorVisible(true);
            campoNomeTitularCartao.setRequiredIndicatorVisible(true);
            campoDataValidadeCartao.setRequiredIndicatorVisible(true);
        }
    }

    public void salvarFormaPagamento() {
        if (clienteSelecionado == null) {
            Notification.show("Erro: Você deve selecionar um cliente na tabela primeiro.");
            return;
        }

        if (radioFormaPagamento.isEmpty()) {
            Notification.show("Selecione o tipo de pagamento.");
            return;
        }
        if (campoCod.isEmpty() || campoDiaVencimento.isEmpty()) {
            Notification.show("Preencha os campos obrigatórios (Código e Vencimento).");
            return;
        }

        try {
            int codigo = Integer.parseInt(campoCod.getValue());
            int diaVencimento = Integer.parseInt(campoDiaVencimento.getValue());
            String tipoPagamento = radioFormaPagamento.getValue();

            FormaPagamento novaFormaPagamento = null;

            if (tipoPagamento.equals("PIX")) {
                if (campoChavePix.isEmpty()) {
                    campoChavePix.setInvalid(true);
                    campoChavePix.setErrorMessage("Este campo é obrigatório.");
                    Notification.show("Preencha a chave PIX.");
                    return;
                }
                novaFormaPagamento = new Pix(codigo, diaVencimento, campoChavePix.getValue());


            } else if (tipoPagamento.equals("Cartão de crédito")) {
                if (campoNumeroCartao.isEmpty() || campoNomeTitularCartao.isEmpty()
                        || campoDataValidadeCartao.isEmpty()) {
                    Notification.show("Preencha todos os dados do cartão.");
                    return;
                }

                String numeroCartao = campoNumeroCartao.getValue();
                String nomeTitular = campoNomeTitularCartao.getValue();
                LocalDate validade = campoDataValidadeCartao.getValue();

                novaFormaPagamento = new CartaoCredito(codigo, diaVencimento, numeroCartao, nomeTitular,
                        validade.toString());
            }

            if (novaFormaPagamento != null && clienteSelecionado != null) {
                formasPagamento.addClienteFormaPagamento(novaFormaPagamento, clienteSelecionado);
            }

            Notification.show("Forma de pagamento cadastrada e vinculada com sucesso!");
            limparFormulario();

        } catch (NumberFormatException e) {
            Notification.show("Erro: Código e Dia de Vencimento devem ser números.");
            campoCod.setInvalid(true);
            campoDiaVencimento.setInvalid(true);
        } catch (Exception e) {
            Notification.show("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private void limparFormulario() {
        campoCod.clear();
        campoDiaVencimento.clear();
        campoChavePix.clear();
        campoNumeroCartao.clear();
        campoNomeTitularCartao.clear();
        campoDataValidadeCartao.clear();
        radioFormaPagamento.clear();
        formCamposDinamicos.removeAll();

     
        gridCliente.deselectAll();
        clienteSelecionado = null;
    }
}