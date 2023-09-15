package br.com.carlos.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private static final Memoria MEMORIA_INSTANCIADA = new Memoria();
    private final List<MemoriaObservador> observadores = new ArrayList<>();
    private TipoDeComando operacaoMaisRecente = null;
    private boolean substituirValorNoDisplay = false;
    private String valorAtual = "";
    private String valorBuffer = "";

    private Memoria() {}

    public static Memoria getMemoriaInstanciada() {
        return MEMORIA_INSTANCIADA;
    }

    public void adicionarObservador(MemoriaObservador observador) {
        observadores.add(observador);
    }

    public String getValorAtual() {
        return valorAtual.isEmpty() ? "0" : valorAtual;
    }

    public void processarNovoValor(String novoValor) {
        TipoDeComando tipoDeComando = detectarTipoDeComando(novoValor);

        if (tipoDeComando == null) {
            return;
        } else if (tipoDeComando == TipoDeComando.INVERTER_SINAL && valorAtual.contains("-")) {
            valorAtual.substring(1);
        } else if (tipoDeComando == TipoDeComando.INVERTER_SINAL && !valorAtual.contains("-")) {
            valorAtual = "-" + valorAtual;
        } else if (tipoDeComando == TipoDeComando.ZERAR) {
            valorAtual = "";
            valorBuffer = "";
            substituirValorNoDisplay = false;
            operacaoMaisRecente = null;
        } else if (tipoDeComando == TipoDeComando.NUMERO || tipoDeComando == TipoDeComando.VIRGULA) {
            valorAtual = substituirValorNoDisplay ? novoValor : valorAtual + novoValor;
            substituirValorNoDisplay = false;
        } else {
            substituirValorNoDisplay = true;
            valorAtual = getResultadoDaOperacao();
            valorBuffer = valorAtual;
            operacaoMaisRecente = tipoDeComando;
        }

        observadores.forEach(obs -> obs.valorAlterado(getValorAtual()));
    }

    private String getResultadoDaOperacao() {
        if (operacaoMaisRecente == null || operacaoMaisRecente == TipoDeComando.IGUAL) {
            return valorAtual;
        }

        double numeroBuffer = Double.parseDouble(valorBuffer.replace(",", "."));
        double numeroAtual = Double.parseDouble(valorAtual.replace(",", "."));
        double resultado = 0;

        if (operacaoMaisRecente == TipoDeComando.SOMA) {
            resultado = numeroBuffer + numeroAtual;
        } else if (operacaoMaisRecente == TipoDeComando.SUBTRACAO) {
            resultado = numeroBuffer - numeroAtual;
        } else if (operacaoMaisRecente == TipoDeComando.MULTIPLICACAO) {
            resultado = numeroBuffer * numeroAtual;
        } else if (operacaoMaisRecente == TipoDeComando.DIVISAO) {
            resultado = numeroBuffer / numeroAtual;
        }

        String textoResultado = Double.toString(resultado).replace(".", ",");
        boolean resultadoEhInteiro = textoResultado.endsWith(",0");
        return resultadoEhInteiro ? textoResultado.replace(",0", "") : textoResultado;
    }

    private TipoDeComando detectarTipoDeComando(String novoValor) {
        if (valorAtual.isEmpty() && "0".equals(novoValor)) {
            return null;
        }

        try {
            Integer.parseInt(novoValor);
            return TipoDeComando.NUMERO;
        } catch (NumberFormatException e) {
            if ("AC".equals(novoValor)) {
                return TipoDeComando.ZERAR;
            } else if ("+".equals(novoValor)) {
                return TipoDeComando.SOMA;
            } else if ("-".equals(novoValor)) {
                return TipoDeComando.SUBTRACAO;
            } else if ("*".equals(novoValor)) {
                return TipoDeComando.MULTIPLICACAO;
            } else if ("/".equals(novoValor)) {
                return TipoDeComando.DIVISAO;
            } else if ("=".equals(novoValor)) {
                return TipoDeComando.IGUAL;
            } else if ("±".equals(novoValor)) {
                return TipoDeComando.INVERTER_SINAL;
            } else if (",".equals(novoValor) && !valorAtual.contains(",")) {
                return TipoDeComando.VIRGULA;
            }
        }

        return null;
    }

}