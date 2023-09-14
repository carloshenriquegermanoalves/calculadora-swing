package br.com.carlos.calc.visao;

import javax.swing.*;
import java.awt.*;

public class Teclado extends JPanel {

    private final Color CINZA_ESCURO = new Color(68,69,68);
    private final Color CINZA_CLARO = new Color(99,99,99);
    private final Color LARANJA = new Color(242,163,60);

    public Teclado() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);
        constraints.weightx = 1; //Faz ocupar o eixo x inteiro
        constraints.weighty = 1; //Faz ocupar o eixo y inteiro
        constraints.fill = GridBagConstraints.BOTH; //Preenche o botão em ambos os lados

        //Primeira linha do teclado
        adicionarBotao("AC", CINZA_ESCURO, constraints, 0, 0);
        adicionarBotao("+/-", CINZA_ESCURO, constraints, 1, 0);
        adicionarBotao("%", CINZA_ESCURO, constraints, 2, 0);
        adicionarBotao("/", LARANJA, constraints, 3, 0);

        //Segunda linha do teclado
        adicionarBotao("7", CINZA_CLARO, constraints, 0, 1);
        adicionarBotao("8", CINZA_CLARO, constraints, 1, 1);
        adicionarBotao("9", CINZA_CLARO, constraints, 2, 1);
        adicionarBotao("*", LARANJA, constraints, 3, 1);

        //Terceira linha do teclado
        adicionarBotao("4", CINZA_CLARO, constraints, 0, 2);
        adicionarBotao("5", CINZA_CLARO, constraints, 1, 2);
        adicionarBotao("6", CINZA_CLARO, constraints, 2, 2);
        adicionarBotao("-", LARANJA, constraints, 3, 2);

        //Quarta linha do teclado
        adicionarBotao("1", CINZA_CLARO, constraints, 0, 3);
        adicionarBotao("2", CINZA_CLARO, constraints, 1, 3);
        adicionarBotao("3", CINZA_CLARO, constraints, 2, 3);
        adicionarBotao("+", LARANJA, constraints, 3, 3);

        //Quinta linha do teclado
        constraints.gridwidth = 2;
        adicionarBotao("0", CINZA_CLARO, constraints, 0, 4);
        constraints.gridwidth = 1;
        adicionarBotao("0", CINZA_CLARO, constraints, 1, 4);
        adicionarBotao(",", CINZA_CLARO, constraints, 2, 4);
        adicionarBotao("=", LARANJA, constraints, 3, 4);

    }

    private void adicionarBotao(String texto, Color cor, GridBagConstraints constraint, int x, int y) {
        constraint.gridx = x;
        constraint.gridy = y;
        Botao botao = new Botao(texto, cor);
        add(botao, constraint);
    }

}
