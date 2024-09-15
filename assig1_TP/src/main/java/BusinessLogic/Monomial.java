package BusinessLogic;
import java.util.* ;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    private double coefficient;
    private int power;
    private String monom; // pt extragere regex

    public Monomial(String monom) { // principal -> se primeste match ul de la regex
        this.monom = monom;
        setCoeficients();
    }

    public Monomial(int power, double coefficient){ //pt sortare (se transorforma din polinom in string )
        this.power = power;
        this.coefficient = coefficient;
    }

    private void setCoeficients(){ //extragerea coeficientilor din string
        List<String> subStrings = extractSubStrings(); // aici stochez
        if(subStrings.isEmpty()){
            coefficient = 0.0;
            power = 0;
        }else {
            coefficient = Double.parseDouble(subStrings.get(0)); // string -> double
            if (subStrings.size() == 2) {
                power = Integer.parseInt(subStrings.get(1));
            } else {
                power = 0;
                if (monom.contains("^")) {
                    power = Integer.parseInt(subStrings.get(0));
                    coefficient = 1;
                } else {
                    if (monom.contains("x")) {
                        power = 1;
                    }
                }
            }
        }
    }

    private List<String> extractSubStrings() {
        List<String> subStrings = new ArrayList<>();
        Pattern pattern = Pattern.compile("[-]?[0-9]*(\\.[0-9]+)?");
        Matcher matcher = pattern.matcher(monom);
        while (matcher.find()) {
            if (!matcher.group().isEmpty()) {
                if (matcher.group().equals("-")) {
                    subStrings.add("-1");
                } else {
                    subStrings.add(matcher.group());
                }
            }
            if (monom.contains("x") && subStrings.isEmpty()) {
                subStrings.add("1");
            }
        }
        return subStrings;// 2x^3
    }

    public double getCoefficient() {
        return coefficient;
    }

    public int getPower() {
        return power;
    }
    @Override
    public String toString() {
        return coefficient + "x^" + power;
    }

}
