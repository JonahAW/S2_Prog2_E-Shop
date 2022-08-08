package Domain.BenutzerObjekte;

import java.io.Serializable;
import common.EshopInterface.BeutzerType;

public abstract class Benutzer implements Serializable {

  // Klassenvariablen
  private String username;
  private byte[] password;
  private String name;
  private BeutzerType benutzerType;

  Benutzer(BeutzerType benutzerType, String name, String username, byte[] password) {
    this.benutzerType = benutzerType;
    this.name = name;
    this.username = username;
    this.password = password;
  }

  // #region getter
  /** kunden nummer */
  public abstract int getKundenNr();

  protected BeutzerType getType() {
    return benutzerType;
  }

  protected String getUsername() {
    return username;
  }

  protected byte[] getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  // #endregion
  // #region setter
  protected void setUsername(String name) {
    this.name = name;
  }

  protected void setName(String username) {
    this.username = name;
  }

  // #endregion

  protected boolean changePassword(String oldPassword, String newPassword) {
    // TODO complete passwort ändern
    // if (oldPassword.compareTo(this.password) == 0) {
    // this.password = newPassword;
    // return true;
    // } else {
    // return false;
    // }
    return false;
  }

  @Override
  public String toString() {
    return "\t" + this.name + "\t" + this.username;
  }
}
