package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;
import org.xml.sax.SAXException;

import richtext.FontStyle;
import richtext.Images;
import richtext.RichStringBuilder;
import richtext.RichTextParser;
import richtext.UIStrings;

public class View extends ViewPart {
    public View() {
    }

    public static final String ID = "app.view";

    private List<StyleRange> cachedStyles = new LinkedList<StyleRange>();

    private static StyledText styledText;

    private ToolItem boldBtn;
    private ToolItem italicBtn;
    private ToolItem strikeThroughBtn;
    private ToolItem underlineBtn;

    private ToolItem copyBtn;
    private ToolItem cutBtn;
    private ToolItem pasteBtn;
    private ToolItem eraserBtn;
    private ToolItem tltmNewItem_1;

    private ToolItem tltmNewItem_2;
    private ToolItem tltmNewItem_3;
    private ToolItem tltmNewItem_4;
    private ToolItem tltmNewItem_5;
    private ToolBar toolBar_1;

    /**
     * The content provider class is responsible for providing objects to the
     * view. It can wrap existing objects in adapters or simply return objects
     * as-is. These objects may be sensitive to the current input of the view,
     * or ignore it and always show the same content (like Task List, for
     * example).
     */

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public static void setFontColor(Color fg) {
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

    public static void setFontSize(int size) {
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

            } else {
                style = new StyleRange(i, 1, null, null, SWT.NORMAL);
            }

            if (style.font != null) {
                style.font.getFontData()[0]
                        .setHeight(style.font.getFontData()[0].getHeight()
                                + size);
            } else {
                // FontData[] fd = JFaceResources.getDefaultFontDescriptor()
                // .getFontData();
                // fd[0].setHeight(fd[0].getHeight() + size);
                // style.font = new Font(null, fd);
            }
            styledText.setStyleRange(style);
        }
        // styledText.setSelectionRange(sel.x + sel.y, 0);
    }

    @Override
    public void createPartControl(Composite parent) {
        parent.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
        createToolBar(parent);
        Font font = JFaceResources.getDefaultFont();
        FontData[] fd = font.getFontData();
        fd[0].setHeight(20);
        parent.setLayout(new GridLayout(1, false));
        styledText = new StyledText(parent, SWT.BORDER | SWT.MULTI
                | SWT.V_SCROLL | SWT.H_SCROLL);
        GridData gd_styledText = new GridData(SWT.CENTER, SWT.CENTER, false,
                false, 1, 2);
        gd_styledText.heightHint = 660;
        gd_styledText.widthHint = 857;
        styledText.setLayoutData(gd_styledText);
        styledText.setFont(new Font(getViewSite().getShell().getDisplay(), fd));
        styledText.setWordWrap(true);
        styledText.setIndent(30);
        initComponents();
    }

    private ToolBar createToolBar(Composite parent) {

        toolBar_1 = new ToolBar(parent, SWT.FLAT);
        boldBtn = new ToolItem(toolBar_1, SWT.CHECK);
        boldBtn.setImage(Images.getImage("bold")[0]);
        boldBtn.setHotImage(Images.getImage("bold")[1]);
        boldBtn.setToolTipText(UIStrings.boldBtn_tooltipText);
        boldBtn.addSelectionListener(new FontStyleButtonListener(FontStyle.BOLD));

        italicBtn = new ToolItem(toolBar_1, SWT.CHECK);
        italicBtn.setImage(Images.getImage("italic")[0]);
        italicBtn.setHotImage(Images.getImage("italic")[1]);
        italicBtn.setToolTipText(UIStrings.italicBtn_tooltipText);
        italicBtn.addSelectionListener(new FontStyleButtonListener(
                FontStyle.ITALIC));

        underlineBtn = new ToolItem(toolBar_1, SWT.CHECK);
        underlineBtn.setImage(Images.getImage("underline")[0]);
        underlineBtn.setHotImage(Images.getImage("underline")[1]);
        underlineBtn.setToolTipText(UIStrings.underlineBtn_tooltipText);
        underlineBtn.addSelectionListener(new FontStyleButtonListener(
                FontStyle.UNDERLINE));

        strikeThroughBtn = new ToolItem(toolBar_1, SWT.CHECK);
        strikeThroughBtn.setImage(Images.getImage("strikeout")[0]);
        strikeThroughBtn.setHotImage(Images.getImage("strikeout")[1]);
        strikeThroughBtn.setToolTipText(UIStrings.strikeThroughBtn_tooltipText);
        strikeThroughBtn.addSelectionListener(new FontStyleButtonListener(
                FontStyle.STRIKE_THROUGH));

        new ToolItem(toolBar_1, SWT.SEPARATOR);

        cutBtn = new ToolItem(toolBar_1, SWT.PUSH);
        cutBtn.setImage(Images.getImage("cut")[0]);
        cutBtn.setHotImage(Images.getImage("cut")[1]);
        cutBtn.setToolTipText(UIStrings.cutBtn_tooltipText);
        cutBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleCutCopy();
                styledText.cut();
            }
        });

        copyBtn = new ToolItem(toolBar_1, SWT.PUSH);
        copyBtn.setImage(Images.getImage("copy")[0]);
        copyBtn.setHotImage(Images.getImage("copy")[1]);
        copyBtn.setToolTipText(UIStrings.copyBtn_tooltipText);
        copyBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                handleCutCopy();
                styledText.copy();
            }
        });

        pasteBtn = new ToolItem(toolBar_1, SWT.PUSH);
        pasteBtn.setEnabled(false);
        pasteBtn.setImage(Images.getImage("paste")[0]);
        pasteBtn.setHotImage(Images.getImage("paste")[1]);
        pasteBtn.setToolTipText(UIStrings.pasteBtn_tooltipText);
        pasteBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                styledText.paste();
            }
        });

        new ToolItem(toolBar_1, SWT.SEPARATOR);

        tltmNewItem_3 = new ToolItem(toolBar_1, SWT.NONE);
        tltmNewItem_3.setToolTipText(UIStrings.increaseBtn_ttoltipText);
        tltmNewItem_3.setImage(Images.getImage("increase")[0]);
        tltmNewItem_3.setHotImage(Images.getImage("increase")[1]);
        tltmNewItem_3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setFontSize(3);
            }
        });

        tltmNewItem_4 = new ToolItem(toolBar_1, SWT.NONE);
        tltmNewItem_4.setToolTipText(UIStrings.decreaseBtn_ttoltipText);
        tltmNewItem_4.setImage(Images.getImage("decrease")[0]);
        tltmNewItem_4.setHotImage(Images.getImage("decrease")[1]);
        tltmNewItem_4.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setFontSize(-3);
            }
        });
        new ToolItem(toolBar_1, SWT.SEPARATOR);

        tltmNewItem_5 = new ToolItem(toolBar_1, SWT.NONE);
        tltmNewItem_5.setToolTipText(UIStrings.leftBtn_ttoltipText);
        tltmNewItem_5.setImage(Images.getImage("left")[0]);
        tltmNewItem_5.setHotImage(Images.getImage("left")[1]);
        tltmNewItem_5.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                styledText.setIndent(5);
                // styledText.setLineAlignment(0, 1, SWT.LEFT);
            }
        });

        tltmNewItem_2 = new ToolItem(toolBar_1, SWT.NONE);
        tltmNewItem_2.setToolTipText(UIStrings.centerBtn_ttoltipText);
        tltmNewItem_2.setImage(Images.getImage("center")[0]);
        tltmNewItem_2.setHotImage(Images.getImage("center")[1]);
        tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                styledText.setLineAlignment(2, 2, SWT.CENTER);
            }
        });
        tltmNewItem_1 = new ToolItem(toolBar_1, SWT.NONE);
        tltmNewItem_1.setToolTipText(UIStrings.rightBtn_ttoltipText);
        tltmNewItem_1.setImage(Images.getImage("right")[0]);
        tltmNewItem_1.setHotImage(Images.getImage("right")[1]);
        tltmNewItem_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                styledText.setLineAlignment(2, 2, SWT.RIGHT);
            }
        });

        new ToolItem(toolBar_1, SWT.SEPARATOR);

        eraserBtn = new ToolItem(toolBar_1, SWT.PUSH);
        eraserBtn.setEnabled(false);
        eraserBtn.setImage(Images.getImage("remove")[0]);
        eraserBtn.setHotImage(Images.getImage("remove")[1]);
        eraserBtn.setToolTipText(UIStrings.eraserBtn_tooltipText);
        eraserBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                clearStylesFromSelection();
            }
        });
        return toolBar_1;
    }

    /**
     * Obtain an HTML formatted text from the component contents
     * 
     * @return an HTML formatted text
     */

    public static String getFormattedText() {
        String plainText = styledText.getText();

        RichStringBuilder builder = new RichStringBuilder();
        Integer[] lineBreaks = getLineBreaks();

        int brIdx = 0;
        int start = 0;
        int end;
        if (lineBreaks.length > brIdx) {
            end = lineBreaks[brIdx++];
        } else {
            end = plainText.length() - 1;
        }

        while (start < end) {
            builder.startParagraph();
            StyleRange[] ranges = styledText.getStyleRanges(start,
                    (end - start));
            if (ranges != null && ranges.length > 0) {
                for (int i = 0; i < ranges.length; i++) {
                    if (start < ranges[i].start) {
                        builder.append(plainText.substring(start,
                                ranges[i].start));
                    }

                    List<FontStyle> styles = translateStyle(ranges[i]);
                    builder.startFontStyles(styles.toArray(new FontStyle[styles
                            .size()]));
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

            if (lineBreaks.length > brIdx) {
                end = lineBreaks[brIdx++];
            } else {
                end = plainText.length() - 1;
            }
            builder.endParagraph();
        }

        return builder.toString();
    }

    public static void setFormattedText(String text)
            throws ParserConfigurationException, SAXException, IOException {
        RichTextParser parser = RichTextParser.parse(text);
        styledText.setText(parser.getText());
        styledText.setStyleRanges(parser.getStyleRanges());
    }

    protected void applyFontStyleToSelection(FontStyle style) {
        Point sel = styledText.getSelectionRange();
        if ((sel == null) || (sel.y == 0)) {
            return;
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
                newStyle.foreground =

                JFaceResources.getColorRegistry().get(style.toString());
                break;
            case CUSTOM_COLOR_2:
                newStyle.foreground = JFaceResources.getColorRegistry().get(
                        style.toString());
                break;
            case CUSTOM_COLOR_3:
                newStyle.foreground = JFaceResources.getColorRegistry().get(
                        style.toString());
                break;
            default:
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
            StyleRange style = new StyleRange(sel.x, sel.y, null, null,
                    SWT.NORMAL);
            styledText.setStyleRange(style);
        }
        styledText.setSelectionRange(sel.x + sel.y, 0);
    }

    private static Integer[] getLineBreaks() {
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
        return list.toArray(new Integer[list.size()]);
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
        if (event.length == 0)
            return;

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
        if ((event.keyCode == SWT.ARROW_LEFT)
                || (event.keyCode == SWT.ARROW_UP)
                || (event.keyCode == SWT.ARROW_RIGHT)
                || (event.keyCode == SWT.ARROW_DOWN)) {
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

    private void initComponents() {
        JFaceResources.getColorRegistry().put(FontStyle.CUSTOM_COLOR_1.name(),
                new RGB(255, 0, 0));
        JFaceResources.getColorRegistry().put(FontStyle.CUSTOM_COLOR_2.name(),
                new RGB(0, 255, 0));
        JFaceResources.getColorRegistry().put(FontStyle.CUSTOM_COLOR_3.name(),
                new RGB(0, 0, 255));
        // JFaceResources.getDialogFontDescriptor();
        // FontData fd1[] = FontDescriptor.createFrom("arial", 20,
        // 0).getFontData();
        //
        // JFaceResources.getFontRegistry().put(FontStyle.CUSTOM_FONT_1.name(),
        // );
        // JFaceResources.getFontRegistry().put(FontStyle.CUSTOM_FONT_1.name(),
        // fontData);
        // JFaceResources.getFontRegistry().put(FontStyle.CUSTOM_FONT_1.name(),
        // fontData);

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

    }

    private static List<FontStyle> translateStyle(StyleRange range) {
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
        private final FontStyle style;

        public FontStyleButtonListener(FontStyle style) {
            this.style = style;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            applyFontStyleToSelection(style);
        }
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {
        styledText.setFocus();
    }
}