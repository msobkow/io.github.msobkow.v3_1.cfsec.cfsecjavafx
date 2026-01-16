// Description: Java 25 JavaFX Picker Form implementation for TSecGrpInc.

/*
 *	io.github.msobkow.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	This file is part of Mark's Code Fractal CFSec.
 *	
 *	Mark's Code Fractal CFSec is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFSec is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFSec is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFSec.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package io.github.msobkow.v3_1.cfsec.cfsecjavafx;

import java.math.*;
import java.time.*;
import java.text.*;
import java.util.*;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.inz.Inz;
import io.github.msobkow.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXTSecGrpIncPickerForm JavaFX Picker Form implementation
 *	for TSecGrpInc.
 */
public class CFSecJavaFXTSecGrpIncPickerForm
extends CFBorderPane
implements ICFSecJavaFXTSecGrpIncPaneList,
	ICFForm
{
	protected ICFFormManager cfFormManager = null;
	protected CFBorderPane javafxPickerPane = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXTSecGrpIncPageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;

	public CFSecJavaFXTSecGrpIncPickerForm( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecTSecGrpIncObj argFocus,
		ICFSecTSecGroupObj argContainer,
		ICFSecJavaFXTSecGrpIncPageCallback argPageCallback,
		ICFSecJavaFXTSecGrpIncChosen whenChosen )
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
		javafxPickerPane = argSchema.getTSecGrpIncFactory().newPickerPane( cfFormManager, argFocus, argContainer, argPageCallback, whenChosen );
		setJavaFXFocusAsTSecGrpInc( argFocus );
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
		if( ( value == null ) || ( value instanceof ICFSecTSecGrpIncObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecTSecGrpIncObj" );
		}
		((ICFSecJavaFXTSecGrpIncPaneCommon)javafxPickerPane).setJavaFXFocus( (ICFSecTSecGrpIncObj)value );
	}

	public ICFSecTSecGrpIncObj getJavaFXFocusAsTSecGrpInc() {
		return( (ICFSecTSecGrpIncObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTSecGrpInc( ICFSecTSecGrpIncObj value ) {
		setJavaFXFocus( value );
	}

	public Collection<ICFSecTSecGrpIncObj> getJavaFXDataCollection() {
		ICFSecJavaFXTSecGrpIncPaneList jplPicker = (ICFSecJavaFXTSecGrpIncPaneList)javafxPickerPane;
		Collection<ICFSecTSecGrpIncObj> cltn = jplPicker.getJavaFXDataCollection();
		return( cltn );
	}

	public void setJavaFXDataCollection( Collection<ICFSecTSecGrpIncObj> value ) {
		ICFSecJavaFXTSecGrpIncPaneList jplPicker = (ICFSecJavaFXTSecGrpIncPaneList)javafxPickerPane;
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
			ICFSecJavaFXTSecGrpIncPaneCommon jpanelCommon = (ICFSecJavaFXTSecGrpIncPaneCommon)javafxPickerPane;
			jpanelCommon.setPaneMode( value );
		}
	}

	public ICFSecTSecGroupObj getJavaFXContainer() {
		ICFSecJavaFXTSecGrpIncPaneList jplPicker = (ICFSecJavaFXTSecGrpIncPaneList)javafxPickerPane;
		ICFSecTSecGroupObj cnt = jplPicker.getJavaFXContainer();
		return( cnt );
	}

	public void setJavaFXContainer( ICFSecTSecGroupObj value ) {
		ICFSecJavaFXTSecGrpIncPaneList jplPicker = (ICFSecJavaFXTSecGrpIncPaneList)javafxPickerPane;
		jplPicker.setJavaFXContainer( value );
	}
}
