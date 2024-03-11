package com.example.examenfinaldesarrollointerfaces;
import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    //Logger para trazar la aplicacion, estatico para llamar siempre al mismo logger


    /**
     * -- GETTER --
     *  Metodo para devolver la conexion
     *
     * @return
     */
    @Getter
    private static final Connection connection;

    static{

        try {

            //Con el driver pasamos la conexion a la variable
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/db", "root", "");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}


