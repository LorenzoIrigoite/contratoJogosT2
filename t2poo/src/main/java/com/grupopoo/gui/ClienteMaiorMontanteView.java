package com.grupopoo.gui;

import com.grupopoo.dados.Cliente;
import com.grupopoo.dados.ClienteRepository;
import com.grupopoo.dados.Contrato;
import com.grupopoo.dados.ContratoRepository;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Cliente com Maior Montante")
@Route(value = "clienteMaiorMontante", layout = MainLayout.class)
public class ClienteMaiorMontanteView extends VerticalLayout {

    H2 tituloPagina = new H2("Cliente(s) com Maior Montante em Contratos");
    private final Grid<Cliente> gridClientes;
    private ContratoRepository contratosRepository;

    @Autowired
    public ClienteMaiorMontanteView(ClienteRepository clienteRepository, ContratoRepository contratosRepository) {
        this.contratosRepository = contratosRepository;

        gridClientes = new Grid<>(Cliente.class, false);
        gridClientes.addColumn(Cliente::getNumero).setHeader("Número/ID");
        gridClientes.addColumn(Cliente::getNome).setHeader("Nome");
        gridClientes.addColumn(Cliente::getEmail).setHeader("E-mail");
        gridClientes.addColumn(cliente -> String.format("R$ %.2f", calcularMontanteTotalCliente(cliente))).setHeader("Montante Acumulado");
        gridClientes.getColumns().forEach(col -> col.setAutoWidth(true));

        if (clienteRepository.getArrayList() == null || clienteRepository.getArrayList().isEmpty()) {
            Notification notificacao = Notification.show("Erro: Não há clientes cadastrados no sistema.");
            notificacao.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (contratosRepository.getTamanho() == 0) {
            Notification notificacao = Notification.show("Aviso: Nenhum contrato foi gerado no sistema ainda, montantes zerados.");
            notificacao.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {

            double maiorMontante = clienteRepository.getArrayList().stream()
                    .mapToDouble(this::calcularMontanteTotalCliente)
                    .max()
                    .orElse(0.0);

            List<Cliente> clientesFiltrados = clienteRepository.getArrayList().stream()
                    .filter(cliente -> calcularMontanteTotalCliente(cliente) == maiorMontante && maiorMontante > 0)
                    .collect(Collectors.toList());

            gridClientes.setItems(clientesFiltrados);
            add(tituloPagina, gridClientes, new Hr());
        }
    }

    private double calcularMontanteTotalCliente(Cliente cliente) {
        return contratosRepository.listarContratos().stream()
                .filter(c -> c.getCliente() != null && c.getCliente().getNumero() == cliente.getNumero())
                .mapToDouble(c -> c.getJogo() != null ? c.getPeriodo() * c.getJogo().getValorDiario() : 0.0)
                .sum();
    }
}