package com.ndp.techsharing.Services;

import java.util.List;

import com.ndp.techsharing.Entities.User;
import com.ndp.techsharing.JpaRepo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo repo;

    public User retrieveOne(String username) {
        User sth = null;

        try {
            sth = repo.findById(username).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public List<User> retrieveAll() {
        return repo.findAll();
    }

    public User createOne(User user) {
        User tmpUser = null;

        Boolean b_existed = false;

        try {
            tmpUser = repo.findById(user.getUsername()).get();
        } catch (Exception e) {
            //TODO: handle exception
            tmpUser = repo.save(user);

            b_existed = true;
        }

        if(b_existed) {
            return tmpUser;
        } else {
            return null;
        }
    }

    public User updateOne(User user) {
        User tmpUser = null;

        try {
            repo.findById(user.getUsername()).get();

            tmpUser = repo.save(user);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUser;
    }
}
