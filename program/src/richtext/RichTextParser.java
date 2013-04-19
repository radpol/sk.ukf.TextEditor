package richtext;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public final class RichTextParser {
 
    public static RichTextParser parse(String formattedText)
    throws ParserConfigurationException, SAXException, IOException {
        return new RichTextParser(formattedText);
    }
 
    private StringBuilder text = new StringBuilder();
 
    private List<StyleRange> styleRangeList = new ArrayList<StyleRange>();
 
    private RichTextParser(String formattedText)
    throws ParserConfigurationException, SAXException, IOException {
        StringReader reader = new StringReader(formattedText);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        DefaultHandler handler = new RichTextContentHandler();
        parser.parse(new InputSource(reader), handler);
    }
 
    public String getText() {
        return text.toString();
    }
 
    public StyleRange[] getStyleRanges() {
        return styleRangeList.toArray(new StyleRange[styleRangeList.size()]);
    }

    
    
    private class RichTextContentHandler extends DefaultHandler {
 
        private Stack<List<FontStyle>> stylesStack = new Stack<List<FontStyle>>();
        private String lastTextChunk = null;
 /**
  * {@link org.xml.sax.helpers.DefaultHandler.characters}
  */
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastTextChunk = new String(ch, start, length);
        }
 
        @Override
        public void endElement(String uri, String localName, String qName)
        throws SAXException {
            // If there is not any previous text chunk parsed then return
            if (lastTextChunk == null) return;
            // If the tag found is not a supported one then return
            boolean recognized = ("p".equals(qName)  || "b".equals(qName)  || "i".equals(qName) ||
					"ins".equals(qName)  || "del".equals(qName) || "cc1".equals(qName));
			if (!recognized ) {
                return;
            }
 
            List<FontStyle> lastStyles = lastFontStyles(true);
            if (lastStyles != null) {
                StyleRange range = transformToStyleRange(lastStyles);
                range.start = currentIndex() + 1;
                range.length = lastTextChunk.length();
                styleRangeList.add(range);
            }
 
            text.append(lastTextChunk);
            lastTextChunk = null;
        }
        

 
        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes atts) throws SAXException {
            // If the tag found is not a supported one then return
        	
			boolean recognized = ("p".equals(qName)  || "b".equals(qName)  || "i".equals(qName) ||
					"ins".equals(qName)  || "del".equals(qName)|| "cc1".equals(qName) );
			if (!recognized ) {
                return;
            }

 
            List<FontStyle> lastStyles = lastFontStyles(false);
            if (lastTextChunk == null) {
                if (lastStyles == null) {
                    lastStyles = new ArrayList<FontStyle>();
                    stylesStack.add(lastStyles);
                }
            } else {
                if (lastStyles != null) {
                    StyleRange range = transformToStyleRange(lastStyles);
                    range.start = currentIndex() + 1;
                    range.length = lastTextChunk.length();
                    styleRangeList.add(range);
                }
 
                text.append(lastTextChunk);
                lastTextChunk = null;
            }
            
            String d = atts.getValue(0);
            String a = atts.getValue(atts.getQName(0));
            
            
            if ("b".equals(qName)) {
                lastStyles.add(FontStyle.BOLD);
            } else if ("i".equals(qName)) {
                lastStyles.add(FontStyle.ITALIC);
            } else if ("ins".equals(qName)) {
                lastStyles.add(FontStyle.UNDERLINE);
            } else if ("del".equals(qName)){
            	lastStyles.add(FontStyle.STRIKE_THROUGH);
            } else if ("cc1".equals(qName)) {
                lastStyles.add(FontStyle.CUSTOM_COLOR_1);
            } else {
            	//TODO error code
            	lastStyles.add(null);
            }
            
        }
 
        private StyleRange transformToStyleRange(List<FontStyle> styles) {
            StyleRange range = new StyleRange();
            range.start = currentIndex() + 1;
            range.length = lastTextChunk.length();
            for (FontStyle fs : styles) {
            	if (fs!=null){
            		
            	
            	switch (fs) {
				case BOLD:
					range.fontStyle += SWT.BOLD;
					break;
				case ITALIC:
					range.fontStyle += SWT.ITALIC;
					break;
				case UNDERLINE:
					range.underline = true;
					break;
				case STRIKE_THROUGH:
					range.strikeout = true;
					break;
				case CUSTOM_COLOR_1:
					range.foreground = JFaceResources.getColorRegistry().get(fs.name());
					break;
				case CUSTOM_COLOR_2:
					break;
				case CUSTOM_COLOR_3:
					break;
				case CUSTOM_FONT_3:
					break;
				case CUSTOM_FONT_1:
					break;
				case CUSTOM_FONT_2:
					break;
				default:
					System.out.println("aaaaaaaa");
					break;
				}
            	}

            }
            return range;
        }
 
        private List<FontStyle> lastFontStyles(boolean remove) {
            List<FontStyle> lastStyles = null;
            if (stylesStack.size() > 0) {
                if (remove) {
                    lastStyles = stylesStack.pop();
                } else {
                    lastStyles = stylesStack.peek();
                }
            }
            return lastStyles;
        }
 
        private int currentIndex() {
            return text.length() - 1;
        }
 
    }
 
}

