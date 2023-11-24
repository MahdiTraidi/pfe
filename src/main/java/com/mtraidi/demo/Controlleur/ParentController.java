package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.GroupeRepository;
import com.mtraidi.demo.dao.ParentRepository;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Parent;
import com.mtraidi.demo.models.User;
import com.mtraidi.demo.models.UserTokenState;
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
@RequestMapping("/users/parent")
@CrossOrigin("*")
public class ParentController {
    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private GroupeRepository groupeRepository;
    @Autowired
    private UserServices service;

    @GetMapping("/all")
    public ResponseEntity<List<Parent>> getAllStudents() {
        return new ResponseEntity<>(parentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Parent> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(parentRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Parent> saveParent(@RequestBody Parent parent) {
        parent.setCodeInscription(parent.getId() + parent.getLastName());

        return new ResponseEntity<Parent>(parentRepository.save(parent), HttpStatus.CREATED);
    }

    @PostMapping("/process_register")
    public ResponseEntity<?> processRegister(@RequestBody Parent user, HttpServletRequest request)
        throws UnsupportedEncodingException, MessagingException {
        service.registerParent(user, getSiteURL(request));
        user.setCodeInscription(user.getId() + "-" + user.getLastName());

        parentRepository.saveAndFlush(user);
        return ResponseEntity.ok(new UserTokenState(null, 0, user));
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Parent> updateParent(@RequestBody Parent parent, @PathVariable Long id) {

        Parent currentParent = parentRepository.findOne(id);
        currentParent.setFirstName(parent.getFirstName());
        currentParent.setLastName(parent.getLastName());
        currentParent.setTelephone(parent.getTelephone());
        currentParent.setDateBirth(parent.getDateBirth());

        return new ResponseEntity<Parent>(parentRepository.saveAndFlush(currentParent), HttpStatus.CREATED);
    }

    @PutMapping("/update_groupe/{id}/{id_groupe}")
    public ResponseEntity<Parent> updateParentGroupe(@PathVariable Long id, @PathVariable Long id_groupe) {

        Parent currentParent = parentRepository.findOne(id);
        Groupe groupe = groupeRepository.findOne(id_groupe);
        currentParent.setGroupe(groupe);


        return new ResponseEntity<Parent>(parentRepository.saveAndFlush(currentParent), HttpStatus.CREATED);
    }

    @PutMapping("/update/autorier/{id}")
    public ResponseEntity<Parent> autoriserParent(@PathVariable Long id) {

        Parent currentParent = parentRepository.findOne(id);
        currentParent.setEnabled(true);

        return new ResponseEntity<Parent>(parentRepository.saveAndFlush(currentParent), HttpStatus.CREATED);
    }

    @PutMapping("/update/bloquer/{id}")
    public ResponseEntity<Parent> bloquerParent(@PathVariable Long id) {

        Parent currentParent = parentRepository.findOne(id);
        currentParent.setEnabled(false);

        return new ResponseEntity<Parent>(parentRepository.saveAndFlush(currentParent), HttpStatus.CREATED);
    }

    @PutMapping("/update_image/{id}")
    public ResponseEntity<Parent> updateParentPic(@RequestParam MultipartFile file, Parent parent, @PathVariable Long id) {

        Parent currentParent = parentRepository.findOne(id);
        try {

            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            currentParent.setImage(original);
        } catch (Exception e) {
            throw new RuntimeException("FAIL Image exception !");
        }
        return new ResponseEntity<Parent>(parentRepository.saveAndFlush(currentParent), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteParent(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            parentRepository.delete(id);
            response.put("Response :", "Parent with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Parent with id : " + id + " Not found !");
            return response;
        }
    }
}
