package com.controller;

import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Voiture;


@Controller
@RequestMapping("/admin/exo/voiture")
public class AdminExoVoiture {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/afficher")
    public String afficherLesVoitures(Model model) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            java.sql.Connection conn = dataSource.getConnection();
            Statement stmt=conn.createStatement();  
            rs = stmt.executeQuery("SELECT foo FROM bar");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt.execute("SELECT foo FROM bar")) {
                rs = stmt.getResultSet();
            }

            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return "admin/exoVoiture/index";
    }

}
