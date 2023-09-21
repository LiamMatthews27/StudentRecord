/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cputArrayElementDisplayGUI;

/**
 *
 * @author matth
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private JTextField num1Field;
    private JTextField num2Field;
    private JTextField resultField;

    public CalculatorGUI() {
        setTitle("GUI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 400);
        setLayout(new FlowLayout());

        num1Field = new JTextField(10);
        num2Field = new JTextField(10);
        resultField = new JTextField(10);
        resultField.setEditable(false);

        JButton addButton = new JButton("+");
        JButton subtractButton = new JButton("-");
        JButton multiplyButton = new JButton("*");
        JButton divideButton = new JButton("/");

        addButton.addActionListener(new OperationListener("+"));
        subtractButton.addActionListener(new OperationListener("-"));
        multiplyButton.addActionListener(new OperationListener("*"));
        divideButton.addActionListener(new OperationListener("/"));

        add(new JLabel("Number 1:"));
        add(num1Field);
        add(new JLabel("Number 2:"));
        add(num2Field);
        add(addButton);
        add(subtractButton);
        add(multiplyButton);
        add(divideButton);
        add(new JLabel("Result:"));
        add(resultField);

        setVisible(true);
    }

    private class OperationListener implements ActionListener {
        private String operation;

        public OperationListener(String operation) {
            this.operation = operation;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                int num1 = Integer.parseInt(num1Field.getText());
                int num2 = Integer.parseInt(num2Field.getText());
                int result = 0;

                switch (operation) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        result = num1 / num2;
                        break;
                }

                resultField.setText(Integer.toString(result));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid number. Please enter valid integers.");
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, "Divide by zero error. Please enter a non-zero divisor.");
            }
        }
    }

    public static void main(String[] args) {
        new CalculatorGUI();
    }
}

 
