package telas;

import componentes.MeuJButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TelaSistema extends JFrame implements ActionListener {

    public JMenuBar jmb = new JMenuBar();
    public JMenu jmCadastros = new JMenu("Cadastros");
    public JMenu jmMovimentos = new JMenu("Movimentos");
    public JMenu jmRelatorios = new JMenu("Relatórios");
    public JMenuItem jmiPais = new JMenuItem("Pais");
    public JMenuItem jmiEstado = new JMenuItem("Estado");
    public JMenuItem jmiCidade = new JMenuItem("Cidade");
    public JMenuItem jmiCompra = new JMenuItem("Compra");
    public JMenuItem jmiProduto = new JMenuItem("Produto");
    public static JDesktopPane jdp = new JDesktopPane() {
        private Image imagem = new ImageIcon(getClass().getResource("/imagem/pais.png")).getImage();
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension dimension = this.getSize();
            int x = (int) (dimension.getWidth() - imagem.getWidth(this)) / 2;
            int y = (int) (dimension.getHeight() - imagem.getHeight(this)) / 2;
//            g.drawImage(imagem, x, y, imagem.getWidth(this), imagem.getHeight(this), this);
            g.drawImage(imagem, 0, 0, getSize().width, getSize().height, this);
        }
    };
   
    public static void main(String args[]) {
        new TelaSistema();
    }

    public TelaSistema() {
        setTitle("Sistema Comercial 2014");
        setExtendedState(MAXIMIZED_BOTH);
        setJMenuBar(jmb);
        jmb.add(jmCadastros);
        jmb.add(jmMovimentos);
        jmb.add(jmRelatorios);
        jmCadastros.add(jmiPais);
        jmCadastros.add(jmiEstado);
        jmCadastros.add(jmiCidade);
        jmCadastros.add(jmiProduto);
        jmMovimentos.add(jmiCompra);
        jmiPais.addActionListener(this);
        jmiEstado.addActionListener(this);
        jmiCidade.addActionListener(this);
        jmiCompra.addActionListener(this);
        jmiProduto.addActionListener(this);
        getContentPane().add("Center", jdp);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jmiEstado) {
            TelaCadastroEstado.getTela();
        }
        if (ae.getSource() == jmiCidade) {
            TelaCadastroCidade.getTela();
        }
        if (ae.getSource() == jmiProduto ) {
            TelaProduto.getTela();
        }
    }
}