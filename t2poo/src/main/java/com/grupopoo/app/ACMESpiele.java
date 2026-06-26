package com.grupopoo.app;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;

import java.util.Scanner;
import java.util.ArrayList;

import com.grupopoo.dados.*;

@Service
public class ACMESpiele {
    // Atributos para redirecionamento de E/S
    
    private Scanner entrada = new Scanner(System.in);  // Atributo para entrada padrao (teclado)
    private PrintStream saidaPadrao = System.out;   // Guarda a saida padrao - tela (console)
    private final String nomeArquivoEntrada = "datain.txt";  // Nome do arquivo de entrada de dados
    private final String nomeArquivoSaida = "dataout.txt";  // Nome do arquivo de saida de dados
    
    //atributos do programa propriamente dito
    private ClienteRepository clientela;
    private JogoRepository jogos; //mudar para colecao
    private ContratoRepository contratos; //mudar para acervo
    //Scanner entrada = new Scanner(System.in);

    // Construtor da classe de aplicacao
    public ACMESpiele() {
        redirecionaEntrada();    // Redireciona Entrada para arquivos
        redirecionaSaida();    // Redireciona Saida para arquivos

        clientela = new ClienteRepository();
        jogos = new JogoRepository();
        contratos = new ContratoRepository();

    }

    public void executar(){
        //System.out.println("========================================"); //40 caracteres
        //System.out.println("=====| ACMEWebbasierteVideospiele |=====");
        //System.out.println("\nCADASTRO DE CLIENTES INDIVIDUAIS");

        //cadastro de clientes
        int num = 0;
        while(num >= 0){
            //System.out.print("Digite o número do cliente ou -1 para encerrar o cadastro: ");
            num = entrada.nextInt();
            if (num < 1)
                break;
            else
                cadastrarClienteIndividual(num);
        }
        
        //System.out.println("\nCADASTRO DE CLIENTES CORPORATIVOS");
        num = 0;
        while(num >= 0){
            //System.out.print("Digite o número do cliente ou -1 para encerrar o cadastro: ");
            num = entrada.nextInt();
            if (num < 1)
                break;
            else
                cadastrarClienteCorporativo(num);
        }

        //System.out.println("\nCADASTRO DE JOGOS");
        num = 0;
        while(num >= 0){
            //System.out.print("Digite o código do jogo ou -1 para encerrar o cadastro: ");
            num = entrada.nextInt();
            if (num < 1)
                break;
            else
                cadastrarJogo(num);
        }

        //System.out.println("\nCADASTRO DE CONTRATOS");
        num = 0;
        while(num >= 0){
            //System.out.print("Digite o id do contrato ou -1 para encerrar o cadastro: ");
            num = entrada.nextInt();
            if (num < 1)
                break;
            else
                cadastrarContrato(num);
        }
        
        //System.out.println("\n|--------------------------------|");
        //System.out.println("CONSULTA DE JOGOS POR CODIGO");
        //System.out.print("Digite o codigo do jogo: ");
        num = entrada.nextInt();
        consultarJogoCodigo(num);

        //System.out.println("\nCONSULTA DE JOGOS POR CATEGORIA");
        num = 0;
        consultarJogosCategoria();

        //System.out.println("ALTERAR NOME DO CLIENTE");
        //System.out.print("Digite o numero do cliente: ");
        num = entrada.nextInt();
        mudarNomeCliente(num);

        //System.out.println("REMOVER CONTRATOS DO JOGO");
        //System.out.print("Digite o codigo do jogo: ");
        num = entrada.nextInt();
        removerContratoJogo(num);

        //System.out.println("LISTAR TODOS OS CONTRATOS");
        listarTodosContratos();
        
        //System.out.println("CLIENTE COM MAIOR VALOR CONTRATADO");
        maiorCliente();
        
    }

    public void cadastrarClienteIndividual(int numero){
        entrada.nextLine();
        String nome = entrada.nextLine();
        //System.out.print("E-MAIL: ");
        String email = entrada.nextLine();
        //System.out.print("CPF: ");
        String cpf = entrada.nextLine();
        if (clientela.encontrarClienteNumero(numero) == null){
            //System.out.println("|-----| DADOS DO CLIENTE |-----|");
            //System.out.print("NOME: ");
            Individual cliente = new Individual(numero, nome, email, cpf);
            clientela.adicionarCliente(cliente);
            System.out.println("1:" + cliente.descrever());
        } else {
            System.out.println("1:erro-numero repetido.");
        }
    }

    public void cadastrarClienteCorporativo(int numero){
        entrada.nextLine();
        String nome = entrada.nextLine();
        //System.out.print("E-MAIL: ");
        String email = entrada.nextLine();
        //System.out.print("CNPJ: ");
        String cnpj = entrada.nextLine();
        //System.out.print("NOME FANTASIA: ");
        String nomeFantasia = entrada.nextLine();
        if (clientela.encontrarClienteNumero(numero) == null){
            //System.out.println("|-----| DADOS DO CLIENTE |-----|");
            //System.out.print("NOME: ");
            Corporativo cliente = new Corporativo(numero, nome, email, cnpj, nomeFantasia);
            clientela.adicionarCliente(cliente);
            System.out.println("2:" + cliente.descrever());
        } else {
            System.out.println("2:erro-numero repetido.");
        }
    }

    public void cadastrarJogo(int codigo){
        //System.out.print("\nDigite o nome do jogo: ");
        entrada.nextLine();
        String nome = entrada.nextLine();
        //System.out.print("Digite o ano do jogo: ");
        int ano = entrada.nextInt();
        //System.out.print("Digite o valor por minuto do jogo: ");
        double valorMinuto = entrada.nextDouble();
        //System.out.print("Digite a categoria do jogo: ");
        entrada.nextLine();
        String cat = entrada.nextLine();
        if(jogos.encontrarJogoCodigo(codigo) == null){
            if (!cat.equals("AVENTURA") && !cat.equals("ESTRATEGIA") && !cat.equals("CORRIDA")){
                System.out.println("3:erro-categoria inexistente.");
                return;
            }
            Categoria categoria;
            if (cat.equals("AVENTURA"))
                categoria = Categoria.AVENTURA;
            else if (cat.equals("ESTRATEGIA"))
                categoria = Categoria.ESTRATEGIA;
            else
                categoria = Categoria.CORRIDA;

            Jogo jogo = new Jogo(codigo, nome, ano, valorMinuto, categoria);
            jogos.adicionarJogo(jogo);
            System.out.println("3:" + jogo.descrever());
        } else
            System.out.println("3:erro-codigo repetido.");
    }

    public void cadastrarContrato(int id){
        //System.out.print("Digite o período do contrato: ");
        int periodo = entrada.nextInt();
        //System.out.print("Digite o numero do cliente: ");
        int numero = entrada.nextInt();
        Cliente cliente = clientela.encontrarClienteNumero(numero);
        //System.out.print("Digite o codigo do jogo: ");
        int codigo = entrada.nextInt();
        Jogo jogo = jogos.encontrarJogoCodigo(codigo);
        if (contratos.encontrarContratoId(id) == null){
            if (cliente == null){
                System.out.println("4:erro-cliente inexistente.");
                return;
            }
            if (jogo == null){
                System.out.println("4:erro-jogo inexistente.");
                return;
            }
            Contrato contrato = new Contrato(id, periodo, cliente, jogo);
            contratos.adicionarContrato(contrato);
            System.out.println("4:" + contrato.descrever());
        } else
            System.out.println("4:erro-id repetido.");
    }

    public void consultarJogoCodigo(int codigo){
        Jogo jogo = jogos.encontrarJogoCodigo(codigo);
        if (jogo != null){
            System.out.println("5:"+jogo.getCodigo()+";"+jogo.getNome()+";"+jogo.getCategoria());
        } else
            System.out.println("5:erro-codigo inexistente.");
    }

    public void consultarJogosCategoria(){
        //System.out.print("Digite a categoria desejada: ");
        entrada.nextLine();
        String nomeCategoria = entrada.nextLine();
        Categoria cat;

        if (!nomeCategoria.equals("AVENTURA") && !nomeCategoria.equals("ESTRATEGIA") && !nomeCategoria.equals("CORRIDA")){
            System.out.println("6:erro-categoria inexistente.");
            return;
        } else {
            if (nomeCategoria.equals("AVENTURA"))
                cat = Categoria.AVENTURA;
            else if (nomeCategoria.equals("ESTRATEGIA"))
                cat = Categoria.ESTRATEGIA;
            else
                cat = Categoria.CORRIDA;
        }
        
        ArrayList<Jogo> jogosCategoria = jogos.encontrarJogoCategoria(cat);
        if (jogosCategoria.size() == 0)
            System.out.println("6:erro-nenhum jogo encontrado.");
        else {
            for (Jogo j : jogosCategoria){
                System.out.println("6:"+j.getCategoria().getNome()+";"+j.getCodigo()+";"+j.getNome());
            }
        }
    }

    public void mudarNomeCliente(int numero){
        Cliente cliente = clientela.encontrarClienteNumero(numero);
        //System.out.print("Digite o novo nome: ");
        entrada.nextLine();
        String nome = entrada.nextLine();
        if(cliente != null){
            cliente.setNome(nome);
            String saida = "7:" + cliente.getNumero() + ";" + cliente.getNome() + ";" + cliente.getEmail() + ";";
            if (cliente instanceof Individual){
                Individual ind = (Individual) cliente;
                saida = saida + ind.getCpf();
            } else {
                Corporativo corp = (Corporativo) cliente;
                saida = saida + corp.getCnpj();
            }
            System.out.println(saida);
        } else
            System.out.println("7:erro-numero inexistente.");
    }

    public void removerContratoJogo(int codigo){
        Jogo j = jogos.encontrarJogoCodigo(codigo);
        if (j != null){
            ArrayList<Contrato> contratosRemovidos = contratos.removerContratosPorJogo(j);
            if (contratosRemovidos.size() > 0){
                for (Contrato contrato : contratosRemovidos){
                    System.out.println("8:contrato removido: " + contrato.getId());
                }
            } else {
                System.out.println("8:nenhum contrato encontrado.");
            }
        } else
            System.out.println("8:erro-codigo inexistente.");
    }
    
    public void listarTodosContratos(){
        ArrayList<Contrato> aux = contratos.listarContratos();
        if (aux.size() > 0){
            for(Contrato c : aux){
                System.out.println("9:" + c.getId() + ";" + c.getPeriodo() + ";" + c.getCliente().getNumero() + ";" + c.getJogo().getCodigo());
            }
        } else
            System.out.println("9:erro-nenhum contrato cadastrado.");
    }

    public void maiorCliente(){
        if (contratos.getTamanho() > 0){
            Cliente maiorCliente = contratos.encontraMaiorCliente();
            System.out.println("10:" + maiorCliente.getNumero() + ";" + maiorCliente.getNome() + ";" + maiorCliente.getEmail() + 
                                ";" + contratos.valorMinutoTotalCliente(maiorCliente));
        } else
            System.out.println("10:erro-nenhum contrato encontrado.");
        
    }



//---------------------------MÉTODOS PARA LER DADOS DE ARQUIVO--------------------------------------------
    // Redireciona Entrada de dados para arquivos em vez de teclado
    // Chame este metodo para redirecionar a leitura de dados para arquivos
    
    private void redirecionaEntrada() {
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader(nomeArquivoEntrada));
            entrada = new Scanner(streamEntrada);   // Usa como entrada um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
        entrada.useLocale(Locale.ENGLISH);   // Ajusta para leitura para ponto decimal
    }

    // Redireciona Saida de dados para arquivos em vez da tela (terminal)
    // Chame este metodo para redirecionar a escrita de dados para arquivos
    private void redirecionaSaida() {
        try {
            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), Charset.forName("UTF-8"));
            System.setOut(streamSaida);             // Usa como saida um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
    }

    // Restaura Entrada padrao para o teclado
    // Chame este metodo para retornar a leitura de dados para o teclado
    private void restauraEntrada() {
        entrada = new Scanner(System.in);
    }

    // Restaura Saida padrao para a tela (terminal)
    // Chame este metodo para retornar a escrita de dados para a tela
    private void restauraSaida() {
        System.setOut(saidaPadrao);
    }
//---------------------------------------------------------------------------------------

}
