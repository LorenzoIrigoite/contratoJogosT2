package com.grupopoo.gui;

import java.util.Locale;
import java.time.LocalDate;
import java.util.stream.Stream;

import com.grupopoo.dados.CartaoCredito;
import com.grupopoo.dados.Cliente;
import com.grupopoo.dados.ClienteRepository;
import com.grupopoo.dados.Contrato;
import com.grupopoo.dados.ContratoRepository;
import com.grupopoo.dados.FormaPagamento;
import com.grupopoo.dados.Jogo;
import com.grupopoo.dados.JogoRepository;
import com.grupopoo.dados.Pix;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@PageTitle ("Cadastro de Contratos")
@Route (value = "cadastroContratos", layout = MainLayout.class)
public class CadastroContratosView extends VerticalLayout{
    private ContratoRepository contratos;
    private JogoRepository jogos;
    private ClienteRepository clientes;

    private TextField campoId = new TextField("Id:");
    private TextField campoPeriodo = new TextField("Período");
    private TextField campoCodigoPagamento = new TextField("Código pagamento:");
    private TextField campoDiaVencimento = new TextField("Dia de vencimento:");
    private TextField campoChavePix = new TextField("Chave PIX:");
    private TextField campoNumeroCartao = new TextField("Número do Cartão:");
    private TextField campoNomeTitularCartao = new TextField("Nome do Titular:");
    private TextField campoDataValidadeCartao = new TextField("Vencimento do cartão:");
    
    private DatePicker campoData = new DatePicker("Data do contrato:");
    private RadioButtonGroup<String> radioFormaPagamento = new RadioButtonGroup<>();
    private Button botaoSalvar = new Button("Cadastrar Contrato");

    private FormLayout formCamposPagamento = new FormLayout();
    private FormLayout formClientes = new FormLayout();
    private FormLayout formJogos = new FormLayout();

    private final Grid<Cliente> gridCliente;
    private final Grid<Jogo> gridJogos;

    private Cliente clienteSelecionado;
    private Jogo jogoSelecionado;

    @Autowired
    public CadastroContratosView(ContratoRepository contratos, JogoRepository jogos, ClienteRepository clientes){
        this.clientes = clientes;
        this.jogos = jogos;
        this.contratos = contratos;

        H2 tituloPagina = new H2("Cadastro de Contratos");


        gridCliente = new Grid<>(Cliente.class);
        gridCliente.setItems(clientes.getArrayList());
        gridCliente.setColumns("numero", "nome", "email");
        gridCliente.asSingleSelect().addValueChangeListener(event -> selecionaCliente(event));;

        formClientes.add(gridCliente);


        gridJogos = new Grid<>(Jogo.class);
        gridJogos.setItems(jogos.getArrayList());
        gridJogos.setColumns("codigo", "nome", "ano", "valorDiario", "categoria");
        gridJogos.asSingleSelect().addValueChangeListener(event -> selecionaJogo(event));

        formJogos.add(gridJogos);


        radioFormaPagamento.setLabel("Forma de Pagamento:");
        radioFormaPagamento.setItems("Cartão de crédito", "PIX", "Não informado");
        radioFormaPagamento.setValue("Não informado");
        atualizarCamposPagamento("Não informado");
        
        radioFormaPagamento.addValueChangeListener(event -> {
            atualizarCamposPagamento(event.getValue());
        });

        //setando Locale pt-BR para adaptar a data para DD/MM/AAAA 
        campoData.setLocale(new Locale("pt", "BR"));
        campoData.setPlaceholder("Selecione a data");

        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoSalvar.addClickListener(e -> salvarContrato());

        //tornando os campos um stream e setando atributo Requires nos campos obrigatórios
        Stream.of(campoId, campoPeriodo, campoData)
              .forEach(campo -> campo.setRequiredIndicatorVisible(true));

        add(tituloPagina, campoId, campoPeriodo, campoData, formClientes, formJogos, radioFormaPagamento, formCamposPagamento, botaoSalvar);
    }

    private void selecionaCliente(ComponentValueChangeEvent<Grid<Cliente>, Cliente> event){
        if (event.getValue() == null)
            return;
        Notification.show("Cliente " + event.getValue().getNome() + " selecionado(a).");
        clienteSelecionado = event.getValue();
    }

    private void selecionaJogo(ComponentValueChangeEvent<Grid<Jogo>, Jogo> event){
        if (event.getValue() == null)
            return;
        Notification.show("Jogo " + event.getValue().getNome() + " selecionado(a).");
        jogoSelecionado = event.getValue();
    }

    private void atualizarCamposPagamento(String tipoPagamento){
        formCamposPagamento.removeAll();

        if(radioFormaPagamento.getValue().equals("PIX") || radioFormaPagamento.getValue().equals("Cartão de crédito")){
            formCamposPagamento.add(campoCodigoPagamento, campoDiaVencimento);
            campoCodigoPagamento.setRequiredIndicatorVisible(true);
            campoDiaVencimento.setRequiredIndicatorVisible(true);
        }

        if (tipoPagamento.equals("PIX")){
            formCamposPagamento.add(campoChavePix);
            campoChavePix.setRequiredIndicatorVisible(true);
        } else if (tipoPagamento.equals("Cartão de crédito")){
            formCamposPagamento.add(campoNumeroCartao, campoNomeTitularCartao, campoDataValidadeCartao);
            campoNumeroCartao.setRequiredIndicatorVisible(true);
            campoNomeTitularCartao.setRequiredIndicatorVisible(true);
            campoDataValidadeCartao.setRequiredIndicatorVisible(true);
        }
    }

    public void salvarContrato(){
        if(campoId.isEmpty() || clienteSelecionado == null || jogoSelecionado == null || campoPeriodo.isEmpty() 
            || campoData.isEmpty() || campoCodigoPagamento.isEmpty() || campoDiaVencimento.isEmpty()){
            Notification.show("Preencha todos os campos obrigatórios.");
            if (campoId.isEmpty()){
                campoId.setInvalid(true);
                campoId.setErrorMessage("Este campo é obrigatório.");
            }
            if (clienteSelecionado == null){
                Notification.show("Selecione um cliente.");
            }
            if (jogoSelecionado == null){
                Notification.show("Selecione um jogo.");
            }
            if (campoPeriodo.isEmpty()){
                campoPeriodo.setInvalid(true);
                campoPeriodo.setErrorMessage("Este campo é obrigatório.");
            }
            if (campoData.isEmpty()){
                campoData.setInvalid(true);
                campoData.setErrorMessage("Este campo é obrigatório.");
            }
            if (campoCodigoPagamento.isEmpty()){
                campoCodigoPagamento.setInvalid(true);
                campoCodigoPagamento.setErrorMessage("Este campo é obrigatório.");
            }
            if (campoDiaVencimento.isEmpty()){
                campoDiaVencimento.setInvalid(true);
                campoDiaVencimento.setErrorMessage("Este campo é obrigatório.");
            }

            return;
        }

        try {
            int id = Integer.parseInt(campoId.getValue());
            int periodo = Integer.parseInt(campoPeriodo.getValue());
            LocalDate data = campoData.getValue();
            String selecaoFormaPagamento = radioFormaPagamento.getValue();

            if (this.contratos.encontrarContratoId(id) != null) {
                Notification.show("Erro: Já existe um contrato cadastrado com este ID.");
                return;
            }
            
            if (contratos.jogoDisponivel(jogoSelecionado, data)){
                Notification.show("Erro: Esse jogo está ocupado na data selecionada.");
                return;
            }

            Contrato contrato;
            FormaPagamento formaPagamento;

            int codigo = Integer.parseInt(campoCodigoPagamento.getValue());
            int diaVencimento = Integer.parseInt(campoDiaVencimento.getValue());

            if (selecaoFormaPagamento.equals("PIX")){
                if(campoChavePix.isEmpty()){
                    campoChavePix.setInvalid(true);
                    campoChavePix.setErrorMessage("Este campo é obrigatório.");
                    Notification.show("Preencha todos os campos.");
                    return;
                }
                String chavePix = campoChavePix.getValue();
                Pix pix = new Pix(codigo, diaVencimento, chavePix);
                formaPagamento = pix;
            } else {
                if(campoNumeroCartao.isEmpty() || campoNomeTitularCartao.isEmpty() || campoDataValidadeCartao.isEmpty()){
                    Notification.show("Preencha todos os campos.");

                    if(campoNumeroCartao.isEmpty()){
                        campoNumeroCartao.setInvalid(true);
                        campoNumeroCartao.setErrorMessage("Este campo é obrigatório.");
                    }
                    if(campoNomeTitularCartao.isEmpty()){
                        campoNomeTitularCartao.setInvalid(true);
                        campoNomeTitularCartao.setErrorMessage("Este campo é obrigatório.");
                    }
                    if(campoDataValidadeCartao.isEmpty()){
                        campoDataValidadeCartao.setInvalid(true);
                        campoDataValidadeCartao.setErrorMessage("Este campo é obrigatório.");
                    }

                    return;
                }
                String numeroCartao = campoNumeroCartao.getValue();
                String nomeTitularCartao = campoNomeTitularCartao.getValue();
                String dataValidadeCartao = campoDataValidadeCartao.getValue();
                CartaoCredito cartaoCredito = new CartaoCredito(codigo, diaVencimento, numeroCartao, nomeTitularCartao, dataValidadeCartao);
                formaPagamento = cartaoCredito;
            }
            contrato = new Contrato(id, periodo, clienteSelecionado, jogoSelecionado, data, formaPagamento);
            

            this.contratos.adicionarContrato(contrato);
            Notification.show("Contrato cadastrado com sucesso!");
            limparFormulario();

        } catch (NumberFormatException e) {
            Notification.show("Erro: Verifique se os campos ID, Número, Código e Dia de Vencimento contém apenas números válidos.");
        } catch (NullPointerException e) {
            Notification.show("Erro: Jogo ou Cliente não foram encontrados.");
        } catch (Exception e){
            Notification.show("Ocorreu um erro inesperado.");
        }

    }

    private void limparFormulario() {
        campoId.clear();
        clienteSelecionado = null;
        if (gridCliente != null) gridCliente.asSingleSelect().clear();
        jogoSelecionado = null;
        if (gridJogos != null) gridJogos.asSingleSelect().clear();
        campoPeriodo.clear();
        campoData.clear();
        radioFormaPagamento.setValue("Não informado");
        campoChavePix.clear();
        campoCodigoPagamento.clear();
        campoDiaVencimento.clear();
        campoDataValidadeCartao.clear();
        campoNomeTitularCartao.clear();
        campoNumeroCartao.clear();
    }

}
