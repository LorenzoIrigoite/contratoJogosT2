package com.grupopoo.gui;

import com.grupopoo.dados.ClienteRepository;
import com.grupopoo.dados.Individual;
import com.grupopoo.dados.Corporativo;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

//identificação da view e definindo o layout base
@PageTitle ("Cadastro de Clientes")
@Route (value = "cadastroClientes", layout = MainLayout.class)
public class CadastroClientesView extends VerticalLayout{
    //criação do repositório na view
    private ClienteRepository clientela;

    //criação dos itens visuais da tela
    private TextField campoNumero = new TextField("Número:");
    private TextField campoNome = new TextField("Nome:");
    private TextField campoEmail = new TextField("E-mail:");  
    private TextField campoCpf = new TextField("CPF:");
    private TextField campoCnpj = new TextField("CNPJ:");
    private TextField campoNomeFantasia = new TextField("Nome Fantasia:");

    private RadioButtonGroup<String> radioTipoCliente = new RadioButtonGroup<>();
    private FormLayout formCamposEspecificos = new FormLayout();
    private Button botaoSalvar = new Button("Cadastrar Cliente");

    //avisa pro Spring que a view depende de outra classe, no caso ClienteRepository
    @Autowired
    public CadastroClientesView(ClienteRepository clientela){
        this.clientela = clientela;

        H2 tituloPagina = new H2("Cadastro de Clientes");

        radioTipoCliente.setLabel("Tipo de Cliente");
        radioTipoCliente.setItems("Individual", "Corporativo");
        radioTipoCliente.setValue("Individual"); //colocar o cadastro de cliente individual como pré-seleção
        atualizarCamposEspecificos("Individual");

        //adicionando evento ao selecionar uma opção no radio
        radioTipoCliente.addValueChangeListener(event -> {
            atualizarCamposEspecificos(event.getValue());
        });

        //estilizando e adicionando evento no botão "Salvar"
        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoSalvar.addClickListener(e -> salvarCliente());

        //adicionando itens na tela
        add(tituloPagina, campoNumero, campoNome, campoEmail, 
            radioTipoCliente, formCamposEspecificos, botaoSalvar);
    }

    //método para atualizar os campos ao selecionar opção no radio
    private void atualizarCamposEspecificos(String tipoCliente){
        formCamposEspecificos.removeAll();

        if (tipoCliente.equals("Individual")){
            formCamposEspecificos.add(campoCpf);
        } else if (tipoCliente.equals("Corporativo")){
            formCamposEspecificos.add(campoCnpj, campoNomeFantasia);
        }

    }
    
    //método para salvar cliente na lista
    private void salvarCliente(){
        //string para guardar a opção do radio
        String tipo = radioTipoCliente.getValue();

        //não avança se nenhuma opção foi selecionada no radio
        /* comentado porque o radio é pré-selecionado
        if (tipo == null)
            return;
        */

        //validação de preenchimento dos campos genéricos para clientes
        if(campoNumero.isEmpty() || campoNome.isEmpty() || campoEmail.isEmpty()){
            Notification.show("Preencha todos os campos.");
            //ifs não encadeados para visualização geral.
            if (campoNumero.isEmpty()){
                campoNumero.setInvalid(true);
                campoNumero.setErrorMessage("Este campo é obrigatório.");
            }
            if (campoNome.isEmpty()){
                campoNome.setInvalid(true);
                campoNome.setErrorMessage("Este campo é obrigatório.");
            } 
            if (campoEmail.isEmpty()){
                campoEmail.setInvalid(true);
                campoEmail.setErrorMessage("Este campo é obrigatório.");
            }
            
            return;
        }

        /*-----------------------------------------------------------------------*/
        try {
            //pegando valores dos campos
            int numero = Integer.parseInt(campoNumero.getValue());
            String nome = campoNome.getValue();
            String email = campoEmail.getValue();

            //verifica se cliente com mesmo número já existe
            if (this.clientela.encontrarClienteNumero(numero) != null) {
                Notification.show("Erro: Já existe um cliente cadastrado com este número.");
                return;
            }

            //validação da opção selecionada no radio
            if (tipo.equals("Individual")) {
                if (campoCpf.isEmpty()) {
                    campoCpf.setInvalid(true);
                    campoCpf.setErrorMessage("Este campo é obrigatório.");
                    return;
                }
                
                String cpf = campoCpf.getValue();
                Individual cliente = new Individual(numero, nome, email, cpf);
                
                this.clientela.adicionarCliente(cliente);
                Notification.show("Cliente Individual cadastrado com sucesso!");
                
            } else {
                if (campoCnpj.isEmpty() || campoNomeFantasia.isEmpty()) {
                    if (campoCnpj.isEmpty()){
                        campoCnpj.setInvalid(true);
                        campoCnpj.setErrorMessage("Este campo é obrigatório.");
                    } 
                    if (campoNomeFantasia.isEmpty()){
                        campoNomeFantasia.setInvalid(true);
                        campoNomeFantasia.setErrorMessage("Este campo é obrigatório.");
                    }
                    return;
                }
                
                String cnpj = campoCnpj.getValue();
                String nomeFantasia = campoNomeFantasia.getValue();
                Corporativo cliente = new Corporativo(numero, nome, email, cnpj, nomeFantasia);
                
                this.clientela.adicionarCliente(cliente);
                Notification.show("Cliente Corporativo cadastrado com sucesso!");
            }

            limparFormulario();

        } catch (NumberFormatException e) {
            Notification.show("Erro: O campo Número precisa ser um valor numérico válido.");
        } catch (Exception e){
            Notification.show("Ocorreu um erro inesperado.");
        }

    }

    //método para limpar os campos de preenchimento
    private void limparFormulario() {
        campoNumero.clear();
        campoNome.clear();
        campoEmail.clear();
        campoCpf.clear();
        campoCnpj.clear();
        campoNomeFantasia.clear();
    }
    
}
