// Description: Java 25 JavaFX Element TabPane implementation for TSecGroup.

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
 *	CFSecJavaFXTSecGroupEltTabPane JavaFX Element TabPane implementation
 *	for TSecGroup.
 */
public class CFSecJavaFXTSecGroupEltTabPane
extends CFTabPane
implements ICFSecJavaFXTSecGroupPaneCommon
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

	public CFSecJavaFXTSecGroupEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecTSecGroupObj argFocus ) {
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
		setJavaFXFocusAsTSecGroup( argFocus );
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
		if( ( value == null ) || ( value instanceof ICFSecTSecGroupObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecTSecGroupObj" );
		}
	}

	public void setJavaFXFocusAsTSecGroup( ICFSecTSecGroupObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecTSecGroupObj getJavaFXFocusAsTSecGroup() {
		return( (ICFSecTSecGroupObj)getJavaFXFocus() );
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
	implements ICFSecJavaFXTSecGrpIncPageCallback
	{
		public PageDataComponentsIncludeList() {
		}

		public List<ICFSecTSecGrpIncObj> pageData( CFLibDbKeyHash256 priorTSecGrpIncId )
		{
			List<ICFSecTSecGrpIncObj> dataList;
			ICFSecTSecGroupObj focus = (ICFSecTSecGroupObj)getJavaFXFocusAsTSecGroup();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getTSecGrpIncTableObj().pageTSecGrpIncByGroupIdx( focus.getRequiredTSecGroupId(),
					priorTSecGrpIncId );
			}
			else {
				dataList = new ArrayList<ICFSecTSecGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsIncludeListPane() {
		if( tabViewComponentsIncludeListPane == null ) {
			ICFSecTSecGroupObj focus = (ICFSecTSecGroupObj)getJavaFXFocusAsTSecGroup();
			ICFSecTSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecTSecGroupObj ) ) {
				javafxContainer = (ICFSecTSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsIncludeListPane = javafxSchema.getTSecGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsIncludeList(), new RefreshComponentsIncludeList(), false );
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
	implements ICFSecJavaFXTSecGrpMembPageCallback
	{
		public PageDataComponentsMemberList() {
		}

		public List<ICFSecTSecGrpMembObj> pageData( CFLibDbKeyHash256 priorTSecGrpMembId )
		{
			List<ICFSecTSecGrpMembObj> dataList;
			ICFSecTSecGroupObj focus = (ICFSecTSecGroupObj)getJavaFXFocusAsTSecGroup();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getTSecGrpMembTableObj().pageTSecGrpMembByGroupIdx( focus.getRequiredTSecGroupId(),
					priorTSecGrpMembId );
			}
			else {
				dataList = new ArrayList<ICFSecTSecGrpMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsMemberListPane() {
		if( tabViewComponentsMemberListPane == null ) {
			ICFSecTSecGroupObj focus = (ICFSecTSecGroupObj)getJavaFXFocusAsTSecGroup();
			ICFSecTSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecTSecGroupObj ) ) {
				javafxContainer = (ICFSecTSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsMemberListPane = javafxSchema.getTSecGrpMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsMemberList(), new RefreshComponentsMemberList(), false );
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
	implements ICFSecJavaFXTSecGrpIncPageCallback
	{
		public PageDataChildrenIncByGroupList() {
		}

		public List<ICFSecTSecGrpIncObj> pageData( CFLibDbKeyHash256 priorTSecGrpIncId )
		{
			List<ICFSecTSecGrpIncObj> dataList;
			ICFSecTSecGroupObj focus = (ICFSecTSecGroupObj)getJavaFXFocusAsTSecGroup();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getTSecGrpIncTableObj().pageTSecGrpIncByIncludeIdx( focus.getRequiredTSecGroupId(),
					priorTSecGrpIncId );
			}
			else {
				dataList = new ArrayList<ICFSecTSecGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenIncByGroupListPane() {
		if( tabViewChildrenIncByGroupListPane == null ) {
			ICFSecTSecGroupObj focus = (ICFSecTSecGroupObj)getJavaFXFocusAsTSecGroup();
			ICFSecTSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecTSecGroupObj ) ) {
				javafxContainer = (ICFSecTSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenIncByGroupListPane = javafxSchema.getTSecGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenIncByGroupList(), new RefreshChildrenIncByGroupList(), false );
		}
		return( tabViewChildrenIncByGroupListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsIncludeListPane != null ) {
			((ICFSecJavaFXTSecGrpIncPaneCommon)tabViewComponentsIncludeListPane).setPaneMode( value );
		}
		if( tabViewComponentsMemberListPane != null ) {
			((ICFSecJavaFXTSecGrpMembPaneCommon)tabViewComponentsMemberListPane).setPaneMode( value );
		}
		if( tabViewChildrenIncByGroupListPane != null ) {
			((ICFSecJavaFXTSecGrpIncPaneCommon)tabViewChildrenIncByGroupListPane).setPaneMode( value );
		}
	}
}
