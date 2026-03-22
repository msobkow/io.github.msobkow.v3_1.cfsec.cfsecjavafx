// Description: Java 25 JavaFX Element TabPane implementation for Tenant.

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
import java.util.List;
import javafx.scene.Parent;
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
	public final String LABEL_TabComponentsSecGroupList = "Optional Components Tenant Security Group";
	protected CFTab tabComponentsSecGroup = null;
	protected CFBorderPane tabViewComponentsSecGroupListPane = null;

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
		tabComponentsSecGroup = new CFTab();
		tabComponentsSecGroup.setText( LABEL_TabComponentsSecGroupList );
		tabComponentsSecGroup.setContent( getTabViewComponentsSecGroupListPane() );
		getTabs().add( tabComponentsSecGroup );
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

	protected class RefreshComponentsSecGroupList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSecGroupList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecTentGrpObj> dataCollection;
			ICFSecTenantObj focus = (ICFSecTenantObj)getJavaFXFocusAsTenant();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsSecGroupListPane();
			ICFSecJavaFXSecTentGrpPaneList jpList = (ICFSecJavaFXSecTentGrpPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsSecGroupListPane() {
		if( tabViewComponentsSecGroupListPane == null ) {
			ICFSecTenantObj focus = (ICFSecTenantObj)getJavaFXFocusAsTenant();
			Collection<ICFSecSecTentGrpObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewComponentsSecGroupListPane = javafxSchema.getSecTentGrpFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsSecGroupList(), false );
		}
		return( tabViewComponentsSecGroupListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsSecGroupListPane != null ) {
			((ICFSecJavaFXSecTentGrpPaneCommon)tabViewComponentsSecGroupListPane).setPaneMode( value );
		}
	}
}
