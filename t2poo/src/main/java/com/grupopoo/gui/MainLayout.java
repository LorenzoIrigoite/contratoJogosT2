package com.grupopoo.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class MainLayout extends AppLayout {

    public MainLayout() {
        criarCabecalho();
        criarMenuLateral();
    }

    private void criarCabecalho() {
        H1 logo = new H1("ACMESpiele");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)");
        logo.getStyle().set("margin", "0 var(--lumo-space-m)");
        logo.getStyle().set("cursor", "pointer");
        logo.addClickListener(e -> UI.getCurrent().navigate(""));

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        
        addToNavbar(header);
    }

    private void criarMenuLateral() {
        SideNav nav = new SideNav();

        SideNavItem itemHome = new SideNavItem("Home", MainView.class);
        itemHome.setPrefixComponent(VaadinIcon.HOME.create());

        /* grupo ações clientes */
        SideNavItem grupoClientes = new SideNavItem("Clientes");
        grupoClientes.setPrefixComponent(VaadinIcon.USER.create());

        SideNavItem subCadastrarCliente = new SideNavItem("Cadastrar Cliente", CadastroClientesView.class);
        subCadastrarCliente.setPrefixComponent(VaadinIcon.PLUS_CIRCLE.create());

        SideNavItem subListaClientes = new SideNavItem("Lista de Clientes", ListaClientesView.class); 
        subListaClientes.setPrefixComponent(VaadinIcon.LIST.create());

        grupoClientes.addItem(subCadastrarCliente);
        grupoClientes.addItem(subListaClientes);
        
        /* grupo ações jogos */
        SideNavItem grupoJogos = new SideNavItem("Jogos");
        grupoJogos.setPrefixComponent(VaadinIcon.GAMEPAD.create());

        SideNavItem subCadastrarJogo = new SideNavItem("Cadastrar Jogo", CadastroJogosView.class);
        subCadastrarJogo.setPrefixComponent(VaadinIcon.PLUS_CIRCLE.create());

        SideNavItem subListaJogos = new SideNavItem("Lista de Jogos", ListaJogosView.class); 
        subListaJogos.setPrefixComponent(VaadinIcon.LIST.create());

        grupoJogos.addItem(subCadastrarJogo);
        grupoJogos.addItem(subListaJogos);


        SideNavItem itemContratos = new SideNavItem("Cadastrar Contratos", CadastroContratosView.class);
        itemContratos.setPrefixComponent(VaadinIcon.BUILDING.create());


        nav.addItem(itemHome);
        nav.addItem(grupoClientes);
        nav.addItem(grupoJogos);
        nav.addItem(itemContratos);

        addToDrawer(nav);
    }
}