package ru.geekbrains.client.swing;

import ru.geekbrains.client.AuthException;
import ru.geekbrains.client.Network;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

public class LoginDialog extends JDialog {

  private final JTextField tfUsername;
  private final JPasswordField pfPassword;

  private boolean connected;

  public LoginDialog(Frame parent, final Network network) {
    super(parent, "Логин", true);

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints cs = new GridBagConstraints();

    cs.fill = GridBagConstraints.HORIZONTAL;

    JLabel lbUsername = new JLabel("Имя пользователя: ");
    cs.gridx = 0;
    cs.gridy = 0;
    cs.gridwidth = 1;
    panel.add(lbUsername, cs);

    tfUsername = new JTextField(20);
    cs.gridx = 1;
    cs.gridy = 0;
    cs.gridwidth = 2;
    panel.add(tfUsername, cs);

    JLabel lbPassword = new JLabel("Пароль: ");
    cs.gridx = 0;
    cs.gridy = 1;
    cs.gridwidth = 1;
    panel.add(lbPassword, cs);

    pfPassword = new JPasswordField(20);
    cs.gridx = 1;
    cs.gridy = 1;
    cs.gridwidth = 2;
    panel.add(pfPassword, cs);
    panel.setBorder(new LineBorder(Color.GRAY));

    JButton btnLogin = new JButton("Войти");
    JButton btnCancel = new JButton("Отмена");

    JPanel bp = new JPanel();
    bp.add(btnLogin);

    btnLogin.addActionListener(
        e -> {
          try {
            network.authorize(tfUsername.getText(), String.valueOf(pfPassword.getPassword()));
            connected = true;
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                LoginDialog.this, "Ошибка сети", "Авторизация", JOptionPane.ERROR_MESSAGE);
            return;
          } catch (AuthException ex) {
            JOptionPane.showMessageDialog(
                LoginDialog.this, "Ошибка авторизации", "Авторизация", JOptionPane.ERROR_MESSAGE);
            return;
          }
          dispose();
        });

    bp.add(btnCancel);
    btnCancel.addActionListener(
        e -> {
          connected = false;
          dispose();
        });

    getContentPane().add(panel, BorderLayout.CENTER);
    getContentPane().add(bp, BorderLayout.PAGE_END);

    pack();
    setResizable(false);
    setLocationRelativeTo(parent);
  }

  public boolean isConnected() {
    return connected;
  }
}
