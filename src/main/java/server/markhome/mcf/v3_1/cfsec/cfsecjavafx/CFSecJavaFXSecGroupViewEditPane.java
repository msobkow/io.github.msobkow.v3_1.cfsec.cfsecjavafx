// Description: Java 25 JavaFX View/Edit Pane implementation for SecGroup.

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
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecGroupViewEditPane JavaFX View/Edit Pane implementation
 *	for SecGroup.
 */
public class CFSecJavaFXSecGroupViewEditPane
extends CFSplitPane
implements ICFSecJavaFXSecGroupPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ScrollPane attrScrollPane = null;
	protected CFGridPane attrPane = null;
	protected CFTabPane eltTabPane = null;

	public CFSecJavaFXSecGroupViewEditPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecGroupObj argFocus ) {
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
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		setJavaFXFocus( argFocus );
		attrPane = argSchema.getSecGroupFactory().newAttrPane( cfFormManager, argFocus );
		attrScrollPane = new ScrollPane();
		attrScrollPane.setFitToWidth( true );
		attrScrollPane.setHbarPolicy( ScrollBarPolicy.NEVER );
		attrScrollPane.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
		attrScrollPane.setContent( attrPane );
		eltTabPane = argSchema.getSecGroupFactory().newEltTabPane( cfFormManager, argFocus );
		setOrientation( Orientation.VERTICAL );
		getItems().add( attrScrollPane );
		getItems().add( eltTabPane );
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

	public ICFSecJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFSecSecGroupObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecGroupObj" );
		}
		if( attrPane != null ) {
			attrPane.setJavaFXFocus( value );
		}
		if( eltTabPane != null ) {
			eltTabPane.setJavaFXFocus( value );
		}
	}

	public void setJavaFXFocusAsSecGroup( ICFSecSecGroupObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecGroupObj getJavaFXFocusAsSecGroup() {
		return( (ICFSecSecGroupObj)getJavaFXFocus() );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		if( oldMode == value ) {
			return;
		}
		try {
			super.setPaneMode( value );
			((ICFSecJavaFXSecGroupPaneCommon)attrPane).setPaneMode( value );
			((ICFSecJavaFXSecGroupPaneCommon)eltTabPane).setPaneMode( value );
		}
		catch( Throwable t ) {
			super.setPaneMode( oldMode );
			((ICFSecJavaFXSecGroupPaneCommon)attrPane).setPaneMode( oldMode );
			((ICFSecJavaFXSecGroupPaneCommon)eltTabPane).setPaneMode( oldMode );
			throw t;
		}
	}
}
