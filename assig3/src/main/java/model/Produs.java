package model;

public class Produs {
    private Integer id ;
    private String nume ;
    private Integer cantitate;
    private Integer pret;

    public Produs(Integer id, String nume, Integer cantitate, Integer pret) {
        this.id = id;
        this.nume = nume;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public Produs(String nume, Integer cantitate, Integer pret) {
        this.nume = nume;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public Produs() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }

    /***
     *  Returnează un array de obiecte (de fapt un array de stringuri) care conține valorile atributelor produsului, util pentru popularea rândurilor unui tabel (JTable).
     * !!!! Metoda row() returnează un array de stringuri, chiar dacă ar fi mai adecvat să returneze un array de obiecte (Object[]) deoarece valorile inițiale sunt de tipuri diferite (Integer și String). În acest caz, conversia fiecărui atribut la string este corectă, dar e bine să fim conștienți de acest detaliu.
     * Returnează: Un array de stringuri care conține id, nume, cantitate și pret.
     * @return
     */
    public Object[] row (){
        return new String[] {this.id.toString() , this.nume.toString() , this.cantitate.toString() ,this.pret.toString()} ;
    }
}
