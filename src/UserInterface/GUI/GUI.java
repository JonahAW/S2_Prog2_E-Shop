package UserInterface.GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import Domain.Eshop;
import Domain.Search.SuchOrdnung;
import Exceptions.Benutzer.ExceptionBenutzerNameUngültig;
import UserInterface.UserInterface;


public class GUI extends UserInterface implements ActionListener{
    JFrame frame;
    LoginGUI login;
    RegisterGUI register;
    KundeGUI kunde;
    InfoBox info;
    SuchOrdnung suchOrdnung = null;

    public GUI(Eshop eshop){

        super(eshop);
        frame = new JFrame("maiNFrame");
        login = new LoginGUI(this);
        register = new RegisterGUI(this);
        kunde = new KundeGUI(this);
        info = new InfoBox();

        buildMainWindow();

    }


    public void buildMainWindow(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(register);
        frame.add(login);
        frame.add(kunde);
        setVisiblePanel("login");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        }


    //reagiert auf aktionen, kommuniziert mit Eshop, führt entsprechende Befehle zur Layout Änderung in den Panels aus
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "login_loginButton":
                switch(eshop.login(this, login.userText.getText(), login.passwordText.getText())){
                        case NONE: 
                            info.infoBox("username oder Passwort falsch", "Login Fehler");
                            login.clearText();
                            break;
                        case KUNDE:
                            setVisiblePanel("kunde");
                            login.clearText();
                            break;
                        case MITARBEITER:
                        login.clearText();
                            setVisiblePanel("kunde");
                            login.clearText();
                            break;
                }
                break;
            case "login_toRegister":
                setVisiblePanel("register");
                break;

            case "register_registerButton":
                try{
                    String name = "" + register.vornameLabel.getText() + register.nameText.getText();
                    String email = register.mailText.getText();
                    String address = "" + register.landBox.getSelectedItem() + register.ortLabel.getText() + register.streetLabel.getText();
                    String un = register.userText.getText();
                    String passwort = register.passwordText.getText();

                    eshop.BV_kundeHinzufügen(name, un, passwort, email, address);
                    info.infoBox("Konto wurde erstellt", "Bestätigung");

                } catch (ExceptionBenutzerNameUngültig e) {
                    info.infoBox(e.getMessage(), "Registrieren Fehler");
                    register.clearText();
                }
                break;  
            case "register_backToLogin":
                setVisiblePanel("login");
                break;
        }
    }

    //bestimmmt sichtbares Panel
    public void setVisiblePanel(String sichtbar){

        //Alle panels unsichtbar setzen
        login.setVisible(false);
        register.setVisible(false);
        kunde.setVisible(false);

        //richtiges Panel sichtbar setzen
        switch(sichtbar){
            case "none":
                break;
            case "login": 
                login.setVisible(true);
                frame.pack();
                break;
            case "register": 
                register.setVisible(true);
                frame.pack();
                break;
            case "kunde":
                kunde.setVisible(true);
                frame.pack();
                break;
        }

    }
        
    public static void main(String[] args){
        Eshop eshop = new Eshop("Nutzer.txt", "Ereignisse.txt");
         GUI gui = new GUI(eshop);
    }

    @Override
    public boolean run(){
        // TODO Auto-generated method stub
        return false;
    }
        
}
