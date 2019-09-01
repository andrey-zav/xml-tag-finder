package com.andreyz.similarity;

import com.andreyz.xml.AllXmlElementsExtractor;
import com.andreyz.xml.ElementByIdExtractor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Finds the path to the element that is most similar to the element in provided XML document in
 * another XML file. Prints the most similar elements.
 */
@Slf4j
public class MostSimilarElementPathFinder {
   /**
    * Number of top element's similarities to print
    */
   private static final int ELEMENTS_TO_PRINT = 5;

   private final ElementByIdExtractor elementByIdExtractor;
   private final ElementSimilarityReportGenerator similarityReportGenerator;
   private final AllXmlElementsExtractor allXmlElementsExtractor;

   public MostSimilarElementPathFinder(
         ElementByIdExtractor xmlElementByIdExtractor,
         AllXmlElementsExtractor allXmlElementsExtractor,
         ElementSimilarityReportGenerator similarityReportGenerator) {
      this.elementByIdExtractor = xmlElementByIdExtractor;
      this.allXmlElementsExtractor = allXmlElementsExtractor;
      this.similarityReportGenerator = similarityReportGenerator;
   }

   /**
    * Finds the most similar XML element from one file in another file
    *
    * @param originFilePath  original XML file
    * @param anotherFilePath another XNL file
    * @param targetElementId ID of a searched element in original file
    * @return path to founded element in another file.
    */
   public String find(String originFilePath, String anotherFilePath,
         String targetElementId) {
      Element originalElement = elementByIdExtractor
            .extractOriginalElement(originFilePath, targetElementId);
      Elements elementsToCompare = allXmlElementsExtractor
            .extractAllElementFromFile(anotherFilePath);

      List<ElementSimilarity> elements =
            similarityReportGenerator.generateSimilarityReport(originalElement,
                  elementsToCompare);

      printTopSimilarElements(elements, originalElement, anotherFilePath);

      ElementSimilarity elementSimilarity = elements.get(0);

      return buildElementPath(elementSimilarity.getElement());
   }

   private static String buildElementPath(Element element) {
      StringBuilder sb = new StringBuilder();
      Elements parents = element.parents();
      for (int i = parents.size() - 1; i >= 0; i--) {
         Element parentElement = parents.get(i);
         appendElement(sb, parentElement);

         sb.append("->");
      }
      appendElement(sb, element);

      return sb.toString();
   }

   private static void appendElement(StringBuilder sb, Element element) {
      sb.append(element.tagName());

      //if this is not the first such tag in parent,
      //we need to add index
      int siblingIndex = getSublingIndexOfElement(element);
      if (siblingIndex > 0) {
         sb.append("[").append(siblingIndex).append("]");
      }
   }

   private static int getSublingIndexOfElement(Element element) {
      return element.parent()
            .children()
            .stream()
            .filter(sublingTag -> sublingTag.tagName().equals(element.tagName()))
            .collect(Collectors.toList())
            .indexOf(element);
   }

   private static void printTopSimilarElements(List<ElementSimilarity> elements,
         Element originalElement,
         String searchedFile) {
      log.info("Here are top {} similar elements found in file {} for this element:\n"
                  + "{}",
            ELEMENTS_TO_PRINT, searchedFile, originalElement.toString());
      for (int i = 0; i < ELEMENTS_TO_PRINT; i++) {
         ElementSimilarity elementSimilarity = elements.get(i);
         log.info("{}) Similarity: {}", i + 1, elementSimilarity.getSimilarityReport());
         log.info(elementSimilarity.getElement().toString());
      }
   }
}
