package dao;

import bd.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Cidade;
import util.MetodosGenericos;

public class CidadeDao extends DaoPadrao {
    private final String INSERTSQL = "INSERT INTO CIDADE VALUES (?,?,?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE CIDADE SET IDESTADO = ?, NOME = ?, ATIVO = ?, OBS = ?, ULTIMAALTERACAO = ? WHERE ID = ?";
    private final String DELETESQL = "DELETE FROM CIDADE WHERE ID = ?";
    private final String CONSULTASQL = "SELECT * FROM CIDADE WHERE ID = ?";
    public final String PESQUISASQL = "SELECT ID, NOME, ATIVO FROM CIDADE";
    private Cidade cidade;
    
    public CidadeDao(Cidade cidade) {
        this.cidade = cidade;
    }    
    
    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            cidade.setId(pegaGenerator("GCIDADE"));
            ps.setInt(1, cidade.getId());
            ps.setInt(2, cidade.getEstado().getId());
            ps.setString(4, cidade.getNome());
            ps.setBoolean(5, cidade.isAtivo());
            ps.setDate(7, MetodosGenericos.dataParaBanco(cidade.getUltimaAlteracao()));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a Cidade.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setInt(1, cidade.getEstado().getId());
            ps.setString(3, cidade.getNome());
            ps.setBoolean(4, cidade.isAtivo());
            ps.setDate(6, MetodosGenericos.dataParaBanco(cidade.getUltimaAlteracao()));
            ps.setInt(7, cidade.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a Cidade.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, cidade.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a Cidade.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, cidade.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cidade.setId(rs.getInt(1));
                cidade.getEstado().setId(rs.getInt(2));
                cidade.setNome(rs.getString(4));
                cidade.setAtivo(rs.getBoolean(5));
                cidade.setUltimaAlteracao(MetodosGenericos.dataDoBanco(rs.getDate(7)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cidade não encontrada.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Cidade.");
            ex.printStackTrace();
            return false;
        }
    }    
}