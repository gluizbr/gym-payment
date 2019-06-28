package com.gym.fe;

import javax.swing.*;

public class SearchFrame extends JScrollPane{

    UIInit UIInitSearchFrame = null;
    JTable tabelaSearchFrame = null;
    JScrollPane painelTabelaSearchFrame = null;

    public SearchFrame (UIInit UIInitParam){
        this.UIInitSearchFrame = UIInitParam;
    }

    public void preparaSearchFrame(JTable tabelaParam){
        this.tabelaSearchFrame = tabelaParam;
        painelTabelaSearchFrame = new JScrollPane(this.tabelaSearchFrame);
    }

    public void atualizaSearchFrame(){
        painelTabelaSearchFrame.revalidate();
        painelTabelaSearchFrame.repaint();
    }

    public void limpaSearchFrame(){
        if (this.tabelaSearchFrame == null){
            this.tabelaSearchFrame = null;
        }
        else{
            painelTabelaSearchFrame.remove(this.tabelaSearchFrame);
        }
    }
}
