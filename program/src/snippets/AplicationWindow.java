package snippets;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AplicationWindow extends ApplicationWindow {
    private final FormToolkit formToolkit = new FormToolkit(
            Display.getDefault());
    private Action action;
    private Action action_1;
    private StyledText styledText;
    private TextViewer textViewer;
    private Action action_2;
    private Action action_3;
    private Action action_4;
    private Text text;

    /**
     * Create the application window.
     */
    public AplicationWindow() {
        super(null);
        createActions();
        addToolBar(SWT.FLAT | SWT.WRAP);
        addMenuBar();
        addStatusLine();
    }

    /**
     * Create contents of the application window.
     * 
     * @param parent
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(null);

        textViewer = new TextViewer(container, SWT.BORDER);
        styledText = textViewer.getTextWidget();
        styledText.setBounds(10, 37, 416, 409);
        formToolkit.paintBordersFor(styledText);

        final FontDialog fontdialog = new FontDialog(parent.getShell());

        ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
        Combo combo_1 = comboViewer.getCombo();
        combo_1.setBounds(10, 8, 149, 23);
        formToolkit.paintBordersFor(combo_1);
        // comboViewer.add(object);
        FontData[] fontData = Display.getCurrent().getFontList(null, true);
        //JFaceResources.getFontRegistry().
        String pom;
        for (int i = 0; i < fontData.length - 1; i++) {
            if (!fontData[i].getName().startsWith(
                    fontData[i + 1].getName().substring(0,
                            (fontData[i + 1].getName().length()) / 2))) {

                StyledString aa = new StyledString(fontData[i].getName());

                comboViewer.add(fontData[i].getName());
            }
        }

        Button btnNewButton = new Button(container, SWT.NONE);
        btnNewButton.setBounds(305, 6, 113, 25);
        btnNewButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            try {
            	StyleRange[] aaa = styledText.getStyleRanges();
            	for (int i = 0; i < aaa.length; i++) {
            		System.out.println(aaa[i].font);
				}
                StyleRange styleRange = new StyleRange();
                styleRange.start = styledText.getSelectionRange().x;
                styleRange.length = styledText.getSelectionRange().y;
                styleRange.font = new Font(null, fontdialog.open());
                styleRange.foreground = new Color(Display.getCurrent(),
                        fontdialog.getRGB());
                styledText.setStyleRange(styleRange);
			} catch (Exception e2) {
				// TODO: handle exception
			}
            }
        });

        formToolkit.adapt(btnNewButton, true, true);
        btnNewButton.setText("Nastav font");

        text = new Text(container, SWT.BORDER);
        text.setBounds(167, 8, 28, 21);
        formToolkit.adapt(text, true, true);

        Button btnNewButton_1 = new Button(container, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                StyleRange styleRange = new StyleRange();
                styleRange.start = styledText.getSelectionRange().x;
                styleRange.length = styledText.getSelectionRange().y;
                FontData fd = new FontData(JFaceResources.getDefaultFont()
                        .getFontData()[0].getName(), Integer.parseInt(text
                        .getText()), SWT.NORMAL);
                styleRange.font = new Font(null, fd);
                styledText.setStyleRange(styleRange);
            }
        });
        btnNewButton_1.setBounds(201, 6, 98, 25);
        formToolkit.adapt(btnNewButton_1, true, true);
        btnNewButton_1.setText("Zmen Velkost");
        return container;
    }

    /**
     * Create the actions.
     */
    private void createActions() {

        // Create the actions
        {
            action = new Action("New Action") {
                private void name() {
                    styledText.getSelectionText();
                    Font font = Font.win32_new(null, 0);
                    styledText.setFont(font);

                }
                // styledText.getSelectionText();
            };
        }
        {
            action_1 = new Action("New Action") {

            };
        }
        {
            action_2 = new Action("New Action") {

            };
        }
        {
            action_3 = new Action("New Action") {

            };
        }
        {
            action_4 = new Action("New Action") {

            };
        }
    }

    /**
     * Create the menu manager.
     * 
     * @return the menu manager
     */
    @Override
    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager("menu");
        {
            MenuManager menuManager_1 = new MenuManager("Subor");
            menuManager.add(menuManager_1);
            menuManager_1.add(action_2);
            menuManager_1.add(action_3);
        }

        MenuManager menuManager_1 = new MenuManager("Pomoc");
        menuManager.add(menuManager_1);
        return menuManager;
    }

    /**
     * Create the toolbar manager.
     * 
     * @return the toolbar manager
     */
    @Override
    protected ToolBarManager createToolBarManager(int style) {
        ToolBarManager toolBarManager = new ToolBarManager(style);
        toolBarManager.add(action_4);
        return toolBarManager;
    }

    /**
     * Create the status line manager.
     * 
     * @return the status line manager
     */
    @Override
    protected StatusLineManager createStatusLineManager() {
        StatusLineManager statusLineManager = new StatusLineManager();
        return statusLineManager;
    }

    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String args[]) {
        try {
            AplicationWindow window = new AplicationWindow();
            window.setBlockOnOpen(true);
            window.open();
            Display.getCurrent().dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configure the shell.
     * 
     * @param newShell
     */
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("New Application");
    }
}
