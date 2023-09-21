/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package  za.ac.cput;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRecordServer {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static Object receivedObject;

//In the constructor listen for incoming client connections    
public StudentRecordServer(){
int port = 9009;

try{
    serverSocket = new ServerSocket (port);
    System.out.println("Server is listening  on port" + port);
    
    
    clientSocket = serverSocket.accept();
    System.out.println("Client Connnected " + clientSocket.getInetAddress().getHostAddress());
    
}
catch (IOException ioe  ) {
    ioe.printStackTrace();
} 
}
//------------------------------------------------------------------------------    
    
//create the io streams
public void getStreams() {
        try {
            in = new  ObjectInputStream (clientSocket.getInputStream());
            out = new ObjectOutputStream (clientSocket.getOutputStream());
            out.flush();
        } //end getStreams()
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
            
        }



//------------------------------------------------------------------------------    
    
//Declare arraylist and handle the communication between server and client    
 public void processClient() {
     ArrayList<Student>studentRecords = new ArrayList();
     while (true){
         try{
             receivedObject = in.readObject();
             if(receivedObject instanceof Student){
                 
                 Student newStudent = (Student)receivedObject; 
                 studentRecords.add(newStudent);
                 System.out.println("Student recorded added " + newStudent) ;
            }
             else if (receivedObject instanceof String && ((String) receivedObject).equals("Retrieved")) {
             ArrayList lstStudents = (ArrayList)studentRecords.clone();
             out.writeObject(lstStudents);
             out.flush();
                 System.out.println("list sent to client "  +  studentRecords);
              }   
                 else if (receivedObject instanceof String && ((String) receivedObject).equals("Exit")){
                 closeConnection();
                        }   
         } catch(ClassNotFoundException cnfe){
             cnfe.printStackTrace();
         }
         catch(IOException ioe){
                     ioe.printStackTrace();
         }
     
     }
 }
   // }//end processClient

//------------------------------------------------------------------------------    

//close all connections to the server and exit the application   
    private static void closeConnection() {
        try {
            out.writeObject("Exit");
            out.flush();
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Server closing connection");
            System.exit(0);
        } //end closeConnection()
        catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }

//------------------------------------------------------------------------------    

//execute the program and call all necessary methods
    public static void main(String[] args) {
    StudentRecordServer srs = new StudentRecordServer ();
    srs.getStreams();
    srs.processClient();
  
    
   }//end main

}


