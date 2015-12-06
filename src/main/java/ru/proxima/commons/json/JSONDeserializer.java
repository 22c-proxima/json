package ru.proxima.commons.json;

import java.util.Collection;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Статический построитель коллекций сущностей из JSON-массивов
 * @author Шомин Владимир, ЗАО ИВЦ Инсофт
 */
public final class JSONDeserializer {

	private final static Logger logger = LoggerFactory.getLogger(JSONDeserializer.class);
/**
 * Построитель статический, нечего пытаться создать его экземпляр
 */
	private JSONDeserializer(){}
/**
 * Создание коллекции экземпляров сущности из JSON-массива
 * @param <E> Тип десериализуемой сущности
 * @param serializedObjects Сериализованные в JSONArray сущности
 * @param deserializer Десериализатор сущностей данного типа
 * @return Коллекция сущностей
 * @throws NullPointerException serializedObjects или deserializer равны null
 * @throws JSONException Ошибка десериализации
 */
	public static <E> Collection<E> parse(
		JSONArray serializedObjects, JSONDeserializeCallback<E> deserializer)
			throws JSONException, NullPointerException {
		return parse(serializedObjects, deserializer, 0);
	}
/**
 * Создание коллекции экземпляров сущности из JSON-массива, с ограничением максимального размера
 * @param <E> Тип десериализуемой сущности
 * @param serializedObjects Сериализованные в JSONArray сущности
 * @param deserializer Десериализатор сущностей данного типа
 * @param maxResults Максимальное количество объектов в коллекции (0 - без ограничения)
 * @return Коллекция сущностей
 * @throws NullPointerException serializedObjects или deserializer равны null
 * @throws IllegalArgumentException maxResults является отрицательным
 * @throws JSONException Ошибка десериализации
 */
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> parse(
		JSONArray serializedObjects, JSONDeserializeCallback<E> deserializer, int maxResults)
			throws JSONException, IllegalArgumentException, NullPointerException {
		if (maxResults < 0) {
			throw new IllegalArgumentException();
		}

		LinkedList<E> collection = new LinkedList<E>();

		for (Object object : serializedObjects) {
			try {
				if (JSONObject.class.isAssignableFrom(object.getClass())) {
					JSONObject jsonObject = (JSONObject)object;
					collection.add(deserializer.deserialize(jsonObject));

					if (--maxResults == 0) {
						break;
					}
				} else {
					logger.warn("No JSONObject found, skipped for deserialization: " + object);
				}
			} catch (NullPointerException ex) {
				throw ex;
			} catch (RuntimeException ex) {
				throw new JSONException(ex);
			}
		}

		return collection;
	}

}
