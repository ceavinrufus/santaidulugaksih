// package com.simplicity;

// import com.simplicity.AbstractClass.*;
// import com.google.gson.*;

// public class FoodAdapter implements JsonSerializer<Food>,
// JsonDeserializer<Food> {
// @Override
// public JsonElement serialize(Food food, java.lang.reflect.Type type,
// JsonSerializationContext jsonSerializationContext) {
// JsonObject result = new JsonObject();
// result.add("type", new JsonPrimitive(food.getClass().getSimpleName()));
// result.add("properties", jsonSerializationContext.serialize(food,
// food.getClass()));
// return result;
// }

// @Override
// public Food deserialize(JsonElement jsonElement, java.lang.reflect.Type type,
// JsonDeserializationContext jsonDeserializationContext) throws
// JsonParseException {
// if (jsonElement instanceof JsonNull) {
// return null;
// }
// JsonObject jsonObject = jsonElement.getAsJsonObject();
// if (jsonObject.has("type")) {
// String typeString = jsonObject.get("type").getAsString();
// JsonElement element = jsonObject.get("properties");
// try {
// return jsonDeserializationContext.deserialize(element,
// Class.forName("com.simplicity." + typeString));
// } catch (ClassNotFoundException cnfe) {
// throw new JsonParseException("Unknown element type: " + typeString, cnfe);
// }
// }
// return null;
// }
// }