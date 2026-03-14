// Description: Java 25 JavaFX Element TabPane implementation for SecUser.

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
	public final String LABEL_TabChildrenSecGrpMembList = "Optional Children Security Group Members";
	protected CFTab tabChildrenSecGrpMemb = null;
	public final String LABEL_TabChildrenTSecGrpMembList = "Optional Children Tenant Security Group Members";
	protected CFTab tabChildrenTSecGrpMemb = null;
	protected CFBorderPane tabViewComponentsSecDevListPane = null;
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
		if( tabViewChildrenSecGrpMembListPane != null ) {
			((ICFSecJavaFXSecGrpMembPaneCommon)tabViewChildrenSecGrpMembListPane).setPaneMode( value );
		}
		if( tabViewChildrenTSecGrpMembListPane != null ) {
			((ICFSecJavaFXTSecGrpMembPaneCommon)tabViewChildrenTSecGrpMembListPane).setPaneMode( value );
		}
	}
}
