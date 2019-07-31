package com.satodai.demo.login.domain.service.jdbc;

import com.satodai.demo.login.domain.model.User;
import com.satodai.demo.login.domain.repository.UserDao;
import com.satodai.demo.login.domain.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    @Override
    public boolean insert(User user) {

        int result = dao.insertOne(user);

        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    @Override
    public List<User> selectMany() {
        return dao.selectMany();
    }

    @Override
    public boolean update(User user) {

        int result = dao.updateOne(user);

        if(result == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean delete(String userId) {

        int result = dao.deleteOne(userId);

        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
}
