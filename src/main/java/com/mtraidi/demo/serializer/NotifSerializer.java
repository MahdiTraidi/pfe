package com.mtraidi.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mtraidi.demo.models.Groupe;
import com.mtraidi.demo.models.Notification;
import org.aspectj.weaver.ast.Not;

import java.io.IOException;
import java.util.List;

public class NotifSerializer extends JsonSerializer<List<Notification>>

{
    @Override
    public void serialize(List<Notification> notifications, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
    {
        jsonGenerator.writeStartArray();
        for (Notification notification : notifications) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", notification.getId());
            jsonGenerator.writeObjectField("title", notification.getTitle());


            jsonGenerator.writeEndObject();
            //TODO : To be continued ...
        }
        jsonGenerator.writeEndArray();
    }
}
