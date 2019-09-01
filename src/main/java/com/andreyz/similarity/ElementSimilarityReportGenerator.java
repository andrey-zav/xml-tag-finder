package com.andreyz.similarity;

import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Searches for the similar XML elements in a provided list and returns the descending ordered (most
 * similar elements comes first) list of each element's similarity.
 */
public class ElementSimilarityReportGenerator {
   private final LongestCommonSubsequence lgc;

   public ElementSimilarityReportGenerator(
         LongestCommonSubsequence lgc) {
      this.lgc = lgc;
   }

   /**
    * Calculates the similarities of elements in a given list to provided reference element.
    *
    * @param originalElement reference element
    * @param elements list of elements to compare
    * @return element's similarities in descending order (similar elements comes first)
    */
   public List<ElementSimilarity> generateSimilarityReport(Element originalElement,
         Elements elements) {

      return elements.stream()
            .map(element -> calculateSimilarity(originalElement, element))
            .sorted(Comparator.comparing(s -> s.getSimilarityReport().getTotalScore(),
                  Comparator.reverseOrder()))
            .collect(Collectors.toList());
   }

   private ElementSimilarity calculateSimilarity(Element originalElement, Element element) {
      Double tagNameScore = compareTagNames(originalElement.tagName(), element.tagName());
      Double attributesScore =
            compareAttributes(originalElement.attributes(), element.attributes());

      Double overallSimilarityScore = DoubleStream.of(tagNameScore, attributesScore).average()
            .orElse(0);
      SimilarityReport similarityReport =
            new SimilarityReport(tagNameScore, attributesScore, overallSimilarityScore);

      return new ElementSimilarity(originalElement, element, similarityReport);
   }

   private double compareAttributes(Attributes originalAttributes, Attributes anotherAttributes) {
      int originalAttributesCount = originalAttributes.size() - 1;
      double totalMatchingScore = 0;
      for (Attribute originalAttribute : originalAttributes) {
         if (originalAttribute.getKey().equals("id")) {
            continue;
         }
         double score = 0;

         String originalValue = originalAttribute.getValue();
         String anotherValue = anotherAttributes.get(originalAttribute.getKey());

         if (anotherValue != null) {
            Integer lgcAmount = lgc.apply(originalValue, anotherValue);

            score += (double) lgcAmount / Math.max(originalValue.length(), anotherValue.length());
         }

         totalMatchingScore += score;
      }

      return totalMatchingScore / originalAttributesCount;
   }

   private double compareTagNames(String originalTagName, String anotherTagName) {
      return originalTagName.equalsIgnoreCase(anotherTagName) ?
            1 : 0;
   }
}
