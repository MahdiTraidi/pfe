package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Matiere;

import java.io.IOException;
import java.util.List;

public class GroupeSerializer  extends JsonSerializer<List<Groupe>>

    {
        @Override
        public void serialize(List<Groupe> groupes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeStartArray();
            for (Groupe groupe : groupes) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("id", groupe.getId());
                jsonGenerator.writeObjectField("libelle", groupe.getLibelle());
                jsonGenerator.writeObjectField("niveau", groupe.getNiveau().getLibelle());
                jsonGenerator.writeObjectField("eleves", groupe.getParents());

                jsonGenerator.writeEndObject();
                //TODO : To be continued ...
            }
            jsonGenerator.writeEndArray();
        }
}
