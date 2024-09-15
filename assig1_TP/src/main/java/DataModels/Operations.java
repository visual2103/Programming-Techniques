package DataModels;
import BusinessLogic.Polynomial;

import java.util.HashMap;
import java.util.Map;
public class Operations {
    public HashMap<Integer, Double> add(HashMap<Integer, Double> polynom1,HashMap<Integer, Double> polynom2) {
        for(Map.Entry<Integer, Double> i : polynom2.entrySet()){
            if(!polynom1.containsKey(i.getKey())){
                polynom1.put(i.getKey(), i.getValue());
            }else{
                double q1 = Math.round(i.getValue() * 100) / 100.0; //val
                double q2 = Math.round(polynom1.get(i.getKey()) * 100) / 100.0; // gaseste un element cu aceeasi cheie ca primul in al doilea
                if(q1 + q2 != 0){
                    polynom1.put(i.getKey(), q1 + q2);
                }else{
                    polynom1.remove(i.getKey());
                }
            }
        }
        return polynom1;
    }

    public HashMap<Integer, Double> substract(HashMap<Integer, Double> polynom1, HashMap<Integer, Double> polynom2) {
        for(Map.Entry<Integer, Double> i : polynom2.entrySet()){
            if(!polynom1.containsKey(i.getKey())){
                polynom1.put(i.getKey(), -i.getValue());
            }else{
                double q1 = Math.round(i.getValue() * 100) / 100.0;
                double q2 = Math.round(polynom1.get(i.getKey()) * 100) / 100.0;
                if(q2 - q1 != 0){
                    polynom1.put(i.getKey(), q2 - q1);
                }else{
                    polynom1.remove(i.getKey());
                }
            }
        }
        return polynom1;
    }

    public HashMap<Integer, Double> derivate(HashMap<Integer, Double> polynom){
        HashMap<Integer, Double> result = new HashMap<>();
        for(Map.Entry<Integer, Double> i : polynom.entrySet()){
            double q1 = i.getKey() * i.getValue();
            q1 = Math.round(q1 * 100) / 100.0;
            if(i.getKey() != 0){
                result.put(i.getKey() - 1, q1);
            }
        }
        return result;
    }

    public HashMap<Integer, Double> integrate (HashMap<Integer, Double> polynom){
        HashMap<Integer, Double> result = new HashMap<>();
        for(Map.Entry<Integer, Double> i : polynom.entrySet()){
            double q1 = ((double) i.getValue() / (i.getKey() + 1)) ;
            q1 = Math.round(q1 * 100) / 100.0;
            result.put(i.getKey() + 1, q1);
        }
        return result;
    }

    public HashMap<Integer, Double> multiply(HashMap<Integer, Double> polynom1,HashMap<Integer, Double> polynom2) {
        HashMap<Integer, Double> result = new HashMap<>();
        result.put(0, 0.0);
        if (polynom1.equals(result) || polynom2.equals(result)) {
            return result;
        }
        result.clear();
        for (Map.Entry<Integer, Double> i : polynom1.entrySet()) {
            for (Map.Entry<Integer, Double> j : polynom2.entrySet()) {
                if(!result.containsKey(i.getKey() + j.getKey())){
                    result.put(i.getKey() + j.getKey(), i.getValue() * j.getValue());
                }else{
                    double buffer1 = Math.round((i.getValue() * j.getValue()) * 100) / 100.0;
                    double buffer2 = Math.round(result.get(i.getKey() + j.getKey()) * 100) / 100.0;
                    if(buffer1 + buffer2 != 0){
                        result.put(i.getKey() + j.getKey(), result.get(i.getKey() + j.getKey()) + (i.getValue() * j.getValue()));
                    }else{
                        result.remove(i.getKey() + j.getKey());
                    }
                }
            }
        }
        return result;
    }

    public int getMaxPower(HashMap<Integer, Double> polynom1){
        int max = Integer.MIN_VALUE;
        for(Map.Entry<Integer, Double> i : polynom1.entrySet()){
            if(i.getKey() > max){
                max = i.getKey();
            }
        }
        return max;
    }
    public HashMap<Integer, Double> divide(HashMap<Integer, Double> myPolynom, HashMap<Integer, Double> number) {
        HashMap<Integer, Double> result = new HashMap<>();
        result.put(0, 0.00);  // verific numitorul
        if (number.equals(result)) {
            return null;
        }
        result.clear();
        while (getMaxPower(myPolynom) >= getMaxPower(number)) { //p1 vs p2
            int max1 = getMaxPower(myPolynom);
            int max2 = getMaxPower(number);
            result.put(max1 - max2, myPolynom.get(max1) / number.get(max2));
            HashMap<Integer, Double> aux1 = new HashMap<>();
            aux1.put(max1 - max2, myPolynom.get(max1) / number.get(max2));
            HashMap<Integer, Double> aux2 = multiply(number, aux1);
            myPolynom = substract(myPolynom, aux2);
        }
        return result;
    }

    public HashMap<Integer, Double> modulo(HashMap<Integer, Double> myPolynom, HashMap<Integer, Double> number) {
        HashMap<Integer, Double> result = new HashMap<>();
        result.put(0, 0.00);
        if (number.equals(result)) {
            return myPolynom;
        }
        result.clear();
        while (getMaxPower(myPolynom) >= getMaxPower(number)) {
            int max1 = getMaxPower(myPolynom);
            int max2 = getMaxPower(number);
            result.put(max1 - max2, myPolynom.get(max1) / number.get(max2));
            HashMap<Integer, Double> aux1 = new HashMap<>();
            aux1.put(max1 - max2, myPolynom.get(max1) / number.get(max2));
            HashMap<Integer, Double> aux2 = multiply(number, aux1);
            myPolynom = substract(myPolynom, aux2);
        }
        return myPolynom; // returnez ce o ramas de la impartire
    }

}
