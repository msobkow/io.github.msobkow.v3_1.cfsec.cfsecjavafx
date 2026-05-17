// Description: Java 25 JavaFX Attribute Pane implementation for SecSysRoleEnables.

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
 *	CFSecJavaFXSecSysRoleEnablesAttrPane JavaFX Attribute Pane implementation
 *	for SecSysRoleEnables.
 */
public class CFSecJavaFXSecSysRoleEnablesAttrPane
extends CFGridPane
implements ICFSecJavaFXSecSysRoleEnablesPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class SecSysRoleEnablesGroupCFLabel
		extends CFLabel
	{
		public SecSysRoleEnablesGroupCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecSysRoleEnables.AttrPane.ParentEnableGroup.EffLabel"));
		}
	}

	protected class CallbackSecSysRoleEnablesGroupChosen
	implements ICFSecJavaFXSecSysGrpChosen
	{
		public CallbackSecSysRoleEnablesGroupChosen() {
		}

		public void choseSecSysGrp( ICFSecSecSysGrpObj value ) {
			if( javafxReferenceParentEnableGroup != null ) {
				ICFSecSecSysRoleEnablesObj cur = getJavaFXFocusAsSecSysRoleEnables();
				if( cur != null ) {
					ICFSecSecSysRoleEnablesEditObj editObj = (ICFSecSecSysRoleEnablesEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceParentEnableGroup.setReferencedObject( value );
							editObj.setRequiredParentEnableGroup( value );
						}
					}
				}
			}
		}
	}

	protected class SecSysRoleEnablesGroupReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			ICFSecSecSysRoleEnablesObj focus = getEffJavaFXFocus();
			ICFSecSecSysGrpObj referencedObj = (ICFSecSecSysGrpObj)javafxReferenceParentEnableGroup.getReferencedObject();
			java.util.List<ICFSecSecSysGrpObj> listOfSecSysGrp = null;
			listOfSecSysGrp = schemaObj.getSecSysGrpTableObj().readAllSecSysGrp();
			if( listOfSecSysGrp == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfSecSysGrp" );
			}
			Collection<ICFSecSecSysGrpObj> cltn = listOfSecSysGrp;
			CFBorderPane form = javafxSchema.getSecSysGrpFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackSecSysRoleEnablesGroupChosen() );
			((ICFSecJavaFXSecSysGrpPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFSecSecSysRoleEnablesObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFSecSecSysGrpObj referencedObj = (ICFSecSecSysGrpObj)javafxReferenceParentEnableGroup.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysGrp.CLASS_CODE ) {
						form = javafxSchema.getSecSysGrpFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFSecJavaFXSecSysGrpPaneCommon spec = (ICFSecJavaFXSecSysGrpPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFSecSecSysGrpObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class SecSysRoleEnablesGroupCFReferenceEditor
		extends CFReferenceEditor
	{
		public SecSysRoleEnablesGroupCFReferenceEditor() {
			super( new SecSysRoleEnablesGroupReferenceCallback() );
			setFieldNameInzTag( "cfsec.javafx.SecSysRoleEnables.AttrPane.SecSysRoleEnablesGroup.EffLabel" );
		}
	}

	protected ICFSecSecSysGrpObj javafxParentEnableGroupObj = null;
	protected SecSysRoleEnablesGroupCFLabel javafxLabelParentEnableGroup = null;
	protected SecSysRoleEnablesGroupCFReferenceEditor javafxReferenceParentEnableGroup = null;

	public CFSecJavaFXSecSysRoleEnablesAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecSysRoleEnablesObj argFocus ) {
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
		setJavaFXFocusAsSecSysRoleEnables( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelParentEnableGroup();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceParentEnableGroup();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
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
		if( ( value == null ) || ( value instanceof ICFSecSecSysRoleEnablesObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSysRoleEnablesObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecSecSysRoleEnablesObj getJavaFXFocusAsSecSysRoleEnables() {
		return( (ICFSecSecSysRoleEnablesObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSysRoleEnables( ICFSecSecSysRoleEnablesObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecSysRoleEnablesObj getEffJavaFXFocus() {
		ICFSecSecSysRoleEnablesObj eff = (ICFSecSecSysRoleEnablesObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecSecSysRoleEnablesObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public ICFSecSecSysGrpObj getJavaFXParentEnableGroupObj() {
		return( javafxParentEnableGroupObj );
	}

	public void setJavaFXParentEnableGroupObj( ICFSecSecSysGrpObj value ) {
		javafxParentEnableGroupObj = value;
	}

	public CFLabel getJavaFXLabelParentEnableGroup() {
		if( javafxLabelParentEnableGroup == null ) {
			javafxLabelParentEnableGroup = new SecSysRoleEnablesGroupCFLabel();
		}
		return( javafxLabelParentEnableGroup );
	}

	public CFReferenceEditor getJavaFXReferenceParentEnableGroup() {
		if( javafxReferenceParentEnableGroup == null ) {
			javafxReferenceParentEnableGroup = new SecSysRoleEnablesGroupCFReferenceEditor();
		}
		return( javafxReferenceParentEnableGroup );
	}

	public void setJavaFXReferenceParentEnableGroup( SecSysRoleEnablesGroupCFReferenceEditor value ) {
		javafxReferenceParentEnableGroup = value;
	}

	public void populateFields()
	{
		ICFSecSecSysRoleEnablesObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxParentEnableGroupObj = null;
		}
		else {
			javafxParentEnableGroupObj = (ICFSecSecSysGrpObj)popObj.getRequiredParentEnableGroup( javafxIsInitializing );
		}
		if( javafxReferenceParentEnableGroup != null ) {
			javafxReferenceParentEnableGroup.setReferencedObject( javafxParentEnableGroupObj );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecSecSysRoleEnablesObj focus = getJavaFXFocusAsSecSysRoleEnables();
		ICFSecSecSysRoleEnablesEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecSecSysRoleEnablesEditObj)(focus.getEdit());
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

		javafxParentEnableGroupObj = (ICFSecSecSysGrpObj)( javafxReferenceParentEnableGroup.getReferencedObject() );
		editObj.setRequiredParentEnableGroup( javafxParentEnableGroupObj );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecSecSysRoleEnablesObj focus = getJavaFXFocusAsSecSysRoleEnables();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecSecSysRoleEnablesEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecSecSysRoleEnablesEditObj)focus.getEdit();
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
								editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
						focus = (ICFSecSecSysRoleEnablesObj)editObj.create();
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
								editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
								editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSysRoleEnablesEditObj)focus.beginEdit();
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
			ICFSecSecSysRoleEnablesObj focus = getJavaFXFocusAsSecSysRoleEnables();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceParentEnableGroup != null ) {
			javafxReferenceParentEnableGroup.setCustomDisable( ! isEditing );
		}
	}
}
