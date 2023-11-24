package com.mtraidi.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtraidi.demo.serializer.AbsenceSerializer;
import com.mtraidi.demo.serializer.GroupeSerializer;
import com.mtraidi.demo.serializer.NoteSerializer;

import javax.persistence.*;
import java.util.List;

@Entity
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    //    private int numStudent ;
    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;
    @ManyToMany
    @JoinTable(
        name = "matiere_enseignant",
        joinColumns = @JoinColumn(name = "matiere_id"),
        inverseJoinColumns = @JoinColumn(name = "enseignant_id"))
    private List<Enseignant> enseignants;
    @OneToMany(mappedBy = "matiere")
    @JsonSerialize(using = AbsenceSerializer.class)
    private List<Absence> absences;
    @OneToMany(mappedBy = "matiere")
    @JsonSerialize(using = NoteSerializer.class)
    private List<Note> notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @JsonIgnore
    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    @JsonIgnore
    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }
@JsonIgnore
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
