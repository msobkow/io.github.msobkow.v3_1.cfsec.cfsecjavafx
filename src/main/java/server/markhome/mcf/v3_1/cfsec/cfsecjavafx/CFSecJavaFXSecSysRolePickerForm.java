// Description: Java 25 JavaFX Picker Form implementation for SecSysRole.

/*
 *	server.markhome.mcf.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow mark.sobkow@gmail.com
 *	
 *	These files are part of Mark's Code Fractal CFSec.
 *	
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 *	
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *	
 */

package server.markhome.mcf.v3_1.cfsec.cfsecjavafx;

import java.math.*;
import java.time.*;
import java.text.*;
import java.util.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecSysRolePickerForm JavaFX Picker Form implementation
 *	for SecSysRole.
 */
public class CFSecJavaFXSecSysRolePickerForm
extends CFBorderPane
implements ICFSecJavaFXSecSysRolePaneList,
	ICFForm
{
	protected ICFFormManager cfFormManager = null;
	protected CFBorderPane javafxPickerPane = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecSysRoleObj> javafxDataCollection = null;

	public CFSecJavaFXSecSysRolePickerForm( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecSysRoleObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecSysRoleObj> argDataCollection,
		ICFSecJavaFXSecSysRoleChosen whenChosen )
	{
		super();
		final String S_ProcName = "construct-schema-focus";
		if( formManager == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"formManager" );
		}
		cfFormManager = formManager;
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argSchema" );
		}
		if( whenChosen == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				6,
				"whenChosen" );
		}
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		javafxPickerPane = argSchema.getSecSysRoleFactory().newPickerPane( cfFormManager, argFocus, argContainer, argDataCollection, whenChosen );
		setJavaFXFocusAsSecSysRole( argFocus );
		setJavaFXDataCollection( argDataCollection );
		setJavaFXContainer( argContainer );
		setCenter( javafxPickerPane );
		setPaneMode( CFPane.PaneMode.View );
	}

	public ICFFormManager getCFFormManager() {
		return( cfFormManager );
	}

	public void setCFFormManager( ICFFormManager value ) {
		final String S_ProcName = "setCFFormManager";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		cfFormManager = value;
	}

	public void forceCancelAndClose() {
		if( cfFormManager != null ) {
			if( cfFormManager.getCurrentForm() == this ) {
				cfFormManager.closeCurrentForm();
			}
		}
	}

	public ICFSecJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFSecSecSysRoleObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSysRoleObj" );
		}
		((ICFSecJavaFXSecSysRolePaneCommon)javafxPickerPane).setJavaFXFocus( (ICFSecSecSysRoleObj)value );
	}

	public ICFSecSecSysRoleObj getJavaFXFocusAsSecSysRole() {
		return( (ICFSecSecSysRoleObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSysRole( ICFSecSecSysRoleObj value ) {
		setJavaFXFocus( value );
	}

	public Collection<ICFSecSecSysRoleObj> getJavaFXDataCollection() {
		ICFSecJavaFXSecSysRolePaneList jplPicker = (ICFSecJavaFXSecSysRolePaneList)javafxPickerPane;
		Collection<ICFSecSecSysRoleObj> cltn = jplPicker.getJavaFXDataCollection();
		return( cltn );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecSysRoleObj> value ) {
		ICFSecJavaFXSecSysRolePaneList jplPicker = (ICFSecJavaFXSecSysRolePaneList)javafxPickerPane;
		jplPicker.setJavaFXDataCollection( value );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		if( value != CFPane.PaneMode.View ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				Inz.x("cflibjavafx.common.PickerFormModes"),
				Inz.s("cflibjavafx.common.PickerFormModes") );
		}
		super.setPaneMode( value );
		if( javafxPickerPane != null ) {
			ICFSecJavaFXSecSysRolePaneCommon jpanelCommon = (ICFSecJavaFXSecSysRolePaneCommon)javafxPickerPane;
			jpanelCommon.setPaneMode( value );
		}
	}

	public ICFLibAnyObj getJavaFXContainer() {
		ICFSecJavaFXSecSysRolePaneList jplPicker = (ICFSecJavaFXSecSysRolePaneList)javafxPickerPane;
		ICFLibAnyObj cnt = jplPicker.getJavaFXContainer();
		return( cnt );
	}

	public void setJavaFXContainer( ICFLibAnyObj value ) {
		ICFSecJavaFXSecSysRolePaneList jplPicker = (ICFSecJavaFXSecSysRolePaneList)javafxPickerPane;
		jplPicker.setJavaFXContainer( value );
	}
}
