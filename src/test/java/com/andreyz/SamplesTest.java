package com.andreyz;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.andreyz.similarity.ElementSimilarityReportGenerator;
import com.andreyz.similarity.MostSimilarElementPathFinder;
import com.andreyz.xml.AllXmlElementsExtractor;
import com.andreyz.xml.ElementByIdExtractor;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SamplesTest {

   private static final String SAMPLE_ELEMENT_ID = "make-everything-ok-button";
   private static String sampleOriginFilePath;
   private MostSimilarElementPathFinder finder;

   @BeforeAll
   static void beforeAll() {
      sampleOriginFilePath = TestUtils.getResourceAbsolutePath(
            "samples/provided/sample-0-origin.html");
   }

   @BeforeEach
   void before() {
      finder = new MostSimilarElementPathFinder(new ElementByIdExtractor(),
            new AllXmlElementsExtractor(),
            new ElementSimilarityReportGenerator(new LongestCommonSubsequence()));
   }

   @Test
   void testSample1() throws Exception {
      String anotherFilePath =
            TestUtils.getResourceAbsolutePath("samples/provided/sample-1-evil-gemini.html");

      doTest(sampleOriginFilePath, anotherFilePath, SAMPLE_ELEMENT_ID,
            "html->body->div->div->div[2]->div->div->div[1]->a[1]");
   }

   @Test
   void testSample2() throws Exception {
      String anotherFilePath =
            TestUtils.getResourceAbsolutePath("samples/provided/sample-2-container-and-clone.html");

      doTest(sampleOriginFilePath, anotherFilePath, SAMPLE_ELEMENT_ID,
            "html->body->div->div->div[2]->div->div->div[1]->div->a");
   }

   @Test
   void testSample3() throws Exception {
      String anotherFilePath =
            TestUtils.getResourceAbsolutePath("samples/provided/sample-3-the-escape.html");

      doTest(sampleOriginFilePath, anotherFilePath, SAMPLE_ELEMENT_ID,
            "html->body->div->div->div[2]->div->div->div[2]->a");
   }

   @Test
   void testSample4() throws Exception {
      String anotherFilePath =
            TestUtils.getResourceAbsolutePath("samples/provided/sample-4-the-mash.html");

      doTest(sampleOriginFilePath, anotherFilePath, SAMPLE_ELEMENT_ID,
            "html->body->div->div->div[2]->div->div->div[2]->a");
   }

   private void doTest(String originFilePath, String anotherFilePath, String targetElementId,
         String expectedResult) {
      String result = finder.find(originFilePath, anotherFilePath, targetElementId);

      assertThat(result, is(expectedResult));
   }
}

