// Description: Java 25 JavaFX Attribute Pane implementation for TSecGrpMemb.

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
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.inz.Inz;
import io.github.msobkow.v3_1.cflib.javafx.*;
import io.github.msobkow.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import org.apache.commons.codec.binary.Base64;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXTSecGrpMembAttrPane JavaFX Attribute Pane implementation
 *	for TSecGrpMemb.
 */
public class CFSecJavaFXTSecGrpMembAttrPane
extends CFGridPane
implements ICFSecJavaFXTSecGrpMembPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class TSecGrpMembUserCFLabel
		extends CFLabel
	{
		public TSecGrpMembUserCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.TSecGrpMemb.AttrPane.ParentUser.EffLabel"));
		}
	}

	protected class CallbackTSecGrpMembUserChosen
	implements ICFSecJavaFXSecUserChosen
	{
		public CallbackTSecGrpMembUserChosen() {
		}

		public void choseSecUser( ICFSecSecUserObj value ) {
			if( javafxReferenceParentUser != null ) {
				ICFSecTSecGrpMembObj cur = getJavaFXFocusAsTSecGrpMemb();
				if( cur != null ) {
					ICFSecTSecGrpMembEditObj editObj = (ICFSecTSecGrpMembEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceParentUser.setReferencedObject( value );
							editObj.setRequiredParentUser( value );
						}
					}
				}
			}
		}
	}

	protected class PageDataParentUserList
	implements ICFSecJavaFXSecUserPageCallback
	{
		public PageDataParentUserList() {
		}

		public List<ICFSecSecUserObj> pageData( CFLibDbKeyHash256 priorSecUserId )
		{
			java.util.List<ICFSecSecUserObj> listOfSecUser = null;
			ICFSecTSecGrpMembObj focus = (ICFSecTSecGrpMembObj)getEffJavaFXFocus();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			listOfSecUser = schemaObj.getSecUserTableObj().pageAllSecUser( priorSecUserId );
			}
			else {
				listOfSecUser = new ArrayList<ICFSecSecUserObj>();
			}
			return( listOfSecUser  );
		}
	}

	protected class TSecGrpMembUserReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			ICFSecTSecGrpMembObj focus = getEffJavaFXFocus();
			ICFSecSecUserObj referencedObj = (ICFSecSecUserObj)javafxReferenceParentUser.getReferencedObject();
			CFBorderPane form = javafxSchema.getSecUserFactory().newPickerForm( cfFormManager, referencedObj, null, new PageDataParentUserList(), new CallbackTSecGrpMembUserChosen() );
			((ICFSecJavaFXSecUserPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFSecTSecGrpMembObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFSecSecUserObj referencedObj = (ICFSecSecUserObj)javafxReferenceParentUser.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecUser.CLASS_CODE ) {
						form = javafxSchema.getSecUserFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFSecJavaFXSecUserPaneCommon spec = (ICFSecJavaFXSecUserPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFSecSecUserObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class TSecGrpMembUserCFReferenceEditor
		extends CFReferenceEditor
	{
		public TSecGrpMembUserCFReferenceEditor() {
			super( new TSecGrpMembUserReferenceCallback() );
			setFieldNameInzTag( "cfsec.javafx.TSecGrpMemb.AttrPane.TSecGrpMembUser.EffLabel" );
		}
	}

	protected class TSecGrpMembIdCFLabel
		extends CFLabel
	{
		public TSecGrpMembIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.TSecGrpMemb.AttrPane.TSecGrpMembId.EffLabel"));
		}
	}

	protected class TSecGrpMembIdEditor
		extends CFDbKeyHash256Editor
	{
		public TSecGrpMembIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.TSecGrpMemb.AttrPane.TSecGrpMembId.EffLabel" );
		}
	}

	protected ICFSecSecUserObj javafxParentUserObj = null;
	protected TSecGrpMembUserCFLabel javafxLabelParentUser = null;
	protected TSecGrpMembUserCFReferenceEditor javafxReferenceParentUser = null;
	protected TSecGrpMembIdCFLabel javafxLabelTSecGrpMembId = null;
	protected TSecGrpMembIdEditor javafxEditorTSecGrpMembId = null;

	public CFSecJavaFXTSecGrpMembAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecTSecGrpMembObj argFocus ) {
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
		setJavaFXFocusAsTSecGrpMemb( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelParentUser();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceParentUser();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelTSecGrpMembId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorTSecGrpMembId();
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
		if( ( value == null ) || ( value instanceof ICFSecTSecGrpMembObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecTSecGrpMembObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecTSecGrpMembObj getJavaFXFocusAsTSecGrpMemb() {
		return( (ICFSecTSecGrpMembObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTSecGrpMemb( ICFSecTSecGrpMembObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecTSecGrpMembObj getEffJavaFXFocus() {
		ICFSecTSecGrpMembObj eff = (ICFSecTSecGrpMembObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecTSecGrpMembObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public ICFSecSecUserObj getJavaFXParentUserObj() {
		return( javafxParentUserObj );
	}

	public void setJavaFXParentUserObj( ICFSecSecUserObj value ) {
		javafxParentUserObj = value;
	}

	public CFLabel getJavaFXLabelParentUser() {
		if( javafxLabelParentUser == null ) {
			javafxLabelParentUser = new TSecGrpMembUserCFLabel();
		}
		return( javafxLabelParentUser );
	}

	public CFReferenceEditor getJavaFXReferenceParentUser() {
		if( javafxReferenceParentUser == null ) {
			javafxReferenceParentUser = new TSecGrpMembUserCFReferenceEditor();
		}
		return( javafxReferenceParentUser );
	}

	public void setJavaFXReferenceParentUser( TSecGrpMembUserCFReferenceEditor value ) {
		javafxReferenceParentUser = value;
	}

	public TSecGrpMembIdCFLabel getJavaFXLabelTSecGrpMembId() {
		if( javafxLabelTSecGrpMembId == null ) {
			javafxLabelTSecGrpMembId = new TSecGrpMembIdCFLabel();
		}
		return( javafxLabelTSecGrpMembId );
	}

	public void setJavaFXLabelTSecGrpMembId( TSecGrpMembIdCFLabel value ) {
		javafxLabelTSecGrpMembId = value;
	}

	public TSecGrpMembIdEditor getJavaFXEditorTSecGrpMembId() {
		if( javafxEditorTSecGrpMembId == null ) {
			javafxEditorTSecGrpMembId = new TSecGrpMembIdEditor();
		}
		return( javafxEditorTSecGrpMembId );
	}

	public void setJavaFXEditorTSecGrpMembId( TSecGrpMembIdEditor value ) {
		javafxEditorTSecGrpMembId = value;
	}

	public void populateFields()
	{
		ICFSecTSecGrpMembObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxParentUserObj = null;
		}
		else {
			javafxParentUserObj = (ICFSecSecUserObj)popObj.getRequiredParentUser( javafxIsInitializing );
		}
		if( javafxReferenceParentUser != null ) {
			javafxReferenceParentUser.setReferencedObject( javafxParentUserObj );
		}

		if( popObj == null ) {
			getJavaFXEditorTSecGrpMembId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorTSecGrpMembId().setDbKeyHash256Value( popObj.getRequiredTSecGrpMembId() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecTSecGrpMembObj focus = getJavaFXFocusAsTSecGrpMemb();
		ICFSecTSecGrpMembEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecTSecGrpMembEditObj)(focus.getEdit());
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

		javafxParentUserObj = (ICFSecSecUserObj)( javafxReferenceParentUser.getReferencedObject() );
		editObj.setRequiredParentUser( javafxParentUserObj );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecTSecGrpMembObj focus = getJavaFXFocusAsTSecGrpMemb();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecTSecGrpMembEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecTSecGrpMembEditObj)focus.getEdit();
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
								editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
						focus = (ICFSecTSecGrpMembObj)editObj.create();
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
								editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
								editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpMembEditObj)focus.beginEdit();
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
			ICFSecTSecGrpMembObj focus = getJavaFXFocusAsTSecGrpMemb();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceParentUser != null ) {
			javafxReferenceParentUser.setCustomDisable( ! isEditing );
		}
		if( javafxEditorTSecGrpMembId != null ) {
			javafxEditorTSecGrpMembId.setDisable( true );
		}
	}
}
