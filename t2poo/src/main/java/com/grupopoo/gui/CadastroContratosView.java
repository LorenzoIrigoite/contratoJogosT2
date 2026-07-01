package com.grupopoo.gui;

import java.util.ArrayList;
import java.util.Locale;
import java.time.LocalDate;
import java.util.stream.Stream;

import com.grupopoo.dados.CartaoCredito;
import com.grupopoo.dados.Cliente;
import com.grupopoo.dados.ClienteRepository;
import com.grupopoo.dados.ClienteFormaDTO;
import com.grupopoo.dados.Contrato;
import com.grupopoo.dados.ContratoRepository;
import com.grupopoo.dados.FormaPagamento;
import com.grupopoo.dados.FormaPagamentoRepository;
import com.grupopoo.dados.Jogo;
import com.grupopoo.dados.JogoRepository;
import com.grupopoo.dados.Pix;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.UI;
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
    private FormaPagamentoRepository pagamentos;

    private TextField campoId = new TextField("Id:");
    private TextField campoPeriodo = new TextField("Período (dias)");
    private DatePicker campoData = new DatePicker("Data do contrato:");
    
    private RadioButtonGroup<String> radioFormaPagamento = new RadioButtonGroup<>();
    private Button botaoSalvar = new Button("Cadastrar Contrato");

    private FormLayout formPagamento = new FormLayout();
    private FormLayout formClientes = new FormLayout();
    private FormLayout formJogos = new FormLayout();
    
    private FormLayout formContrato = new FormLayout();
    private final Grid<Cliente> gridCliente;
    private final Grid<Jogo> gridJogos;
    private final Grid<FormaPagamento> gridFormas;

    private Cliente clienteSelecionado;
    private Jogo jogoSelecionado;
    private FormaPagamento formaSelecionada;

    @Autowired
    public CadastroContratosView(ContratoRepository contratos, JogoRepository jogos, ClienteRepository clientes,
                                 FormaPagamentoRepository pagamentos){
        this.clientes = clientes;
        this.jogos = jogos;
        this.contratos = contratos;
        this.pagamentos = pagamentos;

        H2 tituloPagina = new H2("Cadastro de Contratos");
        
        gridFormas = new Grid<>(FormaPagamento.class, false);
        gridFormas.addColumn(FormaPagamento::getCodigo).setHeader("Código");
        gridFormas.addColumn(FormaPagamento::getDiaVencimento).setHeader("Dia de Vencimento");
        gridFormas.addColumn(forma -> {
            if (forma instanceof Pix)
                return "PIX";
            if (forma instanceof CartaoCredito)
                return "Cartão de Crédito";
            return "Desconhecido";
        }).setHeader("Tipo de Pagamento");

        gridFormas.asSingleSelect().addValueChangeListener(event -> {
            formaSelecionada = event.getValue();
        });
        formPagamento.add(gridFormas);


        gridCliente = new Grid<>(Cliente.class);
        gridCliente.setItems(clientes.getArrayList());
        gridCliente.setColumns("numero", "nome", "email");
        gridCliente.asSingleSelect().addValueChangeListener(event -> {
            clienteSelecionado = event.getValue();
            if (clienteSelecionado != null) {
                Notification.show("Cliente " + event.getValue().getNome() + " selecionado(a).");
                buscarEFiltrarPagamentos(clienteSelecionado.getNumero());
            } else {
                gridFormas.setItems(new ArrayList<>());
                formaSelecionada = null;
            }
        });
        formClientes.add(gridCliente);


        gridJogos = new Grid<>(Jogo.class);
        gridJogos.setItems(jogos.getArrayList());
        gridJogos.setColumns("codigo", "nome", "ano", "valorDiario", "categoria");
        gridJogos.asSingleSelect().addValueChangeListener(event -> selecionaJogo(event));

        formJogos.add(gridJogos);

        //setando Locale pt-BR para adaptar a data para DD/MM/AAAA 
        campoData.setLocale(new Locale("pt", "BR"));
        campoData.setPlaceholder("Selecione a data");

        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoSalvar.addClickListener(e -> salvarContrato());

        //tornando os campos um stream e setando atributo Requires nos campos obrigatórios
        Stream.of(campoId, campoPeriodo, campoData)
              .forEach(campo -> campo.setRequiredIndicatorVisible(true));


        formContrato.add(campoId, campoPeriodo, campoData);

        add(tituloPagina, formContrato, formClientes, formPagamento, formJogos, radioFormaPagamento, botaoSalvar);
    }

    private void buscarEFiltrarPagamentos(int numeroCliente) {
        ClienteFormaDTO dto = pagamentos.formaPorIdCliente(numeroCliente);

        if (dto == null || dto.getBancoFormas() == null || dto.getBancoFormas().isEmpty()) {
            Notification.show("Este cliente não possui formas de pagamento cadastradas! Redirecionando para o cadastro...");

            UI.getCurrent().navigate("cadastroFormasPagamento");
        } else {
            gridFormas.setItems(dto.getBancoFormas());
        }
    }

    private void selecionaJogo(ComponentValueChangeEvent<Grid<Jogo>, Jogo> event){
        if (event.getValue() == null)
            return;
        Notification.show("Jogo " + event.getValue().getNome() + " selecionado(a).");
        jogoSelecionado = event.getValue();
    }

    public void salvarContrato(){
        if(campoId.isEmpty() || campoPeriodo.isEmpty() || campoData.isEmpty()){
            Notification.show("Preencha todos os campos obrigatórios.");
            if (campoId.isEmpty()){
                campoId.setInvalid(true);
                campoId.setErrorMessage("Este campo é obrigatório.");
            }
            if (campoPeriodo.isEmpty()){
                campoPeriodo.setInvalid(true);
                campoPeriodo.setErrorMessage("Este campo é obrigatório.");
            }
            if (campoData.isEmpty()){
                campoData.setInvalid(true);
                campoData.setErrorMessage("Este campo é obrigatório.");
            }

            return;
        }

        if (clienteSelecionado == null) {
            Notification.show("Erro: Você deve selecionar um Cliente na tabela.");
            return;
        }
        if (jogoSelecionado == null) {
            Notification.show("Erro: Você deve selecionar um Jogo na tabela.");
            return;
        }
        if (formaSelecionada == null) {
            Notification.show("Erro: Você deve selecionar uma Forma de Pagamento na tabela.");
            return;
        }

        try {
            int id = Integer.parseInt(campoId.getValue());
            int periodo = Integer.parseInt(campoPeriodo.getValue());
            LocalDate data = campoData.getValue();

            if (this.contratos.encontrarContratoId(id) != null) {
                Notification.show("Erro: Já existe um contrato cadastrado com este ID.");
                campoId.setInvalid(true);
                return;
            }
            
            if (contratos.jogoDisponivel(jogoSelecionado, data)){
                Notification.show("Erro: Esse jogo está ocupado na data selecionada.");
                return;
            }

            Contrato contrato = new Contrato(id, periodo, clienteSelecionado, jogoSelecionado, data, formaSelecionada);

            this.contratos.adicionarContrato(contrato);
            Notification.show("Contrato cadastrado com sucesso!");
            limparFormulario();

        } catch (NumberFormatException e) {
            Notification.show("Erro: Verifique se os campos ID e Período contêm apenas números válidos.");
        } catch (NullPointerException e) {
            Notification.show("Erro: Jogo ou Cliente não foram encontrados.");
        } catch (Exception e){
            Notification.show("Ocorreu um erro inesperado." + e.getMessage());
        }

    }

    private void limparFormulario() {
        campoId.clear();
        campoPeriodo.clear();
        campoData.clear();
        clienteSelecionado = null;
        if (gridCliente != null) gridCliente.asSingleSelect().clear();
        jogoSelecionado = null;
        if (gridJogos != null) gridJogos.asSingleSelect().clear();
        formaSelecionada = null;
        if (gridFormas != null) gridFormas.asSingleSelect().clear();
        gridFormas.setItems(new ArrayList<>());
    }

}
