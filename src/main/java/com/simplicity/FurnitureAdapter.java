package com.simplicity;

import com.simplicity.AbstractClass.*;
import com.google.gson.*;

public class FurnitureAdapter implements JsonSerializer<Furniture>, JsonDeserializer<Furniture> {
    @Override
    public JsonElement serialize(Furniture furniture, java.lang.reflect.Type type,
            JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(furniture.getClass().getSimpleName()));
        result.add("properties", jsonSerializationContext.serialize(furniture, furniture.getClass()));
        return result;
    }

    @Override
    public Furniture deserialize(JsonElement jsonElement, java.lang.reflect.Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String typeString = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return jsonDeserializationContext.deserialize(element, Class.forName("com.simplicity." + typeString));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + typeString, cnfe);
        }
    }
}
