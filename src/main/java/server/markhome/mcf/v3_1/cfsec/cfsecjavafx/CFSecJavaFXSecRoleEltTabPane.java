// Description: Java 25 JavaFX Element TabPane implementation for SecRole.

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
 *	CFSecJavaFXSecRoleEltTabPane JavaFX Element TabPane implementation
 *	for SecRole.
 */
public class CFSecJavaFXSecRoleEltTabPane
extends CFTabPane
implements ICFSecJavaFXSecRolePaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabChildrenEnabledByRoleList = "Optional Children Enabled by Role";
	protected CFTab tabChildrenEnabledByRole = null;
	public final String LABEL_TabChildrenMembByRoleList = "Optional Children Members of Role";
	protected CFTab tabChildrenMembByRole = null;
	protected CFBorderPane tabViewChildrenEnabledByRoleListPane = null;
	protected CFBorderPane tabViewChildrenMembByRoleListPane = null;

	public CFSecJavaFXSecRoleEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecRoleObj argFocus ) {
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
		setJavaFXFocusAsSecRole( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabChildrenEnabledByRole = new CFTab();
		tabChildrenEnabledByRole.setText( LABEL_TabChildrenEnabledByRoleList );
		tabChildrenEnabledByRole.setContent( getTabViewChildrenEnabledByRoleListPane() );
		getTabs().add( tabChildrenEnabledByRole );
		tabChildrenMembByRole = new CFTab();
		tabChildrenMembByRole.setText( LABEL_TabChildrenMembByRoleList );
		tabChildrenMembByRole.setContent( getTabViewChildrenMembByRoleListPane() );
		getTabs().add( tabChildrenMembByRole );
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
		if( ( value == null ) || ( value instanceof ICFSecSecRoleObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecRoleObj" );
		}
	}

	public void setJavaFXFocusAsSecRole( ICFSecSecRoleObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecRoleObj getJavaFXFocusAsSecRole() {
		return( (ICFSecSecRoleObj)getJavaFXFocus() );
	}

	protected class RefreshChildrenEnabledByRoleList
	implements ICFRefreshCallback
	{
		public RefreshChildrenEnabledByRoleList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenEnabledByRoleList
	implements ICFSecJavaFXSecRoleEnablesPageCallback
	{
		public PageDataChildrenEnabledByRoleList() {
		}

		public List<ICFSecSecRoleEnablesObj> pageData( CFLibDbKeyHash256 priorSecRoleId,
		String priorEnableName )
		{
			List<ICFSecSecRoleEnablesObj> dataList;
			ICFSecSecRoleObj focus = (ICFSecSecRoleObj)getJavaFXFocusAsSecRole();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecRoleEnablesTableObj().pageSecRoleEnablesByRoleIdx( focus.getRequiredSecRoleId(),
					priorSecRoleId,
					priorEnableName );
			}
			else {
				dataList = new ArrayList<ICFSecSecRoleEnablesObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenEnabledByRoleListPane() {
		if( tabViewChildrenEnabledByRoleListPane == null ) {
			ICFSecSecRoleObj focus = (ICFSecSecRoleObj)getJavaFXFocusAsSecRole();
			ICFSecSecRoleObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecRoleObj ) ) {
				javafxContainer = (ICFSecSecRoleObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenEnabledByRoleListPane = javafxSchema.getSecRoleEnablesFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenEnabledByRoleList(), new RefreshChildrenEnabledByRoleList(), false );
		}
		return( tabViewChildrenEnabledByRoleListPane );
	}

	protected class RefreshChildrenMembByRoleList
	implements ICFRefreshCallback
	{
		public RefreshChildrenMembByRoleList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenMembByRoleList
	implements ICFSecJavaFXSecRoleMembPageCallback
	{
		public PageDataChildrenMembByRoleList() {
		}

		public List<ICFSecSecRoleMembObj> pageData( CFLibDbKeyHash256 priorSecRoleId,
		String priorLoginId )
		{
			List<ICFSecSecRoleMembObj> dataList;
			ICFSecSecRoleObj focus = (ICFSecSecRoleObj)getJavaFXFocusAsSecRole();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecRoleMembTableObj().pageSecRoleMembByRoleIdx( focus.getRequiredSecRoleId(),
					priorSecRoleId,
					priorLoginId );
			}
			else {
				dataList = new ArrayList<ICFSecSecRoleMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenMembByRoleListPane() {
		if( tabViewChildrenMembByRoleListPane == null ) {
			ICFSecSecRoleObj focus = (ICFSecSecRoleObj)getJavaFXFocusAsSecRole();
			ICFSecSecRoleObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecRoleObj ) ) {
				javafxContainer = (ICFSecSecRoleObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenMembByRoleListPane = javafxSchema.getSecRoleMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenMembByRoleList(), new RefreshChildrenMembByRoleList(), false );
		}
		return( tabViewChildrenMembByRoleListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewChildrenEnabledByRoleListPane != null ) {
			((ICFSecJavaFXSecRoleEnablesPaneCommon)tabViewChildrenEnabledByRoleListPane).setPaneMode( value );
		}
		if( tabViewChildrenMembByRoleListPane != null ) {
			((ICFSecJavaFXSecRoleMembPaneCommon)tabViewChildrenMembByRoleListPane).setPaneMode( value );
		}
	}
}
