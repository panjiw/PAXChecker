package com.github.sunnybat.paxchecker.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author Sunny
 */
public class PreferenceHandler {

  private static final Preferences myPrefs = Preferences.userRoot().node("paxchecker");
  private static final List<Preference> prefArray = new ArrayList<>();

  /**
   * Creates all Preference objects that the program will use. Feels somewhat hacked together at the moment. Can be run more than once without
   * repercussion.
   */
  public static synchronized void init() {
    for (Preference.TYPES pTypes : Preference.TYPES.values()) {
      getPreferenceObject(pTypes);
    }
    getPreferenceObject(Preference.TYPES.LAST_NOTIFICATION_ID).setForceSave();
    getPreferenceObject(Preference.TYPES.SAVE_PREFS).setForceSave();
  }

  protected static synchronized Object loadPreferenceValue(Preference pref) {
    return loadPreferenceValue(pref.getPrefType());
  }

  protected static synchronized Object loadPreferenceValue(Preference.TYPES pref) {
    String value = myPrefs.get(pref.name(), null);
    if (value == null) {
      System.out.println("Pref " + pref.name() + " does not exist!");
      return null;
    }
    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
      return Boolean.parseBoolean(value);
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException nfe) {
      return value;
    }
  }

  public static synchronized boolean getBooleanPreference(Preference.TYPES type) {
    System.out.println("Boolean value of " + type.name() + " = " + Boolean.parseBoolean(String.valueOf(getPreferenceObject(type).getValue())));
    return Boolean.parseBoolean(String.valueOf(getPreferenceObject(type).getValue()));
  }

  /**
   * Gets the integer value for the given preference.
   *
   * @param type The Preference to get the value of
   * @return The integer value, or -1 if preference is not an integer OR does not exist
   */
  public static synchronized int getIntegerPreference(Preference.TYPES type) {
    try {
      return Integer.parseInt(String.valueOf(getPreferenceObject(type).getValue()));
    } catch (NumberFormatException numberFormatException) {
      return -1;
    }
  }

  public static synchronized String getStringPreference(Preference.TYPES type) {
    String str = String.valueOf(getPreferenceObject(type).getValue());
    if (str.equalsIgnoreCase("null")) {
      str = null;
    }
    return str;
  }

  protected static synchronized boolean isInPrefs(Preference.TYPES pref) {
    try {
      String prefT = pref.name();
      String[] keys = myPrefs.keys();
      for (String k : keys) {
        if (k.equalsIgnoreCase(prefT)) {
          System.out.println("Found " + pref.name() + " in Preferences!");
          return true;
        }
      }
    } catch (BackingStoreException bse) {
      bse.printStackTrace();
    }
    return false;
  }

  /**
   * Gets the given Preference object associated with the given TYPES object.
   *
   * @param pref The TYPES object to load the Preference for
   * @return The desired Preference object
   */
  public static synchronized Preference getPreferenceObject(Preference.TYPES pref) {
    for (Preference p : prefArray) {
      if (p.getPrefType() == pref) {
        return p;
      }
    }
    Preference p = new Preference(pref);
    prefArray.add(p);
    return p;
  }

  /**
   * Saves all the preferences for the program. These Preferences should already have been set before calling this.
   */
  public static synchronized void savePreferences() {
    boolean shouldSave = getBooleanPreference(Preference.TYPES.SAVE_PREFS);
    System.out.println("shouldSave = " + shouldSave);
    try {
      for (Preference p : prefArray) {
        if (p.getValue() == null || ((!shouldSave || !p.shouldSave()) && !p.forceSave())) {
          myPrefs.remove(p.getPrefType().name());
        } else {
          myPrefs.put(p.getPrefType().name(), String.valueOf(p.getValue()));
        }
      }
      myPrefs.sync();
    } catch (BackingStoreException bse) {
    }
  }

}
