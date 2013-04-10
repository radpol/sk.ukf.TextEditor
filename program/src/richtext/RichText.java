package richtext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.internal.JFaceActivator;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.xml.sax.SAXException;

public class RichText extends Composite {

	private List<StyleRange> cachedStyles = new LinkedList<StyleRange>();

	private ToolBar toolBar;
	private StyledText styledText;

	private ToolItem boldBtn;
	private ToolItem italicBtn;
	private ToolItem strikeThroughBtn;
	private ToolItem underlineBtn;

	private ToolItem pasteBtn;
	private ToolItem eraserBtn;
	private Button btnNewButton_1;
	private ToolItem tltmNewItem;
	private ToolItem tltmNewItem_1;

	private ToolItem tltmNewItem_2;
	private ToolItem tltmNewItem_3;
	private ToolItem tltmNewItem_4;
	private ToolItem tltmNewItem_5;

	public RichText(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}

/*
 * Set the foreground color for the selected text.
 */
private void fgColor(Color fg) {
	Point sel = styledText.getSelectionRange();
	if ((sel == null) || (sel.y == 0))
		return;
	StyleRange style, range;
	for (int i = sel.x; i < sel.x + sel.y; i++) {
		range = styledText.getStyleRangeAtOffset(i);
		if (range != null) {
			style = (StyleRange) range.clone();
			style.start = i;
			style.length = 1;
			style.foreground = fg;
		} else {
			style = new StyleRange(i, 1, fg, null, SWT.NORMAL);
		}
		styledText.setStyleRange(style);
	}
	styledText.setSelectionRange(sel.x + sel.y, 0);
}
	private ToolBar createToolBar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT);
		boldBtn = new ToolItem(toolBar, SWT.CHECK);
		boldBtn.setImage(RichTextImages.getImgBold());
		boldBtn.setToolTipText(RichTextStrings.boldBtn_tooltipText);
		boldBtn
				.addSelectionListener(new FontStyleButtonListener(
						FontStyle.BOLD));

		italicBtn = new ToolItem(toolBar, SWT.CHECK);
		italicBtn.setImage(RichTextImages.getImgItalic());
		italicBtn.setToolTipText(RichTextStrings.italicBtn_tooltipText);
		italicBtn.addSelectionListener(new FontStyleButtonListener(
				FontStyle.ITALIC));

		underlineBtn = new ToolItem(toolBar, SWT.CHECK);
		underlineBtn.setImage(RichTextImages.getImgUnderline());
		underlineBtn.setToolTipText(RichTextStrings.underlineBtn_tooltipText);
		underlineBtn.addSelectionListener(new FontStyleButtonListener(
				FontStyle.UNDERLINE));

		strikeThroughBtn = new ToolItem(toolBar, SWT.CHECK);
		strikeThroughBtn.setImage(RichTextImages.getImgStrikeThrough());
		strikeThroughBtn
				.setToolTipText(RichTextStrings.strikeThroughBtn_tooltipText);
		strikeThroughBtn.addSelectionListener(new FontStyleButtonListener(
				FontStyle.STRIKE_THROUGH));

		ToolItem cutBtn = new ToolItem(toolBar, SWT.PUSH);
		cutBtn.setImage(RichTextImages.getImgCut());
		cutBtn.setToolTipText(RichTextStrings.cutBtn_tooltipText);
		cutBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleCutCopy();
				styledText.cut();
			}
		});

		ToolItem copyBtn = new ToolItem(toolBar, SWT.PUSH);
		copyBtn.setImage(RichTextImages.getImgCopy());
		copyBtn.setToolTipText(RichTextStrings.copyBtn_tooltipText);
		copyBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleCutCopy();
				styledText.copy();
			}
		});

		pasteBtn = new ToolItem(toolBar, SWT.PUSH);
		pasteBtn.setEnabled(false);
		pasteBtn.setImage(RichTextImages.getImgPaste());
		pasteBtn.setToolTipText(RichTextStrings.pasteBtn_tooltipText);
		pasteBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.paste();
			}
		});
		
				new ToolItem(toolBar, SWT.SEPARATOR);

		eraserBtn = new ToolItem(toolBar, SWT.PUSH);
		eraserBtn.setEnabled(false);
		eraserBtn.setImage(RichTextImages.getImgEraser());
		eraserBtn.setToolTipText(RichTextStrings.eraserBtn_tooltipText);
		
		tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(getFormattedText());
			}
		});
		tltmNewItem.setText("gettext");
		
		tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					StyleRange[] aaa = styledText.getStyleRanges();
					System.out.println(aaa);
					setFormattedText("<p>a<b>a</b><b><i>a</i></b></p>");
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		tltmNewItem_1.setText("settext");
		
		
		final FontDialog fontdialog = new FontDialog(parent.getShell());
		tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
					StyleRange[] aaa = styledText.getStyleRanges();
	            	for (int i = 0; i < aaa.length; i++) {
	            		System.out.println(aaa[i].font);
					}
	                StyleRange styleRange = new StyleRange();
	                styleRange.start = styledText.getSelectionRange().x;
	                styleRange.length = styledText.getSelectionRange().y;
	                FontData fd = fontdialog.open();
	                		FontData fdarray[] = 	JFaceResources.getDialogFontDescriptor().createFrom("arial", 20, 0).getFontData();
	                		//JFaceResources.getFontRegistry().
	                styleRange.font = new Font(null, fd);
	                
	                styleRange.foreground = new Color(Display.getCurrent(),
	                        fontdialog.getRGB());
	                styledText.setStyleRange(styleRange);
//	                try {
//					setFormattedText("<p>a<b>a</b><b><i>a</i></b></p>");
//				} catch (ParserConfigurationException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (SAXException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
		tltmNewItem_2.setText("setfont");
		
		
		
		tltmNewItem_3 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_3.setText("red");
		tltmNewItem_3.setImage(RichTextImages.getImgUnderline());
		//tltmNewItem_3.setToolTipText(RichTextStrings.underlineBtn_tooltipText);
		tltmNewItem_3.addSelectionListener(new FontStyleButtonListener(
				FontStyle.CUSTOM_COLOR_1));
		
		tltmNewItem_4 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_4.setText("green");
		tltmNewItem_4.setImage(RichTextImages.getImgUnderline());
		//tltmNewItem_4.setToolTipText(RichTextStrings.underlineBtn_tooltipText);
		tltmNewItem_4.addSelectionListener(new FontStyleButtonListener(
				FontStyle.CUSTOM_COLOR_2));
		
		tltmNewItem_5 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_5.setText("blue");
		tltmNewItem_5.setImage(RichTextImages.getImgUnderline());
		//tltmNewItem_5.setToolTipText(RichTextStrings.underlineBtn_tooltipText);
		tltmNewItem_5.addSelectionListener(new FontStyleButtonListener(
				FontStyle.CUSTOM_COLOR_3));
		
		
		
		eraserBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clearStylesFromSelection();
			}
		});
		return toolBar;
	}
	
	/**
	 * Obtain an HTML formatted text from the component contents
	 * 
	 * @return an HTML formatted text
	 */

public String getFormattedText() {
    String plainText = styledText.getText();

    RichStringBuilder builder = new RichStringBuilder();
    Integer[] lineBreaks = getLineBreaks();

    int brIdx = 0;
    int start = 0;
    int end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length() - 1);

    while (start < end) {
        builder.startParagraph();
        StyleRange[] ranges = styledText.getStyleRanges(start, (end - start));
        if (ranges != null && ranges.length > 0) {
            for (int i = 0;i < ranges.length;i++) {
                if (start < ranges[i].start) {
                    builder.append(plainText.substring(start, ranges[i].start));
                }

                List<FontStyle> styles = translateStyle(ranges[i]);
                builder.startFontStyles((FontStyle[]) styles.toArray(new FontStyle[styles.size()]));
                builder.append(plainText.substring(ranges[i].start,
                        ranges[i].start + ranges[i].length));
                builder.endFontStyles(styles.size());

                start = (ranges[i].start + ranges[i].length) + 1;
            }
        }
        if (start < end) {
            builder.append(plainText.substring(start, end));
        }
        start = end + styledText.getLineDelimiter().length();
        end = (lineBreaks.length > brIdx ? lineBreaks[brIdx++] : plainText.length() - 1);
        builder.endParagraph();
    }

    return builder.toString();
}

public void setFormattedText(String text)
throws ParserConfigurationException, SAXException, IOException {
    RichTextParser parser = RichTextParser.parse(text);
    styledText.setText(parser.getText());
    styledText.setStyleRanges(parser.getStyleRanges());
}

protected void applyFontStyleToSelection(FontStyle style) {
    Point sel = styledText.getSelectionRange();
    if ((sel == null) || (sel.y == 0)) {
        return ;
    }

    StyleRange newStyle;
    for (int i = sel.x; i < (sel.x + sel.y); i++) {
        StyleRange range = styledText.getStyleRangeAtOffset(i);
        if (range != null) {
            newStyle = (StyleRange) range.clone();
            newStyle.start = i;
            newStyle.length = 1;
        } else {
            newStyle = new StyleRange(i, 1, null, null, SWT.NORMAL);
        }

        switch (style) {
        case BOLD:
            newStyle.fontStyle ^= SWT.BOLD;
            break;
        case ITALIC:
            newStyle.fontStyle ^= SWT.ITALIC;
            break;
        case STRIKE_THROUGH:
            newStyle.strikeout = !newStyle.strikeout;
            break;
        case UNDERLINE:
            newStyle.underline = !newStyle.underline;
            break;
        case CUSTOM_COLOR_1:
        	newStyle.foreground = JFaceResources.getColorRegistry().get(style.toString());
        	break;
        case CUSTOM_COLOR_2:
        	newStyle.foreground = JFaceResources.getColorRegistry().get(style.toString());
        	break;
        case CUSTOM_COLOR_3:
        	newStyle.foreground = JFaceResources.getColorRegistry().get(style.toString());
        	break;
        	
        }

        styledText.setStyleRange(newStyle);
    }

    styledText.setSelectionRange(sel.x + sel.y, 0);
}

/**
 * Clear all styled data
 */
protected void clearStylesFromSelection() {
    Point sel = styledText.getSelectionRange();
    if ((sel != null) && (sel.y != 0)) {
        StyleRange style = new StyleRange(
                sel.x, sel.y, null, null, SWT.NORMAL);
        styledText.setStyleRange(style);
    }
    styledText.setSelectionRange(sel.x + sel.y, 0);
}

private Integer[] getLineBreaks() {
    List<Integer> list = new ArrayList<Integer>();
    int lastIdx = 0;
    while (lastIdx < styledText.getCharCount()) {
        int br = styledText.getText().indexOf(
                styledText.getLineDelimiter(), lastIdx);
        if (br >= lastIdx && !list.contains(br)) {
            list.add(br);
        }
        lastIdx += styledText.getLineDelimiter().length() + 1;
    }
    Collections.sort(list);
    return (Integer[]) list.toArray(new Integer[list.size()]);
}

protected void handleCutCopy() {
    // Save the cut/copied style info so that during paste we will maintain
    // the style information. Cut/copied text is put in the clipboard in
    // RTF format, but is not pasted in RTF format. The other way to
    // handle the pasting of styles would be to access the Clipboard
    // directly and
    // parse the RTF text.
    cachedStyles = new LinkedList<StyleRange>();
    Point sel = styledText.getSelectionRange();
    int startX = sel.x;
    for (int i = sel.x; i <= sel.x + sel.y - 1; i++) {
        StyleRange style = styledText.getStyleRangeAtOffset(i);
        if (style != null) {
            style.start = style.start - startX;
            if (!cachedStyles.isEmpty()) {
                StyleRange lastStyle = cachedStyles
                        .get(cachedStyles.size() - 1);
                if (lastStyle.similarTo(style)
                        && lastStyle.start + lastStyle.length == style.start) {
                    lastStyle.length++;
                } else {
                    cachedStyles.add(style);
                }
            } else {
                cachedStyles.add(style);
            }
        }
    }
    pasteBtn.setEnabled(true);
}

private void handleExtendedModified(ExtendedModifyEvent event) {
    if (event.length == 0) return;

    StyleRange style;
    if (event.length == 1
            || styledText.getTextRange(event.start, event.length).equals(
                    styledText.getLineDelimiter())) {
        // Have the new text take on the style of the text to its right
        // (during
        // typing) if no style information is active.
        int caretOffset = styledText.getCaretOffset();
        style = null;
        if (caretOffset < styledText.getCharCount())
            style = styledText.getStyleRangeAtOffset(caretOffset);
        if (style != null) {
            style = (StyleRange) style.clone();
            style.start = event.start;
            style.length = event.length;
        } else {
            style = new StyleRange(event.start, event.length, null, null,
                    SWT.NORMAL);
        }
        if (boldBtn.getSelection())
            style.fontStyle |= SWT.BOLD;
        if (italicBtn.getSelection())
            style.fontStyle |= SWT.ITALIC;
        style.underline = underlineBtn.getSelection();
        style.strikeout = strikeThroughBtn.getSelection();
        if (!style.isUnstyled())
            styledText.setStyleRange(style);
    } else {
        // paste occurring, have text take on the styles it had when it was
        // cut/copied
        for (int i = 0; i < cachedStyles.size(); i++) {
            style = cachedStyles.get(i);
            StyleRange newStyle = (StyleRange) style.clone();
            newStyle.start = style.start + event.start;
            styledText.setStyleRange(newStyle);
        }
    }
}

private void handleTextSelected(SelectionEvent event) {
    Point sel = styledText.getSelectionRange();
    if ((sel != null) && (sel.y != 0)) {
        StyleRange[] styles = styledText.getStyleRanges(sel.x, sel.y);
        eraserBtn.setEnabled((styles != null) && (styles.length > 0));
    } else {
        eraserBtn.setEnabled(false);
    }
}

private void handleKeyReleased(KeyEvent event) {
    if ((event.keyCode == SWT.ARROW_LEFT) || (event.keyCode == SWT.ARROW_UP)
            || (event.keyCode == SWT.ARROW_RIGHT) || (event.keyCode == SWT.ARROW_DOWN)) {
        updateStyleButtons();
    }
}

private void updateStyleButtons() {
    int caretOffset = styledText.getCaretOffset();
    StyleRange style = null;
    if (caretOffset >= 0 && caretOffset < styledText.getCharCount()) {
        style = styledText.getStyleRangeAtOffset(caretOffset);
    }

    if (style != null) {
        boldBtn.setSelection((style.fontStyle & SWT.BOLD) != 0);
        italicBtn.setSelection((style.fontStyle & SWT.ITALIC) != 0);
        underlineBtn.setSelection(style.underline);
        strikeThroughBtn.setSelection(style.strikeout);
    } else {
        boldBtn.setSelection(false);
        italicBtn.setSelection(false);
        underlineBtn.setSelection(false);
        strikeThroughBtn.setSelection(false);
    }
}

//TODO update color buttons

private void initComponents() {
	JFaceResources.getColorRegistry().put(FontStyle.CUSTOM_COLOR_1.name(), new RGB(255, 0, 0));
	JFaceResources.getColorRegistry().put(FontStyle.CUSTOM_COLOR_2.name(), new RGB(0, 255, 0));
	JFaceResources.getColorRegistry().put(FontStyle.CUSTOM_COLOR_3.name(), new RGB(0, 0, 255));
	//JFaceResources.getDialogFontDescriptor();
//	FontData fd1[] = 	FontDescriptor.createFrom("arial", 20, 0).getFontData();
//	
//	JFaceResources.getFontRegistry().put(FontStyle.CUSTOM_FONT_1.name(), );
//	JFaceResources.getFontRegistry().put(FontStyle.CUSTOM_FONT_1.name(), fontData);
//	JFaceResources.getFontRegistry().put(FontStyle.CUSTOM_FONT_1.name(), fontData);
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    setLayout(layout);

    toolBar = createToolBar(this);
    GridData gd_toolBar = new GridData(GridData.FILL_HORIZONTAL);
    gd_toolBar.verticalAlignment = SWT.FILL;
    gd_toolBar.widthHint = 490;
    toolBar.setLayoutData(gd_toolBar);

    styledText = new StyledText(this, SWT.BORDER | SWT.MULTI |
            SWT.V_SCROLL | SWT.H_SCROLL);
    styledText.setLayoutData(new GridData(GridData.FILL_BOTH));
    styledText.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            handleKeyReleased(e);
        }
    });
    styledText.addExtendedModifyListener(new ExtendedModifyListener() {
        @Override
        public void modifyText(ExtendedModifyEvent event) {
            handleExtendedModified(event);
        }
    });
    styledText.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseUp(MouseEvent e) {
            updateStyleButtons();
        }
    });
    styledText.addSelectionListener(new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent event) {
            handleTextSelected(event);
        }
    });
    Font font = JFaceResources.getDefaultFont();
    FontData[] fd = font.getFontData();
    fd[0].setHeight(20);
    styledText.setFont(new Font(getDisplay(), fd));
}



private List<FontStyle> translateStyle(StyleRange range) {
    List<FontStyle> list = new ArrayList<FontStyle>();

    if ((range.fontStyle & SWT.BOLD) != 0) {
        list.add(FontStyle.BOLD);
    }
    if ((range.fontStyle & SWT.ITALIC) != 0) {
        list.add(FontStyle.ITALIC);
    }
    if (range.strikeout) {
        list.add(FontStyle.STRIKE_THROUGH);
    }
    if (range.underline) {
        list.add(FontStyle.UNDERLINE);
    }

    return list;
}

private class FontStyleButtonListener extends SelectionAdapter {
    private FontStyle style;

    public FontStyleButtonListener(FontStyle style) {
        this.style = style;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        applyFontStyleToSelection(style);
    }
}

}