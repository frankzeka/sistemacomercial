package dao;

import bd.Conexao;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DaoPadrao {
    public Integer pegaGenerator(String nomeGenerator) {
        try {
            String sql = "SELECT GEN_ID(" + nomeGenerator + ", 1) FROM RDB$DATABASE";
            Statement ps = Conexao.getConexao().createStatement();
            ResultSet rs = ps.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível obter o generator.");
            return null;
        }
    }    
}