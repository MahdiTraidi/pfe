package com.mtraidi.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtraidi.demo.serializer.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Enseignant extends User {
    private String matricule;
    private String codeContrat;
    private String typeContrat;
    private String speciality;
    @ManyToMany(mappedBy = "enseignants")
    @JsonSerialize(using = MatiereSerializer.class)
    List<Matiere> matieres;
    @ManyToMany(mappedBy = "enseignants")
    @JsonSerialize(using = GroupeSerializer.class)
    List<Groupe> groupes;
    @OneToMany(mappedBy = "enseignant")
    @JsonSerialize(using = AbsenceSerializer.class)
    private List<Absence> absences;
    @OneToMany(mappedBy = "enseignant")
    @JsonSerialize(using = ExamenSerializer.class)
    private List<Examen> examenList;
    @OneToMany(mappedBy = "enseignant")
    @JsonSerialize(using = NoteSerializer.class)
    private List<Note> notes;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCodeContrat() {
        return codeContrat;
    }

    public void setCodeContrat(String codeContrat) {
        this.codeContrat = codeContrat;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    @JsonIgnore
    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @JsonIgnore
    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    @JsonIgnore
    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    @JsonIgnore
    public List<Examen> getExamenList() {
        return examenList;
    }

    public void setExamenList(List<Examen> examenList) {
        this.examenList = examenList;
    }

    @JsonIgnore
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
