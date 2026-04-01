// Description: Java 25 JavaFX Attribute Pane implementation for SecUser.

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import server.markhome.mcf.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecUserAttrPane JavaFX Attribute Pane implementation
 *	for SecUser.
 */
public class CFSecJavaFXSecUserAttrPane
extends CFGridPane
implements ICFSecJavaFXSecUserPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class SecUserIdCFLabel
		extends CFLabel
	{
		public SecUserIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.SecUserId.EffLabel"));
		}
	}

	protected class SecUserIdEditor
		extends CFDbKeyHash256Editor
	{
		public SecUserIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.SecUserId.EffLabel" );
		}
	}

	protected class LoginIdCFLabel
		extends CFLabel
	{
		public LoginIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.LoginId.EffLabel"));
		}
	}

	protected class LoginIdEditor
		extends CFStringEditor
	{
		public LoginIdEditor() {
			super();
			setMaxLen( 32 );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.LoginId.EffLabel" );
		}
	}

	protected class DfltSysGrpNameCFLabel
		extends CFLabel
	{
		public DfltSysGrpNameCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.DfltSysGrpName.EffLabel"));
		}
	}

	protected class DfltSysGrpNameEditor
		extends CFStringEditor
	{
		public DfltSysGrpNameEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.DfltSysGrpName.EffLabel" );
		}
	}

	protected class DfltClusGrpNameCFLabel
		extends CFLabel
	{
		public DfltClusGrpNameCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.DfltClusGrpName.EffLabel"));
		}
	}

	protected class DfltClusGrpNameEditor
		extends CFStringEditor
	{
		public DfltClusGrpNameEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.DfltClusGrpName.EffLabel" );
		}
	}

	protected class DfltTentGrpNameCFLabel
		extends CFLabel
	{
		public DfltTentGrpNameCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.DfltTentGrpName.EffLabel"));
		}
	}

	protected class DfltTentGrpNameEditor
		extends CFStringEditor
	{
		public DfltTentGrpNameEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.DfltTentGrpName.EffLabel" );
		}
	}

	protected class EMailAddressCFLabel
		extends CFLabel
	{
		public EMailAddressCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.EMailAddress.EffLabel"));
		}
	}

	protected class EMailAddressEditor
		extends CFStringEditor
	{
		public EMailAddressEditor() {
			super();
			setMaxLen( 512 );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.EMailAddress.EffLabel" );
		}
	}

	protected SecUserIdCFLabel javafxLabelSecUserId = null;
	protected SecUserIdEditor javafxEditorSecUserId = null;
	protected LoginIdCFLabel javafxLabelLoginId = null;
	protected LoginIdEditor javafxEditorLoginId = null;
	protected DfltSysGrpNameCFLabel javafxLabelDfltSysGrpName = null;
	protected DfltSysGrpNameEditor javafxEditorDfltSysGrpName = null;
	protected DfltClusGrpNameCFLabel javafxLabelDfltClusGrpName = null;
	protected DfltClusGrpNameEditor javafxEditorDfltClusGrpName = null;
	protected DfltTentGrpNameCFLabel javafxLabelDfltTentGrpName = null;
	protected DfltTentGrpNameEditor javafxEditorDfltTentGrpName = null;
	protected EMailAddressCFLabel javafxLabelEMailAddress = null;
	protected EMailAddressEditor javafxEditorEMailAddress = null;

	public CFSecJavaFXSecUserAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecUserObj argFocus ) {
		super();
		Control ctrl;
		CFLabel label;
		CFReferenceEditor reference;
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
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelSecUserId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSecUserId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLoginId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorLoginId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelDfltSysGrpName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDfltSysGrpName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelDfltClusGrpName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDfltClusGrpName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelDfltTentGrpName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDfltTentGrpName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelEMailAddress();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorEMailAddress();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		populateFields();
		adjustComponentEnableStates();
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
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecSecUserObj getJavaFXFocusAsSecUser() {
		return( (ICFSecSecUserObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecUser( ICFSecSecUserObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecUserObj getEffJavaFXFocus() {
		ICFSecSecUserObj eff = (ICFSecSecUserObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecSecUserObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public SecUserIdCFLabel getJavaFXLabelSecUserId() {
		if( javafxLabelSecUserId == null ) {
			javafxLabelSecUserId = new SecUserIdCFLabel();
		}
		return( javafxLabelSecUserId );
	}

	public void setJavaFXLabelSecUserId( SecUserIdCFLabel value ) {
		javafxLabelSecUserId = value;
	}

	public SecUserIdEditor getJavaFXEditorSecUserId() {
		if( javafxEditorSecUserId == null ) {
			javafxEditorSecUserId = new SecUserIdEditor();
		}
		return( javafxEditorSecUserId );
	}

	public void setJavaFXEditorSecUserId( SecUserIdEditor value ) {
		javafxEditorSecUserId = value;
	}

	public LoginIdCFLabel getJavaFXLabelLoginId() {
		if( javafxLabelLoginId == null ) {
			javafxLabelLoginId = new LoginIdCFLabel();
		}
		return( javafxLabelLoginId );
	}

	public void setJavaFXLabelLoginId( LoginIdCFLabel value ) {
		javafxLabelLoginId = value;
	}

	public LoginIdEditor getJavaFXEditorLoginId() {
		if( javafxEditorLoginId == null ) {
			javafxEditorLoginId = new LoginIdEditor();
		}
		return( javafxEditorLoginId );
	}

	public void setJavaFXEditorLoginId( LoginIdEditor value ) {
		javafxEditorLoginId = value;
	}

	public DfltSysGrpNameCFLabel getJavaFXLabelDfltSysGrpName() {
		if( javafxLabelDfltSysGrpName == null ) {
			javafxLabelDfltSysGrpName = new DfltSysGrpNameCFLabel();
		}
		return( javafxLabelDfltSysGrpName );
	}

	public void setJavaFXLabelDfltSysGrpName( DfltSysGrpNameCFLabel value ) {
		javafxLabelDfltSysGrpName = value;
	}

	public DfltSysGrpNameEditor getJavaFXEditorDfltSysGrpName() {
		if( javafxEditorDfltSysGrpName == null ) {
			javafxEditorDfltSysGrpName = new DfltSysGrpNameEditor();
		}
		return( javafxEditorDfltSysGrpName );
	}

	public void setJavaFXEditorDfltSysGrpName( DfltSysGrpNameEditor value ) {
		javafxEditorDfltSysGrpName = value;
	}

	public DfltClusGrpNameCFLabel getJavaFXLabelDfltClusGrpName() {
		if( javafxLabelDfltClusGrpName == null ) {
			javafxLabelDfltClusGrpName = new DfltClusGrpNameCFLabel();
		}
		return( javafxLabelDfltClusGrpName );
	}

	public void setJavaFXLabelDfltClusGrpName( DfltClusGrpNameCFLabel value ) {
		javafxLabelDfltClusGrpName = value;
	}

	public DfltClusGrpNameEditor getJavaFXEditorDfltClusGrpName() {
		if( javafxEditorDfltClusGrpName == null ) {
			javafxEditorDfltClusGrpName = new DfltClusGrpNameEditor();
		}
		return( javafxEditorDfltClusGrpName );
	}

	public void setJavaFXEditorDfltClusGrpName( DfltClusGrpNameEditor value ) {
		javafxEditorDfltClusGrpName = value;
	}

	public DfltTentGrpNameCFLabel getJavaFXLabelDfltTentGrpName() {
		if( javafxLabelDfltTentGrpName == null ) {
			javafxLabelDfltTentGrpName = new DfltTentGrpNameCFLabel();
		}
		return( javafxLabelDfltTentGrpName );
	}

	public void setJavaFXLabelDfltTentGrpName( DfltTentGrpNameCFLabel value ) {
		javafxLabelDfltTentGrpName = value;
	}

	public DfltTentGrpNameEditor getJavaFXEditorDfltTentGrpName() {
		if( javafxEditorDfltTentGrpName == null ) {
			javafxEditorDfltTentGrpName = new DfltTentGrpNameEditor();
		}
		return( javafxEditorDfltTentGrpName );
	}

	public void setJavaFXEditorDfltTentGrpName( DfltTentGrpNameEditor value ) {
		javafxEditorDfltTentGrpName = value;
	}

	public EMailAddressCFLabel getJavaFXLabelEMailAddress() {
		if( javafxLabelEMailAddress == null ) {
			javafxLabelEMailAddress = new EMailAddressCFLabel();
		}
		return( javafxLabelEMailAddress );
	}

	public void setJavaFXLabelEMailAddress( EMailAddressCFLabel value ) {
		javafxLabelEMailAddress = value;
	}

	public EMailAddressEditor getJavaFXEditorEMailAddress() {
		if( javafxEditorEMailAddress == null ) {
			javafxEditorEMailAddress = new EMailAddressEditor();
		}
		return( javafxEditorEMailAddress );
	}

	public void setJavaFXEditorEMailAddress( EMailAddressEditor value ) {
		javafxEditorEMailAddress = value;
	}

	public void populateFields()
	{
		ICFSecSecUserObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			getJavaFXEditorSecUserId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorSecUserId().setDbKeyHash256Value( popObj.getRequiredSecUserId() );
		}

		if( popObj == null ) {
			getJavaFXEditorLoginId().setStringValue( null );
		}
		else {
			getJavaFXEditorLoginId().setStringValue( popObj.getRequiredLoginId() );
		}

		if( popObj == null ) {
			getJavaFXEditorDfltSysGrpName().setStringValue( null );
		}
		else {
			getJavaFXEditorDfltSysGrpName().setStringValue( popObj.getOptionalDfltSysGrpName() );
		}

		if( popObj == null ) {
			getJavaFXEditorDfltClusGrpName().setStringValue( null );
		}
		else {
			getJavaFXEditorDfltClusGrpName().setStringValue( popObj.getOptionalDfltClusGrpName() );
		}

		if( popObj == null ) {
			getJavaFXEditorDfltTentGrpName().setStringValue( null );
		}
		else {
			getJavaFXEditorDfltTentGrpName().setStringValue( popObj.getOptionalDfltTentGrpName() );
		}

		if( popObj == null ) {
			getJavaFXEditorEMailAddress().setStringValue( null );
		}
		else {
			getJavaFXEditorEMailAddress().setStringValue( popObj.getRequiredEMailAddress() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecSecUserObj focus = getJavaFXFocusAsSecUser();
		ICFSecSecUserEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecSecUserEditObj)(focus.getEdit());
		}
		else {
			editObj = null;
		}
		if( editObj == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				Inz.s("cflibjavafx.common.PaneIsUnfocusedOrNotEditing"),
				Inz.x("cflibjavafx.common.PaneIsUnfocusedOrNotEditing") );
		}

		if( getJavaFXEditorLoginId().getStringValue() == null ) {
			editObj.setRequiredLoginId( "" );
		}
		else {
			editObj.setRequiredLoginId( getJavaFXEditorLoginId().getStringValue() );
		}

		if( ( getJavaFXEditorDfltSysGrpName().getStringValue() != null ) && ( getJavaFXEditorDfltSysGrpName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDfltSysGrpName( null );
		}
		else {
			editObj.setOptionalDfltSysGrpName( getJavaFXEditorDfltSysGrpName().getStringValue() );
		}

		if( ( getJavaFXEditorDfltClusGrpName().getStringValue() != null ) && ( getJavaFXEditorDfltClusGrpName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDfltClusGrpName( null );
		}
		else {
			editObj.setOptionalDfltClusGrpName( getJavaFXEditorDfltClusGrpName().getStringValue() );
		}

		if( ( getJavaFXEditorDfltTentGrpName().getStringValue() != null ) && ( getJavaFXEditorDfltTentGrpName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDfltTentGrpName( null );
		}
		else {
			editObj.setOptionalDfltTentGrpName( getJavaFXEditorDfltTentGrpName().getStringValue() );
		}

		if( getJavaFXEditorEMailAddress().getStringValue() == null ) {
			editObj.setRequiredEMailAddress( "" );
		}
		else {
			editObj.setRequiredEMailAddress( getJavaFXEditorEMailAddress().getStringValue() );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecSecUserObj focus = getJavaFXFocusAsSecUser();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecSecUserEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecSecUserEditObj)focus.getEdit();
		}
		else {
			editObj = null;
		}
		switch( value ) {
			case Unknown:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
							editObj = null;
						}
						break;
				}
				break;
			case Add:
				switch( oldValue ) {
					case Unknown:
					case Add:
					case View:
						if( editObj == null ) {
							if( focus != null ) {
								if( ! focus.getIsNew() ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										Inz.x("cflibjavafx.common.MustBeNew"),
										Inz.s("cflibjavafx.common.MustBeNew") );
								}
								editObj = (ICFSecSecUserEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
										Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
								}
							}
							else {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"focus" );
							}
						}
						break;
					case Edit:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							Inz.x("cflibjavafx.common.CannotTransitionEditToAdd"),
							Inz.s("cflibjavafx.common.CannotTransitionEditToAdd") );
					case Update:
						if( ( editObj == null ) || ( ! editObj.getIsNew() ) ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								Inz.x("cflibjavafx.common.CannotTransitionUpdateToAdd"),
								Inz.s("cflibjavafx.common.CannotTransitionUpdateToAdd") );
						}
						break;
					case Delete:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							Inz.x("cflibjavafx.common.CannotTransitionDeleteToAdd"),
							Inz.s("cflibjavafx.common.CannotTransitionDeleteToAdd") );
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							Inz.x("cflibjavafx.common.CannotTransitionDefaultToAdd"),
							Inz.s("cflibjavafx.common.CannotTransitionDefaultToAdd") );
				}
				break;
			case View:
				switch( oldValue ) {
					case Unknown:
						break;
					case View:
						break;
					case Edit:
						break;
					case Update:
						break;
					case Delete:
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToView"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToView"), oldValue) );
				}
				if( editObj != null ) {
					editObj.endEdit();
					editObj = null;
				}
				break;
			case Edit:
				switch( oldValue ) {
					case Unknown:
						if( editObj == null ) {
							editObj = (ICFSecSecUserEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFSecSecUserEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFSecSecUserEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					case Update:
						if( editObj == null ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue),
								String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue) );
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue) );
				}
				break;
			case Update:
				if( ( oldValue != CFPane.PaneMode.Edit ) && ( oldValue != CFPane.PaneMode.Add ) ) {
					throw new CFLibUsageException( getClass(),
						S_ProcName,
						String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToUpdate"), oldValue),
						String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToUpdate"), oldValue) );
				}
				super.setPaneMode( value );
				if( editObj != null ) {
					postFields();
					if( editObj.getIsNew() ) {
						focus = (ICFSecSecUserObj)editObj.create();
						setJavaFXFocus( focus );
					}
					else {
						editObj.update();
					}
					editObj = null;
				}
				setPaneMode( CFPane.PaneMode.View );
				break;
			case Delete:
				switch( oldValue ) {
					case View:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFSecSecUserEditObj)focus.beginEdit();
								if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
								}
							}
						}
						break;
					case Edit:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFSecSecUserEditObj)focus.beginEdit();
								if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
								}
							}
						}
						break;
					case Update:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue) );
					case Delete:
						if( editObj == null ) {
							editObj = (ICFSecSecUserEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue) );
				}
				editObj.deleteInstance();
				editObj = null;
				setJavaFXFocus( null );
				setPaneMode( CFPane.PaneMode.Unknown );
				break;
			default:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
							editObj = null;
						}
						break;
				}
				break;
		}
		super.setPaneMode( value );
		populateFields();
		adjustComponentEnableStates();
	}

	public void adjustComponentEnableStates() {
		CFPane.PaneMode mode = getPaneMode();
		boolean isEditing;
		switch( mode ) {
			case Unknown:
			case View:
			case Delete:
				isEditing = false;
				break;
			case Add:
			case Edit:
			case Update:
				isEditing = true;
				break;
			default:
				isEditing = false;
				break;
		}
		if( isEditing ) {
			ICFSecSecUserObj focus = getJavaFXFocusAsSecUser();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxEditorSecUserId != null ) {
			javafxEditorSecUserId.setDisable( true );
		}
		if( javafxEditorLoginId != null ) {
			javafxEditorLoginId.setDisable( ! isEditing );
		}
		if( javafxEditorDfltSysGrpName != null ) {
			javafxEditorDfltSysGrpName.setDisable( ! isEditing );
		}
		if( javafxEditorDfltClusGrpName != null ) {
			javafxEditorDfltClusGrpName.setDisable( ! isEditing );
		}
		if( javafxEditorDfltTentGrpName != null ) {
			javafxEditorDfltTentGrpName.setDisable( ! isEditing );
		}
		if( javafxEditorEMailAddress != null ) {
			javafxEditorEMailAddress.setDisable( ! isEditing );
		}
	}
}
