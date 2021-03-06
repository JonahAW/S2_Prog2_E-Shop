package UserInterface.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class RegisterGUI extends JPanel{
    GUI gui;
    JLabel title;
    JPanel inputPanel;
    JLabel nameLabel;
    JLabel vornameLabel;
    JLabel userLabel;
    JLabel passwordLabel;
    JLabel landLabel;
    JLabel mailLabel;
    JLabel ortLabel;
    JLabel streetLabel;
    JTextField nameText;
    JTextField vornameText;
    JTextField userText;
    JTextField passwordText;
    JTextField mailText;
    JTextField ortText;
    JTextField streetText;
    JLabel validation;
    JButton registerButton;
    JButton backToLogin;
    JComboBox landBox;

    public RegisterGUI(GUI gui)   {

        this.gui = gui;

        String[] landAuswahl = {"Deutschland", "England"};
        title = new JLabel("Register", JLabel.CENTER);   
        inputPanel = new JPanel();
        nameLabel = new JLabel("Name");
        vornameLabel = new JLabel("Vorname");
        userLabel = new JLabel("User");
        passwordLabel = new JLabel("password");
        mailLabel = new JLabel("Email");
        landLabel= new JLabel("Land");
        ortLabel = new JLabel("Ort");
        streetLabel = new JLabel("Straße/HausNr");

        nameText = new JTextField();
        vornameText = new JTextField();
        userText = new JTextField();
        passwordText = new JTextField();
        mailText = new JTextField();
        ortText = new JTextField();
        streetText = new JTextField();
        validation = new JLabel("");
        registerButton = new JButton("register");
        registerButton.setActionCommand("register_registerButton");
        backToLogin = new JButton("back to login");
        backToLogin.setActionCommand("register_backToLogin");
        landBox = new JComboBox<>(landAuswahl);
        
        initializeLayout();
        initializeAction();
        setVisible(true);
    }

    private void initializeLayout() {
        
        //gesamtes Panel
        setPreferredSize(new Dimension(400,560));
        setLayout(null);
        setBackground(new Color(71,39,79));
        GridBagConstraints c = new GridBagConstraints();

        //Überschrift Login
        title.setBackground(Color.white);
        title.setOpaque(true);
        Font font = new Font("Verdana", Font.PLAIN, 30);
        title.setFont(font);
        title.setBounds(50,30,300,50);
        add(title);

        //Label, Eingabefelder, Button in einem Panel 
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBounds(50,110,300, 380);
        add(inputPanel);

        c.gridy = 0;
        c.weighty=0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 10, 10,0);
        c.ipadx = 10;
        inputPanel.add(nameLabel, c);

        c.gridy=1;
        c.gridx=0;
        inputPanel.add(vornameLabel, c);

        c.gridy=2;
        inputPanel.add(userLabel, c);

        c.gridy=3;
        inputPanel.add(passwordLabel, c);

        c.gridy=4;
        inputPanel.add(mailLabel, c);

        c.gridy=5;
        inputPanel.add(landLabel, c);

        c.gridy=6;
        inputPanel.add(ortLabel, c);

        c.gridy=7;
        inputPanel.add(streetLabel, c);

        c.weightx = 1;
        c.gridx=1;
        c.gridy = 0;
        c.ipady = 10;
        c.insets = new Insets(0,10,10,10);
        c.fill = GridBagConstraints.BOTH;
        inputPanel.add(nameText, c);

        c.gridy = 1;
        inputPanel.add(vornameText, c);

        c.gridy = 2;
        inputPanel.add(userText, c);

        c.gridy = 3;
        inputPanel.add(passwordText, c);

        c.gridy = 4;
        inputPanel.add(mailText,c);

        c.gridy = 5;
        inputPanel.add(landBox, c);

        c.gridy = 6;
        inputPanel.add(ortText, c);

        c.gridy = 7;
        inputPanel.add(streetText, c);

        c.gridy = 8;
        c.gridx = 0;
        c.ipady = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0,10,0,10);
        inputPanel.add(registerButton,c);

        //back to login button
        backToLogin.setBounds(50,510,300,30);
        backToLogin.setOpaque(false);
        backToLogin.setContentAreaFilled(false);
        backToLogin.setBorderPainted(false);
        backToLogin.setForeground(Color.white);
        add(backToLogin);

    }

    //action Listener (--> gui) und mouseListener
    public void initializeAction(){
        registerButton.addActionListener(gui);
        backToLogin.addActionListener(gui);
        backToLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                backToLogin.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent me) {
                backToLogin.setForeground(Color.white);
            }
        });
    }

    void clearText(){
        nameText.setText("");
        userText.setText("");
        passwordText.setText("");
        mailText.setText("");
        ortText.setText("");
    }

}

