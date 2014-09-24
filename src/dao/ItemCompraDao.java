package dao;

import bd.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.ItemCompra;
import util.MetodosGenericos;

public class ItemCompraDao extends DaoPadrao {
    private final String SQLINCLUIR = "INSERT INTO ITEMCOMPRA VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLCONSULTAR = "SELECT * FROM ITEMCOMPRA WHERE CODIGO = ?";
    private ItemCompra itemCompra;

    public void setItemCompra(ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    public boolean incluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLINCLUIR);
            itemCompra.setId(pegaGenerator("GITEMCOMPRA"));
            ps.setInt(1, itemCompra.getId());
            ps.setInt(2, itemCompra.getCompra().getId());
            ps.setInt(3, itemCompra.getProduto().getId());
            ps.setInt(4, itemCompra.getUsuario().getId());
            ps.setInt(5, itemCompra.getQtde());
            ps.setBigDecimal(6, itemCompra.getValorUnitario());
            ps.setBigDecimal(7, itemCompra.getDesconto());
            ps.setDate(8, MetodosGenericos.dataParaBanco(itemCompra.getUltimaAlteracao()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Item de Compra.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLCONSULTAR);
            ps.setInt(1, itemCompra.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                itemCompra.setId(rs.getInt(1));
                itemCompra.getCompra().setId(rs.getInt(2));
                itemCompra.getProduto().setId(rs.getInt(3));
                itemCompra.getUsuario().setId(rs.getInt(4));
                itemCompra.setQtde((rs.getInt(5)));
                itemCompra.setValorUnitario(rs.getBigDecimal(6));
                itemCompra.setDesconto((rs.getBigDecimal(7)));
                itemCompra.setUltimaAlteracao((rs.getDate(8)));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Item de Compra.");
            e.printStackTrace();
            return false;
        }
    }
}