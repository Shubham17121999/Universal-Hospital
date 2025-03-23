package com.UniversalHospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Management {

    private  static  final String url ="jdbc:mysql://localhost:3306/hospital";
    private  static  final String username = "root";
    private static final String password ="root";

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);

            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor( connection,scanner);

            while(true){
                System.out.println("Welcome to the Universal Hospital!!!");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: " );
                int choice = scanner.nextInt();

                switch (choice){

                    case 1 :
                        patient.addPatient();
                        break;
                    case 2 :
                        patient.viewPatients();
                        break;
                    case 3 :
                        doctor.viewDoctors();
                        break;
                    case 4 :
                        break;
                    case 5 :
                        return;
                    default:
                        System.out.println("Enter valid choice!!");


                }


            }


        }catch (SQLException ex){
            ex.printStackTrace();

        }

    }




    public  static void bookAppointment(Doctor doctor,Patient patient,Connection connection , Scanner scanner){
    System.out.println("Enter the patient Id: ");
    int patientId = scanner.nextInt();
        System.out.println("Enter the doctor Id: ");
     int doctorId = scanner.nextInt();
        System.out.println("Enter the appointment date (yyyy-mm-dd): ");
        String date = scanner.next();
      if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
          String query = "insert into appointments ";
      }else{
          System.out.println("Patient and Doctor with these id does not exist.");
      }



}



}


