package dao;

import bd.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Estado;
import util.MetodosGenericos;

public class EstadoDao extends DaoPadrao {
    private final String INSERTSQL = "INSERT INTO ESTADO VALUES (?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE ESTADO SET NOME = ?, ATIVO = ?, SIGLA = ?, ULTIMAALTERACAO = ? WHERE ID = ?";
    private final String DELETESQL = "DELETE FROM ESTADO WHERE ID = ?";
    private final String CONSULTASQL = "SELECT * FROM ESTADO WHERE ID = ?";
    public final String PESQUISASQL = "SELECT ID, NOME, ATIVO FROM ESTADO";
    private final Estado estado;
    
    public EstadoDao(Estado estado) {
        this.estado = estado;
    }    
    
    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            //estado.setId(pegaGenerator("GESTADO"));
            ps.setInt(1,estado.hashCode());
            ps.setString(2, estado.getNome());
            ps.setString(3, estado.getSigla());
            ps.setString(4, "s");
            ps.setDate(5, MetodosGenericos.dataParaBanco(estado.getUltimaAlteracao()));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Estado.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(3, estado.getNome());
            ps.setBoolean(4, estado.isAtivo());
            ps.setString(5, estado.getSigla());
            ps.setDate(7, MetodosGenericos.dataParaBanco(estado.getUltimaAlteracao()));
            ps.setInt(8, estado.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o Estado.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean excluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, estado.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o Estado.");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, estado.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                estado.setId(rs.getInt(1));
                estado.setNome(rs.getString(4));
                estado.setAtivo(rs.getBoolean(5));
                estado.setSigla(rs.getString(6));
                estado.setUltimaAlteracao(MetodosGenericos.dataDoBanco(rs.getDate(8)));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Estado não encontrado.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Estado.");
            ex.printStackTrace();
            return false;
        }
    }    
}