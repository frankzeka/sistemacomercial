package telas;

import bd.Conexao;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TelaPesquisa extends JInternalFrame implements InternalFrameListener, MouseListener {
    private static TelaPesquisa telaPesquisa = null;

    static void getTela(String pesquisa_de_Cidade, String PESQUISASQL, String[] string, TelaCadastro aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String sql;
    private String[] titulos;
    private TelaCadastro tela;
    private JTable tabela;
    private JScrollPane jsp;
    private DefaultTableModel dtm = new DefaultTableModel();

    public TelaPesquisa(String tituloJanela, String sql, String[] titulosColunas, DefaultTableCellRenderer[] renderizadores, TelaCadastro tela) {
        super(tituloJanela, false, true, false, false);
        this.sql = sql;
        this.titulos = titulosColunas;
        this.tela = tela;
        tabela = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                   return false;
            }
        };
        tabela.setModel(dtm);
        for (int i = 0; i < titulosColunas.length; i++)
            dtm.addColumn(titulosColunas[i]);
        TableColumnModel tcm = tabela.getColumnModel();
        for (int i = 0; i < tcm.getColumnCount(); i++) {
            if (renderizadores[i] != null) {
                tcm.getColumn(i).setCellRenderer(renderizadores[i]);
            }
        }
        jsp = new JScrollPane(tabela);
        getContentPane().add(jsp);
        preencher();
        pack();
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tamanhoTela.width - getWidth())/2, ((tamanhoTela.height - getHeight()))/2);
        setVisible(true);
        tabela.addMouseListener(this);
        addInternalFrameListener(this);
    }

    public static void getTela(String tituloJanela, String sql, String[] titulos, DefaultTableCellRenderer[] renderizadores, TelaCadastro tela) {
        if (telaPesquisa == null) {
            telaPesquisa = new TelaPesquisa(tituloJanela, sql, titulos, renderizadores, tela);
            TelaSistema.jdp.add(telaPesquisa);
        }
        TelaSistema.jdp.setSelectedFrame(telaPesquisa);
        TelaSistema.jdp.moveToFront(telaPesquisa);
    }

    private void preencher() {
        try {
            Vector vetor;
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(sql);
            while (rs.next()) {
                vetor = new Vector();
                for (int i=1; i <= titulos.length; i++) {
                    vetor.add(rs.getString(i));
                }
                dtm.addRow(vetor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher tela de pesquisa");
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            String pk = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);
            tela.consultarBD(Integer.parseInt(pk));
            dispose();
            TelaSistema.jdp.remove(this);
            telaPesquisa = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        TelaSistema.jdp.remove(telaPesquisa);
        telaPesquisa = null;
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}