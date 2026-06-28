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

        SideNavItem itemClientes = new SideNavItem("Cadastrar Clientes", CadastroClientesView.class);
        itemClientes.setPrefixComponent(VaadinIcon.USER.create());
        
        SideNavItem itemJogos = new SideNavItem("Cadastrar Jogos", CadastroJogosView.class);
        itemJogos.setPrefixComponent(VaadinIcon.GAMEPAD.create());

        SideNavItem itemContratos = new SideNavItem("Cadastrar Contratos", CadastroContratosView.class);
        itemContratos.setPrefixComponent(VaadinIcon.BUILDING.create());

        nav.addItem(itemHome);
        nav.addItem(itemClientes);
        nav.addItem(itemJogos);
        nav.addItem(itemContratos);

        addToDrawer(nav);
    }
}