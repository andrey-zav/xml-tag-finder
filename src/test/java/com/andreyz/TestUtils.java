package com.andreyz;

import java.net.URL;

public class TestUtils {
   public static String getResourceAbsolutePath(String filePath) {
      URL resource = TestUtils.class.getClassLoader().getResource(filePath);

      if (resource == null) {
         throw new IllegalArgumentException("File " + filePath + " not found in resources");
      }

      return resource.getPath();
   }
}
