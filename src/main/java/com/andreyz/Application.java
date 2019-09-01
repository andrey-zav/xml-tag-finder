package com.andreyz;

import com.andreyz.input.InputArguments;
import com.andreyz.input.InputArgumentsParser;
import com.andreyz.similarity.ElementSimilarityReportGenerator;
import com.andreyz.similarity.MostSimilarElementPathFinder;
import com.andreyz.xml.AllXmlElementsExtractor;
import com.andreyz.xml.ElementByIdExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LongestCommonSubsequence;

@Slf4j
public class Application {
   public static void main(String[] args) {
      InputArguments input = new InputArgumentsParser().parse(args);

      MostSimilarElementPathFinder finder =
            new MostSimilarElementPathFinder(new ElementByIdExtractor(),
                  new AllXmlElementsExtractor(),
                  new ElementSimilarityReportGenerator(new LongestCommonSubsequence()));
      String elementPath = finder.find(input.originFilePath,
            input.anotherFilePath, input.targetElementId);

      log.warn("Answer:\n {}", elementPath);
   }
}
