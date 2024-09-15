package Operations;
import DataModels.Operations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import BusinessLogic.Monomial;
import BusinessLogic.Polynomial;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.assertEquals;
public class OperationsTest {
    Polynomial polynom1;
    Polynomial polynom2;
    HashMap<Integer, Double> expected;
    Operations operations = new Operations();
    public void buffer(){
        Monomial monom1 = new Monomial("2x");
        Monomial monom2 = new Monomial("x");
        List<Monomial> monomList1 = new ArrayList<>();
        List<Monomial> monomList2 = new ArrayList<>();
        monomList1.add(monom1);
        monomList2.add(monom2);

        System.out.println(monomList1);

        polynom1 = new Polynomial(monomList1);
        polynom2 = new Polynomial(monomList2);

        expected = new HashMap<>();
        polynom1.monomialToPolynomial();
        polynom2.monomialToPolynomial();
    }
    @Test
    public void addTest(){ // 3x
        buffer(); // init
        expected = new HashMap<>();
        expected.put(1,3.0);
        HashMap<Integer, Double> result = operations.add(polynom1.getPolynom(), polynom2.getPolynom());
        assertEquals(result, expected);
    }

    @Test
    public void subTest(){ // 1x
        buffer(); // init
        expected = new HashMap<>();
        expected.put(1,1.0);
        HashMap<Integer, Double> result = operations.substract(polynom1.getPolynom(), polynom2.getPolynom());
        assertEquals(result, expected);
    }

    @Test
    public void derivativeTest(){ // 3x
        buffer(); // init
        expected = new HashMap<>();
        expected.put(0,2.0);
        HashMap<Integer, Double> result = operations.derivate(polynom1.getPolynom());
        assertEquals(result, expected);
    }

    @Test
    public void integrateTest(){ // 3x
        buffer(); // init
        expected = new HashMap<>();
        expected.put(2,1.0);
        HashMap<Integer, Double> result = operations.integrate(polynom1.getPolynom());
        assertEquals(result, expected);
    }

    @Test
    public void multiplyTest(){ // 3x
        buffer(); // init
        expected = new HashMap<>();
        expected.put(2,2.0);
        HashMap<Integer, Double> result = operations.multiply(polynom1.getPolynom(), polynom2.getPolynom());
        assertEquals(result, expected);
    }

    @Test
    public void divideTest(){ // 3x
        buffer(); // init
        expected = new HashMap<>();
        expected.put(0,2.0);
        HashMap<Integer, Double> result = operations.divide(polynom1.getPolynom(), polynom2.getPolynom());
        assertEquals(result, expected);
    }

    @Test
    public void moduloTest(){ // 3x
        buffer(); // init
        expected = new HashMap<>();
        HashMap<Integer, Double> result = operations.modulo(polynom1.getPolynom(), polynom2.getPolynom());
        assertEquals(result, expected);
    }

}
