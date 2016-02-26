package gisko.milan.ffos.giskoclanskaiskaznica;

/**
 * Created by Milan on 21.2.2016..
 */
public class Clan {

    private String greska;
    private String cl_broj;
    private int sifra;
    private boolean aktivan;
    private String valjanost;
    private String korisnik;
    private String lozinka;


    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getCl_broj() {
        return cl_broj;
    }

    public void setCl_broj(String cl_broj) {
        this.cl_broj = cl_broj;
    }

    public String getGreska() {
        return greska;
    }

    public void setGreska(String greska) {
        this.greska = greska;
    }

    public int getSifra() {
        return sifra;
    }

    public void setSifra(int sifra) {
        this.sifra = sifra;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public String getValjanost() {
        return valjanost;
    }

    public void setValjanost(String valjanost) {
        this.valjanost = valjanost;
    }


    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}
