package util;

import dao.ProdutoDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import pojo.ItemCompra;
import pojo.Produto;

public class TableModelItemCompra extends AbstractTableModel {
    protected String[] colunas = new String[] {"Código", "Produto", "Valor Unitário", "Quantidade", "Desconto", "Valor Líquido"};
    protected List<ItemCompra> dados = new ArrayList();
    private Produto produto = new Produto();
    protected ProdutoDao produtoDao = new ProdutoDao(produto);

    public void addRow(ItemCompra itemCompra) {
        dados.add(itemCompra);
        int numeroLinha = dados.size() - 1;
        fireTableRowsInserted(numeroLinha, numeroLinha);
    }    

    public void removeRow(int numeroLinha) {
        dados.remove(numeroLinha);
        fireTableRowsDeleted(numeroLinha, numeroLinha);
    }
    
    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    public ItemCompra getItemCompra(int linha) {
        return dados.get(linha);
    }
    
    public void setDados(List<ItemCompra> dados) {
        this.dados = dados;
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        switch (col) {
            case 0: dados.get(row).getProduto().setId((Integer) value);
                    break;
            case 1: dados.get(row).getProduto().setId((Integer) value);
                    produtoDao.setProduto(dados.get(row).getProduto());
                    produtoDao.consultar();
                    break;
            case 2: dados.get(row).setValorUnitario((BigDecimal) value);
                    break;
            case 3: dados.get(row).setQtde((Integer) value);
                    break;
            case 4: dados.get(row).setDesconto((BigDecimal) value);
                    break;                
            case 5: //Não é preciso tratar a coluna 5, pois ela é resultado de um cálculo
                    break;                
            default: JOptionPane.showMessageDialog(null, "Não existe tratamento para a coluna " + col + " na tabela de Itens de Compra. (set)");
        }
        fireTableCellUpdated(row, col);
    }    

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0: return dados.get(row).getProduto().getId();
            case 1: return dados.get(row).getProduto().getNome();
            case 2: return dados.get(row).getValorUnitario();
            case 3: return dados.get(row).getQtde();
            case 4: return dados.get(row).getDesconto();
            case 5: BigDecimal qtde = new BigDecimal(dados.get(row).getQtde());
                    return dados.get(row).getValorUnitario().multiply(qtde).subtract(dados.get(row).getDesconto());
            default: JOptionPane.showMessageDialog(null, "Não existe tratamento para a coluna " + col + " na tabela de Itens de Compra. (get)");
                     return "";
        }
    }

    @Override
    public String getColumnName(int numeroColuna) {
        return colunas[numeroColuna];
    }
}