package com.satodai.demo.login.domain.repository.jdbc;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * コールバック処理
 */
public class UserRowCallbackHandler implements RowCallbackHandler {

    @Override
    public void processRow(ResultSet rs) throws SQLException {

        try {
            File file = new File("sample.csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            do {
                String str = rs.getString("user_id") + ","
                        + rs.getString("password") + ","
                        + rs.getString("user_name") + ","
                        + rs.getDate("birthday") + ","
                        + rs.getInt("age") + ","
                        + rs.getBoolean("marriage") + ","
                        + rs.getString("role");

                bw.write(str);
                bw.newLine();
            } while(rs.next());

            bw.flush();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
