// Description: Java 25 JavaFX Attribute Pane implementation for SecUserEMConf.

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
 *	CFSecJavaFXSecUserEMConfAttrPane JavaFX Attribute Pane implementation
 *	for SecUserEMConf.
 */
public class CFSecJavaFXSecUserEMConfAttrPane
extends CFGridPane
implements ICFSecJavaFXSecUserEMConfPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class ConfirmEMailAddrCFLabel
		extends CFLabel
	{
		public ConfirmEMailAddrCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserEMConf.AttrPane.ConfirmEMailAddr.EffLabel"));
		}
	}

	protected class ConfirmEMailAddrEditor
		extends CFStringEditor
	{
		public ConfirmEMailAddrEditor() {
			super();
			setMaxLen( 512 );
			setFieldNameInzTag( "cfsec.javafx.SecUserEMConf.AttrPane.ConfirmEMailAddr.EffLabel" );
		}
	}

	protected class EMailSentStampCFLabel
		extends CFLabel
	{
		public EMailSentStampCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserEMConf.AttrPane.EMailSentStamp.EffLabel"));
		}
	}

	protected class EMailSentStampEditor
		extends CFTimestampEditor
	{
		public EMailSentStampEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUserEMConf.AttrPane.EMailSentStamp.EffLabel" );
		}
	}

	protected class EMConfirmationUuid6CFLabel
		extends CFLabel
	{
		public EMConfirmationUuid6CFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserEMConf.AttrPane.EMConfirmationUuid6.EffLabel"));
		}
	}

	protected class EMConfirmationUuid6Editor
		extends CFUuid6Editor
	{
		public EMConfirmationUuid6Editor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUserEMConf.AttrPane.EMConfirmationUuid6.EffLabel" );
		}
	}

	protected class NewAccountCFLabel
		extends CFLabel
	{
		public NewAccountCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUserEMConf.AttrPane.NewAccount.EffLabel"));
		}
	}

	protected class NewAccountEditor
		extends CFBoolEditor
	{
		public NewAccountEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfsec.javafx.SecUserEMConf.AttrPane.NewAccount.EffLabel" );
		}
	}

	protected ConfirmEMailAddrCFLabel javafxLabelConfirmEMailAddr = null;
	protected ConfirmEMailAddrEditor javafxEditorConfirmEMailAddr = null;
	protected EMailSentStampCFLabel javafxLabelEMailSentStamp = null;
	protected EMailSentStampEditor javafxEditorEMailSentStamp = null;
	protected EMConfirmationUuid6CFLabel javafxLabelEMConfirmationUuid6 = null;
	protected EMConfirmationUuid6Editor javafxEditorEMConfirmationUuid6 = null;
	protected NewAccountCFLabel javafxLabelNewAccount = null;
	protected NewAccountEditor javafxEditorNewAccount = null;

	public CFSecJavaFXSecUserEMConfAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecUserEMConfObj argFocus ) {
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
		setJavaFXFocusAsSecUserEMConf( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelConfirmEMailAddr();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorConfirmEMailAddr();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelEMailSentStamp();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorEMailSentStamp();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelEMConfirmationUuid6();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorEMConfirmationUuid6();
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
		if( ( value == null ) || ( value instanceof ICFSecSecUserEMConfObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecUserEMConfObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecSecUserEMConfObj getJavaFXFocusAsSecUserEMConf() {
		return( (ICFSecSecUserEMConfObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecUserEMConf( ICFSecSecUserEMConfObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecUserEMConfObj getEffJavaFXFocus() {
		ICFSecSecUserEMConfObj eff = (ICFSecSecUserEMConfObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecSecUserEMConfObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public ConfirmEMailAddrCFLabel getJavaFXLabelConfirmEMailAddr() {
		if( javafxLabelConfirmEMailAddr == null ) {
			javafxLabelConfirmEMailAddr = new ConfirmEMailAddrCFLabel();
		}
		return( javafxLabelConfirmEMailAddr );
	}

	public void setJavaFXLabelConfirmEMailAddr( ConfirmEMailAddrCFLabel value ) {
		javafxLabelConfirmEMailAddr = value;
	}

	public ConfirmEMailAddrEditor getJavaFXEditorConfirmEMailAddr() {
		if( javafxEditorConfirmEMailAddr == null ) {
			javafxEditorConfirmEMailAddr = new ConfirmEMailAddrEditor();
		}
		return( javafxEditorConfirmEMailAddr );
	}

	public void setJavaFXEditorConfirmEMailAddr( ConfirmEMailAddrEditor value ) {
		javafxEditorConfirmEMailAddr = value;
	}

	public EMailSentStampCFLabel getJavaFXLabelEMailSentStamp() {
		if( javafxLabelEMailSentStamp == null ) {
			javafxLabelEMailSentStamp = new EMailSentStampCFLabel();
		}
		return( javafxLabelEMailSentStamp );
	}

	public void setJavaFXLabelEMailSentStamp( EMailSentStampCFLabel value ) {
		javafxLabelEMailSentStamp = value;
	}

	public EMailSentStampEditor getJavaFXEditorEMailSentStamp() {
		if( javafxEditorEMailSentStamp == null ) {
			javafxEditorEMailSentStamp = new EMailSentStampEditor();
		}
		return( javafxEditorEMailSentStamp );
	}

	public void setJavaFXEditorEMailSentStamp( EMailSentStampEditor value ) {
		javafxEditorEMailSentStamp = value;
	}

	public EMConfirmationUuid6CFLabel getJavaFXLabelEMConfirmationUuid6() {
		if( javafxLabelEMConfirmationUuid6 == null ) {
			javafxLabelEMConfirmationUuid6 = new EMConfirmationUuid6CFLabel();
		}
		return( javafxLabelEMConfirmationUuid6 );
	}

	public void setJavaFXLabelEMConfirmationUuid6( EMConfirmationUuid6CFLabel value ) {
		javafxLabelEMConfirmationUuid6 = value;
	}

	public EMConfirmationUuid6Editor getJavaFXEditorEMConfirmationUuid6() {
		if( javafxEditorEMConfirmationUuid6 == null ) {
			javafxEditorEMConfirmationUuid6 = new EMConfirmationUuid6Editor();
		}
		return( javafxEditorEMConfirmationUuid6 );
	}

	public void setJavaFXEditorEMConfirmationUuid6( EMConfirmationUuid6Editor value ) {
		javafxEditorEMConfirmationUuid6 = value;
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
		ICFSecSecUserEMConfObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			getJavaFXEditorConfirmEMailAddr().setStringValue( null );
		}
		else {
			getJavaFXEditorConfirmEMailAddr().setStringValue( popObj.getRequiredConfirmEMailAddr() );
		}

		if( popObj == null ) {
			getJavaFXEditorEMailSentStamp().setTimestampValue( null );
		}
		else {
			getJavaFXEditorEMailSentStamp().setTimestampValue( popObj.getRequiredEMailSentStamp() );
		}

		if( popObj == null ) {
			getJavaFXEditorEMConfirmationUuid6().setUuid6Value( null );
		}
		else {
			getJavaFXEditorEMConfirmationUuid6().setUuid6Value( popObj.getRequiredEMConfirmationUuid6() );
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
		ICFSecSecUserEMConfObj focus = getJavaFXFocusAsSecUserEMConf();
		ICFSecSecUserEMConfEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecSecUserEMConfEditObj)(focus.getEdit());
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

		if( getJavaFXEditorConfirmEMailAddr().getStringValue() == null ) {
			editObj.setRequiredConfirmEMailAddr( "" );
		}
		else {
			editObj.setRequiredConfirmEMailAddr( getJavaFXEditorConfirmEMailAddr().getStringValue() );
		}

		editObj.setRequiredEMailSentStamp( getJavaFXEditorEMailSentStamp().getTimestampValue() );

		editObj.setRequiredEMConfirmationUuid6( getJavaFXEditorEMConfirmationUuid6().getUuid6Value() );

		editObj.setRequiredNewAccount( getJavaFXEditorNewAccount().getBooleanValue() );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecSecUserEMConfObj focus = getJavaFXFocusAsSecUserEMConf();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecSecUserEMConfEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecSecUserEMConfEditObj)focus.getEdit();
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
								editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
						focus = (ICFSecSecUserEMConfObj)editObj.create();
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
								editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
								editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecUserEMConfEditObj)focus.beginEdit();
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
			ICFSecSecUserEMConfObj focus = getJavaFXFocusAsSecUserEMConf();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxEditorConfirmEMailAddr != null ) {
			javafxEditorConfirmEMailAddr.setDisable( ! isEditing );
		}
		if( javafxEditorEMailSentStamp != null ) {
			javafxEditorEMailSentStamp.setDisable( ! isEditing );
		}
		if( javafxEditorEMConfirmationUuid6 != null ) {
			javafxEditorEMConfirmationUuid6.setDisable( ! isEditing );
		}
		if( javafxEditorNewAccount != null ) {
			javafxEditorNewAccount.setDisable( ! isEditing );
		}
	}
}
