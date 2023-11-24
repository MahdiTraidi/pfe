package com.mtraidi.demo.services;

import com.mtraidi.demo.models.User;

import java.util.List;


public interface UserService {
  //  public User findById(String id);
    public User findByUsername(String username);
    public List<User> findAll();
    public User save(User u);
  //  public String deleteById(String id);
   // public Page<Utilisateur>chercher(String mc, Pageable pageable);
}
