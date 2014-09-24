package telas;

import componentes.MeuCampoComboBox;
import componentes.MeuCampoDBComboBox;
import componentes.MeuCampoData;
import componentes.MeuCampoInteiro;
import componentes.MeuCampoMonetario;
import componentes.MeuCampoTextArea;
import dao.CompraDao;
import dao.FornecedorDao;
import dao.ProdutoDao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import pojo.Compra;
import pojo.Fornecedor;
import pojo.ItemCompra;
import pojo.Produto;
import renderizador.InteiroRender;
import renderizador.MonetarioRender;
import static telas.TelaCadastro.ESQUERDA;
import util.Dados;
import util.TableModelItemCompra;

public class TelaCadastroCompra extends TelaCadastro {
    private static TelaCadastroCompra tela;
    private Compra compra = new Compra();
    private CompraDao compraDao = new CompraDao(compra);
    private Fornecedor fornecedor = new Fornecedor();
    private FornecedorDao fornecedorDao = new FornecedorDao(fornecedor);
    private Produto produto = new Produto();
    private ProdutoDao produtoDao = new ProdutoDao(produto);
    private MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, false, "Código");
    private MeuCampoDBComboBox campoFornecedor = new MeuCampoDBComboBox(null, FornecedorDao.PESQUISASQL, true, true, "Fornecedor");
    private MeuCampoComboBox campoStatus = new MeuCampoComboBox(new String[][]{{"P", "Pedido"}, {"C", "Compra"}}, true, true, "Status");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Aberto"}, {"F", "Fechado"}}, true, true, "Situação");
    private MeuCampoData campoData = new MeuCampoData(12, true, "Data", true);
    private MeuCampoMonetario campoValorCompra = new MeuCampoMonetario(15, false, false, false, "Valor Total");
    private MeuCampoMonetario campoDescontoCompra = new MeuCampoMonetario(15, false, true, false, "Desconto");
    private MeuCampoMonetario campoValorLiquidoCompra = new MeuCampoMonetario(15, true, false, false, "Valor Líquido");
    private MeuCampoDBComboBox campoProdutoItem = new MeuCampoDBComboBox(null, ProdutoDao.PESQUISASQL, true, true, "Produto");
    private MeuCampoMonetario campoValorUnitarioItem = new MeuCampoMonetario(15, false, false, false, "Valor Unitário");
    private MeuCampoInteiro campoQuantidadeItem = new MeuCampoInteiro(15, true, false, false, "Quantidade");
    private MeuCampoMonetario campoDescontoItem = new MeuCampoMonetario(15, false, true, false, "Desconto");
    private MeuCampoMonetario campoValorLiquidoItem = new MeuCampoMonetario(15, true, false, false, "Valor Líquido");
    public MeuCampoTextArea campoObservacao = new MeuCampoTextArea(false, "Observação", 200, 200);
    private JLabel jlItem = new JLabel("Item");
    private TableModelItemCompra tm = new TableModelItemCompra();
    private JTable tabela = new JTable(tm) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (isRowSelected(row)) {
                c.setBackground(new Color(0, 255, 255));
            } else {
                if (row % 2 == 0) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.GRAY);
                }
            }
            return c;
        }
    };
    private JScrollPane jsp = new JScrollPane(tabela);
    private JPanel jpBotoesItem = new JPanel();
    private JButton jbConfirmarItem = new JButton("Ok");
    private JButton jbIncluirItem = new JButton("+");
    private JButton jbExcluirItem = new JButton("-");
    private ItemCompra itemCompra;

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroCompra();
            tela.addInternalFrameListener(new InternalFrameAdapter() { //Adiciona um listener para verificar quando a tela for fechada, fazendo assim a remoção da mesma junto ao JDesktopPane da TelaSistema e setando a variável tela = null para permitir que a tela possa ser aberta novamente em outro momento. Basicamente o mesmo controle efetuado pela tela de pesquisa, porém de uma forma um pouco diferente.
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.jdp.remove(tela);
                    tela = null;
                }
            });
            TelaSistema.jdp.add(tela);
        }
        //Depois do teste acima, independentemente dela já existir ou não, ela é selecionada e movida para frente
        TelaSistema.jdp.setSelectedFrame(tela);
        TelaSistema.jdp.moveToFront(tela);
        return tela;
    }

    public TelaCadastroCompra() {
        super("Cadastro de Compra");
        compra.setFornecedor(fornecedor);
        adicionaCampo(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionaCampo(2, 1, 1, 1, campoData, ESQUERDA);
        adicionaCampo(3, 1, 1, 2, campoFornecedor, ESQUERDA);
        adicionaCampo(1, 4, 1, 1, campoValorCompra, ESQUERDA);
        adicionaCampo(2, 4, 1, 1, campoDescontoCompra, ESQUERDA);
        adicionaCampo(3, 4, 1, 1, campoValorLiquidoCompra, ESQUERDA);
        adicionaCampo(1, 6, 1, 1, campoStatus, ESQUERDA);
        adicionaCampo(2, 6, 1, 1, campoSituacao, ESQUERDA);
        adicionaCampo(3, 6, 7, 1, campoObservacao, ESQUERDA);
        adicionaCampo(4, 1, 1, 1, jlItem, ESQUERDA);
        adicionaCampo(5, 1, 1, 2, campoProdutoItem, ESQUERDA);
        adicionaCampo(6, 1, 1, 1, campoValorUnitarioItem, ESQUERDA);
        adicionaCampo(7, 1, 1, 1, campoQuantidadeItem, ESQUERDA);
        adicionaCampo(8, 1, 1, 1, campoDescontoItem, ESQUERDA);
        adicionaCampo(9, 1, 1, 1, campoValorLiquidoItem, ESQUERDA);
        adicionaCampo(8, 4, 1, 3, jpBotoesItem, ESQUERDA);
        adicionaCampo(10, 1, 1, 7, jsp, ESQUERDA);
        jsp.setPreferredSize(new Dimension(800, 300));
        adicionaListeners();
        adicionaBotoesItem();
        pack();
        habilitarBotoes();
        habilitarCampos(false);
        jbAlterar.setVisible(false);
        jbExcluir.setVisible(false);
        TableColumnModel tableModel = tabela.getColumnModel();
        tableModel.getColumn(0).setCellRenderer(new InteiroRender());
        tableModel.getColumn(2).setCellRenderer(new MonetarioRender());
        tableModel.getColumn(3).setCellRenderer(new InteiroRender());
        tableModel.getColumn(4).setCellRenderer(new MonetarioRender());
        tableModel.getColumn(5).setCellRenderer(new MonetarioRender());
        tableModel.getColumn(0).setMaxWidth(50);
        tableModel.getColumn(1).setMaxWidth(350);
        tableModel.getColumn(2).setMaxWidth(100);
        tableModel.getColumn(3).setMaxWidth(100);
        tableModel.getColumn(4).setMaxWidth(100);
        tableModel.getColumn(5).setMaxWidth(100);
    }

    @Override
    public void habilitarBotoes() {
        super.habilitarBotoes();
        if (jbConfirmarItem != null) {
            jbConfirmarItem.setEnabled(operacao == INCLUINDO);
            jbIncluirItem.setEnabled(operacao == INCLUINDO);
            jbExcluirItem.setEnabled(operacao == INCLUINDO);
        }
    }
    
    @Override
    public void incluir() {
        super.incluir();
        jbIncluirItem.doClick();
    }

    @Override
    public void habilitarCampos(boolean status) {
        super.habilitarCampos(status);
        tabela.setEnabled(status);
    }

    @Override
    public void limparCampos() {
        super.limparCampos();
        campoData.setValor(new Date());
        campoValorUnitarioItem.setValor(BigDecimal.ZERO);
        campoDescontoItem.setValor(BigDecimal.ZERO);
        campoValorLiquidoItem.setValor(BigDecimal.ZERO);
        campoValorCompra.setValor(BigDecimal.ZERO);
        campoDescontoCompra.setValor(BigDecimal.ZERO);
        campoValorLiquidoCompra.setValor(BigDecimal.ZERO);
        tm.setDados(new ArrayList());
    }

    private void calcularItem() {
        double valorUnitarioProduto = ((BigDecimal) campoValorUnitarioItem.getValor()).doubleValue();
        double quantidadeProduto = ((Integer) campoQuantidadeItem.getValor()).doubleValue();
        double descontoProduto = ((BigDecimal) campoDescontoItem.getValor()).doubleValue();
        double valorLiquidoProduto = (valorUnitarioProduto * quantidadeProduto) - descontoProduto;
        campoValorLiquidoItem.setValor(new BigDecimal(valorLiquidoProduto));
    }

    private void calcularTotais() {
        BigDecimal totalCompra = BigDecimal.ZERO;
        BigDecimal qtde, valorItem;
        ItemCompra itemCompra;
        for (int i = 0; i < tm.getRowCount(); i++) {
            itemCompra = tm.getItemCompra(i);
            qtde = new BigDecimal(itemCompra.getQtde());
            valorItem = itemCompra.getValorUnitario().multiply(qtde).subtract(itemCompra.getDesconto());
            totalCompra = totalCompra.add(valorItem);
        }
        campoValorCompra.setValor(totalCompra);
        BigDecimal desconto = (BigDecimal) campoDescontoCompra.getValor();
        BigDecimal valorLiquido = ((BigDecimal) campoValorCompra.getValor()).subtract(desconto);
        campoValorLiquidoCompra.setValor(valorLiquido);
    }

    @Override
    public void setPersistencia() {
        fornecedor.setId((Integer) campoFornecedor.getValor());
        compra.getUsuario().setId(Dados.usuarioLogado.getId());
        compra.setStatus(((String) campoStatus.getValor()).charAt(0));
        compra.setSituacao(((String) campoSituacao.getValor()).charAt(0));
        compra.setData((Date) campoData.getValorDate());
        compra.setValor((BigDecimal) campoValorCompra.getValor());
        compra.setDesconto((BigDecimal) campoDescontoCompra.getValor());
        compra.setObs((String) campoObservacao.getValor());
        compra.setUltimaAlteracao(new Date());
    }

    @Override
    public boolean incluirBD() {
        List<ItemCompra> itensCompra = new ArrayList();
        for (int i = 0; i < tm.getRowCount(); i++) {
            itemCompra = tm.getItemCompra(i);
            itensCompra.add(itemCompra);
        }
        compra.setItens(itensCompra);
        boolean retorno = (super.incluirBD() && compraDao.inserir());
        campoCodigo.setValor(compra.getId());
        return retorno;
    }

    @Override
    public void consultarBD(int pk) {
        compra.setId(pk);
        compraDao.consultar();
        temDadosNaTela = true;
        setGui();
        operacao = PADRAO;
        habilitarBotoes();
    }

    public void setGui() {
        campoCodigo.setValor(compra.getId());
        campoData.setValor(compra.getData());
        campoFornecedor.setValor(compra.getFornecedor().getId());
        campoValorCompra.setValor(compra.getValor());
        campoDescontoCompra.setValor(compra.getDesconto());
        campoValorLiquidoCompra.setValor(compra.getValor().subtract(compra.getDesconto()));
        tm.setDados(compra.getItens());
    }

    @Override
    public void consultar() {
        super.consultar();
        TelaPesquisa.getTela("Pesquisa de Compra", CompraDao.SQLPESQUISAR, new String[]{"Código", "Fornecedor", "Data", "Valor Líquido"}, new DefaultTableCellRenderer[] {new InteiroRender(), null, null, new MonetarioRender()}, this);
    }

    private void adicionaListeners() {
        campoDescontoCompra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcularTotais();
                    }
                });
            }
        });
        KeyAdapter keyAdapterCalcularItem = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcularItem();
                    }
                });
            }
        };
        campoQuantidadeItem.addKeyListener(keyAdapterCalcularItem);
        campoDescontoItem.addKeyListener(keyAdapterCalcularItem);
        campoProdutoItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    produto.setId((Integer) campoProdutoItem.getValor());
                    produtoDao.consultar();
                    campoValorUnitarioItem.setValor(produto.getValorVenda());
                    calcularItem();
                }
            }
        });
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tabela.getSelectedRow() >= 0) {
                    itemCompra = tm.getItemCompra(tabela.getSelectedRow());
                    if (itemCompra.getProduto().getId() == 0) {  //É uma nova linha, dados devem ficar em branco.
                        campoProdutoItem.limpar();
                        campoValorUnitarioItem.setValor(BigDecimal.ZERO);
                        campoQuantidadeItem.setValor(1);
                        campoDescontoItem.setValor(BigDecimal.ZERO);
                        campoValorLiquidoItem.setValor(BigDecimal.ZERO);
                    } else {
                        campoProdutoItem.setValor(itemCompra.getProduto().getId());
                        campoValorUnitarioItem.setValor(itemCompra.getValorUnitario());
                        campoQuantidadeItem.setValor(itemCompra.getQtde());
                        campoDescontoItem.setValor(itemCompra.getDesconto());
                        BigDecimal qtde = new BigDecimal(itemCompra.getQtde());
                        BigDecimal valorTotalItem = itemCompra.getValorUnitario().multiply(qtde).subtract(itemCompra.getDesconto());
                        campoValorLiquidoItem.setValor(valorTotalItem);
                    }
                }
            }
        });
    }

    private void adicionaBotoesItem() {
        jpBotoesItem.setLayout(new GridLayout(1,3));
        jpBotoesItem.add(jbConfirmarItem);
        jpBotoesItem.add(jbIncluirItem);
        jpBotoesItem.add(jbExcluirItem);
        jbConfirmarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (campoProdutoItem.eVazio()) {
                    JOptionPane.showMessageDialog(null, "Selecione um produto.");
                    return;
                }
                if ((Integer) campoQuantidadeItem.getValor() == 0) {
                    JOptionPane.showMessageDialog(null, "Informe a quantidade.");
                    return;
                }
                itemCompra = tm.getItemCompra(tabela.getSelectedRow());
                itemCompra.getProduto().setId((Integer) campoProdutoItem.getValor());
                itemCompra.getProduto().setNome((String) campoProdutoItem.getValorTexto());
                itemCompra.setValorUnitario((BigDecimal) campoValorUnitarioItem.getValor());
                itemCompra.setQtde((Integer) campoQuantidadeItem.getValor());
                itemCompra.setDesconto((BigDecimal) campoDescontoItem.getValor());
                tm.fireTableDataChanged();
                calcularTotais();
            }
        });
        jbIncluirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tm.addRow(new ItemCompra());
                tabela.getSelectionModel().setSelectionInterval(tm.getRowCount() - 1, tm.getRowCount() - 1);
            }
        });
        jbExcluirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Você deve selecionar uma linha.");
                } else {
                    tm.removeRow(linhaSelecionada);
                    tabela.getSelectionModel().setSelectionInterval(tm.getRowCount() - 1, tm.getRowCount() - 1);
                }
            }
        });
    }
}