package org.cesarschool.telas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import br.com.cesarschool.poo.titulos.mediators.MediadorOperacao;

public class TelaOperacao extends JFrame {
    private JTextField campoEhAcao;
    private JTextField campoEntidadeCredito;
    private JTextField campoEntidadeDebito;
    private JTextField campoAcaoOuTitulo;
    private JTextField campoValor;
    private JLabel resultadoLabel;
    private JTextArea extratoArea;

    public TelaOperacao() {
        setTitle("Realizar Operacao");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel labelEhAcao = new JLabel(" Eh Acao? true ou false:");
        labelEhAcao.setBounds(20, 20, 150, 20);
        add(labelEhAcao);

        campoEhAcao = new JTextField();
        campoEhAcao.setBounds(180, 20, 150, 20);
        add(campoEhAcao);

        JLabel labelEntidadeCredito = new JLabel("ID Entidade Credito:");
        labelEntidadeCredito.setBounds(20, 60, 150, 20);
        add(labelEntidadeCredito);

        campoEntidadeCredito = new JTextField();
        campoEntidadeCredito.setBounds(180, 60, 150, 20);
        add(campoEntidadeCredito);

        JLabel labelEntidadeDebito = new JLabel("ID Entidade Debito:");
        labelEntidadeDebito.setBounds(20, 100, 150, 20);
        add(labelEntidadeDebito);

        campoEntidadeDebito = new JTextField();
        campoEntidadeDebito.setBounds(180, 100, 150, 20);
        add(campoEntidadeDebito);

        JLabel labelAcaoOuTitulo = new JLabel("ID Acao e Titulo:");
        labelAcaoOuTitulo.setBounds(20, 140, 150, 20);
        add(labelAcaoOuTitulo);

        campoAcaoOuTitulo = new JTextField();
        campoAcaoOuTitulo.setBounds(180, 140, 150, 20);
        add(campoAcaoOuTitulo);

        JLabel labelValor = new JLabel("Valor:");
        labelValor.setBounds(20, 180, 150, 20);
        add(labelValor);

        campoValor = new JTextField();
        campoValor.setBounds(180, 180, 150, 20);
        add(campoValor);

        JButton botaoOperacao = new JButton("Realizar Operacao");
        botaoOperacao.setBounds(100, 220, 180, 30);
        add(botaoOperacao);

        resultadoLabel = new JLabel("");
        resultadoLabel.setBounds(20, 260, 350, 20);
        add(resultadoLabel);

        botaoOperacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarOperacao();
            }
        });

        // Adicionar botão GERAR EXTRATO
        JButton botaoExtrato = new JButton("GERAR EXTRATO");
        botaoExtrato.setBounds(100, 300, 180, 30);
        add(botaoExtrato);

        // Adicionar área de texto para exibir o extrato
        extratoArea = new JTextArea();
        extratoArea.setBounds(20, 340, 450, 100);
        extratoArea.setEditable(false);
        add(extratoArea);

        botaoExtrato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarExtrato();
            }
        });
    }

    private void realizarOperacao() {
        try {
            boolean ehAcao = Boolean.parseBoolean(campoEhAcao.getText());
            int entidadeCredito = Integer.parseInt(campoEntidadeCredito.getText());
            int entidadeDebito = Integer.parseInt(campoEntidadeDebito.getText());
            int idAcaoOuTitulo = Integer.parseInt(campoAcaoOuTitulo.getText());
            double valor = Double.parseDouble(campoValor.getText());

            MediadorOperacao mediador = MediadorOperacao.getInstancia();
            String resultado = mediador.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor);
            resultadoLabel.setText(resultado);
        } catch (NumberFormatException ex) {
            resultadoLabel.setText("Erro: Entrada invalida.");
        }
    }

    // Método para gerar e exibir o extrato
    private void gerarExtrato() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Transacao.txt"))) {
            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            extratoArea.setText(conteudo.toString());
        } catch (IOException e) {
            extratoArea.setText("Erro ao ler o arquivo de transação.");
        }
    }

    public static void main(String[] args) {
        TelaOperacao tela = new TelaOperacao();
        tela.setVisible(true);
    }
}
