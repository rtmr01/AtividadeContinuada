package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;


public class MediatorTituloDivida {
    private static MediatorTituloDivida mediatorTituloDivida = new MediatorTituloDivida();
    private RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();

    private MediatorTituloDivida() { }

    public static MediatorTituloDivida getInstancia() {
        return mediatorTituloDivida;
    }

    private String validar(TituloDivida titulo) {
        if(titulo.getIdentificador() < 1 || titulo.getIdentificador() > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if(titulo.getNome() == null || titulo.getNome().strip().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if(titulo.getNome().length() < 10 || titulo.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        LocalDate dataAtual = LocalDate.now();
        if(titulo.getDataDeValidade().isBefore(dataAtual.plusDays(180))) {
            return "Data de validade deve ter pelo menos 180 dias na frente da data atual.";
        }
        if(titulo.getTaxaJuros()<0) {
            return "Taxa de juros deve ser maior ou igual a zero.";
        }
        return null;
    }

    public String incluir(TituloDivida titulo) {
        String validar = validar(titulo);
        if (validar == null) {
            boolean inclue = repositorioTituloDivida.incluir(titulo);
            if (inclue == true) {
                return null;
            } else {
                return "Título já existente.";
            }
        }
        return validar;
    }

    public String alterar(TituloDivida titulo) {
        String validar = validar(titulo);
        if (validar == null) {
            boolean altera= repositorioTituloDivida.alterar(titulo);
            if (altera == true ) {
                return null;
            }
            else {
                return "Título inexistente.";
            }
        }
        return validar;
    }
    public String excluir(int identificador) {
        if (identificador < 1 || identificador > 99999) {
            return "Identificador inválido.";
        }
        boolean excluir = repositorioTituloDivida.excluir(identificador);
        if (excluir == true) {
            return null;
        }
        else{
            return "Título inexistente.";
        }
    }
    public TituloDivida buscar(int identificador) {
        if (identificador < 1 || identificador > 99999) {
            return null;
        }
        return repositorioTituloDivida.buscar(identificador);
    }

}