package com.ndp.techsharing.Services;

import java.util.List;

import com.ndp.techsharing.Entities.UserVoteState;
import com.ndp.techsharing.JpaRepo.UserVoteStateRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserVoteStateService {
    @Autowired
    private UserVoteStateRepo repo;

    public List<UserVoteState> retrieveAll() {
        return repo.findAll();
    }

    public UserVoteState retrieveOne(Integer id) {
        UserVoteState sth = null;

        try {
            sth = repo.findById(id).get();
        } catch (Exception e) {
            //TODO: handle exception
        }

        return sth;
    }

    public UserVoteState createOne(UserVoteState userVoteState) {
        UserVoteState tmpUserVoteState = null;

        userVoteState.setId(0);

        try {
            tmpUserVoteState = repo.save(userVoteState);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserVoteState;
    }

    public UserVoteState updateOne(UserVoteState userVoteState) {
        UserVoteState tmpUserVoteState = null;

        try {
            repo.findById(userVoteState.getId()).get();

            tmpUserVoteState = repo.save(userVoteState);
        } catch (Exception e) {
            //TODO: handle exception
        }

        return tmpUserVoteState;
    }
}
