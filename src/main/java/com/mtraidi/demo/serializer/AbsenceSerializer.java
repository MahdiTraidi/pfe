package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.dao.AbsenceRepository;
import com.mtraidi.demo.models.Absence;
import com.mtraidi.demo.models.Groupe;

import java.io.IOException;
import java.util.List;

public class AbsenceSerializer extends JsonSerializer<List<Absence>> {
    @Override
    public void serialize(List<Absence> absences, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        for (Absence absence : absences) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", absence.getId());
            jsonGenerator.writeObjectField("date_debut", absence.getDate_debut());
            jsonGenerator.writeObjectField("date_fin", absence.getDate_fin());
            jsonGenerator.writeObjectField("eleve", absence.getParent().getFirstName() + absence.getParent().getLastName());
            jsonGenerator.writeObjectField("enseignant", absence.getEnseignant().getFirstName() + absence.getEnseignant().getLastName());
            jsonGenerator.writeObjectField("matiere", absence.getMatiere().getLibelle());


            jsonGenerator.writeEndObject();
            //TODO : To be continued ...
        }
        jsonGenerator.writeEndArray();
    }
}
