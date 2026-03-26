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
	public final String LABEL_TabChildrenIncByGrpList = "Optional Children Included by Group";
	protected CFTab tabChildrenIncByGrp = null;
	public final String LABEL_TabChildrenMembByGrpList = "Optional Children Members of Group";
	protected CFTab tabChildrenMembByGrp = null;
	public final String LABEL_TabChildrenSysGrpByNameList = "Optional Children SysGroup by Name";
	protected CFTab tabChildrenSysGrpByName = null;
	protected CFBorderPane tabViewChildrenIncByGrpListPane = null;
	protected CFBorderPane tabViewChildrenMembByGrpListPane = null;
	protected CFBorderPane tabViewChildrenSysGrpByNameListPane = null;

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
		tabChildrenIncByGrp = new CFTab();
		tabChildrenIncByGrp.setText( LABEL_TabChildrenIncByGrpList );
		tabChildrenIncByGrp.setContent( getTabViewChildrenIncByGrpListPane() );
		getTabs().add( tabChildrenIncByGrp );
		tabChildrenMembByGrp = new CFTab();
		tabChildrenMembByGrp.setText( LABEL_TabChildrenMembByGrpList );
		tabChildrenMembByGrp.setContent( getTabViewChildrenMembByGrpListPane() );
		getTabs().add( tabChildrenMembByGrp );
		tabChildrenSysGrpByName = new CFTab();
		tabChildrenSysGrpByName.setText( LABEL_TabChildrenSysGrpByNameList );
		tabChildrenSysGrpByName.setContent( getTabViewChildrenSysGrpByNameListPane() );
		getTabs().add( tabChildrenSysGrpByName );
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

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewChildrenIncByGrpListPane != null ) {
			((ICFSecJavaFXSecSysGrpIncPaneCommon)tabViewChildrenIncByGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenMembByGrpListPane != null ) {
			((ICFSecJavaFXSecSysGrpMembPaneCommon)tabViewChildrenMembByGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenSysGrpByNameListPane != null ) {
			((ICFSecJavaFXSecSysGrpIncPaneCommon)tabViewChildrenSysGrpByNameListPane).setPaneMode( value );
		}
	}
}
