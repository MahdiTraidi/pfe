package com.mtraidi.demo.dao;

import com.mtraidi.demo.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifRepository extends JpaRepository<Notification, Long> {
}
