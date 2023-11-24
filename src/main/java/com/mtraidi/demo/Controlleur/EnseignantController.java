package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.EnseignantRepository;
import com.mtraidi.demo.dao.GroupeRepository;
import com.mtraidi.demo.dao.MatiereRepository;
import com.mtraidi.demo.models.*;
import com.mtraidi.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/users/enseignant")
@CrossOrigin("*")
public class EnseignantController {
    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private UserServices service;
    @Autowired
    private GroupeRepository groupeRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Enseignant>> getAllEnseignants() {
        return new ResponseEntity<>(enseignantRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable Long id) {
        return new ResponseEntity<>(enseignantRepository.findOne(id), HttpStatus.OK);
    }

    @PutMapping("/ens/matiere/{id_ens}/{id_mat}")
    public ResponseEntity<Enseignant> AddMatiereEns(@PathVariable Long id_ens, @PathVariable Long id_mat) {
        Matiere matiere = matiereRepository.findOne(id_mat);
        Enseignant enseignant = enseignantRepository.findOne(id_ens);
        enseignant.getMatieres().add(matiere);
        matiere.getEnseignants().add(enseignant);
        matiereRepository.save(matiere);
        enseignantRepository.save(enseignant);
        return new ResponseEntity<>(enseignant, HttpStatus.OK);

    }

    @PutMapping("/ens/groupe/{id_ens}/{id_mat}")
    public ResponseEntity<Enseignant> AddGroupeEns(@PathVariable Long id_ens, @PathVariable Long id_mat) {
        Groupe groupe = groupeRepository.findOne(id_mat);
        Enseignant enseignant = enseignantRepository.findOne(id_ens);
        enseignant.getGroupes().add(groupe);
        groupe.getEnseignants().add(enseignant);
        groupeRepository.save(groupe);
        enseignantRepository.save(enseignant);
        return new ResponseEntity<>(enseignant, HttpStatus.OK);

    }

    @PutMapping("/ens/matiere_delete/{id_ens}/{id_mat}")
    public ResponseEntity<Enseignant> DeleteMatiereEns(@PathVariable Long id_ens, @PathVariable Long id_mat) {
        Matiere matiere = matiereRepository.findOne(id_mat);
        Enseignant enseignant = enseignantRepository.findOne(id_ens);
        enseignant.getMatieres().remove(matiere);
        matiere.getEnseignants().remove(enseignant);
        matiereRepository.save(matiere);
        enseignantRepository.save(enseignant);
        return new ResponseEntity<>(enseignant, HttpStatus.OK);

    }

    @PutMapping("/ens/groupe_delete/{id_ens}/{id_groupe}")
    public ResponseEntity<Enseignant> DeleteGroupeEns(@PathVariable Long id_ens, @PathVariable Long id_groupe) {
        Groupe groupe = groupeRepository.findOne(id_groupe);
        Enseignant enseignant = enseignantRepository.findOne(id_ens);
        enseignant.getGroupes().remove(groupe);
        groupe.getEnseignants().remove(enseignant);
        groupeRepository.save(groupe);
        enseignantRepository.save(enseignant);
        return new ResponseEntity<>(enseignant, HttpStatus.OK);

    }

    @PostMapping("/save")
    public ResponseEntity<Enseignant> saveEnseignant(@RequestBody Enseignant enseignant) {
        enseignant.setMatricule(enseignant.getId() + enseignant.getLastName());

        return new ResponseEntity<Enseignant>(enseignantRepository.save(enseignant), HttpStatus.CREATED);
    }

    @PostMapping("/process_register")
    public ResponseEntity<?> processRegister(@RequestBody Enseignant user, HttpServletRequest request)
        throws UnsupportedEncodingException, MessagingException {
        service.registerEns(user, getSiteURL(request));
        user.setMatricule(user.getId() + "-" + user.getLastName());
        enseignantRepository.saveAndFlush(user);
        return ResponseEntity.ok(new UserTokenState(null, 0, user));
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@RequestBody Enseignant enseignant, @PathVariable Long id) {

        Enseignant currentEnseignant = enseignantRepository.findOne(id);
        currentEnseignant.setFirstName(enseignant.getFirstName());
        currentEnseignant.setLastName(enseignant.getLastName());
        currentEnseignant.setTelephone(enseignant.getTelephone());
        currentEnseignant.setSpeciality(enseignant.getSpeciality());

        return new ResponseEntity<Enseignant>(enseignantRepository.saveAndFlush(currentEnseignant), HttpStatus.CREATED);
    }

    @PutMapping("/update/autorier/{id}")
    public ResponseEntity<Enseignant> autoriserEnseignant(@PathVariable Long id) {

        Enseignant currentEnseignant = enseignantRepository.findOne(id);
        currentEnseignant.setEnabled(true);

        return new ResponseEntity<Enseignant>(enseignantRepository.saveAndFlush(currentEnseignant), HttpStatus.CREATED);
    }

    @PutMapping("/update/bloquer/{id}")
    public ResponseEntity<Enseignant> bloquerEnseignant(@PathVariable Long id) {

        Enseignant currentEnseignant = enseignantRepository.findOne(id);
        currentEnseignant.setEnabled(false);
        return new ResponseEntity<Enseignant>(enseignantRepository.saveAndFlush(currentEnseignant), HttpStatus.CREATED);
    }

    @PutMapping("/update_image/{id}")
    public ResponseEntity<Enseignant> updateEnseignantPic(@RequestParam MultipartFile file, @PathVariable Long id) {

        Enseignant currentEnseignant = enseignantRepository.findOne(id);
        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            currentEnseignant.setImage(original);
        } catch (Exception e) {
            throw new RuntimeException("FAIL Image exception !");
        }
        return new ResponseEntity<Enseignant>(enseignantRepository.saveAndFlush(currentEnseignant), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteEnseignant(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            enseignantRepository.delete(id);
            response.put("Response :", "Enseignant with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Enseignant with id : " + id + " Not found !");
            return response;
        }
    }
}
