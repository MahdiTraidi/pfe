package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.*;
import com.mtraidi.demo.models.Note;
import com.mtraidi.demo.models.Notification;
import com.mtraidi.demo.models.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/note")
@CrossOrigin("*")
public class NoteController {
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private NotifRepository notifRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(noteRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all_ens/{id_ens}/{id_mat}/{id_grp}")
    public ResponseEntity<List<Note>> getAllNotesEns(@PathVariable Long id_ens,@PathVariable Long id_mat,@PathVariable Long id_grp) {
        List<Note> notes = noteRepository.findAll()
            .stream()
            .filter(x -> x.getEnseignant() == enseignantRepository.findOne(id_ens) && x.getMatiere().getId()==id_mat && x.getParent().getGroupe().getId()==id_grp)
            .collect(Collectors.toList());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/all_parent/{id_parent}")
    public ResponseEntity<List<Note>> getAllNotesParent(@PathVariable Long id_parent) {
        List<Note> notes = noteRepository.findAll()
            .stream()
            .filter(x -> x.getParent() == parentRepository.findOne(id_parent))
            .collect(Collectors.toList());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    @GetMapping("/all_parent/{id_parent}/{id_groupe}")
    public ResponseEntity<List<Note>> getAllNotesGroupe(@PathVariable Long id_parent,@PathVariable Long id_groupe) {
        List<Note> notes = noteRepository.findAll()
            .stream()
            .filter(x -> x.getParent() == parentRepository.findOne(id_parent) && x.getParent().getGroupe().getId() == id_groupe)
            .collect(Collectors.toList());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/all_matiere/{id_matiere}")
    public ResponseEntity<List<Note>> getAllNotesMatiere(@PathVariable Long id_matiere) {
        List<Note> notes = noteRepository.findAll()
            .stream()
            .filter(x -> x.getMatiere() == matiereRepository.findOne(id_matiere))
            .collect(Collectors.toList());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return new ResponseEntity<>(noteRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save/{id_parent}/{id_enseignant}/{id_matiere}")
    public ResponseEntity<Note> saveNote(@RequestBody Note note, Notification notification, @PathVariable Long id_parent, @PathVariable Long id_enseignant, @PathVariable Long id_matiere) {
        Parent parent = parentRepository.findOne(id_parent);
        note.setParent(parent);
        note.setEnseignant(enseignantRepository.findOne(id_enseignant));
        note.setMatiere(matiereRepository.findOne(id_matiere));
        notification.setTitle("L'éléve " + parent.getFirstName() + " " + parent.getLastName() + " a la note d'orale : <" + note.getExamen_orale() + ">" + " et la note d'écrit : <" + note.getExamen_ecrit() + ">" + " dans la matiére" + matiereRepository.findOne(id_matiere).getLibelle());
        notification.setParent(parent);
        notifRepository.save(notification);
        return new ResponseEntity<Note>(noteRepository.save(note), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Note> updateNote(@RequestBody Note note, @PathVariable Long id) {
        Note currentNote = noteRepository.findOne(id);
        currentNote.setExamen_ecrit(note.getExamen_ecrit());
        currentNote.setExamen_orale(note.getExamen_orale());

        return new ResponseEntity<Note>(noteRepository.saveAndFlush(currentNote), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteNote(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            noteRepository.delete(id);
            response.put("Response :", "Note with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Note with id : " + id + " Not found !");
            return response;
        }
    }
}
