package com.andreyz.similarity;

import lombok.Value;
import org.jsoup.nodes.Element;

/**
 * Represents the similarity of some element to
 * the reference element
 */
@Value
class ElementSimilarity {
   /**
    * Reference element
    */
   private Element referenceElement;

   /**
    * This element
    */
   private Element element;

   /**
    * Some details on similarity
    */
   private SimilarityReport similarityReport;
}
