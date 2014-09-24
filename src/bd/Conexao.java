package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {
    private static Connection conexao;

    public static Connection getConexao() {
        try {
            if (conexao == null) {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                conexao = DriverManager.getConnection("jdbc:derby://localhost:1527/UMUPET","SYSDBA","masterkey"
                   );
            }
            return conexao;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o"
                    + " driver de acesso ao banco de dados.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar com"
                    + " o banco de dados.");
            e.printStackTrace();
            return null;
        }
    }
}