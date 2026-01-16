// Description: Java 25 JavaFX View/Edit Pane implementation for ServiceType.

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
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.inz.Inz;
import io.github.msobkow.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXServiceTypeViewEditPane JavaFX View/Edit Pane implementation
 *	for ServiceType.
 */
public class CFSecJavaFXServiceTypeViewEditPane
extends CFSplitPane
implements ICFSecJavaFXServiceTypePaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ScrollPane attrScrollPane = null;
	protected CFGridPane attrPane = null;
	protected CFTabPane eltTabPane = null;

	public CFSecJavaFXServiceTypeViewEditPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecServiceTypeObj argFocus ) {
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
		attrPane = argSchema.getServiceTypeFactory().newAttrPane( cfFormManager, argFocus );
		attrScrollPane = new ScrollPane();
		attrScrollPane.setFitToWidth( true );
		attrScrollPane.setHbarPolicy( ScrollBarPolicy.NEVER );
		attrScrollPane.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
		attrScrollPane.setContent( attrPane );
		eltTabPane = argSchema.getServiceTypeFactory().newEltTabPane( cfFormManager, argFocus );
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
		if( ( value == null ) || ( value instanceof ICFSecServiceTypeObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecServiceTypeObj" );
		}
		if( attrPane != null ) {
			attrPane.setJavaFXFocus( value );
		}
		if( eltTabPane != null ) {
			eltTabPane.setJavaFXFocus( value );
		}
	}

	public void setJavaFXFocusAsServiceType( ICFSecServiceTypeObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecServiceTypeObj getJavaFXFocusAsServiceType() {
		return( (ICFSecServiceTypeObj)getJavaFXFocus() );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		if( oldMode == value ) {
			return;
		}
		try {
			super.setPaneMode( value );
			((ICFSecJavaFXServiceTypePaneCommon)attrPane).setPaneMode( value );
			((ICFSecJavaFXServiceTypePaneCommon)eltTabPane).setPaneMode( value );
		}
		catch( Throwable t ) {
			super.setPaneMode( oldMode );
			((ICFSecJavaFXServiceTypePaneCommon)attrPane).setPaneMode( oldMode );
			((ICFSecJavaFXServiceTypePaneCommon)eltTabPane).setPaneMode( oldMode );
			throw t;
		}
	}
}
