package com.vmware.talentboost.imageclassificationservice.data;

import java.io.Serializable;
import java.sql.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

public class CustomGenerator implements IdentifierGenerator {

    //You can give any name to sequence be sure that you know how to use it.
    private final String DEFAULT_SEQUENCE_NAME = "hibernate_sequence";
    //private final String DEFAULT_SEQUENCE_NAME = "hib_sequence";

    /*
     * This method will generate custom id based on String followed by id
     * e.g. emp0001, emp0002, emp0003 and so on..
     * */

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        //String prefix = "IMAGE";
        //String suffix = "";
        Integer id = 0;
        try {
            Connection connection = session.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(id) from images");
            if(resultSet.next()) {
                id = resultSet.getInt(1) + 1;
                //suffix = id.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
