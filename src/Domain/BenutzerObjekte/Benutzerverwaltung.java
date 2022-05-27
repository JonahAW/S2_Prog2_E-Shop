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

public class Benutzerverwaltung {

  // Verwaltung der Nutzer in einer verketteten Liste
  private List<Benutzer> benutzerRegister;
  // private List<Benutzer, > aktiveNutzer;

  public Benutzerverwaltung() {
    benutzerRegister = new Vector<Benutzer>();
  }

  public void registrieren(String name, String username, String password, String email, String adress) {
    Benutzer einNutzer = new Kunde(name, username, encryptString(password), email, adress);
    // throw new NutzerExistiertBereitsException(einNutzer, " - in 'einfuegen()'");
    // übernimmt Vector:
    this.benutzerRegister.add(einNutzer);
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

  // #region security
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

  // #endregion
  // #region active user management
  public enum AktiverNutzerType {
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
  public AktiverNutzerType login(String username, String passw) {
    Benutzer benutzer = this.sucheNutzer(username);
    // no user found or not matching password
    if (benutzer == null || !(Arrays.equals(benutzer.getPassword(), encryptString(passw)))) {
      return AktiverNutzerType.NONE;
    } else {
      //////////// if user is found ///////////

      // generate user identifier
      byte[] UserHash = generateUserHash(benutzer);
      // give it to the UserInterface used
      setUserHash(UserHash);
      // add user to list of active users
      addAtiverNutzer(UserHash, benutzer);

      // return type of user
      if (benutzer instanceof Mitarbeiter) {
        return AktiverNutzerType.MITARBEITER;
      }
      if (benutzer instanceof Kunde) {
        return AktiverNutzerType.KUNDE;
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
    /*
     * TODO for online, define what user to logout
     * at the moment the user logged out gets defined by the Hash set in the
     * UserInterface
     * This is always the currently logged in user locally
     * but when the server runs its own eshop instance this wont work
     */
    // get active user hash and remove him from list of active users
    byte[] userHash = getUserHash();
    removeAtiverNutzer(userHash);
    // create empy array
    byte arr[] = {};
    // set is as the user hash
    setUserHash(arr);
  }

  // active user management
  private void addAtiverNutzer(byte[] userHash, Benutzer user) {
    ActiverNutzerListe.put(userHash, user);
  }

  private void removeAtiverNutzer(byte[] userHash) {
    ActiverNutzerListe.remove(userHash);
  }

  // #endregion
}
