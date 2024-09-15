package BusinessLogic;
import java.util.*;
public class Polynomial {
    private HashMap<Integer, Double> polynom = new HashMap<>();
    public List<Monomial> monomialList;

    public Polynomial(List<Monomial> monomialList) {
        this.monomialList = monomialList;
        //monomialToPolynomial();
    }

    public void monomialToPolynomial() {
        for (Monomial k : monomialList) {
            double aux1 = k.getCoefficient();
            aux1 = Math.round(aux1 * 100) / 100.0;
            int aux2 = k.getPower();
            if (polynom.containsKey(aux2)) {
                if (polynom.get(aux2) + aux1 != 0.0) {
                    polynom.put(aux2, polynom.get(aux2) + aux1);
                } else {
                    polynom.remove(aux2);
                }
            } else {
                polynom.put(aux2, aux1);
            }
        }
        System.out.println(polynom);
    }

    public HashMap<Integer, Double> getPolynom() {
        return polynom;
    }

    public Monomial[] hashToArray(HashMap<Integer, Double> hashMap) { //pt afisare si sortare
        Monomial[] array = new Monomial[hashMap.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> k : hashMap.entrySet()) {
            array[i] = new Monomial(k.getKey(), k.getValue());
            i++;
        }
        Arrays.sort(array, new SortMonoms());
        return array; // input pt toString
    }


    public String toString(HashMap<Integer, Double> polynom) {
        String s = new String("");
        Monomial[] array = hashToArray(polynom); // ascending order
        for(Monomial i : array){
            if(i.getCoefficient() < 0.0){
                if(i.getCoefficient() != -1.00){
                    s += i.getCoefficient();
                }
            }
            else{
                if(i.getCoefficient() > 0.0){
                    s += "+";
                    if(i.getCoefficient() != 1){
                        s += i.getCoefficient();
                    }
                }
            }
            if(i.getPower() == 0){
                if(i.getCoefficient() == 1 || i.getCoefficient() == -1){
                    s += i.getCoefficient();
                }
            }
            else{
                if(i.getCoefficient() == -1){
                    s += "-";
                }
                s += "x";
                if(i.getPower() != 1){
                    s += ("^" + i.getPower());
                }
            }
        }
        return s;
    }
}
class SortMonoms implements Comparator<Monomial>{
    public int compare(Monomial x, Monomial y){
        return x.getPower() - y.getPower();
    }
} // sortare
