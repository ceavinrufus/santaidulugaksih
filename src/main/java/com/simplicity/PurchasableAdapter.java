package com.simplicity;

import com.simplicity.Interface.*;
import com.google.gson.*;

public class PurchasableAdapter implements JsonSerializer<Purchasable>,
        JsonDeserializer<Purchasable> {
    @Override
    public JsonElement serialize(Purchasable purchasable, java.lang.reflect.Type type,
            JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(purchasable.getClass().getSimpleName()));
        result.add("properties", jsonSerializationContext.serialize(purchasable,
                purchasable.getClass()));
        return result;
    }

    @Override
    public Purchasable deserialize(JsonElement jsonElement,
            java.lang.reflect.Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String typeString = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return jsonDeserializationContext.deserialize(element,
                    Class.forName("com.simplicity." + typeString));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + typeString, cnfe);
        }
    }
}
