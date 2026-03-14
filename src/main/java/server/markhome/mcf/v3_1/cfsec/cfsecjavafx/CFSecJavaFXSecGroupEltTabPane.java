// Description: Java 25 JavaFX Element TabPane implementation for SecGroup.

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
 *	CFSecJavaFXSecGroupEltTabPane JavaFX Element TabPane implementation
 *	for SecGroup.
 */
public class CFSecJavaFXSecGroupEltTabPane
extends CFTabPane
implements ICFSecJavaFXSecGroupPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsIncludeList = "Optional Components Sub Group";
	protected CFTab tabComponentsInclude = null;
	public final String LABEL_TabComponentsMemberList = "Optional Components Group Member";
	protected CFTab tabComponentsMember = null;
	public final String LABEL_TabChildrenIncByGroupList = "Required Children Included By Group";
	protected CFTab tabChildrenIncByGroup = null;
	protected CFBorderPane tabViewComponentsIncludeListPane = null;
	protected CFBorderPane tabViewComponentsMemberListPane = null;
	protected CFBorderPane tabViewChildrenIncByGroupListPane = null;

	public CFSecJavaFXSecGroupEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecGroupObj argFocus ) {
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
		setJavaFXFocusAsSecGroup( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsInclude = new CFTab();
		tabComponentsInclude.setText( LABEL_TabComponentsIncludeList );
		tabComponentsInclude.setContent( getTabViewComponentsIncludeListPane() );
		getTabs().add( tabComponentsInclude );
		tabComponentsMember = new CFTab();
		tabComponentsMember.setText( LABEL_TabComponentsMemberList );
		tabComponentsMember.setContent( getTabViewComponentsMemberListPane() );
		getTabs().add( tabComponentsMember );
		tabChildrenIncByGroup = new CFTab();
		tabChildrenIncByGroup.setText( LABEL_TabChildrenIncByGroupList );
		tabChildrenIncByGroup.setContent( getTabViewChildrenIncByGroupListPane() );
		getTabs().add( tabChildrenIncByGroup );
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
	}

	public void setJavaFXFocusAsSecGroup( ICFSecSecGroupObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecGroupObj getJavaFXFocusAsSecGroup() {
		return( (ICFSecSecGroupObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsIncludeList
	implements ICFRefreshCallback
	{
		public RefreshComponentsIncludeList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataComponentsIncludeList
	implements ICFSecJavaFXSecGrpIncPageCallback
	{
		public PageDataComponentsIncludeList() {
		}

		public List<ICFSecSecGrpIncObj> pageData( CFLibDbKeyHash256 priorSecGrpIncId )
		{
			List<ICFSecSecGrpIncObj> dataList;
			ICFSecSecGroupObj focus = (ICFSecSecGroupObj)getJavaFXFocusAsSecGroup();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecGrpIncTableObj().pageSecGrpIncByGroupIdx( focus.getRequiredSecGroupId(),
					priorSecGrpIncId );
			}
			else {
				dataList = new ArrayList<ICFSecSecGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsIncludeListPane() {
		if( tabViewComponentsIncludeListPane == null ) {
			ICFSecSecGroupObj focus = (ICFSecSecGroupObj)getJavaFXFocusAsSecGroup();
			ICFSecSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecGroupObj ) ) {
				javafxContainer = (ICFSecSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsIncludeListPane = javafxSchema.getSecGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsIncludeList(), new RefreshComponentsIncludeList(), false );
		}
		return( tabViewComponentsIncludeListPane );
	}

	protected class RefreshComponentsMemberList
	implements ICFRefreshCallback
	{
		public RefreshComponentsMemberList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataComponentsMemberList
	implements ICFSecJavaFXSecGrpMembPageCallback
	{
		public PageDataComponentsMemberList() {
		}

		public List<ICFSecSecGrpMembObj> pageData( CFLibDbKeyHash256 priorSecGrpMembId )
		{
			List<ICFSecSecGrpMembObj> dataList;
			ICFSecSecGroupObj focus = (ICFSecSecGroupObj)getJavaFXFocusAsSecGroup();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecGrpMembTableObj().pageSecGrpMembByGroupIdx( focus.getRequiredSecGroupId(),
					priorSecGrpMembId );
			}
			else {
				dataList = new ArrayList<ICFSecSecGrpMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsMemberListPane() {
		if( tabViewComponentsMemberListPane == null ) {
			ICFSecSecGroupObj focus = (ICFSecSecGroupObj)getJavaFXFocusAsSecGroup();
			ICFSecSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecGroupObj ) ) {
				javafxContainer = (ICFSecSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsMemberListPane = javafxSchema.getSecGrpMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsMemberList(), new RefreshComponentsMemberList(), false );
		}
		return( tabViewComponentsMemberListPane );
	}

	protected class RefreshChildrenIncByGroupList
	implements ICFRefreshCallback
	{
		public RefreshChildrenIncByGroupList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenIncByGroupList
	implements ICFSecJavaFXSecGrpIncPageCallback
	{
		public PageDataChildrenIncByGroupList() {
		}

		public List<ICFSecSecGrpIncObj> pageData( CFLibDbKeyHash256 priorSecGrpIncId )
		{
			List<ICFSecSecGrpIncObj> dataList;
			ICFSecSecGroupObj focus = (ICFSecSecGroupObj)getJavaFXFocusAsSecGroup();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecGrpIncTableObj().pageSecGrpIncByIncludeIdx( focus.getRequiredSecGroupId(),
					priorSecGrpIncId );
			}
			else {
				dataList = new ArrayList<ICFSecSecGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenIncByGroupListPane() {
		if( tabViewChildrenIncByGroupListPane == null ) {
			ICFSecSecGroupObj focus = (ICFSecSecGroupObj)getJavaFXFocusAsSecGroup();
			ICFSecSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecGroupObj ) ) {
				javafxContainer = (ICFSecSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenIncByGroupListPane = javafxSchema.getSecGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenIncByGroupList(), new RefreshChildrenIncByGroupList(), false );
		}
		return( tabViewChildrenIncByGroupListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsIncludeListPane != null ) {
			((ICFSecJavaFXSecGrpIncPaneCommon)tabViewComponentsIncludeListPane).setPaneMode( value );
		}
		if( tabViewComponentsMemberListPane != null ) {
			((ICFSecJavaFXSecGrpMembPaneCommon)tabViewComponentsMemberListPane).setPaneMode( value );
		}
		if( tabViewChildrenIncByGroupListPane != null ) {
			((ICFSecJavaFXSecGrpIncPaneCommon)tabViewChildrenIncByGroupListPane).setPaneMode( value );
		}
	}
}
