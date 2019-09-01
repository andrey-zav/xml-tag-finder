package com.andreyz.xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class ElementByIdExtractor {
   private static final String CHARSET_NAME = "utf8";

   public Element extractOriginalElement(String filePath, String targetElementId) {
      File htmlFile = new File(filePath);

      if (!htmlFile.exists()) {
         throw new IllegalArgumentException(String.format("File %s not found", filePath));
      }

      try {
         Document doc = Jsoup.parse(
               htmlFile,
               CHARSET_NAME,
               htmlFile.getAbsolutePath());

         Element elementById = doc.getElementById(targetElementId);

         if (elementById == null) {
            throw new IllegalArgumentException(String.format(
                  "Failed to find element with ID %s in file %s",
                  targetElementId, filePath));
         }

         return elementById;
      } catch (IOException e) {
         throw new RuntimeException(
               "Failed to extract original element from file " + filePath, e);
      }
   }
}
