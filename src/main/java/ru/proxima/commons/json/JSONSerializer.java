package ru.proxima.commons.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Статический построитель JSON-массивов из сущностей
 * @author Шомин Владимир, ЗАО ИВЦ Инсофт
 */
public final class JSONSerializer {

	private final static Logger logger = LoggerFactory.getLogger(JSONSerializer.class);
/**
 * Построитель статический, нечего пытаться создать его экземпляр
 */
	private JSONSerializer(){}
/**
 * Создание JSON-объекта из экземпляра сущности
 * @param <E> Тип сериализуемой сущности
 * @param object Экземпляр сущности
 * @param builder Фабрика, создающая JSON-объект из сущности
 * @return JSONObject Сериализованная в JSONObject сущность
 * @throws NullPointerException object или factory равны null
 */
	public static <E> JSONObject build(E object, JSONBuilder<? super E> builder)
			throws NullPointerException {
		JSONObject jsonObject = new JSONObject();
		builder.build(object, jsonObject);
		return jsonObject;
	}
/**
 * Создание JSON-массива из набора экземпляров сущности
 * @param <E> Тип сериализуемой сущности
 * @param objects Перечисление сущностей
 * @param builder Фабрика, создающая JSON-объекты из сущностей
 * @return JSONArray Сериализованное в JSONArray перечисление сущностей
 * @throws NullPointerException objects или factory равны null
 */
	public static <E> JSONArray build(Iterable<E> objects, JSONBuilder<? super E> builder)
			throws NullPointerException {
		return build(objects, builder, 0);
	}
/**
 * Создание JSON-массива из набора экземпляров сущности, с ограничением максимального размера
 * @param <E> Тип сериализуемой сущности
 * @param objects Перечисление сущностей
 * @param builder Фабрика, создающая JSON-объекты из сущностей
 * @param maxResults Максимальное количество объектов в массиве (0 - без ограничения)
 * @return JSONArray Сериализованное в JSONArray перечисление сущностей
 * @throws IllegalArgumentException maxResults является отрицательным
 * @throws NullPointerException objects или factory равны null
 */
	public static <E> JSONArray build(
		Iterable<E> objects, JSONBuilder<? super E> builder, int maxResults)
			throws IllegalArgumentException, NullPointerException {
		if (maxResults < 0) {
			throw new IllegalArgumentException();
		}

		JSONArray jsonArray = new JSONArray();

		for (E object : objects) {
			try {
				JSONObject jsonObject = new JSONObject();
				builder.build(object, jsonObject);
				jsonArray.put(jsonObject);

				if (--maxResults == 0) {
					break;
				}
			} catch (NullPointerException ex) {
				throw ex;
			} catch (RuntimeException ex) {
				logger.warn("Error due factoring an JSON object from entity, skipped one", ex);
			}
		}

		return jsonArray;
	}

}
