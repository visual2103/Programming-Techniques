package model;

public class Comanda {
    private Integer id ;
    private String nume ;
    private Integer cantitate_totala;

    public Comanda(String nume, Integer cantitate_totala) {
        this.nume = nume;
        this.cantitate_totala = cantitate_totala;
    }

    public Comanda(Integer id, String nume, Integer cantitate_totala) {
        this.id = id;
        this.nume = nume;
        this.cantitate_totala = cantitate_totala;
    }



    public Comanda() {
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

    public Integer getCantitate_totala() {
        return cantitate_totala;
    }

    public void setCantitate_totala(Integer cantitate_totala) {
        this.cantitate_totala = cantitate_totala;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", cantitate_totala=" + cantitate_totala +
                '}';
    }
    public Object[] row (){
        return new String[] {this.id.toString() , this.nume.toString() , this.cantitate_totala.toString()} ;
    }
}
