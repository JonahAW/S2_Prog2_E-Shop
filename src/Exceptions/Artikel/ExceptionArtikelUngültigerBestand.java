package Exceptions.Artikel;

import Domain.Artikel.Artikel;

public class ExceptionArtikelUng├╝ltigerBestand extends ExceptionArtikel {

  public ExceptionArtikelUng├╝ltigerBestand(Artikel artikel) {
    super(artikel, "Bestand des Artikels ist ung├╝ltig");
  }

}
