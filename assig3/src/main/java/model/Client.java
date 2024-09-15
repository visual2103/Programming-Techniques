package model;

public class Client {
    Integer id;
    String nume;
    String email ;
    Integer varsta ;
    public Client() {
    }

    public Client(String nume , String email , Integer varsta) {
        this.nume = nume;
        this.email=email;
        this.varsta=varsta;
    }

    public Client(Integer id, String nume, String email, Integer varsta) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.varsta = varsta;
    }

    // Getter pentru id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter pentru nume
    public String getNume() {
        return nume;
    }

    // Setter pentru nume
    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                ", varsta=" + varsta +
                '}';
    }

    public Object[] row (){
        return new String[] {this.id.toString() , this.nume.toString() , this.email.toString() ,this.varsta.toString()} ;
    }
}
