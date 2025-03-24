package com.UniversalHospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointments {

    private Connection connection;

    public Appointments(Connection connection) {
        this.connection = connection;
    }

    public void viewAppointments(){
        try{
            String query =" select id,doctor_id,appointment_date from appointments;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Appointments: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| sr no      | doctor Id          | Appointment date |");
            System.out.println("+------------+--------------------+------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
             int doctor_id = resultSet.getInt("doctor_id");
             String date = resultSet.getString("appointment_date");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, doctor_id, date);
                System.out.println("+------------+--------------------+------------------+");

            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
