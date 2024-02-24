package com.project.usertask.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.usertask.project1.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.userName = :userName")
	public User getUserByUserName(String userName);
    
}
