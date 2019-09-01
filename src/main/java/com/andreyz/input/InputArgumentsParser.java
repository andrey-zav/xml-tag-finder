package com.andreyz.input;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InputArgumentsParser {
   public InputArguments parse(String[] args) {
      if (args.length != 3) {
         throw new IllegalArgumentException(
               "Please provide exactly 3 arguments");
      }

      String originFilePath = args[0];
      String anotherFilePath = args[1];
      String targetElementId = args[2];

      checkNotBlank(originFilePath, "originFilePath");
      checkNotBlank(anotherFilePath, "anotherFilePath");
      checkNotBlank(targetElementId, "targetElementId");

      return InputArguments.builder()
            .originFilePath(originFilePath)
            .anotherFilePath(anotherFilePath)
            .targetElementId(targetElementId)
            .build();
   }

   private static void printUsage() {
      log.error("java -jar <your_bundled_app>.jar <input_origin_file_path> "
            + "<input_other_sample_file_path> <original_element_id>");
   }

   private void checkNotBlank(String arg, String argName) {
      if (arg == null || arg.isEmpty()) {
         printUsage();
         throw new IllegalArgumentException(String.format(
               "%s argument is missing", argName));
      }
   }
}
