package com.gym.fe;

import com.gym.be.payment.PaymentController;
import com.gym.be.controller.RegisterController;
import com.gym.be.payment.PaymentModel;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

@Service
public class UIInit extends JFrame {
  @Autowired
  private RegisterController registerController;
  @Autowired
  private PaymentController paymentController;

  public void initUI() {

    this.setLayout(null);

    //Criação do label Nome
    final JLabel clienteLabel = new JLabel("Cliente:");
    createLayout(clienteLabel, this, 150, 50, 5, 5);

    //Criação do TextField Nome
    final JTextField clienteText = new JTextField();
    createLayout(clienteText, this, 100, 20, 5, 40);

    //Criação do label Data Inicial
    final JLabel dtInicialLabel = new JLabel("Data Inicial:");
    createLayout(dtInicialLabel, this, 150, 50, 5, 45);

    //Criação do DateTextField Data Inicial
    final JTextField dtInicialTextField = new JTextField();
    dtInicialTextField.setText(
        new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));
    createLayout(dtInicialTextField, this, 100, 20, 5, 80);

    //Criação do label Data Final
    final JLabel dtFinalLabel = new JLabel("Data Final:");
    createLayout(dtFinalLabel, this, 150, 50, 170, 45);

    //Criação do DateTextField Data Final
    final JTextField dtFinalTextField = new JTextField();
    dtFinalTextField.setText(
        new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));
    createLayout(dtFinalTextField, this, 100, 20, 165, 80);

    //Criação do label CheckBox
    final JLabel statusLabel = new JLabel("Status:");
    createLayout(statusLabel, this, 150, 50, 5, 85);

    //Criação do CheckBox Pago
    final JCheckBox pagoCheckBox = new JCheckBox("Pago");
    createLayout(pagoCheckBox, this, 150, 50, 5, 105);

    //Criação do label Modalidades
    final JLabel modalidadeLabel = new JLabel("Modalidade:");
    createLayout(modalidadeLabel, this, 150, 50, 170, 5);

    //Criação do TextField Modalidade
    final JTextField modalidadeText = new JTextField();
    createLayout(modalidadeText, this, 100, 20, 165, 40);

    //Dados que serão exibidos na tabelaLimpa
    String[][] dadosLimpo = {
        {"", "", "", "", "", ""}
    };

    //Colunas da tabela
    String[] colunasTabela = {"ID", "Cliente", "Modalidades", "Data Vencimento", "Valor", "Pago"};

    //Criação do JTabel TabelaLimpa
    JTable tabelaLimpa;

    //Inicialização da tabelaLimpa com os dados e coluna
    tabelaLimpa = new JTable(dadosLimpo, colunasTabela);

    //Criação do JScollPane painelTabelaLimpa
    JScrollPane painelTabelaLimpa = new JScrollPane(tabelaLimpa);

    createLayout(painelTabelaLimpa, this, 300, 200, 5, 160);

    //Frame para o popup do botão Pesquisar
    final JFrame pesquisaFrame = new JFrame("Frame de Pesquisa");
    pesquisaFrame.setSize(250, 150);

    //Criação do button Pesquisar
    var pesquisarButton = new JButton("Pesquisar");

    ////////////////// Ação do botão pesquisar //////////////////

    //Cria o SearchFrame
    SearchFrame searchFrame = new SearchFrame(this);

    //Cria a tabela
    SearchTable tabela = new SearchTable();

    pesquisarButton.addActionListener((ActionEvent event) -> {

      if (this.isAncestorOf(tabela.tabela)) {
        this.remove(tabela.tabela);
        this.remove(searchFrame.painelTabelaSearchFrame);
      }

      //Desabilita a tabelaLimpa e o painelTabelaLimpa
      tabelaLimpa.setVisible(false);
      painelTabelaLimpa.setVisible(false);

      ////////////////// Atualiza JTabel Tabela //////////////////

      //Verifica preenchimento do dtInicialTextField
      Date dtInicial = null;
      if (dtInicialTextField.getText().isEmpty() == false) {

        //Passa a data inicial já separada em dia, mês e ano para vecDtInicial
        String[] vecDtInicial = dtInicialTextField.getText().split("/");

        try {
          //Formata dtInicial para o SimpleDateFormat
          dtInicial = new SimpleDateFormat("yyyyMMdd").parse(
              vecDtInicial[2] + vecDtInicial[1] + vecDtInicial[0]);
        } catch (ParseException e) {
          System.out.println("Erro no formato de data");
          dtInicial = null;
          e.printStackTrace();
        }
      }

      //Verifica preenchimento do dtFinalTextField
      Date dtFinal = null;
      if (dtFinalTextField.getText().isEmpty() == false) {

        //Passa a data final já separada em dia, mês e ano para vecDtFinal
        String[] vecDtFinal = dtFinalTextField.getText().split("/");

        try {
          //Formata dtFinal para o SimpleDateFormat
          dtFinal =
              new SimpleDateFormat("yyyyMMdd").parse(vecDtFinal[2] + vecDtFinal[1] + vecDtFinal[0]);
        } catch (ParseException e) {
          System.out.println("Erro no formato de data");
          dtFinal = null;
          e.printStackTrace();
        }
      }

      //Passa vecModalidadeText para listModalidades
      List<String> listModalidade = modalidadeText.getText().equals("") ? null : Arrays.asList(
          modalidadeText.getText().replaceAll(" ", "").split(","));

      List<PaymentModel> listRetorno = (List<PaymentModel>) paymentController.paymentGet(
          (clienteText.getText().isEmpty() ? null : clienteText.getText()), dtInicial, dtFinal,
          pagoCheckBox.isSelected(), (listModalidade == null ? null : listModalidade)).getBody();

      //Matriz usada na criação tabela
      String[][] dados = new String[listRetorno.size()][6];

      //Popula a matriz com os dados
      listRetorno.forEach(payment -> {
        int i = listRetorno.indexOf(payment);
        String id = payment.getId();
        dados[i][0] = id;
        String nome = payment.getRegister().getName();
        dados[i][1] = nome;
        String modalidades = payment.getRegister().getModalities().toString().replace("]", "");
        dados[i][2] = modalidades.replace("[", "");
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        String vencimento = fmt.format(payment.getPaymentDate());
        dados[i][3] = vencimento;
        String valor = payment.getRegister().getValue().toString();
        dados[i][4] = valor;
        String pago = payment.getPayed() ? "Pago" : "Pendente";
        dados[i][5] = pago;
      });

      tabela.preencheTabela(dados, colunasTabela);

      searchFrame.preparaSearchFrame(tabela.tabela);

      createLayout(searchFrame.painelTabelaSearchFrame, this, 300, 200, 5, 160);
    });

    createLayout(pesquisarButton, this, 100, 30, 162, 117);

    ////////////////// PAGAMENTO //////////////////

    //Criação do label ID
    final JLabel idLabel = new JLabel("ID:");
    createLayout(idLabel, this, 150, 50, 40, 355);

    //Criação do TextField ID
    final JTextField idText = new JTextField();
    createLayout(idText, this, 100, 20, 57, 370);

    //Criação do button Cadastro
    var pagarButton = new JButton("Pagar");

    pagarButton.addActionListener((ActionEvent event) -> {
      String idPagar = idText.getText();
      paymentController.paymentPost(idPagar);

    });

    createLayout(pagarButton, this, 100, 30, 155, 366);

    ////////////////// CADASTRO //////////////////

    //Frame para o popup do botão Cadastro
    final JFrame cadastroFrame = new JFrame("Cadastro");
    cadastroFrame.setSize(250, 150);

    //Criação do button Cadastro
    var cadastrarButton = new JButton("Cadastrar");

    cadastrarButton.addActionListener((ActionEvent event) -> {
      JOptionPane.showMessageDialog(cadastroFrame, "Cadastro");
    });

    createLayout(cadastrarButton, this, 100, 30, 110, 400);

    ////////////////// TELA INICIAL //////////////////

    setResizable(false);
    setTitle("Gym Application");
    setSize(310, 450);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  void createLayout(JComponent componente, JFrame frame, int tamX, int tamY, int locX, int locY) {
    //Tamanho
    componente.setSize(tamX, tamY);

    //Posição
    componente.setLocation(locX, locY);

    //Adiciona componente ao Frame
    frame.add(componente);
  }
}