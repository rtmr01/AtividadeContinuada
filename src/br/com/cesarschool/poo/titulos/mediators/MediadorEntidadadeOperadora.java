package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediadorEntidadadeOperadora {

    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    // Singleton
    private static MediadorEntidadadeOperadora instance;

    private MediadorEntidadadeOperadora() {}

    public static MediadorEntidadadeOperadora getInstancia() {
        if (instance == null) {
            instance = new MediadorEntidadadeOperadora();
        }
        return instance;
    }

    // Validação
    private String validar(EntidadeOperadora entidade) {
        if (entidade.getIdentificador() <100 || entidade.getIdentificador() > 1000000) {
            return "Identificador deve ser entre 100 e 1000000.";
        }

        String nome = entidade.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            return "Por favor, preencha o nome.";
        }

        if (nome.length() < 10 || nome.length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }


        //if (entidade.getSaldoAcao() <= 0) {
      //      return "Saldo de operação deve ser maior que 0.";
      //  }

        return null;
    }

    //CRUD

    public String incluir(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao == null) {
            boolean incluido = repositorioEntidadeOperadora.create(entidade);
            if (incluido) {
                return null;
            } else {
                return "Entidade já existente";
            }
        } else {
            return validacao;
        }
    }

    public String alterar(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao == null) {
            boolean alterado = repositorioEntidadeOperadora.update(entidade);
            if (alterado) {
                return null;
            } else {
                return "Entidade inexistente";
            }
        } else {
            return validacao; // Retorna mensagem de validação
        }
    }

    public String excluir(long identificador) {
        if (identificador <= 99 || identificador > 999999) {
            return "Identificador deve estar entre 100 e 1000000.";
        }
        boolean excluido = repositorioEntidadeOperadora.delete(identificador);
        if (excluido) {
            return null;
        } else {
            return "Entidade não existente";
        }
    }

    public EntidadeOperadora buscar(long identificador) {
        if (identificador < 100 || identificador >= 1000000) {
            System.out.println("O identificador não está válido");
            return null;
        }
        return repositorioEntidadeOperadora.read(identificador);
    }
}

