package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.models.Matiere;

import java.io.IOException;
import java.util.List;

public class MatiereSerializer extends JsonSerializer<List<Matiere>> {
    @Override
    public void serialize(List<Matiere> matieres, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        for (Matiere matiere : matieres) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", matiere.getId());
            jsonGenerator.writeObjectField("libelle", matiere.getLibelle());
            jsonGenerator.writeObjectField("niveau", matiere.getNiveau().getLibelle());
            jsonGenerator.writeEndObject();
            //TODO : To be continued ...
        }
        jsonGenerator.writeEndArray();
    }
}
