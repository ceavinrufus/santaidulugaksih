package com.simplicity;

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
        String typeString;
        if (jsonObject.get("type") != null) {
            typeString = jsonObject.get("type").getAsString();
        } else {
            typeString = jsonObject.get("properties").getAsJsonNull().getAsString();
        }
        JsonElement properties = jsonObject.get("properties");
        try {
            return jsonDeserializationContext.deserialize(properties, Class.forName("com.simplicity." + typeString));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + typeString, e);
        }
    }
}
