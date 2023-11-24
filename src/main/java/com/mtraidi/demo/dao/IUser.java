package com.mtraidi.demo.dao;

import com.mtraidi.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUser  extends JpaRepository<User,Long> {

    /*@Query("{'username':?0}")*/
     @Query("select u from  User  u where u.username= :username")
    User findByUsername(@Param("username") String username);
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
     User findByVerificationCode(String code);

}
