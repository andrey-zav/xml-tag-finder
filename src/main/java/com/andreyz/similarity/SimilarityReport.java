package com.andreyz.similarity;

import lombok.Value;

/**
 * Details on elements similarity
 */
@Value
public class SimilarityReport {
   /**
    * Shows how similar are tag names of elements
    */
   public Double tagNameScore;

   /**
    * Shows how similar are elements attributes
    */
   public Double attributesScore;

   /**
    * Overall similarity score
    */
   public Double totalScore;
}
