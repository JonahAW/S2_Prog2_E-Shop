package BenutzerObjekte;

public class Kunde extends Benutzer{

    private String email;
    private String adress = "";
    private int kundenNr;
    private Warenkorb warenkorb;
    //private String plz = "";
    //private String wohnort = "";

    /**
     * 
     * @param name  name des kunden
     * @param username benutzername für login
     * @param password passwort zum einlloggen
     * @param kundenNr 
     * @param email
     * @param adress
     */
    public Kunde(String name, String username, String password, int kundenNr, String email, String adress) {
        super(name, username, password);
        this.kundenNr = kundenNr;
        this.email = email;
        this.adress = adress;
        this.warenkorb = new Warenkorb;
    }

    //#region Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getKundenNr() {
        return kundenNr;
    }
    //#endregion
    // Weitere Dienste der Kunden-Objekte
}
