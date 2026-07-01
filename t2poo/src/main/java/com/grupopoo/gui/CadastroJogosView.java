package com.grupopoo.gui;

import com.grupopoo.dados.Categoria;
import com.grupopoo.dados.Jogo;
import com.grupopoo.dados.JogoRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle ("Cadastro de Jogos")
@Route (value = "cadastroJogos", layout = MainLayout.class)
public class CadastroJogosView extends VerticalLayout {
    private JogoRepository jogos;

    private TextField campoCodigo = new TextField("Código:");
    private TextField campoNome = new TextField("Nome:");
    private TextField campoAno = new TextField("Ano:");
    private NumberField campoValorDiario = new NumberField("Valor Diário (R$):");
    
    private Select<String> selectCategorias = new Select<>();

    private Button botaoSalvar = new Button("Cadastrar Jogo");

    @Autowired
    public CadastroJogosView(JogoRepository jogos){
        this.jogos = jogos;

        H2 tituloPagina = new H2("Cadastro de Jogos");

        campoValorDiario.setPlaceholder("0.00");
        campoValorDiario.setMin(0.0);

        selectCategorias.setLabel("Categorias:");
        selectCategorias.setPlaceholder("Selecione uma opção");
        selectCategorias.setItems("Aventura", "Corrida", "Estratégia");

        botaoSalvar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoSalvar.addClickListener(e -> salvarJogo());

        add(tituloPagina, campoCodigo, campoNome, campoAno, campoValorDiario, selectCategorias, botaoSalvar);
    }

    /*
    public void atualizarSelect(String categoria){

    }
    */

    private void salvarJogo(){

        if (campoCodigo.isEmpty() || campoNome.isEmpty() || campoAno.isEmpty() || campoValorDiario.isEmpty() || selectCategorias.isEmpty()){
            Notification.show("Preencha todos os campos.");

            if(campoCodigo.isEmpty()){
                campoCodigo.setInvalid(true);
                campoCodigo.setErrorMessage("Este campo é obrigatório.");
            }
            if(campoNome.isEmpty()){
                campoNome.setInvalid(true);
                campoNome.setErrorMessage("Este campo é obrigatório.");
            }
            if(campoAno.isEmpty()){
                campoAno.setInvalid(true);
                campoAno.setErrorMessage("Este campo é obrigatório.");
            }
            if(campoValorDiario.isEmpty()){
                campoValorDiario.setInvalid(true);
                campoValorDiario.setErrorMessage("Este campo é obrigatório.");
            }
            if(selectCategorias.isEmpty()){
                selectCategorias.setInvalid(true);
                selectCategorias.setErrorMessage("Este campo é obrigatório.");
            }

            return;
        }

        try {
            int codigo = Integer.parseInt(campoCodigo.getValue());
            String nome = campoNome.getValue();
            int ano = Integer.parseInt(campoAno.getValue());
            double valorDiario = campoValorDiario.getValue();
            String nomeCategoria = selectCategorias.getValue();

            if (this.jogos.encontrarJogoCodigo(codigo) != null) {
                Notification.show("Erro: Já existe um cliente cadastrado com este número.");
                return;
            }

            Categoria categoria;
            if (nomeCategoria.equals("Aventura"))
                categoria = Categoria.AVENTURA;
            else if (nomeCategoria.equals("Corrida"))
                categoria = Categoria.CORRIDA;
            else
                categoria = Categoria.ESTRATEGIA;

            Jogo novoJogo = new Jogo (codigo, nome, ano, valorDiario, categoria);
            this.jogos.adicionarJogo(novoJogo);
            Notification.show("Jogo cadastrado com sucesso!");

        } catch (NumberFormatException e){
            Notification.show("Erro: O campo Código e o campo Ano precisam ser um valor numérico válido.");
        } catch (IllegalArgumentException e) {

        } catch (Exception e){
            Notification.show("Ocorreu um erro inesperado.");
        }

        limparFormulario();
    }

    public void limparFormulario(){
        this.campoCodigo.clear();
        this.campoNome.clear();
        this.campoAno.clear();
        this.campoValorDiario.clear();
        this.selectCategorias.clear();
    }
}
