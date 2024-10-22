package org.cesarschool.telas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.com.cesarschool.poo.titulos.mediators.MediadorEntidadadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

public class TelaEntidadeOperadora extends JFrame {

    private JTextField txtIdentificador, txtNome, txtSaldoAcao, txtSaldoTituloDivida, txtAutorizadoAcao;
    private MediadorEntidadadeOperadora mediador;

    public TelaEntidadeOperadora() {
        mediador = MediadorEntidadadeOperadora.getInstancia();

        setTitle("Entidade Operadora ");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblIdentificador = new JLabel("Identificador:");
        lblIdentificador.setBounds(10, 10, 120, 25);
        add(lblIdentificador);

        txtIdentificador = new JTextField();
        txtIdentificador.setBounds(140, 10, 200, 25);
        add(txtIdentificador);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 40, 120, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(140, 40, 200, 25);
        add(txtNome);

        JLabel lblAutorizadoAcao = new JLabel("Autorizado Acao true ou false:");
        lblAutorizadoAcao.setBounds(10, 70, 120, 25);
        add(lblAutorizadoAcao);

        txtAutorizadoAcao = new JTextField();
        txtAutorizadoAcao.setBounds(140, 70, 200, 25);
        add(txtAutorizadoAcao);

        JLabel lblSaldoAcao = new JLabel("Saldo de Acao:");
        lblSaldoAcao.setBounds(10, 100, 120, 25);
        add(lblSaldoAcao);

        txtSaldoAcao = new JTextField();
        txtSaldoAcao.setBounds(140, 100, 200, 25);
        add(txtSaldoAcao);

        JLabel lblSaldoTituloDivida = new JLabel("Saldo Titulo Divida:");
        lblSaldoTituloDivida.setBounds(10, 130, 120, 25);
        add(lblSaldoTituloDivida);

        txtSaldoTituloDivida = new JTextField();
        txtSaldoTituloDivida.setBounds(140, 130, 200, 25);
        add(txtSaldoTituloDivida);

        JButton btnIncluir = new JButton("Incluir");
        btnIncluir.setBounds(10, 170, 100, 30);
        add(btnIncluir);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setBounds(120, 170, 100, 30);
        add(btnAlterar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(230, 170, 100, 30);
        add(btnExcluir);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(340, 170, 100, 30);
        add(btnBuscar);

        // Event Listeners
        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incluirEntidade();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarEntidade();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirEntidade();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEntidade();
            }
        });
    }

    private void incluirEntidade() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());
            double saldoAcao = Double.parseDouble(txtSaldoAcao.getText());
            double saldoTituloDivida = Double.parseDouble(txtSaldoTituloDivida.getText());

            EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida);
            String resultado = mediador.incluir(entidade);

            JOptionPane.showMessageDialog(this, resultado == null ? "Entidade incluída com sucesso!" : resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro de formatação nos dados. Verifique os valores informados.");
        }
    }

    private void alterarEntidade() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());
            double saldoAcao = Double.parseDouble(txtSaldoAcao.getText());
            double saldoTituloDivida = Double.parseDouble(txtSaldoTituloDivida.getText());

            // Buscar a entidade existente
            EntidadeOperadora entidadeExistente = mediador.buscar(identificador);

            if (entidadeExistente != null) {
                // Atualizar os dados da entidade existente
                entidadeExistente.setNome(nome);
                entidadeExistente.setAutorizadoAcao(autorizadoAcao);

                // Atualizando saldo de ação e saldo de títulos de dívida
                // Note que, geralmente, o saldo não é alterado diretamente, mas
                // se for o caso no seu cenário, o código a seguir está correto
                entidadeExistente.creditarSaldoAcao(saldoAcao - entidadeExistente.getSaldoAcao());
                entidadeExistente.creditarSaldoTituloDivida(saldoTituloDivida - entidadeExistente.getSaldoTituloDivida());

                // Alterar a entidade no mediador
                String resultado = mediador.alterar(entidadeExistente);

                JOptionPane.showMessageDialog(this, resultado == null ? "Entidade alterada com sucesso!" : resultado);
            } else {
                JOptionPane.showMessageDialog(this, "Entidade não encontrada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro de formatação nos dados. Verifique os valores informados.");
        }
    }


    private void excluirEntidade() {
        long identificador = Long.parseLong(txtIdentificador.getText());
        String resultado = mediador.excluir(identificador);

        JOptionPane.showMessageDialog(this, resultado == null ? "Entidade excluída com sucesso!" : resultado);
    }

    private void buscarEntidade() {
        long identificador = Long.parseLong(txtIdentificador.getText());
        EntidadeOperadora entidade = mediador.buscar(identificador);

        if (entidade != null) {
            txtNome.setText(entidade.getNome());
            txtAutorizadoAcao.setText(String.valueOf(entidade.getAutorizadoAcao()));
            txtSaldoAcao.setText(String.valueOf(entidade.getSaldoAcao()));
            txtSaldoTituloDivida.setText(String.valueOf(entidade.getSaldoTituloDivida()));
            JOptionPane.showMessageDialog(this, "Entidade encontrada!");
        } else {
            JOptionPane.showMessageDialog(this, "Entidade não encontrada.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TelaEntidadeOperadora tela = new TelaEntidadeOperadora();
                tela.setVisible(true);
            }
        });
    }
}
