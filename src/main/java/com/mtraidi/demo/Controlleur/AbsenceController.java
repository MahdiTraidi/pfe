package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.*;
import com.mtraidi.demo.models.Absence;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Notification;
import com.mtraidi.demo.models.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/absence")
@CrossOrigin("*")
public class AbsenceController {
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private NotifRepository notifRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Absence>> getAllAbsences() {
        return new ResponseEntity<>(absenceRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all_ens/{id_ens}")
    public ResponseEntity<List<Absence>> getAllAbsencesEns(@PathVariable Long id_ens) {
        List<Absence> absences = absenceRepository.findAll()
            .stream()
            .filter(x -> x.getEnseignant() == enseignantRepository.findOne(id_ens))
            .collect(Collectors.toList());
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }
    @GetMapping("/all_parent/{id_parent}")
    public ResponseEntity<List<Absence>> getAllAbsencesParent(@PathVariable Long id_parent) {
        List<Absence> absences = absenceRepository.findAll()
            .stream()
            .filter(x -> x.getParent() == parentRepository.findOne(id_parent))
            .collect(Collectors.toList());
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }
    @GetMapping("/all_matiere/{id_matiere}")
    public ResponseEntity<List<Absence>> getAllAbsencesMatiere(@PathVariable Long id_matiere) {
        List<Absence> absences = absenceRepository.findAll()
            .stream()
            .filter(x -> x.getMatiere() == matiereRepository.findOne(id_matiere))
            .collect(Collectors.toList());
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        return new ResponseEntity<>(absenceRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save/{id_parent}/{id_enseignant}/{id_matiere}")
    public ResponseEntity<Absence> saveAbsence(@RequestBody Absence absence, Notification notification, @PathVariable Long id_parent, @PathVariable Long id_enseignant, @PathVariable Long id_matiere) {
        Parent parent = parentRepository.findOne(id_parent);
        absence.setParent(parent);
        absence.setEnseignant(enseignantRepository.findOne(id_enseignant));
        absence.setMatiere(matiereRepository.findOne(id_matiere));
        notification.setTitle("L'éléve " + parent.getFirstName() + " " + parent.getLastName() + " est absent entre " + absence.getDate_debut() + " et " + absence.getDate_fin() + " dans une séance de " + matiereRepository.findOne(id_matiere).getLibelle());
        notification.setParent(parent);
        notifRepository.save(notification);
        return new ResponseEntity<Absence>(absenceRepository.save(absence), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Absence> updateAbsence(@RequestBody Absence absence, @PathVariable Long id) {
        Absence currentAbsence = absenceRepository.findOne(id);

        return new ResponseEntity<Absence>(absenceRepository.saveAndFlush(currentAbsence), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteAbsence(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            absenceRepository.delete(id);
            response.put("Response :", "Absence with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Absence with id : " + id + " Not found !");
            return response;
        }
    }
}
