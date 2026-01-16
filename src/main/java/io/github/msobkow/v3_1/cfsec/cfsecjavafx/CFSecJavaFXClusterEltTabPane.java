// Description: Java 25 JavaFX Element TabPane implementation for Cluster.

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
	public final String LABEL_TabComponentsHostNodeList = "Optional Components Host Node";
	protected CFTab tabComponentsHostNode = null;
	public final String LABEL_TabComponentsTenantList = "Optional Components Tenant";
	protected CFTab tabComponentsTenant = null;
	public final String LABEL_TabComponentsSecGroupList = "Optional Components Security Group";
	protected CFTab tabComponentsSecGroup = null;
	public final String LABEL_TabComponentsSysClusterList = "Optional Components System Cluster";
	protected CFTab tabComponentsSysCluster = null;
	protected CFBorderPane tabViewComponentsHostNodeListPane = null;
	protected CFBorderPane tabViewComponentsTenantListPane = null;
	protected CFBorderPane tabViewComponentsSecGroupListPane = null;
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
		tabComponentsHostNode = new CFTab();
		tabComponentsHostNode.setText( LABEL_TabComponentsHostNodeList );
		tabComponentsHostNode.setContent( getTabViewComponentsHostNodeListPane() );
		getTabs().add( tabComponentsHostNode );
		tabComponentsTenant = new CFTab();
		tabComponentsTenant.setText( LABEL_TabComponentsTenantList );
		tabComponentsTenant.setContent( getTabViewComponentsTenantListPane() );
		getTabs().add( tabComponentsTenant );
		tabComponentsSecGroup = new CFTab();
		tabComponentsSecGroup.setText( LABEL_TabComponentsSecGroupList );
		tabComponentsSecGroup.setContent( getTabViewComponentsSecGroupListPane() );
		getTabs().add( tabComponentsSecGroup );
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

	protected class RefreshComponentsHostNodeList
	implements ICFRefreshCallback
	{
		public RefreshComponentsHostNodeList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataComponentsHostNodeList
	implements ICFSecJavaFXHostNodePageCallback
	{
		public PageDataComponentsHostNodeList() {
		}

		public List<ICFSecHostNodeObj> pageData( CFLibDbKeyHash256 priorHostNodeId )
		{
			List<ICFSecHostNodeObj> dataList;
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getHostNodeTableObj().pageHostNodeByClusterIdx( focus.getRequiredId(),
					priorHostNodeId );
			}
			else {
				dataList = new ArrayList<ICFSecHostNodeObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsHostNodeListPane() {
		if( tabViewComponentsHostNodeListPane == null ) {
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			ICFSecClusterObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecClusterObj ) ) {
				javafxContainer = (ICFSecClusterObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsHostNodeListPane = javafxSchema.getHostNodeFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsHostNodeList(), new RefreshComponentsHostNodeList(), false );
		}
		return( tabViewComponentsHostNodeListPane );
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
			Collection<ICFSecSecGroupObj> dataCollection;
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecGroup( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsSecGroupListPane();
			ICFSecJavaFXSecGroupPaneList jpList = (ICFSecJavaFXSecGroupPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsSecGroupListPane() {
		if( tabViewComponentsSecGroupListPane == null ) {
			ICFSecClusterObj focus = (ICFSecClusterObj)getJavaFXFocusAsCluster();
			Collection<ICFSecSecGroupObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSecGroup( javafxIsInitializing );
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
			tabViewComponentsSecGroupListPane = javafxSchema.getSecGroupFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsSecGroupList(), false );
		}
		return( tabViewComponentsSecGroupListPane );
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
		if( tabViewComponentsHostNodeListPane != null ) {
			((ICFSecJavaFXHostNodePaneCommon)tabViewComponentsHostNodeListPane).setPaneMode( value );
		}
		if( tabViewComponentsTenantListPane != null ) {
			((ICFSecJavaFXTenantPaneCommon)tabViewComponentsTenantListPane).setPaneMode( value );
		}
		if( tabViewComponentsSecGroupListPane != null ) {
			((ICFSecJavaFXSecGroupPaneCommon)tabViewComponentsSecGroupListPane).setPaneMode( value );
		}
		if( tabViewComponentsSysClusterListPane != null ) {
			((ICFSecJavaFXSysClusterPaneCommon)tabViewComponentsSysClusterListPane).setPaneMode( value );
		}
	}
}
