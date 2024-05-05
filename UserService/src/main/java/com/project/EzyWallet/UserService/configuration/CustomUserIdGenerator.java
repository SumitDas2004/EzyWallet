package com.project.EzyWallet.UserService.configuration;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.sql.*;

public class CustomUserIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "U";
        int base=100000;
        try {
            Connection con = session.getJdbcConnectionAccess().obtainConnection();
            Statement statement = con.createStatement();
            ResultSet rows = statement.executeQuery("SELECT COUNT(id) FROM user");
            if(rows.next()){
                int cnt = rows.getInt(1);
                return prefix+(cnt+base);
            }
            return prefix+base;
        } catch (SQLException e) {
            throw new RuntimeException("User id generation failed.");
        }
    }
}
