package telas;

import dao.UsuarioDao;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import pojo.Usuario;
import util.Dados;

public class TelaLogin extends JDialog implements ActionListener {

    private int contador;
    private JLabel jlUsuario = new JLabel("Usuário: ");
    private JTextField jtfUsuario = new JTextField(10);
    private JLabel jlSenha = new JLabel("Senha: ");
    private JPasswordField jpfSenha = new JPasswordField(10);
    private JButton jbOk = new JButton("Ok");
    private JButton jbCancela = new JButton("Cancela");

    public TelaLogin() {
        setTitle("Login");
        getContentPane().setLayout(new GridLayout(3, 2));
        getContentPane().add(jlUsuario);
        getContentPane().add(jtfUsuario);
        getContentPane().add(jlSenha);
        getContentPane().add(jpfSenha);
        getContentPane().add(jbOk);
        getContentPane().add(jbCancela);
        jbOk.addActionListener(this);
        jbCancela.addActionListener(this);
        pack();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbOk) {
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario usuario = usuarioDao.getUsuario(jtfUsuario.getText());
            if (usuario != null && String.valueOf(jpfSenha.getPassword()).equals(usuario.getSenha())) {
                Dados.usuarioLogado = usuario;
                setModal(false);
                dispose();
            } else {
                contador++;
                if (contador != 3) {
                    JOptionPane.showMessageDialog(null, "Usuário/Senha inválidos, tente novamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Número de tentativas excedido.");
                    System.exit(0);
                }
            }
        } else {
            //if (ae.getSource() == jbCancela) {
            System.exit(0);
        }
    }
}