package com.pe.poweressentials.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.vincentrussell.ini.Ini;
import com.pe.poweressentials.Loader;
import com.pe.poweressentials.utils.StringArrayMultiton;

public class PELang extends StringArrayMultiton {

  public static final String LANGUAGE_EXTENSION = "ini";
  public static final String FALLBACK_LANGUAGE = "en_us";

  private static String consoleLocale = FALLBACK_LANGUAGE;
  private static String defaultSection = "main";
  private Ini ini;

  public PELang(Loader loader) {
    super(consoleLocale);
    File file = new File(loader.getDataFolder() + "/language/" + FALLBACK_LANGUAGE + "." + LANGUAGE_EXTENSION);
    if (!file.exists()) {
      loader.saveResource("language/" + FALLBACK_LANGUAGE + "." + LANGUAGE_EXTENSION);
    }

    try {
      this.ini = new Ini();
      this.ini.load(new FileInputStream(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getLang() {
    return consoleLocale;
  }

  public String translateString(@Nonnull String key, @Nullable String[] params) {
    return this.translateString(key, defaultSection, params);
  }

  public String translateString(@Nonnull String key, @Nullable String section) {
    return this.translateString(key, section, null);
  }

  public String translateString(@Nonnull String key) {
    return this.translateString(key, defaultSection, null);
  }

  public String translateString(@Nonnull String key, @Nullable String section, @Nullable String[] params) {
    String str = this.ini.getValue(section, key, String.class);
    if (str == null || params == null) {
      return str;
    }

    for (int i = 0; i < params.length; i++) {
      str = str.replace("%" + i, params[i] == null ? "null" : params[i].toString());
    }

    return str;
  }

  public static void setConsoleLocale(String locale) {
    consoleLocale = locale;
  }

  public static PELang fromConsole() {
    return fromLocale(consoleLocale);
  }

  public static PELang fromLocale(String locale) {
    PELang instance = (PELang) getInstance(locale);
    if (instance == null) {
      instance = (PELang) getInstance(FALLBACK_LANGUAGE);
    }
    return instance;
  }
}
