// Description: Java 25 JavaFX Element TabPane implementation for SecClusGrp.

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
 *	CFSecJavaFXSecClusGrpEltTabPane JavaFX Element TabPane implementation
 *	for SecClusGrp.
 */
public class CFSecJavaFXSecClusGrpEltTabPane
extends CFTabPane
implements ICFSecJavaFXSecClusGrpPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabChildrenIncByGrpList = "Optional Children Included by Group";
	protected CFTab tabChildrenIncByGrp = null;
	public final String LABEL_TabChildrenMembByGrpList = "Optional Children Members of Group";
	protected CFTab tabChildrenMembByGrp = null;
	protected CFBorderPane tabViewChildrenIncByGrpListPane = null;
	protected CFBorderPane tabViewChildrenMembByGrpListPane = null;

	public CFSecJavaFXSecClusGrpEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecClusGrpObj argFocus ) {
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
		setJavaFXFocusAsSecClusGrp( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabChildrenIncByGrp = new CFTab();
		tabChildrenIncByGrp.setText( LABEL_TabChildrenIncByGrpList );
		tabChildrenIncByGrp.setContent( getTabViewChildrenIncByGrpListPane() );
		getTabs().add( tabChildrenIncByGrp );
		tabChildrenMembByGrp = new CFTab();
		tabChildrenMembByGrp.setText( LABEL_TabChildrenMembByGrpList );
		tabChildrenMembByGrp.setContent( getTabViewChildrenMembByGrpListPane() );
		getTabs().add( tabChildrenMembByGrp );
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
		if( ( value == null ) || ( value instanceof ICFSecSecClusGrpObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecClusGrpObj" );
		}
	}

	public void setJavaFXFocusAsSecClusGrp( ICFSecSecClusGrpObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecClusGrpObj getJavaFXFocusAsSecClusGrp() {
		return( (ICFSecSecClusGrpObj)getJavaFXFocus() );
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
	implements ICFSecJavaFXSecClusGrpIncPageCallback
	{
		public PageDataChildrenIncByGrpList() {
		}

		public List<ICFSecSecClusGrpIncObj> pageData( CFLibDbKeyHash256 priorSecClusGrpId,
		String priorInclName )
		{
			List<ICFSecSecClusGrpIncObj> dataList;
			ICFSecSecClusGrpObj focus = (ICFSecSecClusGrpObj)getJavaFXFocusAsSecClusGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecClusGrpIncTableObj().pageSecClusGrpIncByClusGrpIdx( focus.getRequiredSecClusGrpId(),
					priorSecClusGrpId,
					priorInclName );
			}
			else {
				dataList = new ArrayList<ICFSecSecClusGrpIncObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenIncByGrpListPane() {
		if( tabViewChildrenIncByGrpListPane == null ) {
			ICFSecSecClusGrpObj focus = (ICFSecSecClusGrpObj)getJavaFXFocusAsSecClusGrp();
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewChildrenIncByGrpListPane = javafxSchema.getSecClusGrpIncFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenIncByGrpList(), new RefreshChildrenIncByGrpList(), false );
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
	implements ICFSecJavaFXSecClusGrpMembPageCallback
	{
		public PageDataChildrenMembByGrpList() {
		}

		public List<ICFSecSecClusGrpMembObj> pageData( CFLibDbKeyHash256 priorSecClusGrpId,
		String priorLoginId )
		{
			List<ICFSecSecClusGrpMembObj> dataList;
			ICFSecSecClusGrpObj focus = (ICFSecSecClusGrpObj)getJavaFXFocusAsSecClusGrp();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
				dataList = schemaObj.getSecClusGrpMembTableObj().pageSecClusGrpMembByClusGrpIdx( focus.getRequiredSecClusGrpId(),
					priorSecClusGrpId,
					priorLoginId );
			}
			else {
				dataList = new ArrayList<ICFSecSecClusGrpMembObj>();
			}
			return( dataList );
		}
	}

	public CFBorderPane getTabViewChildrenMembByGrpListPane() {
		if( tabViewChildrenMembByGrpListPane == null ) {
			ICFSecSecClusGrpObj focus = (ICFSecSecClusGrpObj)getJavaFXFocusAsSecClusGrp();
			ICFLibAnyObj javafxContainer;
			javafxContainer = null;
			tabViewChildrenMembByGrpListPane = javafxSchema.getSecClusGrpMembFactory().newListPane( cfFormManager, javafxContainer, null, new PageDataChildrenMembByGrpList(), new RefreshChildrenMembByGrpList(), false );
		}
		return( tabViewChildrenMembByGrpListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewChildrenIncByGrpListPane != null ) {
			((ICFSecJavaFXSecClusGrpIncPaneCommon)tabViewChildrenIncByGrpListPane).setPaneMode( value );
		}
		if( tabViewChildrenMembByGrpListPane != null ) {
			((ICFSecJavaFXSecClusGrpMembPaneCommon)tabViewChildrenMembByGrpListPane).setPaneMode( value );
		}
	}
}
