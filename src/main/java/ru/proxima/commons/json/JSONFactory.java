package ru.proxima.commons.json;
/**
 * Интерфейс фабрики JSON-объекта из экземпляра сущности.
 */
public interface JSONFactory<T> {
/**
 * Создание JSON-объекта из экземпляра сущности
 * @param	fromObject		Исходный экземпляр
 * @param	toJSONObject	Пустой JSON-объект для записи даных
 */
	void build(T fromObject, JSONObject toJSONObject);
}
