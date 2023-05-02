package org.palladiosimulator.addons.explanationgeneration.ui;

import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.uka.ipd.sdq.workflow.launchconfig.tabs.TabHelper;

/**
 * FIXME
 * While this works by itself, the component currently does not have a way to get the files set here.
 * */
public class ExplanationFileTab extends AbstractLaunchConfigurationTab{
	
	private static final String NAME = "Currently not used!: Explanations";
	private static final String please = "Please select your case definition file";
	private static final String[] fileExtensions = new String[] {"*.yml"};
	private static final String caseDefinitions = "casedefinitions";
	
	private final HashMap<String,Text> texts = new HashMap<>();
	private final ModifyListener modifyListener;
	private Composite container;
	private boolean isValid;
	
	public ExplanationFileTab() {
		isValid = false;
		
		//copied from SimulationArchitectureModelsTab()
		this.modifyListener = modifyEvent -> {
			setDirty(true);
			updateLaunchConfigurationDialog();
		};
	}

	@Override
	public void createControl(Composite parent) {
		//copied from SimulationArchitectureModelsTab()
		this.container = new Composite(parent, SWT.NONE);
		setControl(container);
		container.setLayout(new GridLayout());
		
		final Text text = new Text(container, SWT.SINGLE | SWT.BORDER);
		TabHelper.createFileInputSection(container, modifyListener, "Test", fileExtensions, text, please, getShell(), "");
		texts.put(caseDefinitions, text);
		
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {};

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		texts.forEach((name, text) -> {
			try {
				text.setText(configuration.getAttribute(name, ""));
			} catch (final CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		texts.forEach((name, text) -> {
			configuration.setAttribute(name, text.getText());
		});
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		isValid = true;
		
		texts.forEach((name, text) -> {
			isValid &= TabHelper.validateFilenameExtension(text.getText(), fileExtensions);
		});
		
		return isValid;
	}

}
