// Description: Java 25 JavaFX Element TabPane implementation for ISOCtry.

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
 *	CFSecJavaFXISOCtryEltTabPane JavaFX Element TabPane implementation
 *	for ISOCtry.
 */
public class CFSecJavaFXISOCtryEltTabPane
extends CFTabPane
implements ICFSecJavaFXISOCtryPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsCcyList = "Optional Components Ccy";
	protected CFTab tabComponentsCcy = null;
	public final String LABEL_TabComponentsLangList = "Optional Components Lang";
	protected CFTab tabComponentsLang = null;
	protected CFBorderPane tabViewComponentsCcyListPane = null;
	protected CFBorderPane tabViewComponentsLangListPane = null;

	public CFSecJavaFXISOCtryEltTabPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecISOCtryObj argFocus ) {
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
		setJavaFXFocusAsISOCtry( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsCcy = new CFTab();
		tabComponentsCcy.setText( LABEL_TabComponentsCcyList );
		tabComponentsCcy.setContent( getTabViewComponentsCcyListPane() );
		getTabs().add( tabComponentsCcy );
		tabComponentsLang = new CFTab();
		tabComponentsLang.setText( LABEL_TabComponentsLangList );
		tabComponentsLang.setContent( getTabViewComponentsLangListPane() );
		getTabs().add( tabComponentsLang );
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
		if( ( value == null ) || ( value instanceof ICFSecISOCtryObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecISOCtryObj" );
		}
	}

	public void setJavaFXFocusAsISOCtry( ICFSecISOCtryObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecISOCtryObj getJavaFXFocusAsISOCtry() {
		return( (ICFSecISOCtryObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsCcyList
	implements ICFRefreshCallback
	{
		public RefreshComponentsCcyList() {
		}

		public void refreshMe() {
			Collection<ICFSecISOCtryCcyObj> dataCollection;
			ICFSecISOCtryObj focus = (ICFSecISOCtryObj)getJavaFXFocusAsISOCtry();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsCcy( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsCcyListPane();
			ICFSecJavaFXISOCtryCcyPaneList jpList = (ICFSecJavaFXISOCtryCcyPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsCcyListPane() {
		if( tabViewComponentsCcyListPane == null ) {
			ICFSecISOCtryObj focus = (ICFSecISOCtryObj)getJavaFXFocusAsISOCtry();
			Collection<ICFSecISOCtryCcyObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsCcy( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFSecISOCtryObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecISOCtryObj ) ) {
				javafxContainer = (ICFSecISOCtryObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsCcyListPane = javafxSchema.getISOCtryCcyFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsCcyList(), false );
		}
		return( tabViewComponentsCcyListPane );
	}

	protected class RefreshComponentsLangList
	implements ICFRefreshCallback
	{
		public RefreshComponentsLangList() {
		}

		public void refreshMe() {
			Collection<ICFSecISOCtryLangObj> dataCollection;
			ICFSecISOCtryObj focus = (ICFSecISOCtryObj)getJavaFXFocusAsISOCtry();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsLang( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsLangListPane();
			ICFSecJavaFXISOCtryLangPaneList jpList = (ICFSecJavaFXISOCtryLangPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsLangListPane() {
		if( tabViewComponentsLangListPane == null ) {
			ICFSecISOCtryObj focus = (ICFSecISOCtryObj)getJavaFXFocusAsISOCtry();
			Collection<ICFSecISOCtryLangObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsLang( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFSecISOCtryObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFSecISOCtryObj ) ) {
				javafxContainer = (ICFSecISOCtryObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsLangListPane = javafxSchema.getISOCtryLangFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsLangList(), false );
		}
		return( tabViewComponentsLangListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsCcyListPane != null ) {
			((ICFSecJavaFXISOCtryCcyPaneCommon)tabViewComponentsCcyListPane).setPaneMode( value );
		}
		if( tabViewComponentsLangListPane != null ) {
			((ICFSecJavaFXISOCtryLangPaneCommon)tabViewComponentsLangListPane).setPaneMode( value );
		}
	}
}
