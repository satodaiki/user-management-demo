package com.satodai.demo.login.domain.repository.jdbc;

import com.satodai.demo.login.domain.model.User;
import com.satodai.demo.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int count() throws DataAccessException {
        return 0;
    }

    @Override
    public int insertOne(User user) throws DataAccessException {

        String sql = "";
        sql += "INSERT INTO public.m_user(" +
                "    user_id," +
                "    password," +
                "    user_name," +
                "    birthday," +
                "    age," +
                "    marriage," +
                "    role" +
                ") VALUES (?,?,?,?,?,?,?)";

        int rowNumber = jdbc.update(
                sql,
                user.getUserId(),
                user.getPassword(),
                user.getUserName(),
                user.getBirthday(),
                user.getAge(),
                user.isMarriage(),
                user.getRole()
        );

        return rowNumber;
    }

    @Override
    public User selectOne(String userId) throws DataAccessException {
        return null;
    }

    @Override
    public List<User> selectMany(User user) throws DataAccessException {
        return null;
    }

    @Override
    public int updateOne(User user) throws DataAccessException {
        return 0;
    }

    @Override
    public int deleteOne(String userId) throws DataAccessException {
        return 0;
    }

    @Override
    public void userCsvOut() throws DataAccessException {

    }
}
