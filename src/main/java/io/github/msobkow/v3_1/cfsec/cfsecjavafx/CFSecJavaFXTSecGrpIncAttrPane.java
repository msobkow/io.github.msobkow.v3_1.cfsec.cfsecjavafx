// Description: Java 25 JavaFX Attribute Pane implementation for TSecGrpInc.

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
 *	CFSecJavaFXTSecGrpIncAttrPane JavaFX Attribute Pane implementation
 *	for TSecGrpInc.
 */
public class CFSecJavaFXTSecGrpIncAttrPane
extends CFGridPane
implements ICFSecJavaFXTSecGrpIncPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class TSecGrpIncSubGroupCFLabel
		extends CFLabel
	{
		public TSecGrpIncSubGroupCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.TSecGrpInc.AttrPane.ParentSubGroup.EffLabel"));
		}
	}

	protected class CallbackTSecGrpIncSubGroupChosen
	implements ICFSecJavaFXTSecGroupChosen
	{
		public CallbackTSecGrpIncSubGroupChosen() {
		}

		public void choseTSecGroup( ICFSecTSecGroupObj value ) {
			if( javafxReferenceParentSubGroup != null ) {
				ICFSecTSecGrpIncObj cur = getJavaFXFocusAsTSecGrpInc();
				if( cur != null ) {
					ICFSecTSecGrpIncEditObj editObj = (ICFSecTSecGrpIncEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceParentSubGroup.setReferencedObject( value );
							editObj.setRequiredParentSubGroup( value );
						}
					}
				}
			}
		}
	}

	protected class TSecGrpIncSubGroupReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			ICFSecTSecGrpIncObj focus = getEffJavaFXFocus();
			ICFSecTSecGroupObj referencedObj = (ICFSecTSecGroupObj)javafxReferenceParentSubGroup.getReferencedObject();
			java.util.List<ICFSecTSecGroupObj> listOfTSecGroup = null;
			ICFSecAuthorization auth = schemaObj.getAuthorization();
			CFLibDbKeyHash256 containingTenantId = auth.getSecTenantId();
			listOfTSecGroup = schemaObj.getTSecGroupTableObj().readTSecGroupByTenantIdx( containingTenantId );
			if( listOfTSecGroup == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfTSecGroup" );
			}
			Collection<ICFSecTSecGroupObj> cltn = listOfTSecGroup;
			CFBorderPane form = javafxSchema.getTSecGroupFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackTSecGrpIncSubGroupChosen() );
			((ICFSecJavaFXTSecGroupPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFSecTSecGrpIncObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFSecTSecGroupObj referencedObj = (ICFSecTSecGroupObj)javafxReferenceParentSubGroup.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecTSecGroup.CLASS_CODE ) {
						form = javafxSchema.getTSecGroupFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFSecJavaFXTSecGroupPaneCommon spec = (ICFSecJavaFXTSecGroupPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFSecTSecGroupObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class TSecGrpIncSubGroupCFReferenceEditor
		extends CFReferenceEditor
	{
		public TSecGrpIncSubGroupCFReferenceEditor() {
			super( new TSecGrpIncSubGroupReferenceCallback() );
			setFieldNameInzTag( "cfsec.javafx.TSecGrpInc.AttrPane.TSecGrpIncSubGroup.EffLabel" );
		}
	}

	protected class TSecGrpIncIdCFLabel
		extends CFLabel
	{
		public TSecGrpIncIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.TSecGrpInc.AttrPane.TSecGrpIncId.EffLabel"));
		}
	}

	protected class TSecGrpIncIdEditor
		extends CFDbKeyHash256Editor
	{
		public TSecGrpIncIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.TSecGrpInc.AttrPane.TSecGrpIncId.EffLabel" );
		}
	}

	protected ICFSecTSecGroupObj javafxParentSubGroupObj = null;
	protected TSecGrpIncSubGroupCFLabel javafxLabelParentSubGroup = null;
	protected TSecGrpIncSubGroupCFReferenceEditor javafxReferenceParentSubGroup = null;
	protected TSecGrpIncIdCFLabel javafxLabelTSecGrpIncId = null;
	protected TSecGrpIncIdEditor javafxEditorTSecGrpIncId = null;

	public CFSecJavaFXTSecGrpIncAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecTSecGrpIncObj argFocus ) {
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
		setJavaFXFocusAsTSecGrpInc( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelParentSubGroup();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceParentSubGroup();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelTSecGrpIncId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorTSecGrpIncId();
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
		if( ( value == null ) || ( value instanceof ICFSecTSecGrpIncObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecTSecGrpIncObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecTSecGrpIncObj getJavaFXFocusAsTSecGrpInc() {
		return( (ICFSecTSecGrpIncObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTSecGrpInc( ICFSecTSecGrpIncObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecTSecGrpIncObj getEffJavaFXFocus() {
		ICFSecTSecGrpIncObj eff = (ICFSecTSecGrpIncObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecTSecGrpIncObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public ICFSecTSecGroupObj getJavaFXParentSubGroupObj() {
		return( javafxParentSubGroupObj );
	}

	public void setJavaFXParentSubGroupObj( ICFSecTSecGroupObj value ) {
		javafxParentSubGroupObj = value;
	}

	public CFLabel getJavaFXLabelParentSubGroup() {
		if( javafxLabelParentSubGroup == null ) {
			javafxLabelParentSubGroup = new TSecGrpIncSubGroupCFLabel();
		}
		return( javafxLabelParentSubGroup );
	}

	public CFReferenceEditor getJavaFXReferenceParentSubGroup() {
		if( javafxReferenceParentSubGroup == null ) {
			javafxReferenceParentSubGroup = new TSecGrpIncSubGroupCFReferenceEditor();
		}
		return( javafxReferenceParentSubGroup );
	}

	public void setJavaFXReferenceParentSubGroup( TSecGrpIncSubGroupCFReferenceEditor value ) {
		javafxReferenceParentSubGroup = value;
	}

	public TSecGrpIncIdCFLabel getJavaFXLabelTSecGrpIncId() {
		if( javafxLabelTSecGrpIncId == null ) {
			javafxLabelTSecGrpIncId = new TSecGrpIncIdCFLabel();
		}
		return( javafxLabelTSecGrpIncId );
	}

	public void setJavaFXLabelTSecGrpIncId( TSecGrpIncIdCFLabel value ) {
		javafxLabelTSecGrpIncId = value;
	}

	public TSecGrpIncIdEditor getJavaFXEditorTSecGrpIncId() {
		if( javafxEditorTSecGrpIncId == null ) {
			javafxEditorTSecGrpIncId = new TSecGrpIncIdEditor();
		}
		return( javafxEditorTSecGrpIncId );
	}

	public void setJavaFXEditorTSecGrpIncId( TSecGrpIncIdEditor value ) {
		javafxEditorTSecGrpIncId = value;
	}

	public void populateFields()
	{
		ICFSecTSecGrpIncObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxParentSubGroupObj = null;
		}
		else {
			javafxParentSubGroupObj = (ICFSecTSecGroupObj)popObj.getRequiredParentSubGroup( javafxIsInitializing );
		}
		if( javafxReferenceParentSubGroup != null ) {
			javafxReferenceParentSubGroup.setReferencedObject( javafxParentSubGroupObj );
		}

		if( popObj == null ) {
			getJavaFXEditorTSecGrpIncId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorTSecGrpIncId().setDbKeyHash256Value( popObj.getRequiredTSecGrpIncId() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecTSecGrpIncObj focus = getJavaFXFocusAsTSecGrpInc();
		ICFSecTSecGrpIncEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecTSecGrpIncEditObj)(focus.getEdit());
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

		javafxParentSubGroupObj = (ICFSecTSecGroupObj)( javafxReferenceParentSubGroup.getReferencedObject() );
		editObj.setRequiredParentSubGroup( javafxParentSubGroupObj );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecTSecGrpIncObj focus = getJavaFXFocusAsTSecGrpInc();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecTSecGrpIncEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecTSecGrpIncEditObj)focus.getEdit();
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
								editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
						focus = (ICFSecTSecGrpIncObj)editObj.create();
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
								editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
								editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
							editObj = (ICFSecTSecGrpIncEditObj)focus.beginEdit();
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
			ICFSecTSecGrpIncObj focus = getJavaFXFocusAsTSecGrpInc();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceParentSubGroup != null ) {
			javafxReferenceParentSubGroup.setCustomDisable( ! isEditing );
		}
		if( javafxEditorTSecGrpIncId != null ) {
			javafxEditorTSecGrpIncId.setDisable( true );
		}
	}
}
