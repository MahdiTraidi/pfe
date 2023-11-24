package com.mtraidi.demo.dao;

import com.mtraidi.demo.models.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe,Long> {
}
