package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.models.Absence;
import com.mtraidi.demo.models.Note;

import java.io.IOException;
import java.util.List;

public class NoteSerializer  extends JsonSerializer<List<Note>> {
    @Override
    public void serialize(List<Note> notes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        for (Note note : notes) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", note.getId());
            jsonGenerator.writeObjectField("examen_ecrit", note.getExamen_ecrit());
            jsonGenerator.writeObjectField("examen_orale", note.getExamen_orale());
            jsonGenerator.writeObjectField("eleve", note.getParent().getFirstName() + note.getParent().getLastName());
            jsonGenerator.writeObjectField("enseignant", note.getEnseignant().getFirstName() + note.getEnseignant().getLastName());
            jsonGenerator.writeObjectField("matiere", note.getMatiere().getLibelle());


            jsonGenerator.writeEndObject();
            //TODO : To be continued ...
        }
        jsonGenerator.writeEndArray();
    }
}
