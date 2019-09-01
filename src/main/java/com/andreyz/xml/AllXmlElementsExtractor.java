package com.andreyz.xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Extracts all XML elements from the given XML file.
 */
public class AllXmlElementsExtractor {
   private static final String CHARSET_NAME = "utf8";

   public Elements extractAllElementFromFile(String filePath) {
      File htmlFile = new File(filePath);
      Document document;
      try {
         document = Jsoup.parse(
               htmlFile,
               CHARSET_NAME,
               htmlFile.getAbsolutePath());
      } catch (IOException e) {
         throw new RuntimeException(
               "Failed to open file " + filePath, e);
      }

      return document.body().select("*");
   }
}
