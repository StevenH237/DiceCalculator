package net.nixill.dice.operations;

import java.util.HashMap;
import java.util.Map;

import net.nixill.dice.objects.DCEntity;

public class Functions {
  private static ThreadLocal<HashMap<String, DCEntity>> functionScopes;
  private static ThreadLocal<HashMap<String, DCEntity>> localScopes;
  private static HashMap<String, DCEntity> globalScopes;
  private static ThreadLocal<HashMap<String, DCEntity>> mergedScopes;

  static {
    functionScopes = ThreadLocal.withInitial(() -> {return new HashMap<>();});
    localScopes = ThreadLocal.withInitial(() -> {return new HashMap<>();});
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

  public static HashMap<String, DCEntity> setGlobals(Map<String, DCEntity> from) {
    HashMap<String, DCEntity> out = globalScopes;
    globalScopes = new HashMap<>(from);
    mergedScopes = newMerged();
    return out;
  }

  public static DCEntity putGlobal(String key, DCEntity value) {
    mergedScopes = newMerged();
    return globalScopes.put(key, value);
  }

  public static DCEntity getGlobal(String key) {
    mergedScopes = newMerged();
    return globalScopes.get(key);
  }

  public static HashMap<String, DCEntity> setLocals(Map<String, DCEntity> from) {
    HashMap<String, DCEntity> out = localScopes.get();
    localScopes.set(new HashMap<>(from));
    mergedScopes.remove();
    return out;
  }

  public static DCEntity putLocal(String key, DCEntity value) {
    mergedScopes.remove();
    return localScopes.get().put(key, value);
  }

  public static DCEntity getLocal(String key) {
    mergedScopes.remove();
    return localScopes.get().get(key);
  }

  public static HashMap<String, DCEntity> clearLocals() {
    HashMap<String, DCEntity> out = localScopes.get();
    localScopes.remove();
    mergedScopes.remove();
    return out;
  }

  public static HashMap<String, DCEntity> setFunctions(Map<String, DCEntity> from) {
    HashMap<String, DCEntity> out = functionScopes.get();
    functionScopes.set(new HashMap<>(from));
    mergedScopes.remove();
    return out;
  }

  public static DCEntity putFunction(String key, DCEntity value) {
    mergedScopes.remove();
    return functionScopes.get().put(key, value);
  }

  public static DCEntity getFunction(String key) {
    mergedScopes.remove();
    return functionScopes.get().get(key);
  }

  public static HashMap<String, DCEntity> clearFunctions() {
    HashMap<String, DCEntity> out = functionScopes.get();
    localScopes.remove();
    mergedScopes.remove();
    return out;
  }

  public static HashMap<String, DCEntity> getAllMerged() {
    return mergedScopes.get();
  }

  public static void clearAllMerged() {
    mergedScopes.remove();
  }
}