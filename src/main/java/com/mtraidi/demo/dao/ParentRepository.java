package com.mtraidi.demo.dao;

import com.mtraidi.demo.models.Parent;
import com.mtraidi.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
    @Query("SELECT u FROM Parent u WHERE u.verificationCode = ?1")
    Parent findByVerificationCode(String code);
}
