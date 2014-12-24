package ca.csf.rdore.pinmarks.util;

import java.util.UUID;

import org.jsoup.Jsoup;


/**
 * <p>Miscellaneous helpful utilities</p>
 * 
 * @author Raphael Doré
 * @version 0.1
 */
public class MiscUtils {

  /**
   * <p>
   * Generates a pseudo random 32 character string
   * </p>
   * 
   * @return the random 32 character string
   */
  public static String GenerateRandomSlug() {
    UUID uniqueSlug = UUID.randomUUID();
    return uniqueSlug.toString().replace("-", "");
  }
  
  public static String inputToPureText(String input) {
    return Jsoup.parse(input).text();
  }
}
