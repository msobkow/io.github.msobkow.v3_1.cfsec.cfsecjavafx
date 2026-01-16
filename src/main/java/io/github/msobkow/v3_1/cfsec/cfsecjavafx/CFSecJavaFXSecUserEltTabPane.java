// Description: Java 25 JavaFX Element TabPane implementation for SecUser.

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
 *	CFSecJavaFXSecUserEltTabPane JavaFX Element TabPane implementation
 *	for SecUser.
 */
public class CFSecJavaFXSecUserEltTabPane
extends CFTabPane
implements ICFSecJavaFXSecUserPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsSecDevList = "Optional Components Security Device";
	protected CFTab tabComponentsSecDev = null;
	public final String LABEL_TabComponentsSecSessList = "Optional Components Security Session";
	protected CFTab tabComponentsSecSess = null;
	public final String LABEL_TabChildrenSecProxyList = "Optional Children Security Proxy Session";
	protected CFTab tabChildrenSecProxy = null;
	public final String LABEL_TabChildrenSecGrpMembList = "Optional Children Security Group Members";
	protected CFTab tabChildrenSecGrpMemb = null;
	public final String LABEL_TabChildrenTSecGrpMembList = "Optional Children Tenant Security Group Members";
	protected CFTab tabChildrenTSecGrpMemb = null;
	protected CFBorderPane tabViewComponentsSecDevListPane = null;
	protected CFBorderPane tabViewComponentsSecSessListPane = null;
	protected CFBorderPane tabViewChildrenSecProxyListPane = null;
	protected CFBorderPane tabViewChildrenSecGrpMembListPane = null;
	protected CFBorderPane tabViewChildrenTSecGrpMembListPane = null;

	public CFSecJavaFXSecUserEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecUserObj argFocus ) {
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
		setJavaFXFocusAsSecUser( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsSecDev = new CFTab();
		tabComponentsSecDev.setText( LABEL_TabComponentsSecDevList );
		tabComponentsSecDev.setContent( getTabViewComponentsSecDevListPane() );
		getTabs().add( tabComponentsSecDev );
		tabComponentsSecSess = new CFTab();
		tabComponentsSecSess.setText( LABEL_TabComponentsSecSessList );
		tabComponentsSecSess.setContent( getTabViewComponentsSecSessListPane() );
		getTabs().add( tabComponentsSecSess );
		tabChildrenSecProxy = new CFTab();
		tabChildrenSecProxy.setText( LABEL_TabChildrenSecProxyList );
		tabChildrenSecProxy.setContent( getTabViewChildrenSecProxyListPane() );
		getTabs().add( tabChildrenSecProxy );
		tabChildrenSecGrpMemb = new CFTab();
		tabChildrenSecGrpMemb.setText( LABEL_TabChildrenSecGrpMembList );
		tabChildrenSecGrpMemb.setContent( getTabViewChildrenSecGrpMembListPane() );
		getTabs().add( tabChildrenSecGrpMemb );
		tabChildrenTSecGrpMemb = new CFTab();
		tabChildrenTSecGrpMemb.setText( LABEL_TabChildrenTSecGrpMembList );
		tabChildrenTSecGrpMemb.setContent( getTabViewChildrenTSecGrpMembListPane() );
		getTabs().add( tabChildrenTSecGrpMemb );
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
		if( ( value == null ) || ( value instanceof ICFSecSecUserObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecUserObj" );
		}
	}

	public void setJavaFXFocusAsSecUser( ICFSecSecUserObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecUserObj getJavaFXFocusAsSecUser() {
		return( (ICFSecSecUserObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsSecDevList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSecDevList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataComponentsSecDevList
	implements ICFSecJavaFXSecDevicePageCallback
	{
		public PageDataComponentsSecDevList() {
		}

		public List<ICFSecSecDeviceObj> pageData( CFLibDbKeyHash256 priorSecUserId,
		String priorDevName )
		{
			List<ICFSecSecDeviceObj> dataList;
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecDeviceTableObj().pageSecDeviceByUserIdx( focus.getRequiredSecUserId(),
					priorSecUserId,
					priorDevName );
			}
			else {
				dataList = new ArrayList<ICFSecSecDeviceObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsSecDevListPane() {
		if( tabViewComponentsSecDevListPane == null ) {
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			ICFSecSecUserObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecUserObj ) ) {
				javafxContainer = (ICFSecSecUserObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsSecDevListPane = javafxSchema.getSecDeviceFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsSecDevList(), new RefreshComponentsSecDevList(), false );
		}
		return( tabViewComponentsSecDevListPane );
	}

	protected class RefreshComponentsSecSessList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSecSessList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataComponentsSecSessList
	implements ICFSecJavaFXSecSessionPageCallback
	{
		public PageDataComponentsSecSessList() {
		}

		public List<ICFSecSecSessionObj> pageData( CFLibDbKeyHash256 priorSecSessionId )
		{
			List<ICFSecSecSessionObj> dataList;
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSessionTableObj().pageSecSessionBySecUserIdx( focus.getRequiredSecUserId(),
					priorSecSessionId );
			}
			else {
				dataList = new ArrayList<ICFSecSecSessionObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewComponentsSecSessListPane() {
		if( tabViewComponentsSecSessListPane == null ) {
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			ICFSecSecUserObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecUserObj ) ) {
				javafxContainer = (ICFSecSecUserObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsSecSessListPane = javafxSchema.getSecSessionFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataComponentsSecSessList(), new RefreshComponentsSecSessList(), false );
		}
		return( tabViewComponentsSecSessListPane );
	}

	protected class RefreshChildrenSecProxyList
	implements ICFRefreshCallback
	{
		public RefreshChildrenSecProxyList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenSecProxyList
	implements ICFSecJavaFXSecSessionPageCallback
	{
		public PageDataChildrenSecProxyList() {
		}

		public List<ICFSecSecSessionObj> pageData( CFLibDbKeyHash256 priorSecSessionId )
		{
			List<ICFSecSecSessionObj> dataList;
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecSessionTableObj().pageSecSessionBySecProxyIdx( focus.getRequiredSecUserId(),
					priorSecSessionId );
			}
			else {
				dataList = new ArrayList<ICFSecSecSessionObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenSecProxyListPane() {
		if( tabViewChildrenSecProxyListPane == null ) {
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			ICFSecSecUserObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecUserObj ) ) {
				javafxContainer = (ICFSecSecUserObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenSecProxyListPane = javafxSchema.getSecSessionFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenSecProxyList(), new RefreshChildrenSecProxyList(), false );
		}
		return( tabViewChildrenSecProxyListPane );
	}

	protected class RefreshChildrenSecGrpMembList
	implements ICFRefreshCallback
	{
		public RefreshChildrenSecGrpMembList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenSecGrpMembList
	implements ICFSecJavaFXSecGrpMembPageCallback
	{
		public PageDataChildrenSecGrpMembList() {
		}

		public List<ICFSecSecGrpMembObj> pageData( CFLibDbKeyHash256 priorSecGrpMembId )
		{
			List<ICFSecSecGrpMembObj> dataList;
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecGrpMembTableObj().pageSecGrpMembByUserIdx( focus.getRequiredSecUserId(),
					priorSecGrpMembId );
			}
			else {
				dataList = new ArrayList<ICFSecSecGrpMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenSecGrpMembListPane() {
		if( tabViewChildrenSecGrpMembListPane == null ) {
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			ICFSecSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecSecGroupObj ) ) {
				javafxContainer = (ICFSecSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenSecGrpMembListPane = javafxSchema.getSecGrpMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenSecGrpMembList(), new RefreshChildrenSecGrpMembList(), false );
		}
		return( tabViewChildrenSecGrpMembListPane );
	}

	protected class RefreshChildrenTSecGrpMembList
	implements ICFRefreshCallback
	{
		public RefreshChildrenTSecGrpMembList() {
		}

		public void refreshMe() {
			// Use page data instead
		}
	}

	protected class PageDataChildrenTSecGrpMembList
	implements ICFSecJavaFXTSecGrpMembPageCallback
	{
		public PageDataChildrenTSecGrpMembList() {
		}

		public List<ICFSecTSecGrpMembObj> pageData( CFLibDbKeyHash256 priorTSecGrpMembId )
		{
			List<ICFSecTSecGrpMembObj> dataList;
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getTSecGrpMembTableObj().pageTSecGrpMembByUserIdx( focus.getRequiredSecUserId(),
					priorTSecGrpMembId );
			}
			else {
				dataList = new ArrayList<ICFSecTSecGrpMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenTSecGrpMembListPane() {
		if( tabViewChildrenTSecGrpMembListPane == null ) {
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getJavaFXFocusAsSecUser();
			ICFSecTSecGroupObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecTSecGroupObj ) ) {
				javafxContainer = (ICFSecTSecGroupObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenTSecGrpMembListPane = javafxSchema.getTSecGrpMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenTSecGrpMembList(), new RefreshChildrenTSecGrpMembList(), false );
		}
		return( tabViewChildrenTSecGrpMembListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsSecDevListPane != null ) {
			((ICFSecJavaFXSecDevicePaneCommon)tabViewComponentsSecDevListPane).setPaneMode( value );
		}
		if( tabViewComponentsSecSessListPane != null ) {
			((ICFSecJavaFXSecSessionPaneCommon)tabViewComponentsSecSessListPane).setPaneMode( value );
		}
		if( tabViewChildrenSecProxyListPane != null ) {
			((ICFSecJavaFXSecSessionPaneCommon)tabViewChildrenSecProxyListPane).setPaneMode( value );
		}
		if( tabViewChildrenSecGrpMembListPane != null ) {
			((ICFSecJavaFXSecGrpMembPaneCommon)tabViewChildrenSecGrpMembListPane).setPaneMode( value );
		}
		if( tabViewChildrenTSecGrpMembListPane != null ) {
			((ICFSecJavaFXTSecGrpMembPaneCommon)tabViewChildrenTSecGrpMembListPane).setPaneMode( value );
		}
	}
}
