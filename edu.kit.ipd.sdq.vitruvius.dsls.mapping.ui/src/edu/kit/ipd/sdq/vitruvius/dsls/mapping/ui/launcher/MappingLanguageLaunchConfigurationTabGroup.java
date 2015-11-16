package edu.kit.ipd.sdq.vitruvius.dsls.mapping.ui.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.ui.internal.MappingLanguageActivator;

public class MappingLanguageLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {
	public static final String FILES_TO_GENERATE = MappingLanguageActivator.EDU_KIT_IPD_SDQ_VITRUVIUS_DSLS_MAPPING_MAPPINGLANGUAGE + ".FILES_TO_GENERATE";

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new Tab(),
				new CommonTab()
			};
		setTabs(tabs);
	}
	
	
	public static class Tab extends AbstractLaunchConfigurationTab {
		protected Text txtField;
		
		@Override
		public boolean canSave() {
			return true;
		}
		
		@Override
		protected boolean isDirty() {
			return true;
		}
		
		
		@Override
		public boolean isValid(ILaunchConfiguration launchConfig) {
			return true;
		}
		
		@Override
		public void createControl(Composite parent) {
			//Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, GridData.FILL_BOTH);
			txtField = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
			setControl(txtField);
			txtField.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent e) {
					setDirty(true);
					updateLaunchConfigurationDialog();
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					setDirty(true);
					updateLaunchConfigurationDialog();
				}
			});
			updateLaunchConfigurationDialog();
		}

		@Override
		public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
			configuration.setAttribute(FILES_TO_GENERATE, "");
		}

		@Override
		public void initializeFrom(ILaunchConfiguration configuration) {
			try {
				txtField.setText(configuration.getAttribute(FILES_TO_GENERATE, ""));
			} catch (CoreException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void performApply(ILaunchConfigurationWorkingCopy configuration) {
			configuration.setAttribute(FILES_TO_GENERATE, txtField.getText());
		}

		@Override
		public String getName() {
			return "Mappings";
		}
		
	}
}
