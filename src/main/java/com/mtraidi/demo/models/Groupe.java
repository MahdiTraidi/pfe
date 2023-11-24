package com.mtraidi.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtraidi.demo.serializer.ExamenSerializer;
import com.mtraidi.demo.serializer.NoteSerializer;
import com.mtraidi.demo.serializer.ParentSerializer;

import javax.persistence.*;
import java.util.List;

@Entity
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;
    @OneToMany(mappedBy = "groupe")
    @JsonSerialize(using = ParentSerializer.class)
    private List<Parent> parents;
    @ManyToMany
    @JoinTable(
        name = "groupe_enseignant",
        joinColumns = @JoinColumn(name = "groupe_id"),
        inverseJoinColumns = @JoinColumn(name = "enseignant_id"))
    private List<Enseignant> enseignants;

    @OneToMany(mappedBy = "groupe")
    @JsonSerialize(using = ExamenSerializer.class)
    private List<Examen> examenList;


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
    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    @JsonIgnore
    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

    @JsonIgnore
    public List<Examen> getExamenList() {
        return examenList;
    }

    public void setExamenList(List<Examen> examenList) {
        this.examenList = examenList;
    }


}
