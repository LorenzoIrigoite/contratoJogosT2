package com.grupopoo.gui;

import com.grupopoo.dados.Cliente;
import com.grupopoo.dados.ClienteRepository;
import com.grupopoo.dados.Corporativo;
import com.grupopoo.dados.Individual;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Lista de Clientes")
@Route(value = "listaClientes", layout = MainLayout.class)
public class ListaClientesView extends VerticalLayout {
    private ClienteRepository clientes;

    private final H2 tituloPagina = new H2("Lista de clientes");
    private final Grid<Cliente> gridCliente;

    @Autowired
    public ListaClientesView (ClienteRepository clientes){
        this.clientes = clientes;

        //criando o grid de clientes, não adicionando de forma automática as colunas
        gridCliente = new Grid<>(Cliente.class, false);
        gridCliente.addColumn(Cliente::getNumero).setHeader("Número");
        gridCliente.addColumn(Cliente::getNome).setHeader("Nome");
        gridCliente.addColumn(Cliente::getEmail).setHeader("E-mail");

        //adicionando a coluna Editar com o botão de edição dos dados do cliente
        gridCliente.addComponentColumn(c -> {
            Button botaoEditar = new Button(VaadinIcon.PENCIL.create());
            botaoEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            botaoEditar.addClickListener(e -> modalEdicao(c));
            return botaoEditar;
        }).setHeader("Editar");

        if(clientes.size() == 0){
            Notification.show("Ainda não há nenhum cliente cadastrado.");
        } else {
            add(tituloPagina, gridCliente, new Hr());
            atualizarLista();
        }

    }

    private void atualizarLista() {
        gridCliente.setItems(clientes.getArrayList()); 
    }

    private void modalEdicao(Cliente cliente) {
        // cria o modal
        Dialog modal = new Dialog();
        modal.setHeaderTitle("Editar Cliente");

        //cria uma formLayout para comportar os campos específicos
        FormLayout formLayout = new FormLayout();

        TextField campoNumero = new TextField("Numero:");
        campoNumero.setValue(String.valueOf(cliente.getNumero()));
        campoNumero.setReadOnly(true);

        TextField campoNome = new TextField("Nome:");
        campoNome.setValue(cliente.getNome());

        TextField campoEmail = new TextField("E-mail:");
        campoEmail.setValue(cliente.getEmail());

        //adiciona os campos comuns direto no modal
        modal.add(campoNumero, campoNome, campoEmail);

        TextField campoCpf = new TextField("CPF:");
        TextField campoCnpj = new TextField("CNPJ:");
        TextField campoNomeFantasia = new TextField("Nome Fantasia:");

        if (cliente instanceof Corporativo corporativo) {
            campoCnpj.setValue(corporativo.getCnpj());
            campoNomeFantasia.setValue(corporativo.getNomeFantasia());
            formLayout.add(campoCnpj, campoNomeFantasia);
        } else if (cliente instanceof Individual individual) {
            campoCpf.setValue(individual.getCpf());
            formLayout.add(campoCpf);
        }

        modal.add(formLayout);

        Button botaoSalvar = new Button("Salvar", e -> {
            try {
                String nome = campoNome.getValue();
                String email = campoEmail.getValue();

                if (cliente instanceof Corporativo) {
                    String cnpj = campoCnpj.getValue();
                    String nomeFantasia = campoNomeFantasia.getValue();

                    Corporativo corporativo = new Corporativo(99999, nome, email, cnpj, nomeFantasia);
                    clientes.alterarDadosCliente(corporativo, cliente.getNumero()); 
                } else if (cliente instanceof Individual) {
                    String cpf = campoCpf.getValue();
                    
                    Individual individual = new Individual(99999, nome, email, cpf);
                    clientes.alterarDadosCliente(individual, cliente.getNumero()); 
                }

                Notification.show("Cliente atualizado com sucesso!");
                atualizarLista();
                modal.close();
            } catch (Exception ex) {
                Notification.show("Ocorreu um erro inesperado.");
            }
        });
        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button botaoCancelar = new Button("Cancelar", e -> modal.close());

        modal.getFooter().add(botaoCancelar, botaoSalvar);
        modal.open();
    }

}
