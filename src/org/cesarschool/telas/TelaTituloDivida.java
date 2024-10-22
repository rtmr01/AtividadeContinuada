package org.cesarschool.telas;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import br.com.cesarschool.poo.titulos.mediators.MediadorTituloDivida;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class TelaTituloDivida extends JFrame {

    private JTextField txtIdentificador, txtNome, txtDataValidade, txtTaxaJuros;
    private MediadorTituloDivida mediador;

    public TelaTituloDivida() {
        mediador = MediadorTituloDivida.getInstancia();

        setTitle("Titulo de Divida ");
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

        JLabel lblDataValidade = new JLabel("Data de Validade (yyyy-mm-dd):");
        lblDataValidade.setBounds(10, 70, 250, 25);
        add(lblDataValidade);

        txtDataValidade = new JTextField();
        txtDataValidade.setBounds(10, 100, 200, 25);
        add(txtDataValidade);

        JLabel lblTaxaJuros = new JLabel("Taxa de Juros:");
        lblTaxaJuros.setBounds(10, 130, 120, 25);
        add(lblTaxaJuros);

        txtTaxaJuros = new JTextField();
        txtTaxaJuros.setBounds(140, 130, 200, 25);
        add(txtTaxaJuros);

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
                incluirTitulo();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarTitulo();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirTitulo();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTitulo();
            }
        });
    }

    private void incluirTitulo() {
        int identificador = Integer.parseInt(txtIdentificador.getText());
        String nome = txtNome.getText();
        LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
        double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

        TituloDivida titulo = new TituloDivida(identificador, nome, dataValidade, taxaJuros);
        String resultado = mediador.incluir(titulo);

        JOptionPane.showMessageDialog(this, resultado == null ? "Título incluído com sucesso!" : resultado);
    }

    private void alterarTitulo() {
        int identificador = Integer.parseInt(txtIdentificador.getText());
        String nome = txtNome.getText();
        LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
        double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

        TituloDivida titulo = new TituloDivida(identificador, nome, dataValidade, taxaJuros);
        String resultado = mediador.alterar(titulo);

        JOptionPane.showMessageDialog(this, resultado == null ? "Título alterado com sucesso!" : resultado);
    }

    private void excluirTitulo() {
        int identificador = Integer.parseInt(txtIdentificador.getText());
        String resultado = mediador.excluir(identificador);

        JOptionPane.showMessageDialog(this, resultado == null ? "Título excluído com sucesso!" : resultado);
    }

    private void buscarTitulo() {
        int identificador = Integer.parseInt(txtIdentificador.getText());
        TituloDivida titulo = mediador.buscar(identificador);

        if (titulo != null) {
            txtNome.setText(titulo.getNome());
            txtDataValidade.setText(titulo.getDataDeValidade().toString());
            txtTaxaJuros.setText(String.valueOf(titulo.getTaxaJuros()));
            JOptionPane.showMessageDialog(this, "Título encontrado!");
        } else {
            JOptionPane.showMessageDialog(this, "Título não encontrado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TelaTituloDivida tela = new TelaTituloDivida();
                tela.setVisible(true);
            }
        });
    }
}

