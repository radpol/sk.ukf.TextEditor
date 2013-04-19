package program;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import richtext.RichText;

public class View extends ViewPart {

    public static final String ID = "program.view";
    private RichText richtext;

    /**
     * The text control that's displaying the content of the email message.
     */
    // private Text messageText;

    @Override
    public void createPartControl(Composite parent) {
        richtext = new RichText(parent, SWT.NONE);
        // richtext.getFormattedText();

    }

    @Override
    public void setFocus() {
        // messageText.setFocus();
        // richtext.setFocus();
    }
}
