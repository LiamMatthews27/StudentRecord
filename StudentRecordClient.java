/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput;



/**
 *
 * @author logan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class StudentRecordClient implements ActionListener {

    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static JFrame frame;
    private static JTextField nameField;
    private static JTextField idField;
    private static JTextField scoreField;
    private static JLabel nameLblField;
    private static JLabel idLblField;
    private static JLabel scoreLblField;
    private static JTextArea recordsTextArea;
    private static JButton addButton;
    private static JButton retrieveButton;
    private static JButton exitButton;
    private static JPanel center, north, south;
    private static Socket socket;

// Connect to the server, create io streams, and call the method that defines the gui
    public StudentRecordClient() throws IOException {
        socket = new Socket("127.0.0.1", 1234);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        createAndShowGUI();
    }//end constructor

//------------------------------------------------------------------------------    
//Create the swing-based gui
    private void createAndShowGUI() {
        frame = new JFrame();
        nameField = new JTextField(30);
        idField = new JTextField(30);
        scoreField = new JTextField(30);
        nameLblField = new JLabel("Name:");
        idLblField = new JLabel("ID:");
        scoreLblField = new JLabel("Score");
        addButton = new JButton("Add Record");
        retrieveButton = new JButton("Retrieve Record");
        exitButton = new JButton("Exit");
        recordsTextArea = new JTextArea(10, 30);
        center = new JPanel();
        south = new JPanel();
        north = new JPanel();

        center.setLayout(new GridLayout(1, 3));
        north.setLayout(new GridLayout(3, 2));

        center.add(addButton);
        center.add(retrieveButton);
        center.add(exitButton);

        north.add(nameLblField);
        north.add(nameField);
        north.add(idLblField);
        north.add(idField);
        north.add(scoreLblField);
        north.add(scoreField);

        south.add(recordsTextArea);

        frame.setLayout(new BorderLayout());

        frame.add(north, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(south, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addButton.addActionListener(this);
        retrieveButton.addActionListener(this);
        exitButton.addActionListener(this);

    }//end createAndShowGUI()

//------------------------------------------------------------------------------    
// In this method, construct a Student object that is initialized with the values entered by the user on the gui.   
// Send the object to the server.
// Clear the textfields and place the cursor in the name textfield    
    private static void addStudentRecord() throws IOException {
        Student stud = new Student(nameField.getText(), idField.getText(), Integer.parseInt(scoreField.getText()));
        out.writeObject(stud);
        out.flush();
        nameField.setText("");
        idField.setText("");
        scoreField.setText("");
        nameField.requestFocus();
    }//end addStudentRecord()

//------------------------------------------------------------------------------    
// In this method, send a string to the server that indicates a retrieve request.
// Read the Arraylist Object sent from the server, and call the method to display the student records.
    private static void retrieveStudentRecords() throws IOException, ClassNotFoundException {
        String msg = "Retrieve";
        out.writeObject(msg);
        out.flush();
        ArrayList arr = (ArrayList) in.readObject();
        displayStudentRecords(arr);
    }//end retrieveStudentRecords()

//------------------------------------------------------------------------------    
// In this method, you must append the records in the arraylist (sent as a parameter) in the textarea.
    private static void displayStudentRecords(List<Student> studentList) {
        for (var i = 0; i < studentList.size(); i++) {
            recordsTextArea.append(studentList.get(i).toString());
        }
    }//end displayStudentRecords()

//------------------------------------------------------------------------------    
// Send a string value to the server indicating an exit request. 
// Read the returning string from the server
// Close all connections and exit the application    
    private static void closeConnection() throws IOException, ClassNotFoundException {
        String msg = "Exit";
        out.writeObject(msg);
        out.flush();
        recordsTextArea.append((String) in.readObject());
        in.close();
        out.close();
        socket.close();
    }//end closeConnection()
//------------------------------------------------------------------------------    
// Handle all action events generated by the user-interaction with the buttons

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton){
            try {
                addStudentRecord();
            } catch (IOException ex) {
                System.out.println("Err:" + ex.getMessage());
            }
        }
        
        if(e.getSource() == retrieveButton){
            try {
                retrieveStudentRecords();
            } catch (IOException ex) {
                System.out.println("Err:" + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Err:" + ex.getMessage());
            }
        }
        
        if(e.getSource() == exitButton){
            try {
                closeConnection();
            } catch (IOException ex) {
                System.out.println("Err:" + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Err:" + ex.getMessage());
            }
        }
        
    }//end actionPerformed
    
    //------------------------------------------------------------------------------    
    // Execute the application by calling the necessary methods   

    public static void main(String[] args) {
        try {
            StudentRecordClient obj = new StudentRecordClient();
        } catch (IOException ex) {
            System.out.println("Err:" + ex.getMessage());
        }
    }//end main
}//end class

