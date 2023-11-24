package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.models.Examen;
import com.mtraidi.demo.models.Notification;

import java.io.IOException;
import java.util.List;

public class ExamenSerializer extends JsonSerializer<List<Examen>> {
    @Override
    public void serialize(List<Examen> examenList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        for (Examen examen : examenList) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", examen.getId());
            jsonGenerator.writeObjectField("title", examen.getTitle());
            jsonGenerator.writeObjectField("description", examen.getDescription());
            jsonGenerator.writeObjectField("createdAt", examen.getCreatedAt());
            jsonGenerator.writeObjectField("file", examen.getFichier());
            jsonGenerator.writeEndObject();
            //TODO : To be continued ...
        }
        jsonGenerator.writeEndArray();
    }
}
