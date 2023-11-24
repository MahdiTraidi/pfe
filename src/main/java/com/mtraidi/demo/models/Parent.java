package com.mtraidi.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mtraidi.demo.serializer.AbsenceSerializer;
import com.mtraidi.demo.serializer.NoteSerializer;
import com.mtraidi.demo.serializer.NotifSerializer;

import javax.persistence.*;
import java.util.List;

@Entity
public class Parent extends User {
    private String codeInscription;
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    @OneToMany(mappedBy = "parent")
    @JsonSerialize(using = AbsenceSerializer.class)
    private List<Absence> absences;
    @OneToMany(mappedBy = "parent")
    @JsonSerialize(using = NotifSerializer.class)
    private List<Notification> notifications;
    @OneToMany(mappedBy = "parent")
    @JsonSerialize(using = NoteSerializer.class)
    private List<Note> notes;

    public String getCodeInscription() {
        return codeInscription;
    }

    public void setCodeInscription(String codeInscription) {
        this.codeInscription = codeInscription;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    @JsonIgnore
    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    @JsonIgnore
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @JsonIgnore
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
