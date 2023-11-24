package com.mtraidi.demo.dao;

import com.mtraidi.demo.models.Enseignant;
import com.mtraidi.demo.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {
    @Query("SELECT u FROM Enseignant u WHERE u.verificationCode = ?1")
    Enseignant findByVerificationCode(String code);
}
