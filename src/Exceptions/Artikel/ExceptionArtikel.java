package Exceptions.Artikel;

import Domain.Artikel.Artikel;

public class ExceptionArtikel extends Exception {
  public Artikel artikel = null;

  public ExceptionArtikel(Artikel artikel, String message) {
    super(message);
    this.artikel = artikel;
  }
}