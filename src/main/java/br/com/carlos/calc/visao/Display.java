package br.com.carlos.calc.visao;

import br.com.carlos.calc.modelo.Memoria;
import br.com.carlos.calc.modelo.MemoriaObservador;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class Display extends JPanel implements MemoriaObservador {

    private final JLabel label;
    public Display() {
        Memoria.getMemoriaInstanciada().adicionarObservador(this);
        setBackground(new Color(46,49,50));
        label = new JLabel(Memoria.getMemoriaInstanciada().getValorAtual());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("courier", Font.PLAIN, 30));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
        add(label);
    }

    @Override
    public void valorAlterado(String novoValor) {
        label.setText(novoValor);
    }
}
