package net.nixill.dice.operations;

import java.util.HashMap;
import java.util.Map;

import net.nixill.dice.objects.DCEntity;

/**
 * A class that stores saved {@link Function}s per thread and allows them to be
 * accessed statically.
 * <p>
 * The "global" scope is a set of named functions that is shared between
 * threads. The "local" scope is a set used by a single thread, but persisting
 * for the life of that thread. The "function" scope is a set used internally by
 * Functions, passing around things like parameters.
 */
public class SavedFunctions {
  private static ThreadLocal<HashMap<String, DCEntity>> functionScopes;
  private static ThreadLocal<HashMap<String, DCEntity>> localScopes;
  private static HashMap<String, DCEntity> globalScopes = new HashMap<>();
  private static ThreadLocal<HashMap<String, DCEntity>> mergedScopes;

  static {
    functionScopes = ThreadLocal.withInitial(() -> {
      return new HashMap<>();
    });
    localScopes = ThreadLocal.withInitial(() -> {
      return new HashMap<>();
    });
    mergedScopes = newMerged();
  }

  private static ThreadLocal<HashMap<String, DCEntity>> newMerged() {
    return ThreadLocal.withInitial(() -> {
      HashMap<String, DCEntity> out = new HashMap<>();
      out.putAll(globalScopes);
      out.putAll(localScopes.get());
      out.putAll(functionScopes.get());
      return out;
    });
  }

  /**
   * Replaces the global scope with a new set of named functions.
   * 
   * @param from The source map of functions.
   * @return The previous map of functions.
   */
  public static HashMap<String, DCEntity> setGlobals(Map<String, DCEntity> from) {
    HashMap<String, DCEntity> out = globalScopes;
    globalScopes = new HashMap<>(from);
    mergedScopes = newMerged();
    return out;
  }

  /**
   * Adds a new function to the global scope.
   * 
   * @param key   The name of the function.
   * @param value What the name should map to.
   * @return The previous function by that name, if any.
   */
  public static DCEntity putGlobal(String key, DCEntity value) {
    mergedScopes = newMerged();
    return globalScopes.put(key, value);
  }

  /**
   * Retrieves a function from the global scope.
   * 
   * @param key The name of the function.
   * @return The function by that name, if any.
   */
  public static DCEntity getGlobal(String key) {
    mergedScopes = newMerged();
    return globalScopes.get(key);
  }

  /**
   * Replaces the local scope with a new set of functions.
   * 
   * @param from The source map of functions.
   * @return The previous map, if any.
   */
  public static HashMap<String, DCEntity> setLocals(Map<String, DCEntity> from) {
    HashMap<String, DCEntity> out = localScopes.get();
    localScopes.set(new HashMap<>(from));
    mergedScopes.remove();
    return out;
  }

  /**
   * Put a new function in the local scope.
   * 
   * @param key   The name of the function.
   * @param value What the name should map to.
   * @return The previous function by that name, if any.
   */
  public static DCEntity putLocal(String key, DCEntity value) {
    mergedScopes.remove();
    return localScopes.get().put(key, value);
  }

  /**
   * Retrieves a function from the local scope.
   * 
   * @param key The name of the function.
   * @return The function by that name, if any.
   */
  public static DCEntity getLocal(String key) {
    mergedScopes.remove();
    return localScopes.get().get(key);
  }

  /**
   * Clears the local scope entirely.
   * 
   * @return The previous map of local scope functions.
   */
  public static HashMap<String, DCEntity> clearLocals() {
    HashMap<String, DCEntity> out = localScopes.get();
    localScopes.remove();
    mergedScopes.remove();
    return out;
  }

  /**
   * Replaces the function scope with a new set of functions.
   * 
   * @param from The source map of functions.
   * @return The previous map, if any.
   */
  public static HashMap<String, DCEntity> setFunctions(Map<String, DCEntity> from) {
    HashMap<String, DCEntity> out = functionScopes.get();
    functionScopes.set(new HashMap<>(from));
    mergedScopes.remove();
    return out;
  }

  /**
   * Put a new function in the function scope.
   * 
   * @param key   The name of the function.
   * @param value What the name should map to.
   * @return The previous function by that name, if any.
   */
  public static DCEntity putFunction(String key, DCEntity value) {
    mergedScopes.remove();
    return functionScopes.get().put(key, value);
  }

  /**
   * Retrieves a function from the function scope.
   * 
   * @param key The name of the function.
   * @return The function by that name, if any.
   */
  public static DCEntity getFunction(String key) {
    mergedScopes.remove();
    return functionScopes.get().get(key);
  }

  /**
   * Clears the function scope entirely.
   * 
   * @return The previous map of local scope functions.
   */
  public static HashMap<String, DCEntity> clearFunctions() {
    HashMap<String, DCEntity> out = functionScopes.get();
    localScopes.remove();
    mergedScopes.remove();
    return out;
  }

  /**
   * Gets the functions from all three scopes.
   */
  public static HashMap<String, DCEntity> getAllMerged() {
    return mergedScopes.get();
  }

  /**
   * Clears the merged scope for a refresh.
   */
  public static void clearAllMerged() {
    mergedScopes.remove();
  }
}