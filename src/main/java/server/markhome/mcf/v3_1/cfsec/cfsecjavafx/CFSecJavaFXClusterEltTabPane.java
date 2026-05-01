// Description: Java 25 JavaFX Element TabPane implementation for Cluster.

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
 *	CFSecJavaFXClusterEltTabPane JavaFX Element TabPane implementation
 *	for Cluster.
 */
public class CFSecJavaFXClusterEltTabPane
extends CFTabPane
implements ICFSecJavaFXClusterPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsTenantList = "Optional Components Tenant";
	protected CFTab tabComponentsTenant = null;
	public final String LABEL_TabComponentsSecGroupList = "Optional Components Security Group";
	protected CFTab tabComponentsSecGroup = null;
	public final String LABEL_TabComponentsSecRoleList = "Optional Components Security Role";
	protected CFTab tabComponentsSecRole = null;
	public final String LABEL_TabComponentsSysClusterList = "Optional Components System Cluster";
	protected CFTab tabComponentsSysCluster = null;
	protected CFBorderPane tabViewComponentsTenantListPane = null;
	protected CFBorderPane tabViewComponentsSecGroupListPane = null;
	protected CFBorderPane tabViewComponentsSecRoleListPane = null;
	protected CFBorderPane tabViewComponentsSysClusterListPane = null;

	public CFSecJavaFXClusterEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecClusterObj argFocus ) {
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
		setJavaFXFocusAsCluster( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsTenant = new CFTab();
		tabComponentsTenant.setText( LABEL_TabComponentsTenantList );
		tabComponentsTenant.setContent( getTabViewComponentsTenantListPane() );
		getTabs().add( tabComponentsTenant );
		tabComponentsSecGroup = new CFTab();
		tabComponentsSecGroup.setText( LABEL_TabComponentsSecGroupList );
		tabComponentsSecGroup.setContent( getTabViewComponentsSecGroupListPane() );
		getTabs().add( tabComponentsSecGroup );
		tabComponentsSecRole = new CFTab();
		tabComponentsSecRole.setText( LABEL_TabComponentsSecRoleList );
		tabComponentsSecRole.setContent( getTabViewComponentsSecRoleListPane() );
		getTabs().add( tabComponentsSecRole );
		tabComponentsSysCluster = new CFTab();
		tabComponentsSysCluster.setText( LABEL_TabComponentsSysClusterList );
		tabComponentsSysCluster.setContent( getTabViewComponentsSysClusterListPane() );
		getTabs().add( tabComponentsSysCluster );
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
		if( ( value == null ) || ( value instanceof ICFSecClusterObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecClusterObj" );
		}
	}

	public void setJavaFXFocusAsCluster( ICFSecClusterObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecClusterObj getJavaFXFocusAsCluster() {
		return( (ICFSecClusterObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsTenantList
	implements ICFRefreshCallback
	{
		public RefreshComponentsTenantList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataComponentsTenantList
	implements ICFSecJavaFXTenantPageCallback
	{
		public PageDataComponentsTenantList() {
		}

		public List<ICFSecTenantObj> pageData( CFLibDbKeyHash256 priorId )
		{
			List<ICFSecTenantObj> dataList;
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getTenantTableObj().pageTenantByClusterIdx( focus.getRequiredId(),
					priorId );
			}
			else {
				dataList = new ArrayList<ICFSecTenantObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsTenantListPane() {
		if( tabViewComponentsTenantListPane == null ) {
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			ICFSecClusterObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecClusterObj ) ) {
				javafxContainer = (ICFSecClusterObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsTenantListPane = javafxSchema.getTenantFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsTenantList(), new RefreshComponentsTenantList(), false );
		}
		return( tabViewComponentsTenantListPane );
	}

	protected class RefreshComponentsSecGroupList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSecGroupList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecClusGrpObj> dataCollection;
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsSecGroupListPane();
			ICFSecJavaFXSecClusGrpPaneList jpList = (ICFSecJavaFXSecClusGrpPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsSecGroupListPane() {
		if( tabViewComponentsSecGroupListPane == null ) {
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			Collection<ICFSecSecClusGrpObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewComponentsSecGroupListPane = javafxSchema.getSecClusGrpFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsSecGroupList(), false );
		}
		return( tabViewComponentsSecGroupListPane );
	}

	protected class RefreshComponentsSecRoleList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSecRoleList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecClusRoleObj> dataCollection;
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecRole( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsSecRoleListPane();
			ICFSecJavaFXSecClusRolePaneList jpList = (ICFSecJavaFXSecClusRolePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsSecRoleListPane() {
		if( tabViewComponentsSecRoleListPane == null ) {
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			Collection<ICFSecSecClusRoleObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecRole( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewComponentsSecRoleListPane = javafxSchema.getSecClusRoleFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsSecRoleList(), false );
		}
		return( tabViewComponentsSecRoleListPane );
	}

	protected class RefreshComponentsSysClusterList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSysClusterList() {
		}

		public void refreshMe() {
			Collection<ICFSecSysClusterObj> dataCollection;
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSysCluster( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsSysClusterListPane();
			ICFSecJavaFXSysClusterPaneList jpList = (ICFSecJavaFXSysClusterPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsSysClusterListPane() {
		if( tabViewComponentsSysClusterListPane == null ) {
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			Collection<ICFSecSysClusterObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSysCluster( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFSecClusterObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecClusterObj ) ) {
				javafxContainer = (ICFSecClusterObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsSysClusterListPane = javafxSchema.getSysClusterFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsSysClusterList(), false );
		}
		return( tabViewComponentsSysClusterListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsTenantListPane != null ) {
			((ICFSecJavaFXTenantPaneCommon)tabViewComponentsTenantListPane).setPaneMode( value );
		}
		if( tabViewComponentsSecGroupListPane != null ) {
			((ICFSecJavaFXSecClusGrpPaneCommon)tabViewComponentsSecGroupListPane).setPaneMode( value );
		}
		if( tabViewComponentsSecRoleListPane != null ) {
			((ICFSecJavaFXSecClusRolePaneCommon)tabViewComponentsSecRoleListPane).setPaneMode( value );
		}
		if( tabViewComponentsSysClusterListPane != null ) {
			((ICFSecJavaFXSysClusterPaneCommon)tabViewComponentsSysClusterListPane).setPaneMode( value );
		}
	}
}
