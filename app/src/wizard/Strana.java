package wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Strana extends WizardPage {

    protected Strana(String pageName) {
        super(pageName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        setControl(container);

    }
}
