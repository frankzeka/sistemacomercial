package dao;

import bd.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Pais;
import util.MetodosGenericos;

public class PaisDao extends DaoPadrao {
    private final String INSERTSQL = "INSERT INTO PAIS VALUES (?,?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE PAIS SET IDUSUARIO = ?, NOME = ?, ATIVO = ?, OBS = ?, ULTIMAALTERACAO = ? WHERE ID = ?";
    private final String DELETESQL = "DELETE FROM PAIS WHERE ID = ?";
    private final String CONSULTASQL = "SELECT * FROM PAIS WHERE ID = ?";
    public final String PESQUISASQL = "SELECT ID, NOME, ATIVO FROM PAIS";
    private Pais pais;
    
    public PaisDao(Pais pais) {
        this.pais = pais;
    }    
    
    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            pais.setId(pegaGenerator("GPAIS"));
            ps.setInt(1, pais.getId());
            ps.setInt(2, pais.getUsuario().getId());
            ps.setString(3, pais.getNome());
            ps.setBoolean(4, pais.isAtivo());
            ps.setString(5, pais.getObs());
            ps.setDate(6, MetodosGenericos.dataParaBanco(pais.getUltimaAlteracao()));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o País.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setInt(1, pais.getUsuario().getId());
            ps.setString(2, pais.getNome());
            ps.setBoolean(3, pais.isAtivo());
            ps.setString(4, pais.getObs());
            ps.setDate(5, MetodosGenericos.dataParaBanco(pais.getUltimaAlteracao()));
            ps.setInt(6, pais.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o País.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, pais.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o País.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, pais.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pais.setId(rs.getInt(1));
                pais.getUsuario().setId(rs.getInt(2));
                pais.setNome(rs.getString(3));
                pais.setAtivo(rs.getBoolean(4));
                pais.setObs(rs.getString(5));
                pais.setUltimaAlteracao(MetodosGenericos.dataDoBanco(rs.getDate(6)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "País não encontrado.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o País.");
            ex.printStackTrace();
            return false;
        }
    }    
}