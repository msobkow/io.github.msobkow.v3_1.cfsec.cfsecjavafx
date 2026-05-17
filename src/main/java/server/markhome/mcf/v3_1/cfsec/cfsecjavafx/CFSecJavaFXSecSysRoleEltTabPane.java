// Description: Java 25 JavaFX Element TabPane implementation for SecSysRole.

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
 *	CFSecJavaFXSecSysRoleEltTabPane JavaFX Element TabPane implementation
 *	for SecSysRole.
 */
public class CFSecJavaFXSecSysRoleEltTabPane
extends CFTabPane
implements ICFSecJavaFXSecSysRolePaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabChildrenEnabledByRoleList = "Optional Children Enabled by Role";
	protected CFTab tabChildrenEnabledByRole = null;
	public final String LABEL_TabChildrenMembByRoleList = "Optional Children Members of System Role";
	protected CFTab tabChildrenMembByRole = null;
	protected CFBorderPane tabViewChildrenEnabledByRoleListPane = null;
	protected CFBorderPane tabViewChildrenMembByRoleListPane = null;

	public CFSecJavaFXSecSysRoleEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecSysRoleObj argFocus ) {
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
		setJavaFXFocusAsSecSysRole( argFocus );
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
	}

	public void setJavaFXFocusAsSecSysRole( ICFSecSecSysRoleObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecSysRoleObj getJavaFXFocusAsSecSysRole() {
		return( (ICFSecSecSysRoleObj)getJavaFXFocus() );
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
	implements ICFSecJavaFXSecSysRoleEnablesPageCallback
	{
		public PageDataChildrenEnabledByRoleList() {
		}

		public List<ICFSecSecSysRoleEnablesObj> pageData( CFLibDbKeyHash256 priorSecSysRoleId,
		String priorEnableName )
		{
			List<ICFSecSecSysRoleEnablesObj> dataList;
			ICFSecSecSysRoleObj focus = (ICFSecSecSysRoleObj)getJavaFXFocusAsSecSysRole();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSysRoleEnablesTableObj().pageSecSysRoleEnablesBySysRoleIdx( focus.getRequiredSecSysRoleId(),
					priorSecSysRoleId,
					priorEnableName );
			}
			else {
				dataList = new ArrayList<ICFSecSecSysRoleEnablesObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenEnabledByRoleListPane() {
		if( tabViewChildrenEnabledByRoleListPane == null ) {
			ICFSecSecSysRoleObj focus = (ICFSecSecSysRoleObj)getJavaFXFocusAsSecSysRole();
			ICFSecSecSysRoleObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecSysRoleObj ) ) {
				javafxContainer = (ICFSecSecSysRoleObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenEnabledByRoleListPane = javafxSchema.getSecSysRoleEnablesFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenEnabledByRoleList(), new RefreshChildrenEnabledByRoleList(), false );
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
	implements ICFSecJavaFXSecSysRoleMembPageCallback
	{
		public PageDataChildrenMembByRoleList() {
		}

		public List<ICFSecSecSysRoleMembObj> pageData( CFLibDbKeyHash256 priorSecSysRoleId,
		String priorLoginId )
		{
			List<ICFSecSecSysRoleMembObj> dataList;
			ICFSecSecSysRoleObj focus = (ICFSecSecSysRoleObj)getJavaFXFocusAsSecSysRole();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSysRoleMembTableObj().pageSecSysRoleMembBySysRoleIdx( focus.getRequiredSecSysRoleId(),
					priorSecSysRoleId,
					priorLoginId );
			}
			else {
				dataList = new ArrayList<ICFSecSecSysRoleMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenMembByRoleListPane() {
		if( tabViewChildrenMembByRoleListPane == null ) {
			ICFSecSecSysRoleObj focus = (ICFSecSecSysRoleObj)getJavaFXFocusAsSecSysRole();
			ICFSecSecSysRoleObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecSysRoleObj ) ) {
				javafxContainer = (ICFSecSecSysRoleObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenMembByRoleListPane = javafxSchema.getSecSysRoleMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenMembByRoleList(), new RefreshChildrenMembByRoleList(), false );
		}
		return( tabViewChildrenMembByRoleListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewChildrenEnabledByRoleListPane != null ) {
			((ICFSecJavaFXSecSysRoleEnablesPaneCommon)tabViewChildrenEnabledByRoleListPane).setPaneMode( value );
		}
		if( tabViewChildrenMembByRoleListPane != null ) {
			((ICFSecJavaFXSecSysRoleMembPaneCommon)tabViewChildrenMembByRoleListPane).setPaneMode( value );
		}
	}
}
