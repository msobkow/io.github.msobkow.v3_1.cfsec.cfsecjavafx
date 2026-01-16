// Description: Java 25 JavaFX Element TabPane implementation for Tenant.

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
import java.util.List;
import javafx.scene.Parent;
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
 *	CFSecJavaFXTenantEltTabPane JavaFX Element TabPane implementation
 *	for Tenant.
 */
public class CFSecJavaFXTenantEltTabPane
extends CFTabPane
implements ICFSecJavaFXTenantPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsTSecGroupList = "Optional Components Tenant Security Group";
	protected CFTab tabComponentsTSecGroup = null;
	protected CFBorderPane tabViewComponentsTSecGroupListPane = null;

	public CFSecJavaFXTenantEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecTenantObj argFocus ) {
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
		setJavaFXFocusAsTenant( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsTSecGroup = new CFTab();
		tabComponentsTSecGroup.setText( LABEL_TabComponentsTSecGroupList );
		tabComponentsTSecGroup.setContent( getTabViewComponentsTSecGroupListPane() );
		getTabs().add( tabComponentsTSecGroup );
		javafxIsInitializing = false;
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
		if( ( value == null ) || ( value instanceof ICFSecTenantObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecTenantObj" );
		}
	}

	public void setJavaFXFocusAsTenant( ICFSecTenantObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecTenantObj getJavaFXFocusAsTenant() {
		return( (ICFSecTenantObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsTSecGroupList
	implements ICFRefreshCallback
	{
		public RefreshComponentsTSecGroupList() {
		}

		public void refreshMe() {
			Collection<ICFSecTSecGroupObj> dataCollection;
			ICFSecTenantObj focus = (ICFSecTenantObj)getJavaFXFocusAsTenant();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsTSecGroupListPane();
			ICFSecJavaFXTSecGroupPaneList jpList = (ICFSecJavaFXTSecGroupPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsTSecGroupListPane() {
		if( tabViewComponentsTSecGroupListPane == null ) {
			ICFSecTenantObj focus = (ICFSecTenantObj)getJavaFXFocusAsTenant();
			Collection<ICFSecTSecGroupObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFSecTenantObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecTenantObj ) ) {
				javafxContainer = (ICFSecTenantObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsTSecGroupListPane = javafxSchema.getTSecGroupFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsTSecGroupList(), false );
		}
		return( tabViewComponentsTSecGroupListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsTSecGroupListPane != null ) {
			((ICFSecJavaFXTSecGroupPaneCommon)tabViewComponentsTSecGroupListPane).setPaneMode( value );
		}
	}
}
