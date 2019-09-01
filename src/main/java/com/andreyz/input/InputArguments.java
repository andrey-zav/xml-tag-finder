package com.andreyz.input;

import lombok.Builder;
import lombok.Value;

/**
 * Application command line input parameters.
 */
@Value
@Builder
public class InputArguments {
   /**
    * Absolute path to original file
    */
   public String originFilePath;

   /**
    * Absolute path another file
    */
   public String anotherFilePath;

   /**
    * ID of target element
    */
   public String targetElementId;
}
