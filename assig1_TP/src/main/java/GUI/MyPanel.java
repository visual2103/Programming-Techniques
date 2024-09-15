package GUI;

import javax.swing.*;

public class MyPanel extends JFrame{
    static JTextField textField1 , textField2 ; // fields for input
    static JLabel textArea ; //field for the result (output)
    JButton button1 , button2 , button3 , button4 , button5 , button6 ,button7 ,button8 , button9 ;
    JButton calculate , exit ;
    public MyPanel(){
        setTitle("Polynomial Calculator") ;
        setSize(600 ,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        //text box for first poly
        textField1 = new JTextField() ;
        textField1.setBounds(160, 60, 250, 40);
        add(textField1) ;

        //text box for second poly
        textField2 = new JTextField() ;
        textField2.setBounds(160, 100, 250, 40);
        add(textField2) ;

        //text box for output
        textArea = new JLabel() ;
        textArea.setBounds(165, 150, 400, 30);
        //textArea.setBackground(new Color(204 ,204,255));
        textArea.setText("alina alina alina");
        add(textArea) ;

        JLabel label1 = new JLabel("First Polynomial") ;
        label1.setBounds(30, 60, 250, 40) ;
        add(label1) ;

        JLabel label2 = new JLabel("Second Polynomial") ;
        label2.setBounds(30, 100, 250, 40) ;
        add(label2) ;

        JLabel label3 = new JLabel("Result") ;
        label3.setBounds(35, 150, 400, 30) ;
        add(label3) ;

        ActionHandler actionHandler = new ActionHandler() ;

        button1 = new JButton("+");
        button1.setBounds(130, 200, 100, 60);
        button1.addActionListener(actionHandler);
        add(button1);

        button2 = new JButton("-");
        button2.setBounds(250, 200, 100, 60);
        button2.addActionListener(actionHandler);
        add(button2);

        button3 = new JButton("x");
        button3.setBounds(370, 200, 100, 60);
        button3.addActionListener(actionHandler);
        add(button3);

        button4 = new JButton("/");
        button4.setBounds(130, 270, 100, 60);
        button4.addActionListener(actionHandler);
        add(button4);

        button5 = new JButton("%");
        button5.setBounds(250, 270, 100, 60);
        button5.addActionListener(actionHandler);
        add(button5);

        button6 = new JButton("d/dx P1");
        button6.setBounds(370, 270, 100, 60);
        button6.addActionListener(actionHandler);
        add(button6);

        button7 = new JButton("d/dx P2");
        button7.setBounds(130, 340, 100, 60);
        button7.addActionListener(actionHandler);
        add(button7);

        button8 = new JButton("∫ P1 dx");
        button8.setBounds(250, 340, 100, 60);
        button8.addActionListener(actionHandler);
        add(button8);

        button9 = new JButton("∫ P2 dx");
        button9.setBounds(370, 340, 100, 60);
        button9.addActionListener(actionHandler);
        add(button9);

        calculate = new JButton("Delete");
        calculate.setBounds(200, 410, 200, 40);
        calculate.addActionListener(actionHandler);
        add(calculate);

        setVisible(true);
    }
}
