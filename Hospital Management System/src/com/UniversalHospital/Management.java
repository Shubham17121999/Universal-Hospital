package com.UniversalHospital;

import java.sql.*;
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
            Appointments appointments = new Appointments(connection);

            while(true){
                System.out.println("Welcome to the Universal Hospital!!!");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. View Appointments");
                System.out.println("6. Exit");
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
                        bookAppointment(doctor,patient,connection,scanner);
                        break;
                    case 5 :
                        appointments.viewAppointments();
                        break;
                    case 6 :

                        System.out.println("Thank you for using service....");
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
          if(checkDoctorAvailability(doctorId,date,connection)) {
              String query = "insert into appointments(patient_id,doctor_id,appointment_date) values (?,?,?)";
              try {
                  PreparedStatement preparedStatement = connection.prepareStatement(query);
                  preparedStatement.setInt(1, patientId);
                  preparedStatement.setInt(2, doctorId);
                  preparedStatement.setString(3, date);
                  int rowAffected = preparedStatement.executeUpdate();
                  if (rowAffected > 0) {
                      System.out.println("Your appointment is booked");
                  } else {
                      System.out.println("Failed to book appointment . try again later...");
                  }
              } catch (SQLException ex) {
                  ex.printStackTrace();

              }
          } else {
              System.out.println("Doctor is not available on these date.");
          }
      }else{
          System.out.println("Patient and Doctor with these id does not exist.");
      }



}

public static  boolean checkDoctorAvailability(int doctorId, String date, Connection connection){
        String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try{
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setInt(1,doctorId);
         preparedStatement.setString(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                   return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
}

}


