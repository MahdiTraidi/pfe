package com.mtraidi.demo.models;

import javax.persistence.*;
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String examen_orale;
    private String examen_ecrit;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Parent parent;
    @ManyToOne
    @JoinColumn(name = "id_enseignant")
    private Enseignant enseignant;
    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamen_orale() {
        return examen_orale;
    }

    public void setExamen_orale(String examen_orale) {
        this.examen_orale = examen_orale;
    }

    public String getExamen_ecrit() {
        return examen_ecrit;
    }

    public void setExamen_ecrit(String examen_ecrit) {
        this.examen_ecrit = examen_ecrit;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}
