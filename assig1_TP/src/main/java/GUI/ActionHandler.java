package GUI;

import BusinessLogic.Monomial;
import BusinessLogic.Polynomial;
import DataModels.ExtractFromStrings;
import DataModels.Operations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource() ;
        if (sourceButton instanceof JButton){
            List<String> alina;
            ExtractFromStrings q = new ExtractFromStrings(MyPanel.textField1.getText());
            alina = q.extractMonoms();
            List<Monomial> monomialList = new ArrayList<>();
            for(String i : alina){
                monomialList.add(new Monomial(i));
            }
            Polynomial p1 = new Polynomial(monomialList);
            p1.monomialToPolynomial();

            //al doilea polynom
            List<String> alina2;
            ExtractFromStrings q1 = new ExtractFromStrings(MyPanel.textField2.getText());
            alina2 = q1.extractMonoms();
            List<Monomial> monomialList2 = new ArrayList<>();
            for(String i : alina2){
                monomialList2.add(new Monomial(i));
            }
            Polynomial p2 = new Polynomial(monomialList2);
            p2.monomialToPolynomial();

//            MyPanel.textArea.setText(p2.toString(p2.getPolynom()));
            HashMap<Integer, Double> result = new HashMap<>();
            Operations operations = new Operations();
            JButton button = (JButton) sourceButton ;
            switch (button.getText()) {
                case "+":
                    result = operations.add(p1.getPolynom(), p2.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "-":
                    result = operations.substract(p1.getPolynom(), p2.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "x":
                    result = operations.multiply(p1.getPolynom(), p2.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "/":
                    result = operations.divide(p1.getPolynom(), p2.getPolynom());
                    if(result != null) {
                        MyPanel.textArea.setText(p1.toString(result));
                    }else{
                        MyPanel.textArea.setText("Division by 0 is not allowed");
                    }
                    break;
                case "%":
                    result = operations.modulo(p1.getPolynom(), p2.getPolynom());
                    if(!result.isEmpty()) {
                        MyPanel.textArea.setText(p1.toString(result));
                    }else{
                        MyPanel.textArea.setText("0");
                    }
                    break;
                case "d/dx P1":
                    result = operations.derivate(p1.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "d/dx P2":
                    result = operations.derivate(p2.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "∫ P1 dx":
                    result = operations.integrate(p1.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "∫ P2 dx":
                    result = operations.integrate(p2.getPolynom());
                    MyPanel.textArea.setText(p1.toString(result));
                    break;
                case "Delete":
                    MyPanel.textField1.setText("");
                    MyPanel.textField2.setText("");
                    break;
            }

        }
    }
}
