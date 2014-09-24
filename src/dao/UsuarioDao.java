package dao;

import bd.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.Usuario;

public class UsuarioDao extends DaoPadrao {
    private Usuario usuario = new Usuario();
    private String CONSULTASQL = "SELECT * FROM USUARIO WHERE NOME = ?";

    public Usuario getUsuario(String nome) {
        try {
            usuario.setUsuario(new Usuario());
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.getUsuario().setId(rs.getInt(2));
                usuario.setNome(rs.getString(3));
                usuario.setSenha(rs.getString(4));
                usuario.setAtivo(rs.getBoolean(5));
                usuario.setObs(rs.getString(6));
                usuario.setUltimaAlteracao(rs.getDate(7));
                return usuario;
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não existe.");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível obter o usuário.");
            e.printStackTrace();
            return null;
        }
    }
}