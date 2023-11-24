package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.EnseignantRepository;
import com.mtraidi.demo.dao.MatiereRepository;
import com.mtraidi.demo.dao.NiveauRepository;
import com.mtraidi.demo.models.Enseignant;
import com.mtraidi.demo.models.Matiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/matiere")
@CrossOrigin("*")
public class MatiereController {
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Matiere>> getAllMatieres() {
        return new ResponseEntity<>(matiereRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all_out/{id_ens}")
    public ResponseEntity<List<Matiere>> getAllMatieresNonEns(@PathVariable Long id_ens) {
        Enseignant enseignant = enseignantRepository.findOne(id_ens);
       List<Matiere> matiereList = matiereRepository.findAll()
            .stream()
            .filter(x -> !x.getEnseignants().contains(enseignant))
            .collect(Collectors.toList());
        return new ResponseEntity<>(matiereList, HttpStatus.OK);
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<Matiere> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(matiereRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Matiere> saveMatiere(@RequestBody Matiere matiere) {

        return new ResponseEntity<Matiere>(matiereRepository.save(matiere), HttpStatus.CREATED);
    }

    @PostMapping("/save/{niveau_id}")
    public ResponseEntity<Matiere> saveMatiereNiveau(@RequestBody Matiere matiere, @PathVariable Long niveau_id) {
        matiere.setNiveau(niveauRepository.findOne(niveau_id));
        return new ResponseEntity<Matiere>(matiereRepository.save(matiere), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Matiere> updateMatiere(@RequestBody Matiere matiere, @PathVariable Long id) {

        Matiere currentMatiere = matiereRepository.findOne(id);
        currentMatiere.setLibelle(matiere.getLibelle());

        return new ResponseEntity<Matiere>(matiereRepository.saveAndFlush(currentMatiere), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteMatiere(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            matiereRepository.delete(id);
            response.put("Response :", "Matiere with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Matiere with id : " + id + " Not found !");
            return response;
        }
    }
}
