package com.example;

import java.util.List;

/**
 * Interfaz genérica para acceder a los datos (Data Access Object - DAO).
 *
 * @param <T> Tipo de entidad que se gestionará mediante la interfaz.
 */
public interface DAO<T> {

    /**
     * Obtiene una lista de todas las entidades del tipo especificado.
     *
     * @return Lista de entidades.
     */
    public List<T> getAll();

    /**
     * Obtiene una entidad por su identificador único.
     *
     * @param id Identificador único de la entidad.
     * @return La entidad correspondiente al identificador o null si no se encuentra.
     */
    public T get(Long id);

    /**
     * Guarda una nueva entidad o actualiza una existente.
     *
     * @param data La entidad a guardar o actualizar.
     * @return La entidad guardada o actualizada.
     */
    public T save(T data);

    /**
     * Actualiza una entidad existente.
     *
     * @param data La entidad a actualizar.
     */
    public void update(T data);

    /**
     * Elimina una entidad.
     *
     * @param data La entidad a eliminar.
     */
    public void delete(T data);
}