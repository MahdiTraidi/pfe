package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.models.Matiere;
import com.mtraidi.demo.models.Parent;

import java.io.IOException;
import java.util.List;

public class ParentSerializer  extends JsonSerializer<List<Parent>> {
    @Override
    public void serialize(List<Parent> parents, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        for (Parent parent : parents) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", parent.getId());
            jsonGenerator.writeObjectField("firstName", parent.getFirstName());
            jsonGenerator.writeObjectField("lastName", parent.getLastName());
            jsonGenerator.writeObjectField("email", parent.getEmail());


            jsonGenerator.writeEndObject();
            //TODO : To be continued ...
        }
        jsonGenerator.writeEndArray();
    }
}
