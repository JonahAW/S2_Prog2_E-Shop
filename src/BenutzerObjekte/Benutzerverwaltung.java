package BenutzerObjekte;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import Exceptions.NutzerExistiertBereitsException;

public class Benutzerverwaltung {

  // Verwaltung der Nutzer in einer verketteten Liste
  private List<Benutzer> benutzerRegister;
  private Benutzer aktiverNutzer = null;

  public Benutzerverwaltung() {
    benutzerRegister = new Vector<Benutzer>();
  }

  public void registrieren(String name, String username, String password, int nr, String email, String adress) {

    Benutzer einNutzer = new Kunde(name, username, password, nr, email, adress);

    if (benutzerRegister.contains(einNutzer)) {
      // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    }
    // übernimmt Vector:
    benutzerRegister.add(einNutzer);
  }

  public void registrieren(String name, String username, String password, int mitarbeiterNr){
    Benutzer einNutzer = new Mitarbeiter(name, username, password, nr, email, adress);

    if (benutzerRegister.contains(einNutzer)) {
      // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    }
    // übernimmt Vector:
    benutzerRegister.add(einNutzer);
  }

  public void loeschen(String username) {
    Benutzer b = this.sucheNutzer(username);
    // übernimmt Vector:
    benutzerRegister.remove(b);
  }

  public Benutzer sucheNutzer(String username) {
    for (Benutzer benutzer : benutzerRegister) {
      if (benutzer.getUsername().equals(username)) {
        return benutzer;
      }
    }
    return null;
  }

  public Benutzer login(String username, String passw) {
    Benutzer b = this.sucheNutzer(username);
    if(b.password.equals(passw)){
      this.aktiverNutzer = b;
      return b;
    }
    return null;
  }

  public void logout(){
    this.aktiverNutzer = null;
  }
  
}
