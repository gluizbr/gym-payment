package com.gym.fe;

import javax.swing.*;

public class SearchTable extends JTable {
    JTable tabela = null;

    public void preencheTabela(String[][] dados, String[] colunas){
        this.tabela = new JTable(dados,colunas);
    }

}
