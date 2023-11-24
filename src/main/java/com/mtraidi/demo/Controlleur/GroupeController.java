package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.EnseignantRepository;
import com.mtraidi.demo.dao.GroupeRepository;
import com.mtraidi.demo.dao.NiveauRepository;
import com.mtraidi.demo.models.Enseignant;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Matiere;
import com.mtraidi.demo.models.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/groupe")
@CrossOrigin("*")
public class GroupeController {
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @GetMapping("/all")
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return new ResponseEntity<>(groupeRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/all_out/{id_ens}")
    public ResponseEntity<List<Groupe>> getAllGroupesNonEns(@PathVariable Long id_ens) {
        Enseignant enseignant = enseignantRepository.findOne(id_ens);
        List<Groupe> groupeList = groupeRepository.findAll()
            .stream()
            .filter(x -> !x.getEnseignants().contains(enseignant))
            .collect(Collectors.toList());
        return new ResponseEntity<>(groupeList, HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        return new ResponseEntity<>(groupeRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save/{id_niveau}")
    public ResponseEntity<Groupe> saveGroupe(@RequestBody Groupe groupe, @PathVariable Long id_niveau) {
        groupe.setNiveau(niveauRepository.findOne(id_niveau));
        return new ResponseEntity<Groupe>(groupeRepository.save(groupe), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Groupe> updateGroupe(@RequestBody Groupe groupe, @PathVariable Long id) {
        Groupe currentGroupe = groupeRepository.findOne(id);
        currentGroupe.setLibelle(groupe.getLibelle());

        return new ResponseEntity<Groupe>(groupeRepository.saveAndFlush(currentGroupe), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}/{id_niveau}")
    public ResponseEntity<Groupe> updateGroupeNiveau(@RequestBody Groupe groupe, @PathVariable Long id,@PathVariable Long id_niveau) {
        Groupe currentGroupe = groupeRepository.findOne(id);
        Niveau niveau = niveauRepository.findOne(id_niveau) ;
        currentGroupe.setNiveau(niveau);
        return new ResponseEntity<Groupe>(groupeRepository.saveAndFlush(currentGroupe), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteGroupe(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            groupeRepository.delete(id);
            response.put("Response :", "Groupe with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Groupe with id : " + id + " Not found !");
            return response;
        }
    }
}
