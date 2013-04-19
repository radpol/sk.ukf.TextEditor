package wizard;

import java.util.LinkedList;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class Doplnovacka extends Wizard {
	
	int length = 5;
	java.util.List<Strana> strany = new LinkedList<Strana>();

	public Doplnovacka() {
		super();
		for (int i = 0; i < length; i++) {
			strany.add(new Strana("Strana "+i));
			strany.get(i).setTitle("aaaaaaaaa");
			strany.get(i).setDescription("bbbbbbbbbbbbb");
			strany.get(i).setMessage("cccc");
			addPage(strany.get(i));
		}
		
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public void addPage(IWizardPage page) {
		// TODO Auto-generated method stub
		super.addPage(page);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		// TODO Auto-generated method stub
		super.addPages();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return super.canFinish();
	}	

}
