package ru.proxima.commons.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Статический построитель JSON-массивов из сущностей
 * @author Шомин Владимир, ЗАО ИВЦ Инсофт
 */
public final class JSONBuilder {

	private final static Logger logger = LoggerFactory.getLogger(JSONBuilder.class);
/**
 * Построитель статический, нечего пытаться создать его экземпляр
 */
	private JSONBuilder(){}
/**
 * Создание JSON-объекта из экземпляра сущности
 * @param	object		Экземпляр сущности
 * @param	factory		Фабрика, создающая JSON-объект из сущности
 * @return	JSONObject	Сериализованная в JSONObject сущность
 * @throws	NullPointerException		object или factory равны null
 */
	public static <E> JSONObject build(E object, JSONFactory<? super E> factory)
			throws NullPointerException {
		JSONObject jsonObject = new JSONObject();
		factory.build(object, jsonObject);
		return jsonObject;
	}
/**
 * Создание JSON-массива из набора экземпляров сущности
 * @param	objects		Перечисление сущностей
 * @param	factory		Фабрика, создающая JSON-объекты из сущностей
 * @return	JSONArray	Сериализованное в JSONArray перечисление сущностей
 * @throws	NullPointerException	objects или factory равны null
 */
	public static <E> JSONArray build(Iterable<E> objects, JSONFactory<? super E> factory)
			throws NullPointerException {
		return build(objects, factory, 0);
	}
/**
 * Создание JSON-массива из набора экземпляров сущности, с ограничением максимального размера
 * @param	objects		Перечисление сущностей
 * @param	factory		Фабрика, создающая JSON-объекты из сущностей
 * @param	maxResults	Максимальное количество объектов в массиве (0 - без ограничения)
 * @return	JSONArray	Сериализованное в JSONArray перечисление сущностей
 * @throws	IllegalArgumentException	maxResults является отрицательным
 * @throws	NullPointerException		objects или factory равны null
 */
	public static <E> JSONArray build(
			Iterable<E> objects, JSONFactory<? super E> factory, int maxResults)
			throws IllegalArgumentException, NullPointerException {
		if (maxResults < 0) {
			throw new IllegalArgumentException();
		}

		JSONArray jsonArray = new JSONArray();

		for (E object : objects) {
			try {
				JSONObject jsonObject = new JSONObject();
				factory.build(object, jsonObject);
				jsonArray.put(jsonObject);

				if (--maxResults == 0) {
					break;
				}
			} catch (RuntimeException ex) {
				logger.warn("Error due factoring an JSON object from entity", ex);
			}
		}

		return jsonArray;
	}

}
