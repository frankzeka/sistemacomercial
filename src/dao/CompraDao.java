package dao;

import bd.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Compra;
import pojo.ItemCompra;
import pojo.Produto;
import util.Dados;
import util.MetodosGenericos;

public class CompraDao extends DaoPadrao {
    private final String SQLINCLUIR = "INSERT INTO COMPRA VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQLCONSULTAR = "SELECT * FROM COMPRA WHERE ID = ?";
    private final String SQLCONSULTARITENS = "SELECT ID FROM ITEMCOMPRA WHERE IDCOMPRA = ?";
    public static final String SQLPESQUISAR = "SELECT COMPRA.ID, FORNECEDOR.NOMERAZAOSOCIAL, COMPRA.DATA, COMPRA.VALOR FROM COMPRA, FORNECEDOR WHERE COMPRA.IDFORNECEDOR = FORNECEDOR.ID ORDER BY FORNECEDOR.NOMERAZAOSOCIAL, COMPRA.DATA, COMPRA.VALOR";
    private Compra compra;

    public CompraDao(Compra compra) {
        this.compra = compra;
    }

    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLINCLUIR);
            compra.setId(pegaGenerator("GCOMPRA"));
            ps.setInt(1, compra.getId());
            ps.setInt(2, compra.getFornecedor().getId());
            ps.setInt(3, Dados.usuarioLogado.getId());
            ps.setString(4, "" + compra.getStatus());
            ps.setString(5, "" + compra.getSituacao());
            ps.setDate(6, MetodosGenericos.dataParaBanco(compra.getData()));
            ps.setBigDecimal(7, compra.getValor());
            ps.setBigDecimal(8, compra.getDesconto());
            ps.setString(9, compra.getObs());
            ps.setDate(10, MetodosGenericos.dataParaBanco(compra.getUltimaAlteracao()));
            Conexao.getConexao().setAutoCommit(false);
            ps.executeUpdate();
            List<ItemCompra> itensCompra = compra.getItens();
            ItemCompra itemCompra;
            ItemCompraDao itemCompraDao = new ItemCompraDao();
            for (int i = 0; i < itensCompra.size(); i++) {
                itemCompra = itensCompra.get(i);
                itemCompra.setCompra(compra);
                itemCompra.setUsuario(Dados.usuarioLogado);
                itemCompra.setUltimaAlteracao(new Date());
                itemCompraDao.setItemCompra(itemCompra);
                itemCompraDao.incluir();
            }
            Conexao.getConexao().commit();
            Conexao.getConexao().setAutoCommit(true);
            return true;
        } catch (Exception e) {
            try {
                JOptionPane.showMessageDialog(null, "Não foi possível incluir a Compra.");
                Conexao.getConexao().rollback();
                Conexao.getConexao().setAutoCommit(true);
            } catch (Exception ev) {
                e.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLCONSULTAR);
            ps.setInt(1, compra.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                compra.setId(rs.getInt(1));
                compra.getFornecedor().setId(rs.getInt(2));
                compra.getUsuario().setId(rs.getInt(3));
                compra.setStatus(rs.getString(4).charAt(0));
                compra.setSituacao(rs.getString(5).charAt(0));
                compra.setData(rs.getDate(6));
                compra.setValor(rs.getBigDecimal(7));
                compra.setDesconto(rs.getBigDecimal(8));
                compra.setObs(rs.getString(9));
                compra.setUltimaAlteracao(rs.getDate(10));
                compra.setItens(consultarItens());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Compra não encontrada.");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Compra");
            e.printStackTrace();
            return false;
        }
    }

    public List<ItemCompra> consultarItens() {
        List<ItemCompra> itensCompra = new ArrayList();
        try {
            ItemCompraDao itemCompraDao = new ItemCompraDao();
            Produto produto = new Produto();
            ProdutoDao produtoDao = new ProdutoDao(produto);
            ItemCompra itemCompra;
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLCONSULTARITENS);
            ps.setInt(1, compra.getId());
            ResultSet rs = ps.executeQuery();
            List<Integer> codigosItens = new ArrayList();
            while (rs.next()) {
                codigosItens.add(rs.getInt(1));
            }
            for (int i = 0; i < codigosItens.size(); i++) {
                itemCompra = new ItemCompra();
                itemCompra.setId(codigosItens.get(i));
                itemCompraDao.setItemCompra(itemCompra);
                itemCompraDao.consultar();
                produto.setId(itemCompra.getProduto().getId());
                produtoDao.consultar();
                itensCompra.add(itemCompra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itensCompra;
    }
}