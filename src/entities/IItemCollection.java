package entities;

import java.util.ArrayList;
import java.util.function.Predicate;

public interface IItemCollection<T> {
/**
 * Filters the collection based on the specified predicate.
 * @param predicate the predicate used to filter the collection
 * @return an ArrayList of objects in the collection that satisfy the predicate
 */
public ArrayList<T> filter(Predicate<T> predicate);

/**
 * Returns the object in the collection with the specified ID.
 * @param id the ID of the object to search for
 * @return the object with the specified ID, or null if no such object exists
 */
public T getById(int id);

/**
 * Returns an ArrayList of all the objects in the collection.
 * @return an ArrayList of all the objects in the collection
 */
public ArrayList<T> getAll();

/**
 * Inserts an object into the collection.
 * @param obj the object to insert into the collection
 */
public void insert(T obj);

}
