package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.NiveauRepository;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users/niveau")
@CrossOrigin("*")
public class NiveauController {
    @Autowired
    private NiveauRepository niveauRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Niveau>> getNiveaux() {
        return new ResponseEntity<>(niveauRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable Long id) {
        return new ResponseEntity<>(niveauRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Niveau> saveNiveau(@RequestBody Niveau niveau) {

        return new ResponseEntity<Niveau>(niveauRepository.save(niveau), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Niveau> updateNiveau(@RequestBody Niveau niveau, @PathVariable Long id) {

        Niveau currentGroupe = niveauRepository.findOne(id);
        currentGroupe.setLibelle(niveau.getLibelle());

        return new ResponseEntity<Niveau>(niveauRepository.saveAndFlush(currentGroupe), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteNiveau(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            niveauRepository.delete(id);
            response.put("Response :", "Niveau with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Niveau with id : " + id + " Not found !");
            return response;
        }
    }
}
