package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.EnseignantRepository;
import com.mtraidi.demo.dao.ExamenRepository;
import com.mtraidi.demo.dao.GroupeRepository;
import com.mtraidi.demo.dao.NiveauRepository;
import com.mtraidi.demo.models.Enseignant;
import com.mtraidi.demo.models.Examen;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users/examen")
@CrossOrigin("*")
public class ExamenController {
    @Autowired
    private ExamenRepository examenRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private GroupeRepository groupeRepository;

    private final Path rootLocation = Paths.get("upload-dir");

    @GetMapping("/all")
    public ResponseEntity<List<Examen>> getExamens() {
        return new ResponseEntity<>(examenRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/all/{id_groupe}")
    public ResponseEntity<List<Examen>> getExamensGroupe(@PathVariable Long id_groupe) {
        Groupe groupe= groupeRepository.findOne(id_groupe);
        List<Examen> examenList = examenRepository.findAll().stream().filter(e->e.getGroupe().getId()==id_groupe).collect(Collectors.toList());
        return new ResponseEntity<>(examenList, HttpStatus.OK);
    }
    @GetMapping("/all/ens/{id_ens}")
    public ResponseEntity<List<Examen>> getExamensEns(@PathVariable Long id_ens) {
        Enseignant enseignant= enseignantRepository.findOne(id_ens);
        List<Examen> examenList = examenRepository.findAll().stream().filter(e->e.getEnseignant().getId()==id_ens).collect(Collectors.toList());
        return new ResponseEntity<>(examenList, HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Examen> getExamenById(@PathVariable Long id) {
        return new ResponseEntity<>(examenRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save/{ens_id}/{groupe_id}")
    public ResponseEntity<Examen> saveExamen(@RequestParam MultipartFile file, Examen examen, @PathVariable Long ens_id, @PathVariable Long groupe_id) {
        Enseignant enseignant = enseignantRepository.findOne(ens_id);
        Groupe groupe = groupeRepository.findOne(groupe_id);
        examen.setEnseignant(enseignant);
        examen.setGroupe(groupe);

        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            examen.setFichier(original);
        } catch (Exception e) {
            throw new RuntimeException("FAIL File exception !");
        }
        return new ResponseEntity<Examen>(examenRepository.save(examen), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Examen> updateExamen(@RequestBody Examen examen, @PathVariable Long id) {

        Examen currentExamen = examenRepository.findOne(id);
        currentExamen.setTitle(examen.getTitle());
        currentExamen.setDescription(examen.getDescription());


        return new ResponseEntity<Examen>(examenRepository.saveAndFlush(currentExamen), HttpStatus.CREATED);
    }

    @PutMapping("/update_file/{id}")
    public ResponseEntity<Examen> updateExamenFile(@RequestParam MultipartFile file, Examen examen, @PathVariable Long id) {

        Examen currentExamen = examenRepository.findOne(id);
        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            currentExamen.setFichier(original);
        } catch (Exception e) {
            throw new RuntimeException("FAIL File exception !");
        }
        return new ResponseEntity<Examen>(examenRepository.saveAndFlush(currentExamen), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteExamen(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            examenRepository.delete(id);
            response.put("Response :", "Examen with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Examen with id : " + id + " Not found !");
            return response;
        }
    }
}
