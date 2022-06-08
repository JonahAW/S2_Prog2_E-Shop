package Domain.BenutzerObjekte;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import UserInterface.UserInterface;
import persistence.FilePersistenceManager;
import persistence.PersistenceManager;

public class Benutzerverwaltung {

  // Verwaltung der Nutzer in einer verketteten Liste
  private Vector<Benutzer> benutzerRegister;
  private PersistenceManager pm = new FilePersistenceManager();

  public Benutzerverwaltung() {
    benutzerRegister = new Vector<Benutzer>();
  }

  public void registrieren(String name, String username, String password, String email, String adress) {
    Benutzer einNutzer = new Kunde(name, username, encryptString(password), email, adress);
    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
    System.out.println(""+Kunde.kundenNrZähler);
  }

  public void registrieren(String name, String username, String password) {
    Benutzer einNutzer = new Mitarbeiter(name, username, encryptString(password));

    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
  }

  void loeschen(String username) {
    Benutzer benutzer = this.sucheNutzer(username);
    // übernimmt Vector:
    benutzerRegister.remove(benutzer);
  }

  public Benutzer sucheNutzer(String username) {
    for (Benutzer benutzer : benutzerRegister) {
      if (benutzer.getUsername().equals(username)) {
        return benutzer;
      }
    }
    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // #region security
  /*
   * TODO for online, define what user to logout
   * at the moment the user logged out gets defined by the Hash set in the
   * UserInterface
   * This is always the currently logged in user locally
   * but when the server runs its own eshop instance this wont work
   */
  /**
   * encrypted einen sting to SHA-1
   * SHA-1 is a hash type
   * A hash is a generated value that is unique to the input
   * 
   * @author github & Malte
   * @return encrypted bytes in an array: byte[]
   */
  private static byte[] encryptString(String string) {
    byte[] sha1 = null;
    try {
      // findet ein MessageDigest obj
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      // Cleared vorhandene daten im obj
      crypt.reset();
      // convertiert tring zu bytes und fügt die daten dem obj hinzu
      crypt.update(string.getBytes("UTF-8"));
      // encoded die daten und gibt sie in variable
      sha1 = crypt.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return sha1;
  }

  /**
   * sets user identifier in UserInterface used
   * 
   * @param byteArr Hash Value generated by generateUserHash, is a byte array
   */
  private void setUserHash(byte[] byteArr) {
    UserInterface.userHash = byteArr;
  }

  /**
   * sets user identifier in UserInterface used
   * 
   * @param byteArr Hash Value generated by generateUserHash, is a byte array
   */
  private void resetUserHash() {
    // create empy array
    byte arr[] = {};
    // set is as the user hash
    setUserHash(arr);
  }

  /**
   * gets the user identifier from the UserInterface used
   * 
   * @param byteArr
   */
  private byte[] getUserHash() {
    return UserInterface.userHash;
  }

  /**
   * Generates a unique hash for the user online instance
   * 
   * @param user user to generate a hash for/from
   * @return encrypted bytes in an array: byte[]
   */
  private byte[] generateUserHash(Benutzer user) {
    // hash
    return encryptString(user.toString() + System.currentTimeMillis());
  }

  // #endregion//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // #region active user management
  public enum AktiverBeutzerType {
    MITARBEITER,
    KUNDE,
    NONE;
  }

  private Map<byte[], Benutzer> ActiverNutzerListe = new HashMap<byte[], Benutzer>();

  /**
   * logt den user ein: Generiert User Identifier, fügt ihn zur aktiven liste
   * hinzu
   * 
   * @param username
   * @param passw
   * @return
   */
  public AktiverBeutzerType login(String username, String passw) {
    Benutzer benutzer = this.sucheNutzer(username);
    // no user found or not matching password
    if (benutzer == null || !(Arrays.equals(benutzer.getPassword(), encryptString(passw)))) {
      return AktiverBeutzerType.NONE;
    } else {
      //////////// if user is found ///////////

      // generate user identifier
      byte[] userHash = generateUserHash(benutzer);
      // give it to the UserInterface used
      setUserHash(userHash);
      // add user to list of active users
      addAtiverBenutzer(userHash, benutzer);

      // return type of user
      if (benutzer instanceof Mitarbeiter) {
        return AktiverBeutzerType.MITARBEITER;
      }
      if (benutzer instanceof Kunde) {
        return AktiverBeutzerType.KUNDE;
      }
    }
    return null;
  }

  /**
   * log the user out:
   * overwrite userhash
   * removes from active user list
   */
  public void logout() {
    // get active user hash and remove him from list of active users
    byte[] userHash = getUserHash();
    removeAtiverBenutzer(userHash);

    // resets userHash to default state
    resetUserHash();
  }

  // active user management
  /**
   * add user to active user list
   * 
   * @param userHash
   * @param user
   */
  private void addAtiverBenutzer(byte[] userHash, Benutzer user) {
    ActiverNutzerListe.put(userHash, user);
  }

  /**
   * remove user from active user list
   * 
   * @param userHash
   */
  private void removeAtiverBenutzer(byte[] userHash) {
    ActiverNutzerListe.remove(userHash);
  }

  /**
   * checkt ob benutzer online ist
   * 
   * @param userHash
   * @return boolen ob benutzer online ist
   */
  public boolean istBenutzerAktiv(byte[] userHash) {
    return ActiverNutzerListe.containsKey(userHash);
  }

  /**
   * returnt das Benutzer Objekt assoziert mit dem userHash
   * 
   * @param userHash
   * @return
   */
  public Benutzer getAktiverBenutzer(byte[] userHash) {
    return ActiverNutzerListe.get(userHash);
  }

  /*public void schreibeDaten(String kundenDatei, String mitarbeiterDatei) throws IOException  {
		// PersistenzManager für Schreibvorgänge in Kunde.txt öffnen
		pm.openForWriting(kundenDatei);
    //Liste durch iterieren, wenn nutzer = Kunde --> in Kunden Datei speichern 
		for (Benutzer kunde : this.benutzerRegister){
				if(kunde instanceof Kunde) {pm.speichereKunde(kunde);}
    //Persistenz schließen
    pm.close();
    // PersistenzManager für Schreibvorgänge in Mitarbeiter.txt öffnen
    pm.openForWriting(mitarbeiterDatei);
    //Liste durch iterieren, 
    for (Benutzer mitarbeiter : this.benutzerRegister){
        if(mitarbeiter instanceof Kunde) {pm.speichereMitarbeiter(mitarbeiter);}
        
			}
    pm.close();
	}

  public void liesDaten(String kundenDatei, String MitarbeiterDatei) throws IOException {
		// PersistenzManager für Lesevorgänge öffnen
		pm.openForReading(kundenDatei);
		Kunde kunde;
		while ((kunde = pm.ladeKunde()) != null) {
				benutzerRegister.add(kunde);
    }
		// Persistenz-Schnittstelle wieder schließen
		pm.close();

    pm.openForReading(String MitarbeiterDatei);
		Mitarbeiter mitarbeiter;
		while ((mitarbeiter = pm.ladeMitarbeiter()) != null) {
				benutzerRegister.add(mitarbeiter);
    }
	}*/

  public void save(String nutzerDoc){
    pm.saveNutzer(nutzerDoc, benutzerRegister);
  }

  public void load(String nutzerDoc){
   benutzerRegister =  pm.loadNutzer(nutzerDoc);
   int k = 0;
   int m = 0;
   //zählt Kunden im vektor und setzt statisches Attribut kundenNrzähler 
   for(Benutzer b : benutzerRegister){
     if(b instanceof Kunde){k++;}
     if(b instanceof Mitarbeiter){m++;}
    }
   Kunde.kundenNrZähler = k;
   Mitarbeiter.zähler = m;
  // #endregion
  }
}

