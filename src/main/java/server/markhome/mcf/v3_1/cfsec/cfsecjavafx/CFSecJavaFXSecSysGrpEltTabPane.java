// Description: Java 25 JavaFX Element TabPane implementation for SecSysGrp.

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
 *	CFSecJavaFXSecSysGrpEltTabPane JavaFX Element TabPane implementation
 *	for SecSysGrp.
 */
public class CFSecJavaFXSecSysGrpEltTabPane
extends CFTabPane
implements ICFSecJavaFXSecSysGrpPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabChildrenImplRoleAttr = "Optional Children Implements role";
	protected CFTab tabChildrenImplRole = null;
	public final String LABEL_TabChildrenIncByGrpList = "Optional Children Included by Group";
	protected CFTab tabChildrenIncByGrp = null;
	public final String LABEL_TabChildrenMembByGrpList = "Optional Children Members of Group";
	protected CFTab tabChildrenMembByGrp = null;
	public final String LABEL_TabChildrenImplClusGrpList = "Optional Children Implements cluster group";
	protected CFTab tabChildrenImplClusGrp = null;
	public final String LABEL_TabChildrenImplTentGrpList = "Optional Children Implements tenant group";
	protected CFTab tabChildrenImplTentGrp = null;
	public final String LABEL_TabChildrenImplClusRoleList = "Optional Children Implements cluster role";
	protected CFTab tabChildrenImplClusRole = null;
	public final String LABEL_TabChildrenImplTentRoleList = "Optional Children Implements tenant role";
	protected CFTab tabChildrenImplTentRole = null;
	public final String LABEL_TabChildrenSysGrpByNameList = "Optional Children SysGroup by Name";
	protected CFTab tabChildrenSysGrpByName = null;
	public final String LABEL_TabChildrenClusGrpByNameList = "Optional Children Cluster Group by Name";
	protected CFTab tabChildrenClusGrpByName = null;
	public final String LABEL_TabChildrenTentGrpByNameList = "Optional Children Tenant Group by Name";
	protected CFTab tabChildrenTentGrpByName = null;
	public final String LABEL_TabChildrenRoleByEnableNameList = "Optional Children Role by Name";
	protected CFTab tabChildrenRoleByEnableName = null;
	protected ScrollPane tabViewChildrenImplRoleAttrScrollPane = null;
	protected CFGridPane tabViewChildrenImplRoleAttrPane = null;
	protected CFBorderPane tabViewChildrenIncByGrpListPane = null;
	protected CFBorderPane tabViewChildrenMembByGrpListPane = null;
	protected CFBorderPane tabViewChildrenImplClusGrpListPane = null;
	protected CFBorderPane tabViewChildrenImplTentGrpListPane = null;
	protected CFBorderPane tabViewChildrenImplClusRoleListPane = null;
	protected CFBorderPane tabViewChildrenImplTentRoleListPane = null;
	protected CFBorderPane tabViewChildrenSysGrpByNameListPane = null;
	protected CFBorderPane tabViewChildrenClusGrpByNameListPane = null;
	protected CFBorderPane tabViewChildrenTentGrpByNameListPane = null;
	protected CFBorderPane tabViewChildrenRoleByEnableNameListPane = null;

	public CFSecJavaFXSecSysGrpEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecSysGrpObj argFocus ) {
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
		setJavaFXFocusAsSecSysGrp( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabChildrenImplRole = new CFTab();
		tabChildrenImplRole.setText( LABEL_TabChildrenImplRoleAttr );
		tabChildrenImplRole.setContent( getTabViewChildrenImplRoleAttrScrollPane() );
		getTabs().add( tabChildrenImplRole );
		tabChildrenIncByGrp = new CFTab();
		tabChildrenIncByGrp.setText( LABEL_TabChildrenIncByGrpList );
		tabChildrenIncByGrp.setContent( getTabViewChildrenIncByGrpListPane() );
		getTabs().add( tabChildrenIncByGrp );
		tabChildrenMembByGrp = new CFTab();
		tabChildrenMembByGrp.setText( LABEL_TabChildrenMembByGrpList );
		tabChildrenMembByGrp.setContent( getTabViewChildrenMembByGrpListPane() );
		getTabs().add( tabChildrenMembByGrp );
		tabChildrenImplClusGrp = new CFTab();
		tabChildrenImplClusGrp.setText( LABEL_TabChildrenImplClusGrpList );
		tabChildrenImplClusGrp.setContent( getTabViewChildrenImplClusGrpListPane() );
		getTabs().add( tabChildrenImplClusGrp );
		tabChildrenImplTentGrp = new CFTab();
		tabChildrenImplTentGrp.setText( LABEL_TabChildrenImplTentGrpList );
		tabChildrenImplTentGrp.setContent( getTabViewChildrenImplTentGrpListPane() );
		getTabs().add( tabChildrenImplTentGrp );
		tabChildrenImplClusRole = new CFTab();
		tabChildrenImplClusRole.setText( LABEL_TabChildrenImplClusRoleList );
		tabChildrenImplClusRole.setContent( getTabViewChildrenImplClusRoleListPane() );
		getTabs().add( tabChildrenImplClusRole );
		tabChildrenImplTentRole = new CFTab();
		tabChildrenImplTentRole.setText( LABEL_TabChildrenImplTentRoleList );
		tabChildrenImplTentRole.setContent( getTabViewChildrenImplTentRoleListPane() );
		getTabs().add( tabChildrenImplTentRole );
		tabChildrenSysGrpByName = new CFTab();
		tabChildrenSysGrpByName.setText( LABEL_TabChildrenSysGrpByNameList );
		tabChildrenSysGrpByName.setContent( getTabViewChildrenSysGrpByNameListPane() );
		getTabs().add( tabChildrenSysGrpByName );
		tabChildrenClusGrpByName = new CFTab();
		tabChildrenClusGrpByName.setText( LABEL_TabChildrenClusGrpByNameList );
		tabChildrenClusGrpByName.setContent( getTabViewChildrenClusGrpByNameListPane() );
		getTabs().add( tabChildrenClusGrpByName );
		tabChildrenTentGrpByName = new CFTab();
		tabChildrenTentGrpByName.setText( LABEL_TabChildrenTentGrpByNameList );
		tabChildrenTentGrpByName.setContent( getTabViewChildrenTentGrpByNameListPane() );
		getTabs().add( tabChildrenTentGrpByName );
		tabChildrenRoleByEnableName = new CFTab();
		tabChildrenRoleByEnableName.setText( LABEL_TabChildrenRoleByEnableNameList );
		tabChildrenRoleByEnableName.setContent( getTabViewChildrenRoleByEnableNameListPane() );
		getTabs().add( tabChildrenRoleByEnableName );
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
		if( ( value == null ) || ( value instanceof ICFSecSecSysGrpObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSysGrpObj" );
		}
	}

	public void setJavaFXFocusAsSecSysGrp( ICFSecSecSysGrpObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecSysGrpObj getJavaFXFocusAsSecSysGrp() {
		return( (ICFSecSecSysGrpObj)getJavaFXFocus() );
	}

	public ScrollPane getTabViewChildrenImplRoleAttrScrollPane() {
		if( tabViewChildrenImplRoleAttrScrollPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecRoleObj refImplRole =
				( focus != null )
					? focus.getOptionalChildrenImplRole()
					: null;
			tabViewChildrenImplRoleAttrPane = javafxSchema.getSecRoleFactory().newAttrPane( cfFormManager, refImplRole );
			tabViewChildrenImplRoleAttrScrollPane = new ScrollPane();
			tabViewChildrenImplRoleAttrScrollPane.setFitToWidth( true );
			tabViewChildrenImplRoleAttrScrollPane.setHbarPolicy( ScrollBarPolicy.NEVER );
			tabViewChildrenImplRoleAttrScrollPane.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
			tabViewChildrenImplRoleAttrScrollPane.setContent( tabViewChildrenImplRoleAttrPane );
		}
		return( tabViewChildrenImplRoleAttrScrollPane );
	}

	protected class RefreshChildrenIncByGrpList
	implements ICFRefreshCallback
	{
		public RefreshChildrenIncByGrpList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenIncByGrpList
	implements ICFSecJavaFXSecSysGrpIncPageCallback
	{
		public PageDataChildrenIncByGrpList() {
		}

		public List<ICFSecSecSysGrpIncObj> pageData( CFLibDbKeyHash256 priorSecSysGrpId,
		String priorInclName )
		{
			List<ICFSecSecSysGrpIncObj> dataList;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSysGrpIncTableObj().pageSecSysGrpIncBySysGrpIdx( focus.getRequiredSecSysGrpId(),
					priorSecSysGrpId,
					priorInclName );
			}
			else {
				dataList = new ArrayList<ICFSecSecSysGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenIncByGrpListPane() {
		if( tabViewChildrenIncByGrpListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecSysGrpObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecSysGrpObj ) ) {
				javafxContainer = (ICFSecSecSysGrpObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenIncByGrpListPane = javafxSchema.getSecSysGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenIncByGrpList(), new RefreshChildrenIncByGrpList(), false );
		}
		return( tabViewChildrenIncByGrpListPane );
	}

	protected class RefreshChildrenMembByGrpList
	implements ICFRefreshCallback
	{
		public RefreshChildrenMembByGrpList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenMembByGrpList
	implements ICFSecJavaFXSecSysGrpMembPageCallback
	{
		public PageDataChildrenMembByGrpList() {
		}

		public List<ICFSecSecSysGrpMembObj> pageData( CFLibDbKeyHash256 priorSecSysGrpId,
		String priorLoginId )
		{
			List<ICFSecSecSysGrpMembObj> dataList;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSysGrpMembTableObj().pageSecSysGrpMembBySysGrpIdx( focus.getRequiredSecSysGrpId(),
					priorSecSysGrpId,
					priorLoginId );
			}
			else {
				dataList = new ArrayList<ICFSecSecSysGrpMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenMembByGrpListPane() {
		if( tabViewChildrenMembByGrpListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecSysGrpObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecSysGrpObj ) ) {
				javafxContainer = (ICFSecSecSysGrpObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenMembByGrpListPane = javafxSchema.getSecSysGrpMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenMembByGrpList(), new RefreshChildrenMembByGrpList(), false );
		}
		return( tabViewChildrenMembByGrpListPane );
	}

	protected class RefreshChildrenImplClusGrpList
	implements ICFRefreshCallback
	{
		public RefreshChildrenImplClusGrpList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecClusGrpObj> dataCollection;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplClusGrp( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewChildrenImplClusGrpListPane();
			ICFSecJavaFXSecClusGrpPaneList jpList = (ICFSecJavaFXSecClusGrpPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewChildrenImplClusGrpListPane() {
		if( tabViewChildrenImplClusGrpListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			Collection<ICFSecSecClusGrpObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplClusGrp( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewChildrenImplClusGrpListPane = javafxSchema.getSecClusGrpFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshChildrenImplClusGrpList(), false );
		}
		return( tabViewChildrenImplClusGrpListPane );
	}

	protected class RefreshChildrenImplTentGrpList
	implements ICFRefreshCallback
	{
		public RefreshChildrenImplTentGrpList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecTentGrpObj> dataCollection;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplTentGrp( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewChildrenImplTentGrpListPane();
			ICFSecJavaFXSecTentGrpPaneList jpList = (ICFSecJavaFXSecTentGrpPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewChildrenImplTentGrpListPane() {
		if( tabViewChildrenImplTentGrpListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			Collection<ICFSecSecTentGrpObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplTentGrp( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewChildrenImplTentGrpListPane = javafxSchema.getSecTentGrpFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshChildrenImplTentGrpList(), false );
		}
		return( tabViewChildrenImplTentGrpListPane );
	}

	protected class RefreshChildrenImplClusRoleList
	implements ICFRefreshCallback
	{
		public RefreshChildrenImplClusRoleList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecClusRoleObj> dataCollection;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplClusRole( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewChildrenImplClusRoleListPane();
			ICFSecJavaFXSecClusRolePaneList jpList = (ICFSecJavaFXSecClusRolePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewChildrenImplClusRoleListPane() {
		if( tabViewChildrenImplClusRoleListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			Collection<ICFSecSecClusRoleObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplClusRole( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewChildrenImplClusRoleListPane = javafxSchema.getSecClusRoleFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshChildrenImplClusRoleList(), false );
		}
		return( tabViewChildrenImplClusRoleListPane );
	}

	protected class RefreshChildrenImplTentRoleList
	implements ICFRefreshCallback
	{
		public RefreshChildrenImplTentRoleList() {
		}

		public void refreshMe() {
			Collection<ICFSecSecTentRoleObj> dataCollection;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplTentRole( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewChildrenImplTentRoleListPane();
			ICFSecJavaFXSecTentRolePaneList jpList = (ICFSecJavaFXSecTentRolePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewChildrenImplTentRoleListPane() {
		if( tabViewChildrenImplTentRoleListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			Collection<ICFSecSecTentRoleObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenImplTentRole( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFSecSecSysGrpObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecSysGrpObj ) ) {
				javafxContainer = (ICFSecSecSysGrpObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenImplTentRoleListPane = javafxSchema.getSecTentRoleFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshChildrenImplTentRoleList(), false );
		}
		return( tabViewChildrenImplTentRoleListPane );
	}

	protected class RefreshChildrenSysGrpByNameList
	implements ICFRefreshCallback
	{
		public RefreshChildrenSysGrpByNameList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenSysGrpByNameList
	implements ICFSecJavaFXSecSysGrpIncPageCallback
	{
		public PageDataChildrenSysGrpByNameList() {
		}

		public List<ICFSecSecSysGrpIncObj> pageData( CFLibDbKeyHash256 priorSecSysGrpId,
		String priorInclName )
		{
			List<ICFSecSecSysGrpIncObj> dataList;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSysGrpIncTableObj().pageSecSysGrpIncByNameIdx( focus.getRequiredName(),
					priorSecSysGrpId,
					priorInclName );
			}
			else {
				dataList = new ArrayList<ICFSecSecSysGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenSysGrpByNameListPane() {
		if( tabViewChildrenSysGrpByNameListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecSysGrpObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecSysGrpObj ) ) {
				javafxContainer = (ICFSecSecSysGrpObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenSysGrpByNameListPane = javafxSchema.getSecSysGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenSysGrpByNameList(), new RefreshChildrenSysGrpByNameList(), false );
		}
		return( tabViewChildrenSysGrpByNameListPane );
	}

	protected class RefreshChildrenClusGrpByNameList
	implements ICFRefreshCallback
	{
		public RefreshChildrenClusGrpByNameList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenClusGrpByNameList
	implements ICFSecJavaFXSecClusGrpIncPageCallback
	{
		public PageDataChildrenClusGrpByNameList() {
		}

		public List<ICFSecSecClusGrpIncObj> pageData( CFLibDbKeyHash256 priorSecClusGrpId,
		String priorInclName )
		{
			List<ICFSecSecClusGrpIncObj> dataList;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecClusGrpIncTableObj().pageSecClusGrpIncByNameIdx( focus.getRequiredName(),
					priorSecClusGrpId,
					priorInclName );
			}
			else {
				dataList = new ArrayList<ICFSecSecClusGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenClusGrpByNameListPane() {
		if( tabViewChildrenClusGrpByNameListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecClusGrpObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecClusGrpObj ) ) {
				javafxContainer = (ICFSecSecClusGrpObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenClusGrpByNameListPane = javafxSchema.getSecClusGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenClusGrpByNameList(), new RefreshChildrenClusGrpByNameList(), false );
		}
		return( tabViewChildrenClusGrpByNameListPane );
	}

	protected class RefreshChildrenTentGrpByNameList
	implements ICFRefreshCallback
	{
		public RefreshChildrenTentGrpByNameList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenTentGrpByNameList
	implements ICFSecJavaFXSecTentGrpIncPageCallback
	{
		public PageDataChildrenTentGrpByNameList() {
		}

		public List<ICFSecSecTentGrpIncObj> pageData( CFLibDbKeyHash256 priorSecTentGrpId,
		String priorInclName )
		{
			List<ICFSecSecTentGrpIncObj> dataList;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecTentGrpIncTableObj().pageSecTentGrpIncByNameIdx( focus.getRequiredName(),
					priorSecTentGrpId,
					priorInclName );
			}
			else {
				dataList = new ArrayList<ICFSecSecTentGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenTentGrpByNameListPane() {
		if( tabViewChildrenTentGrpByNameListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecTentGrpObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecTentGrpObj ) ) {
				javafxContainer = (ICFSecSecTentGrpObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenTentGrpByNameListPane = javafxSchema.getSecTentGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenTentGrpByNameList(), new RefreshChildrenTentGrpByNameList(), false );
		}
		return( tabViewChildrenTentGrpByNameListPane );
	}

	protected class RefreshChildrenRoleByEnableNameList
	implements ICFRefreshCallback
	{
		public RefreshChildrenRoleByEnableNameList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenRoleByEnableNameList
	implements ICFSecJavaFXSecRoleEnablesPageCallback
	{
		public PageDataChildrenRoleByEnableNameList() {
		}

		public List<ICFSecSecRoleEnablesObj> pageData( CFLibDbKeyHash256 priorSecRoleId,
		String priorEnableName )
		{
			List<ICFSecSecRoleEnablesObj> dataList;
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecRoleEnablesTableObj().pageSecRoleEnablesByNameIdx( focus.getRequiredName(),
					priorSecRoleId,
					priorEnableName );
			}
			else {
				dataList = new ArrayList<ICFSecSecRoleEnablesObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenRoleByEnableNameListPane() {
		if( tabViewChildrenRoleByEnableNameListPane == null ) {
			ICFSecSecSysGrpObj focus = (ICFSecSecSysGrpObj)getJavaFXFocusAsSecSysGrp();
			ICFSecSecRoleObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecRoleObj ) ) {
				javafxContainer = (ICFSecSecRoleObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenRoleByEnableNameListPane = javafxSchema.getSecRoleEnablesFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenRoleByEnableNameList(), new RefreshChildrenRoleByEnableNameList(), false );
		}
		return( tabViewChildrenRoleByEnableNameListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
	if( tabViewChildrenImplRoleAttrPane != null ) {
		((ICFSecJavaFXSecRolePaneCommon)tabViewChildrenImplRoleAttrPane).setPaneMode( CFPane.PaneMode.View );
	}
		if( tabViewChildrenIncByGrpListPane != null ) {
			((ICFSecJavaFXSecSysGrpIncPaneCommon)tabViewChildrenIncByGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenMembByGrpListPane != null ) {
			((ICFSecJavaFXSecSysGrpMembPaneCommon)tabViewChildrenMembByGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenImplClusGrpListPane != null ) {
			((ICFSecJavaFXSecClusGrpPaneCommon)tabViewChildrenImplClusGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenImplTentGrpListPane != null ) {
			((ICFSecJavaFXSecTentGrpPaneCommon)tabViewChildrenImplTentGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenImplClusRoleListPane != null ) {
			((ICFSecJavaFXSecClusRolePaneCommon)tabViewChildrenImplClusRoleListPane).setPaneMode( value );
		}
		if( tabViewChildrenImplTentRoleListPane != null ) {
			((ICFSecJavaFXSecTentRolePaneCommon)tabViewChildrenImplTentRoleListPane).setPaneMode( value );
		}
		if( tabViewChildrenSysGrpByNameListPane != null ) {
			((ICFSecJavaFXSecSysGrpIncPaneCommon)tabViewChildrenSysGrpByNameListPane).setPaneMode( value );
		}
		if( tabViewChildrenClusGrpByNameListPane != null ) {
			((ICFSecJavaFXSecClusGrpIncPaneCommon)tabViewChildrenClusGrpByNameListPane).setPaneMode( value );
		}
		if( tabViewChildrenTentGrpByNameListPane != null ) {
			((ICFSecJavaFXSecTentGrpIncPaneCommon)tabViewChildrenTentGrpByNameListPane).setPaneMode( value );
		}
		if( tabViewChildrenRoleByEnableNameListPane != null ) {
			((ICFSecJavaFXSecRoleEnablesPaneCommon)tabViewChildrenRoleByEnableNameListPane).setPaneMode( value );
		}
	}
}
