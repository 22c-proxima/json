package ru.proxima.commons.json;
/**
 * Интерфейс фабрики JSON-объекта из экземпляра сущности.
 */
public interface JSONDeserializeCallback<T> {
/**
 * Создание экземпляра сущности из JSON-объекта 
 * @param fromJSONObject Исходный JSON-объект
 * @return Полученный экземпляр сущности
 * @throws JSONException На случай ошибки десериализации
 */
	T deserialize(JSONObject fromJSONObject) throws JSONException;
}
