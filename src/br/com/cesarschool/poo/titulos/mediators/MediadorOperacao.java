package br.com.cesarschool.poo.titulos.mediators;
import java.time.LocalDateTime;
import java.util.Arrays;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

public class MediadorOperacao {
    private final MediadorAcao mediatorAcao = MediadorAcao.getInstancia();
    private final MediadorTituloDivida mediatorTituloDivida = MediadorTituloDivida.getInstancia();
    private final MediadorEntidadadeOperadora mediatorEntidadeOperadora = MediadorEntidadadeOperadora.getInstancia();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    private static MediadorOperacao instancia;


    private MediadorOperacao() {}


    public static MediadorOperacao getInstancia() {
        if (instancia == null) {
            instancia = new MediadorOperacao();
        }
        return instancia;
    }


    public String realizarOperacao(boolean ehAcao, int entidadeCredito, int idEntidadeDebito, int idAcaoOuTitulo, double valor) {
        if (valor <= 0) {
            return "Valor inválido";
        }
        EntidadeOperadora credito = mediatorEntidadeOperadora.buscar(entidadeCredito);
        if (credito == null) {
            return "Entidade credito inexistente";
        }
        EntidadeOperadora debito = mediatorEntidadeOperadora.buscar(idEntidadeDebito);
        if (debito == null) {
            return "Entidade debito inexistente";
        }
        if (ehAcao && credito.getAutorizadoAcao()==false) {
            return "Entidade de credito nao autorizada para acao";
        }
        if (ehAcao && debito.getAutorizadoAcao()==false) {
            return "Entidade de débito não autorizada para ação";
        }
        Acao acao = null;
        TituloDivida titulo = null;
        if (ehAcao == true) {
            acao = mediatorAcao.buscar(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação inexistente";
            }
        } else {
            titulo = mediatorTituloDivida.buscar(idAcaoOuTitulo);
            if (titulo == null) {
                return "Título inexistente";
            }
        }
        if (ehAcao) {
            if (debito.getSaldoAcao() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
        } else {
            if (debito.getSaldoTituloDivida() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
        }
        if (ehAcao && acao.getValorUnitario() > valor) {
            return "Valor da operação é menor do que o valor unitário da ação";
        }
        double valorOperacao = ehAcao ? valor : titulo.calcularPrecoTransacao(valor);
        if (ehAcao) {
            credito.creditarSaldoAcao(valorOperacao);
        } else {
            credito.creditarSaldoTituloDivida(valorOperacao);
        }
        if (ehAcao) {
            debito.debitarSaldoAcao(valorOperacao);
        } else {
            debito.debitarSaldoTituloDivida(valorOperacao);
        }
        String resultadoCredito = mediatorEntidadeOperadora.alterar(credito);
        if (resultadoCredito != null) {
            return resultadoCredito;
        }
        String resultadoDebito = mediatorEntidadeOperadora.alterar(debito);
        if (resultadoDebito != null) {
            return resultadoDebito;
        }
        Transacao transacao = new Transacao(
                credito, debito, ehAcao ? acao : null, ehAcao ? null : titulo,
                valorOperacao, LocalDateTime.now()
        );
        repositorioTransacao.incluir(transacao);
        return "Operação realizada com sucesso";
    }
    public Transacao[] gerarExtrato(int entidade) {
        Transacao[] transacoesCredoras = repositorioTransacao.buscarPorEntidadeCredora(entidade);
        Transacao[] transacoesDevedoras = repositorioTransacao.buscarPorEntidadeDevedora(entidade);
        Transacao[] todasTransacoes = new Transacao[transacoesCredoras.length + transacoesDevedoras.length];
        System.arraycopy(transacoesCredoras, 0, todasTransacoes, 0, transacoesCredoras.length);
        System.arraycopy(transacoesDevedoras, 0, todasTransacoes, transacoesCredoras.length, transacoesDevedoras.length);
        Arrays.sort(todasTransacoes, (t1, t2) -> t2.getDataHoraOperacao().compareTo(t1.getDataHoraOperacao()));
        return todasTransacoes;
    }
}
