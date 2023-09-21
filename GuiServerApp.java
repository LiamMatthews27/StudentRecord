/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Radford
 */

public class GuiServerApp extends JFrame implements ActionListener
{
    private ServerSocket listener;
    private String msg = "";
    private String upCaseMsg = "";
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JButton exitBtn = new JButton("EXIT");
    private JTextArea clientTxtArea = new JTextArea(5,40);
    private String response = "";
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
  
    // Client connection
    private Socket client;
    
    //----------------------------------------------------------------------------------    
    // Define a constructor in which you construct a ServerSocket object and set up the GUI
    public GuiServerApp()
    {
        try{
            listener = new ServerSocket(12346,10);
        }
        catch(IOException ioe){
            System.out.println("IO Exception" + ioe.getMessage());
        }
        setLayout(new BorderLayout());
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JButton("exit"));
        
        centerPanel.setLayout(new FlowLayout() );
        centerPanel.add(new JTextArea ());
        centerPanel.add(clientTxtArea);
        
        exitBtn.addActionListener(this);
        exitBtn.setVisible(false);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        setVisible(true);
        setSize(450,450);
    }
    
    // Declare a method to listen for client connections
    private void listenForClients() throws IOException
    {
        client = listener.accept();
        System.out.println("server started");
        clientTxtArea.setText(client.getInetAddress()+ " has connected \n");
    }

    // Declare a method to initiate communication streams
    private void getStreams() 
    {
      try{
          out =  new ObjectOutputStream(client.getOutputStream());
      
        out.flush();
        in = new ObjectInputStream(client.getInputStream()); 
      }catch(IOException ioe){
         ioe.printStackTrace();
      }
    }

    // Declare a method in which you write data to the client    
    private void sendData(String myMsg)
    {
        try {
            out.writeObject(myMsg);
            out.flush();
        }
        catch (IOException ioe) {
            clientTxtArea.append("IO Exception in client sendData" + ioe.getMessage());
        }
    }
    
    // Declare a method in which you close the streams and the socket connection    
    public void closeConnection ()
    {
        try {
            out.writeObject ("Exit");
            
            out.close();        
            in.close();
            listener.close();
            System.exit(0);
        }
        catch(IOException ioe){
            System.out.println("IO Exception closing " +ioe.getMessage());
        }
    }
   
    // Declare a method in which you continuously read from the client; process the incoming data; and write results back to the client.    
    public void processClient() throws ClassNotFoundException
    {
     
        
    try {
        do {
            msg = (String)in.readObject();
            sendData(msg.toUpperCase()) ;
            clientTxtArea.append("FROM Client>> " + msg + "\n");
        } while (!msg.equalsIgnoreCase("TERMINATE"));
    } catch (IOException ioe) {
        System.out.println("IO Exception found " + ioe.getMessage());
    } catch (ClassNotFoundException cnfe) {
        System.out.println("Class Exception Not Found" + cnfe.getMessage());
    }
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitBtn) {
            closeConnection();
            System.exit(0);
        }
    }

    public static void main(String[] args){
        GuiServerApp obj = null;
    try{    
      obj = new GuiServerApp();
      obj.listenForClients();
      obj.getStreams();
      obj.processClient();
    } catch(IOException ioe){
        ioe.printStackTrace();
    }   catch (ClassNotFoundException ex) {
           ex.printStackTrace();
        }finally {
        if (obj != null){
          obj.closeConnection();
    }
    }
    }
}
