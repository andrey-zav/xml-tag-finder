This utility finds the similar XML element from one file in another XML file.

### Building:

`mvn clean package`

### Usage:

`java -jar xml-tag-finder.jar <absolute-path-to-origin-file> <absolute-path-to-another-file> <searched-element-id>
`
e.g.

`java -jar xml-tag-finder.jar /Users/monig/code/xml-tag-finder/src/test/resources/samples/provided/sample-0-origin.html /Users/monig/code/xml-tag-finder/src/test/resources/samples/provided/sample-1-evil-gemini.html make-everything-ok-button
`