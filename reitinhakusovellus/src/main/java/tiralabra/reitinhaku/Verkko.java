package tiralabra.reitinhaku;

import java.util.ArrayList;

/**
 * Kartasta verkon muodostava luokka.
 */

public class Verkko {
    private String nimi;
    private int koko;
    private Solmu[][] solmut;
    private ArrayList<String> reitti;

    /**
     * Verkko-olion konstruktori.
     * 
     * Luo tyhjän matriisitaulukon kartan tietoja varten sekä 
     * listan reitin tietojen tallennukseen tulostusta varten.
     * 
     * @param koko Kartan sivun pituus, joka tarvitaan matriisitaulukon luontiin.
     * @param nimi Verkon nimi helpottamaan kartan hakua session aikana. Huom! Ei ole tällä hetkellä uniikki.
     */

    public Verkko(int koko, String nimi) {
        this.koko = koko;
        this.nimi = nimi;
        this.solmut = new Solmu[koko][koko];
        this.reitti = new ArrayList<String>();
    }

    /**
     * Metodi luo uuden solmun ja lisää sen verkkoon.
     * 
     * @param x          Solmun x-koordinaatin arvo.
     * @param y          Solmun y-koordinaatin arvo.
     * @param seina      Kartan kohta jota ei voi käyttää osana reittiä.
     * @param kuljettava Kartan kohta joka sopii osaksi reittiä.
     */

    public void lisaaSolmu(int x, int y, boolean seina, boolean kuljettava) {
        this.solmut[x][y] = new Solmu(x, y, seina, kuljettava);
    }

    /**
     * Metodi kertoo verkon solmujen määrä.
     * 
     * @return int Palauttaa verkossa olevien solmujen määrän.
     */
    public int solmujenMaara() {
        return this.koko * this.koko;
    }

    /**
     * Metodi kertoo verkon nimen, joka muodostettu kartan ja algoritmin nimestä.
     * @return String Palauttaa verkon nimen.
     */
    public String getNimi() {
        return this.nimi;
    }

    /**
     * Metodi kertoo verkon pohjana olevan neliömuotoisen kartan sivun leveyden.
     * @return int Palauttaa verkon koon.
     */
    public int getKoko() {
        return this.koko;
    }

    /** Metodi palauttaa matriisimuotoisen kartan, joka säilötty Verkko-olioon.
     * @return Solmu[][] Palauttaa verkon solmut matriisitaulukkona.
     */
    public Solmu[][] getSolmut() {
        return this.solmut;
    }

    /** Metodi palauttaa tulostukseen sopivan kuvauksen reitillä olevista solmuista.
     * @return ArrayList Palauttaa tekstimuotoisen kuvauksen reitistä.
     */
    public ArrayList<String> getReitilla() {
        return reitti;
    }

    /** Metodi tallentaa reitillä olevasta solmusta kuvauksen tulostusta varten.
     * @param reitilla Lisää reittikuvaukseen tiedon solmusta ja sen etäisyydestä reitin alkuun.
     */
    public void lisaaReitti(String reitilla) {
        this.reitti.add(reitilla);
    }

}
