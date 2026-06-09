package com.ptit.ktx.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
  private DateUtil() {}
  public static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static LocalDate parseDMY(String v) {
    if (v == null || v.isBlank()) return null;
    return LocalDate.parse(v, DMY);
  }

  public static String formatDMY(LocalDate d) {
    if (d == null) return null;
    return d.format(DMY);
  }
}
