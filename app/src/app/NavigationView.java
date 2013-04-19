package app;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

public class NavigationView extends ViewPart {
    public NavigationView() {
    }

    public static final String ID = "app.navigationView";

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout(1, false));
        FillLayout fillLayout = new FillLayout();
        fillLayout.type = SWT.VERTICAL;
        parent.setLayout(fillLayout);
        // final WizardDialog d = new WizardDialog(parent.getShell(),
        // new Doplnovacka());

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(3, false));

        Canvas[] paleta = new Canvas[36];

        for (int i = 0; i < paleta.length; i++) {
            paleta[i] = new Canvas(composite, SWT.NONE);
            GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER,
                    false, false, 1, 1);
            gd_btnNewButton.heightHint = 30;
            gd_btnNewButton.widthHint = 30;
            paleta[i].setLayoutData(gd_btnNewButton);
            paleta[i].setBackground(SWTResourceManager.getColor(i));
            final int index = i;
            paleta[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseDown(MouseEvent e) {
                    View.setFontColor(SWTResourceManager.getColor(index));
                }
            });
        }

    }

    @Override
    public void setFocus() {

    }

}