// Description: Java 25 JavaFX Attribute Pane implementation for SecUserPWHistory.

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
 *	CFSecJavaFXSecUserPWHistoryAttrPane JavaFX Attribute Pane implementation
 *	for SecUserPWHistory.
 */
public class CFSecJavaFXSecUserPWHistoryAttrPane
extends CFGridPane
implements ICFSecJavaFXSecUserPWHistoryPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class SecUserIdCFLabel
		extends CFLabel
	{
		public SecUserIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWHistory.AttrPane.SecUserId.EffLabel"));
		}
	}

	protected class SecUserIdEditor
		extends CFDbKeyHash256Editor
	{
		public SecUserIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUserPWHistory.AttrPane.SecUserId.EffLabel" );
		}
	}

	protected class PWSetStampCFLabel
		extends CFLabel
	{
		public PWSetStampCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWHistory.AttrPane.PWSetStamp.EffLabel"));
		}
	}

	protected class PWSetStampEditor
		extends CFTimestampEditor
	{
		public PWSetStampEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUserPWHistory.AttrPane.PWSetStamp.EffLabel" );
		}
	}

	protected class PWReplacedStampCFLabel
		extends CFLabel
	{
		public PWReplacedStampCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWHistory.AttrPane.PWReplacedStamp.EffLabel"));
		}
	}

	protected class PWReplacedStampEditor
		extends CFTimestampEditor
	{
		public PWReplacedStampEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUserPWHistory.AttrPane.PWReplacedStamp.EffLabel" );
		}
	}

	protected class PasswordHashCFLabel
		extends CFLabel
	{
		public PasswordHashCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWHistory.AttrPane.PasswordHash.EffLabel"));
		}
	}

	protected class PasswordHashEditor
		extends CFStringEditor
	{
		public PasswordHashEditor() {
			super();
			setMaxLen( 256 );
			setFieldNameInzTag( "cfsec.javafx.SecUserPWHistory.AttrPane.PasswordHash.EffLabel" );
		}
	}

	protected SecUserIdCFLabel javafxLabelSecUserId = null;
	protected SecUserIdEditor javafxEditorSecUserId = null;
	protected PWSetStampCFLabel javafxLabelPWSetStamp = null;
	protected PWSetStampEditor javafxEditorPWSetStamp = null;
	protected PWReplacedStampCFLabel javafxLabelPWReplacedStamp = null;
	protected PWReplacedStampEditor javafxEditorPWReplacedStamp = null;
	protected PasswordHashCFLabel javafxLabelPasswordHash = null;
	protected PasswordHashEditor javafxEditorPasswordHash = null;

	public CFSecJavaFXSecUserPWHistoryAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecUserPWHistoryObj argFocus ) {
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
		setJavaFXFocusAsSecUserPWHistory( argFocus );
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

		label = getJavaFXLabelPWSetStamp();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorPWSetStamp();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelPWReplacedStamp();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorPWReplacedStamp();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelPasswordHash();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorPasswordHash();
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
		if( ( value == null ) || ( value instanceof ICFSecSecUserPWHistoryObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecUserPWHistoryObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecSecUserPWHistoryObj getJavaFXFocusAsSecUserPWHistory() {
		return( (ICFSecSecUserPWHistoryObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecUserPWHistory( ICFSecSecUserPWHistoryObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecUserPWHistoryObj getEffJavaFXFocus() {
		ICFSecSecUserPWHistoryObj eff = (ICFSecSecUserPWHistoryObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecSecUserPWHistoryObj)eff.getEdit();
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

	public PWSetStampCFLabel getJavaFXLabelPWSetStamp() {
		if( javafxLabelPWSetStamp == null ) {
			javafxLabelPWSetStamp = new PWSetStampCFLabel();
		}
		return( javafxLabelPWSetStamp );
	}

	public void setJavaFXLabelPWSetStamp( PWSetStampCFLabel value ) {
		javafxLabelPWSetStamp = value;
	}

	public PWSetStampEditor getJavaFXEditorPWSetStamp() {
		if( javafxEditorPWSetStamp == null ) {
			javafxEditorPWSetStamp = new PWSetStampEditor();
		}
		return( javafxEditorPWSetStamp );
	}

	public void setJavaFXEditorPWSetStamp( PWSetStampEditor value ) {
		javafxEditorPWSetStamp = value;
	}

	public PWReplacedStampCFLabel getJavaFXLabelPWReplacedStamp() {
		if( javafxLabelPWReplacedStamp == null ) {
			javafxLabelPWReplacedStamp = new PWReplacedStampCFLabel();
		}
		return( javafxLabelPWReplacedStamp );
	}

	public void setJavaFXLabelPWReplacedStamp( PWReplacedStampCFLabel value ) {
		javafxLabelPWReplacedStamp = value;
	}

	public PWReplacedStampEditor getJavaFXEditorPWReplacedStamp() {
		if( javafxEditorPWReplacedStamp == null ) {
			javafxEditorPWReplacedStamp = new PWReplacedStampEditor();
		}
		return( javafxEditorPWReplacedStamp );
	}

	public void setJavaFXEditorPWReplacedStamp( PWReplacedStampEditor value ) {
		javafxEditorPWReplacedStamp = value;
	}

	public PasswordHashCFLabel getJavaFXLabelPasswordHash() {
		if( javafxLabelPasswordHash == null ) {
			javafxLabelPasswordHash = new PasswordHashCFLabel();
		}
		return( javafxLabelPasswordHash );
	}

	public void setJavaFXLabelPasswordHash( PasswordHashCFLabel value ) {
		javafxLabelPasswordHash = value;
	}

	public PasswordHashEditor getJavaFXEditorPasswordHash() {
		if( javafxEditorPasswordHash == null ) {
			javafxEditorPasswordHash = new PasswordHashEditor();
		}
		return( javafxEditorPasswordHash );
	}

	public void setJavaFXEditorPasswordHash( PasswordHashEditor value ) {
		javafxEditorPasswordHash = value;
	}

	public void populateFields()
	{
		ICFSecSecUserPWHistoryObj popObj = getEffJavaFXFocus();
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
			getJavaFXEditorPWSetStamp().setTimestampValue( null );
		}
		else {
			getJavaFXEditorPWSetStamp().setTimestampValue( popObj.getRequiredPWSetStamp() );
		}

		if( popObj == null ) {
			getJavaFXEditorPWReplacedStamp().setTimestampValue( null );
		}
		else {
			getJavaFXEditorPWReplacedStamp().setTimestampValue( popObj.getRequiredPWReplacedStamp() );
		}

		if( popObj == null ) {
			getJavaFXEditorPasswordHash().setStringValue( null );
		}
		else {
			getJavaFXEditorPasswordHash().setStringValue( popObj.getRequiredPasswordHash() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecSecUserPWHistoryObj focus = getJavaFXFocusAsSecUserPWHistory();
		ICFSecSecUserPWHistoryEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecSecUserPWHistoryEditObj)(focus.getEdit());
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

		editObj.setRequiredPWReplacedStamp( getJavaFXEditorPWReplacedStamp().getTimestampValue() );

		if( getJavaFXEditorPasswordHash().getStringValue() == null ) {
			editObj.setRequiredPasswordHash( "" );
		}
		else {
			editObj.setRequiredPasswordHash( getJavaFXEditorPasswordHash().getStringValue() );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecSecUserPWHistoryObj focus = getJavaFXFocusAsSecUserPWHistory();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecSecUserPWHistoryEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecSecUserPWHistoryEditObj)focus.getEdit();
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
								editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
						focus = (ICFSecSecUserPWHistoryObj)editObj.create();
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
								editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
								editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWHistoryEditObj)focus.beginEdit();
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
			ICFSecSecUserPWHistoryObj focus = getJavaFXFocusAsSecUserPWHistory();
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
		if( javafxEditorPWSetStamp != null ) {
			javafxEditorPWSetStamp.setDisable( true );
		}
		if( javafxEditorPWReplacedStamp != null ) {
			javafxEditorPWReplacedStamp.setDisable( ! isEditing );
		}
		if( javafxEditorPasswordHash != null ) {
			javafxEditorPasswordHash.setDisable( ! isEditing );
		}
	}
}
