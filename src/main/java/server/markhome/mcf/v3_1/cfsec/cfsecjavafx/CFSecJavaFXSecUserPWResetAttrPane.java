// Description: Java 25 JavaFX Attribute Pane implementation for SecUserPWReset.

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
 *	CFSecJavaFXSecUserPWResetAttrPane JavaFX Attribute Pane implementation
 *	for SecUserPWReset.
 */
public class CFSecJavaFXSecUserPWResetAttrPane
extends CFGridPane
implements ICFSecJavaFXSecUserPWResetPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class SentToEMailAddrCFLabel
		extends CFLabel
	{
		public SentToEMailAddrCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWReset.AttrPane.SentToEMailAddr.EffLabel"));
		}
	}

	protected class SentToEMailAddrEditor
		extends CFStringEditor
	{
		public SentToEMailAddrEditor() {
			super();
			setMaxLen( 512 );
			setFieldNameInzTag( "cfsec.javafx.SecUserPWReset.AttrPane.SentToEMailAddr.EffLabel" );
		}
	}

	protected class PasswordResetUuid6CFLabel
		extends CFLabel
	{
		public PasswordResetUuid6CFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWReset.AttrPane.PasswordResetUuid6.EffLabel"));
		}
	}

	protected class PasswordResetUuid6Editor
		extends CFUuid6Editor
	{
		public PasswordResetUuid6Editor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUserPWReset.AttrPane.PasswordResetUuid6.EffLabel" );
		}
	}

	protected class NewAccountCFLabel
		extends CFLabel
	{
		public NewAccountCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserPWReset.AttrPane.NewAccount.EffLabel"));
		}
	}

	protected class NewAccountEditor
		extends CFBoolEditor
	{
		public NewAccountEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfsec.javafx.SecUserPWReset.AttrPane.NewAccount.EffLabel" );
		}
	}

	protected SentToEMailAddrCFLabel javafxLabelSentToEMailAddr = null;
	protected SentToEMailAddrEditor javafxEditorSentToEMailAddr = null;
	protected PasswordResetUuid6CFLabel javafxLabelPasswordResetUuid6 = null;
	protected PasswordResetUuid6Editor javafxEditorPasswordResetUuid6 = null;
	protected NewAccountCFLabel javafxLabelNewAccount = null;
	protected NewAccountEditor javafxEditorNewAccount = null;

	public CFSecJavaFXSecUserPWResetAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecUserPWResetObj argFocus ) {
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
		setJavaFXFocusAsSecUserPWReset( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelSentToEMailAddr();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSentToEMailAddr();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelPasswordResetUuid6();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorPasswordResetUuid6();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelNewAccount();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorNewAccount();
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
		if( ( value == null ) || ( value instanceof ICFSecSecUserPWResetObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecUserPWResetObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecSecUserPWResetObj getJavaFXFocusAsSecUserPWReset() {
		return( (ICFSecSecUserPWResetObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecUserPWReset( ICFSecSecUserPWResetObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecUserPWResetObj getEffJavaFXFocus() {
		ICFSecSecUserPWResetObj eff = (ICFSecSecUserPWResetObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecSecUserPWResetObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public SentToEMailAddrCFLabel getJavaFXLabelSentToEMailAddr() {
		if( javafxLabelSentToEMailAddr == null ) {
			javafxLabelSentToEMailAddr = new SentToEMailAddrCFLabel();
		}
		return( javafxLabelSentToEMailAddr );
	}

	public void setJavaFXLabelSentToEMailAddr( SentToEMailAddrCFLabel value ) {
		javafxLabelSentToEMailAddr = value;
	}

	public SentToEMailAddrEditor getJavaFXEditorSentToEMailAddr() {
		if( javafxEditorSentToEMailAddr == null ) {
			javafxEditorSentToEMailAddr = new SentToEMailAddrEditor();
		}
		return( javafxEditorSentToEMailAddr );
	}

	public void setJavaFXEditorSentToEMailAddr( SentToEMailAddrEditor value ) {
		javafxEditorSentToEMailAddr = value;
	}

	public PasswordResetUuid6CFLabel getJavaFXLabelPasswordResetUuid6() {
		if( javafxLabelPasswordResetUuid6 == null ) {
			javafxLabelPasswordResetUuid6 = new PasswordResetUuid6CFLabel();
		}
		return( javafxLabelPasswordResetUuid6 );
	}

	public void setJavaFXLabelPasswordResetUuid6( PasswordResetUuid6CFLabel value ) {
		javafxLabelPasswordResetUuid6 = value;
	}

	public PasswordResetUuid6Editor getJavaFXEditorPasswordResetUuid6() {
		if( javafxEditorPasswordResetUuid6 == null ) {
			javafxEditorPasswordResetUuid6 = new PasswordResetUuid6Editor();
		}
		return( javafxEditorPasswordResetUuid6 );
	}

	public void setJavaFXEditorPasswordResetUuid6( PasswordResetUuid6Editor value ) {
		javafxEditorPasswordResetUuid6 = value;
	}

	public NewAccountCFLabel getJavaFXLabelNewAccount() {
		if( javafxLabelNewAccount == null ) {
			javafxLabelNewAccount = new NewAccountCFLabel();
		}
		return( javafxLabelNewAccount );
	}

	public void setJavaFXLabelNewAccount( NewAccountCFLabel value ) {
		javafxLabelNewAccount = value;
	}

	public NewAccountEditor getJavaFXEditorNewAccount() {
		if( javafxEditorNewAccount == null ) {
			javafxEditorNewAccount = new NewAccountEditor();
		}
		return( javafxEditorNewAccount );
	}

	public void setJavaFXEditorNewAccount( NewAccountEditor value ) {
		javafxEditorNewAccount = value;
	}

	public void populateFields()
	{
		ICFSecSecUserPWResetObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			getJavaFXEditorSentToEMailAddr().setStringValue( null );
		}
		else {
			getJavaFXEditorSentToEMailAddr().setStringValue( popObj.getRequiredSentToEMailAddr() );
		}

		if( popObj == null ) {
			getJavaFXEditorPasswordResetUuid6().setUuid6Value( null );
		}
		else {
			getJavaFXEditorPasswordResetUuid6().setUuid6Value( popObj.getRequiredPasswordResetUuid6() );
		}

		if( popObj == null ) {
			getJavaFXEditorNewAccount().setBooleanValue( null );
		}
		else {
			getJavaFXEditorNewAccount().setBooleanValue( popObj.getRequiredNewAccount() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecSecUserPWResetObj focus = getJavaFXFocusAsSecUserPWReset();
		ICFSecSecUserPWResetEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecSecUserPWResetEditObj)(focus.getEdit());
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

		if( getJavaFXEditorSentToEMailAddr().getStringValue() == null ) {
			editObj.setRequiredSentToEMailAddr( "" );
		}
		else {
			editObj.setRequiredSentToEMailAddr( getJavaFXEditorSentToEMailAddr().getStringValue() );
		}

		editObj.setRequiredPasswordResetUuid6( getJavaFXEditorPasswordResetUuid6().getUuid6Value() );

		editObj.setRequiredNewAccount( getJavaFXEditorNewAccount().getBooleanValue() );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecSecUserPWResetObj focus = getJavaFXFocusAsSecUserPWReset();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecSecUserPWResetEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecSecUserPWResetEditObj)focus.getEdit();
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
								editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
						focus = (ICFSecSecUserPWResetObj)editObj.create();
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
								editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
								editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserPWResetEditObj)focus.beginEdit();
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
			ICFSecSecUserPWResetObj focus = getJavaFXFocusAsSecUserPWReset();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxEditorSentToEMailAddr != null ) {
			javafxEditorSentToEMailAddr.setDisable( ! isEditing );
		}
		if( javafxEditorPasswordResetUuid6 != null ) {
			javafxEditorPasswordResetUuid6.setDisable( ! isEditing );
		}
		if( javafxEditorNewAccount != null ) {
			javafxEditorNewAccount.setDisable( ! isEditing );
		}
	}
}
