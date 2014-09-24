package dao;

import bd.Conexao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pojo.Produto;
import util.MetodosGenericos;

public class ProdutoDao extends DaoPadrao {
    private final String INSERTSQL = "INSERT INTO PRODUTO VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final String CONSULTASQL = "SELECT * FROM PRODUTO WHERE ID = ?";
    public static final String PESQUISASQL = "SELECT ID, NOME, ATIVO FROM PRODUTO";
    private Produto produto;
    
    public ProdutoDao(Produto produto) {
        setProduto(produto);
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            produto.setId(pegaGenerator("GPRODUTO"));
            ps.setInt(1, produto.getId());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());
            ps.setInt(4, produto.getQtde());
            ps.setInt(5, produto.getQtdeMin());
            ps.setInt(6,produto.getQtdeMax());
            ps.setBigDecimal(7, produto.getValorCusto());
            ps.setBigDecimal(8,produto.getValorVenda());
            ps.setDate(9,MetodosGenericos.dataParaBanco(produto.getDataCadastro()));         
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, produto.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                produto.setId(rs.getInt(1));
                produto.getUsuario().setId(rs.getInt(2));
                produto.setNome(rs.getString(3));
                produto.setQtde(rs.getInt(4));
                produto.setQtdeMin(rs.getInt(5));
                produto.setQtdeMax(rs.getInt(6));
                produto.setPontoPedido(rs.getInt(7));
                produto.setValorCusto(rs.getBigDecimal(8));
                produto.setValorVenda(rs.getBigDecimal(9));
                produto.setAtivo(rs.getBoolean(10));
                produto.setObs(rs.getString(11));
                produto.setDateCadastro(MetodosGenericos.dataDoBanco(rs.getDate(12)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Produto.");
            ex.printStackTrace();
            return false;
        }
    }    
}