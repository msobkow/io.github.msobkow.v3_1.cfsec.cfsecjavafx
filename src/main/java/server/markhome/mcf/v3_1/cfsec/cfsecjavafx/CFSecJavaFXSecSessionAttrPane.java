// Description: Java 25 JavaFX Attribute Pane implementation for SecSession.

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
 *	CFSecJavaFXSecSessionAttrPane JavaFX Attribute Pane implementation
 *	for SecSession.
 */
public class CFSecJavaFXSecSessionAttrPane
extends CFGridPane
implements ICFSecJavaFXSecSessionPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class SecSessionIdCFLabel
		extends CFLabel
	{
		public SecSessionIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecSession.AttrPane.SecSessionId.EffLabel"));
		}
	}

	protected class SecSessionIdEditor
		extends CFDbKeyHash256Editor
	{
		public SecSessionIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecSession.AttrPane.SecSessionId.EffLabel" );
		}
	}

	protected class SecUserIdCFLabel
		extends CFLabel
	{
		public SecUserIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecSession.AttrPane.SecUserId.EffLabel"));
		}
	}

	protected class SecUserIdEditor
		extends CFDbKeyHash256Editor
	{
		public SecUserIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecSession.AttrPane.SecUserId.EffLabel" );
		}
	}

	protected class StartCFLabel
		extends CFLabel
	{
		public StartCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecSession.AttrPane.Start.EffLabel"));
		}
	}

	protected class StartEditor
		extends CFTimestampEditor
	{
		public StartEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecSession.AttrPane.Start.EffLabel" );
		}
	}

	protected class FinishCFLabel
		extends CFLabel
	{
		public FinishCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecSession.AttrPane.Finish.EffLabel"));
		}
	}

	protected class FinishEditor
		extends CFTimestampEditor
	{
		public FinishEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecSession.AttrPane.Finish.EffLabel" );
		}
	}

	protected class SecProxyIdCFLabel
		extends CFLabel
	{
		public SecProxyIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecSession.AttrPane.SecProxyId.EffLabel"));
		}
	}

	protected class SecProxyIdEditor
		extends CFDbKeyHash256Editor
	{
		public SecProxyIdEditor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecSession.AttrPane.SecProxyId.EffLabel" );
		}
	}

	protected SecSessionIdCFLabel javafxLabelSecSessionId = null;
	protected SecSessionIdEditor javafxEditorSecSessionId = null;
	protected SecUserIdCFLabel javafxLabelSecUserId = null;
	protected SecUserIdEditor javafxEditorSecUserId = null;
	protected StartCFLabel javafxLabelStart = null;
	protected StartEditor javafxEditorStart = null;
	protected FinishCFLabel javafxLabelFinish = null;
	protected FinishEditor javafxEditorFinish = null;
	protected SecProxyIdCFLabel javafxLabelSecProxyId = null;
	protected SecProxyIdEditor javafxEditorSecProxyId = null;

	public CFSecJavaFXSecSessionAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecSecSessionObj argFocus ) {
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
		setJavaFXFocusAsSecSession( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelSecSessionId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSecSessionId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelSecUserId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSecUserId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelStart();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorStart();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelFinish();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorFinish();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelSecProxyId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSecProxyId();
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
		if( ( value == null ) || ( value instanceof ICFSecSecSessionObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSessionObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecSecSessionObj getJavaFXFocusAsSecSession() {
		return( (ICFSecSecSessionObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSession( ICFSecSecSessionObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecSecSessionObj getEffJavaFXFocus() {
		ICFSecSecSessionObj eff = (ICFSecSecSessionObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecSecSessionObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public SecSessionIdCFLabel getJavaFXLabelSecSessionId() {
		if( javafxLabelSecSessionId == null ) {
			javafxLabelSecSessionId = new SecSessionIdCFLabel();
		}
		return( javafxLabelSecSessionId );
	}

	public void setJavaFXLabelSecSessionId( SecSessionIdCFLabel value ) {
		javafxLabelSecSessionId = value;
	}

	public SecSessionIdEditor getJavaFXEditorSecSessionId() {
		if( javafxEditorSecSessionId == null ) {
			javafxEditorSecSessionId = new SecSessionIdEditor();
		}
		return( javafxEditorSecSessionId );
	}

	public void setJavaFXEditorSecSessionId( SecSessionIdEditor value ) {
		javafxEditorSecSessionId = value;
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

	public StartCFLabel getJavaFXLabelStart() {
		if( javafxLabelStart == null ) {
			javafxLabelStart = new StartCFLabel();
		}
		return( javafxLabelStart );
	}

	public void setJavaFXLabelStart( StartCFLabel value ) {
		javafxLabelStart = value;
	}

	public StartEditor getJavaFXEditorStart() {
		if( javafxEditorStart == null ) {
			javafxEditorStart = new StartEditor();
		}
		return( javafxEditorStart );
	}

	public void setJavaFXEditorStart( StartEditor value ) {
		javafxEditorStart = value;
	}

	public FinishCFLabel getJavaFXLabelFinish() {
		if( javafxLabelFinish == null ) {
			javafxLabelFinish = new FinishCFLabel();
		}
		return( javafxLabelFinish );
	}

	public void setJavaFXLabelFinish( FinishCFLabel value ) {
		javafxLabelFinish = value;
	}

	public FinishEditor getJavaFXEditorFinish() {
		if( javafxEditorFinish == null ) {
			javafxEditorFinish = new FinishEditor();
		}
		return( javafxEditorFinish );
	}

	public void setJavaFXEditorFinish( FinishEditor value ) {
		javafxEditorFinish = value;
	}

	public SecProxyIdCFLabel getJavaFXLabelSecProxyId() {
		if( javafxLabelSecProxyId == null ) {
			javafxLabelSecProxyId = new SecProxyIdCFLabel();
		}
		return( javafxLabelSecProxyId );
	}

	public void setJavaFXLabelSecProxyId( SecProxyIdCFLabel value ) {
		javafxLabelSecProxyId = value;
	}

	public SecProxyIdEditor getJavaFXEditorSecProxyId() {
		if( javafxEditorSecProxyId == null ) {
			javafxEditorSecProxyId = new SecProxyIdEditor();
		}
		return( javafxEditorSecProxyId );
	}

	public void setJavaFXEditorSecProxyId( SecProxyIdEditor value ) {
		javafxEditorSecProxyId = value;
	}

	public void populateFields()
	{
		ICFSecSecSessionObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			getJavaFXEditorSecSessionId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorSecSessionId().setDbKeyHash256Value( popObj.getRequiredSecSessionId() );
		}

		if( popObj == null ) {
			getJavaFXEditorSecUserId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorSecUserId().setDbKeyHash256Value( popObj.getRequiredSecUserId() );
		}

		if( popObj == null ) {
			getJavaFXEditorStart().setTimestampValue( null );
		}
		else {
			getJavaFXEditorStart().setTimestampValue( popObj.getRequiredStart() );
		}

		if( popObj == null ) {
			getJavaFXEditorFinish().setTimestampValue( null );
		}
		else {
			getJavaFXEditorFinish().setTimestampValue( popObj.getOptionalFinish() );
		}

		if( popObj == null ) {
			getJavaFXEditorSecProxyId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorSecProxyId().setDbKeyHash256Value( popObj.getOptionalSecProxyId() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecSecSessionObj focus = getJavaFXFocusAsSecSession();
		ICFSecSecSessionEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecSecSessionEditObj)(focus.getEdit());
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

		editObj.setRequiredSecUserId( getJavaFXEditorSecUserId().getDbKeyHash256Value() );

		editObj.setRequiredStart( getJavaFXEditorStart().getTimestampValue() );

		editObj.setOptionalFinish( getJavaFXEditorFinish().getTimestampValue() );

		editObj.setOptionalSecProxyId( getJavaFXEditorSecProxyId().getDbKeyHash256Value() );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecSecSessionObj focus = getJavaFXFocusAsSecSession();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecSecSessionEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecSecSessionEditObj)focus.getEdit();
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
								editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
						focus = (ICFSecSecSessionObj)editObj.create();
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
								editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
								editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
							editObj = (ICFSecSecSessionEditObj)focus.beginEdit();
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
			ICFSecSecSessionObj focus = getJavaFXFocusAsSecSession();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxEditorSecSessionId != null ) {
			javafxEditorSecSessionId.setDisable( true );
		}
		if( javafxEditorSecUserId != null ) {
			javafxEditorSecUserId.setDisable( ! isEditing );
		}
		if( javafxEditorStart != null ) {
			javafxEditorStart.setDisable( ! isEditing );
		}
		if( javafxEditorFinish != null ) {
			javafxEditorFinish.setDisable( ! isEditing );
		}
		if( javafxEditorSecProxyId != null ) {
			javafxEditorSecProxyId.setDisable( ! isEditing );
		}
	}
}
