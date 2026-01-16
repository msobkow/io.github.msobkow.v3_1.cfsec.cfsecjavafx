// Description: Java 25 JavaFX Attribute Pane implementation for ISOLang.

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
 *	CFSecJavaFXISOLangAttrPane JavaFX Attribute Pane implementation
 *	for ISOLang.
 */
public class CFSecJavaFXISOLangAttrPane
extends CFGridPane
implements ICFSecJavaFXISOLangPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class ISOLangIdCFLabel
		extends CFLabel
	{
		public ISOLangIdCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.ISOLang.AttrPane.ISOLangId.EffLabel"));
		}
	}

	protected class ISOLangIdEditor
		extends CFInt16Editor
	{
		public ISOLangIdEditor() {
			super();
			setMinValue( ICFSecISOLang.ISOLANGID_MIN_VALUE );
			setFieldNameInzTag( "cfsec.javafx.ISOLang.AttrPane.ISOLangId.EffLabel" );
		}
	}

	protected class ISO6392CodeCFLabel
		extends CFLabel
	{
		public ISO6392CodeCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.ISOLang.AttrPane.ISO6392Code.EffLabel"));
		}
	}

	protected class ISO6392CodeEditor
		extends CFStringEditor
	{
		public ISO6392CodeEditor() {
			super();
			setMaxLen( 3 );
			setFieldNameInzTag( "cfsec.javafx.ISOLang.AttrPane.ISO6392Code.EffLabel" );
		}
	}

	protected class ISO6391CodeCFLabel
		extends CFLabel
	{
		public ISO6391CodeCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.ISOLang.AttrPane.ISO6391Code.EffLabel"));
		}
	}

	protected class ISO6391CodeEditor
		extends CFStringEditor
	{
		public ISO6391CodeEditor() {
			super();
			setMaxLen( 2 );
			setFieldNameInzTag( "cfsec.javafx.ISOLang.AttrPane.ISO6391Code.EffLabel" );
		}
	}

	protected class EnglishNameCFLabel
		extends CFLabel
	{
		public EnglishNameCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.ISOLang.AttrPane.EnglishName.EffLabel"));
		}
	}

	protected class EnglishNameEditor
		extends CFStringEditor
	{
		public EnglishNameEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfsec.javafx.ISOLang.AttrPane.EnglishName.EffLabel" );
		}
	}

	protected ISOLangIdCFLabel javafxLabelISOLangId = null;
	protected ISOLangIdEditor javafxEditorISOLangId = null;
	protected ISO6392CodeCFLabel javafxLabelISO6392Code = null;
	protected ISO6392CodeEditor javafxEditorISO6392Code = null;
	protected ISO6391CodeCFLabel javafxLabelISO6391Code = null;
	protected ISO6391CodeEditor javafxEditorISO6391Code = null;
	protected EnglishNameCFLabel javafxLabelEnglishName = null;
	protected EnglishNameEditor javafxEditorEnglishName = null;

	public CFSecJavaFXISOLangAttrPane( ICFFormManager formManager, ICFSecJavaFXSchema argSchema, ICFSecISOLangObj argFocus ) {
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
		setJavaFXFocusAsISOLang( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelISOLangId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorISOLangId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelISO6392Code();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorISO6392Code();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelISO6391Code();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorISO6391Code();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelEnglishName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorEnglishName();
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
		if( ( value == null ) || ( value instanceof ICFSecISOLangObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecISOLangObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFSecISOLangObj getJavaFXFocusAsISOLang() {
		return( (ICFSecISOLangObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsISOLang( ICFSecISOLangObj value ) {
		setJavaFXFocus( value );
	}

	public ICFSecISOLangObj getEffJavaFXFocus() {
		ICFSecISOLangObj eff = (ICFSecISOLangObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFSecISOLangObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public ISOLangIdCFLabel getJavaFXLabelISOLangId() {
		if( javafxLabelISOLangId == null ) {
			javafxLabelISOLangId = new ISOLangIdCFLabel();
		}
		return( javafxLabelISOLangId );
	}

	public void setJavaFXLabelISOLangId( ISOLangIdCFLabel value ) {
		javafxLabelISOLangId = value;
	}

	public ISOLangIdEditor getJavaFXEditorISOLangId() {
		if( javafxEditorISOLangId == null ) {
			javafxEditorISOLangId = new ISOLangIdEditor();
		}
		return( javafxEditorISOLangId );
	}

	public void setJavaFXEditorISOLangId( ISOLangIdEditor value ) {
		javafxEditorISOLangId = value;
	}

	public ISO6392CodeCFLabel getJavaFXLabelISO6392Code() {
		if( javafxLabelISO6392Code == null ) {
			javafxLabelISO6392Code = new ISO6392CodeCFLabel();
		}
		return( javafxLabelISO6392Code );
	}

	public void setJavaFXLabelISO6392Code( ISO6392CodeCFLabel value ) {
		javafxLabelISO6392Code = value;
	}

	public ISO6392CodeEditor getJavaFXEditorISO6392Code() {
		if( javafxEditorISO6392Code == null ) {
			javafxEditorISO6392Code = new ISO6392CodeEditor();
		}
		return( javafxEditorISO6392Code );
	}

	public void setJavaFXEditorISO6392Code( ISO6392CodeEditor value ) {
		javafxEditorISO6392Code = value;
	}

	public ISO6391CodeCFLabel getJavaFXLabelISO6391Code() {
		if( javafxLabelISO6391Code == null ) {
			javafxLabelISO6391Code = new ISO6391CodeCFLabel();
		}
		return( javafxLabelISO6391Code );
	}

	public void setJavaFXLabelISO6391Code( ISO6391CodeCFLabel value ) {
		javafxLabelISO6391Code = value;
	}

	public ISO6391CodeEditor getJavaFXEditorISO6391Code() {
		if( javafxEditorISO6391Code == null ) {
			javafxEditorISO6391Code = new ISO6391CodeEditor();
		}
		return( javafxEditorISO6391Code );
	}

	public void setJavaFXEditorISO6391Code( ISO6391CodeEditor value ) {
		javafxEditorISO6391Code = value;
	}

	public EnglishNameCFLabel getJavaFXLabelEnglishName() {
		if( javafxLabelEnglishName == null ) {
			javafxLabelEnglishName = new EnglishNameCFLabel();
		}
		return( javafxLabelEnglishName );
	}

	public void setJavaFXLabelEnglishName( EnglishNameCFLabel value ) {
		javafxLabelEnglishName = value;
	}

	public EnglishNameEditor getJavaFXEditorEnglishName() {
		if( javafxEditorEnglishName == null ) {
			javafxEditorEnglishName = new EnglishNameEditor();
		}
		return( javafxEditorEnglishName );
	}

	public void setJavaFXEditorEnglishName( EnglishNameEditor value ) {
		javafxEditorEnglishName = value;
	}

	public void populateFields()
	{
		ICFSecISOLangObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			getJavaFXEditorISOLangId().setInt16Value( null );
		}
		else {
			getJavaFXEditorISOLangId().setInt16Value( popObj.getRequiredISOLangId() );
		}

		if( popObj == null ) {
			getJavaFXEditorISO6392Code().setStringValue( null );
		}
		else {
			getJavaFXEditorISO6392Code().setStringValue( popObj.getRequiredISO6392Code() );
		}

		if( popObj == null ) {
			getJavaFXEditorISO6391Code().setStringValue( null );
		}
		else {
			getJavaFXEditorISO6391Code().setStringValue( popObj.getOptionalISO6391Code() );
		}

		if( popObj == null ) {
			getJavaFXEditorEnglishName().setStringValue( null );
		}
		else {
			getJavaFXEditorEnglishName().setStringValue( popObj.getRequiredEnglishName() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFSecISOLangObj focus = getJavaFXFocusAsISOLang();
		ICFSecISOLangEditObj editObj;
		if( focus != null ) {
			editObj = (ICFSecISOLangEditObj)(focus.getEdit());
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

		if( getJavaFXEditorISO6392Code().getStringValue() == null ) {
			editObj.setRequiredISO6392Code( "" );
		}
		else {
			editObj.setRequiredISO6392Code( getJavaFXEditorISO6392Code().getStringValue() );
		}

		if( ( getJavaFXEditorISO6391Code().getStringValue() != null ) && ( getJavaFXEditorISO6391Code().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalISO6391Code( null );
		}
		else {
			editObj.setOptionalISO6391Code( getJavaFXEditorISO6391Code().getStringValue() );
		}

		if( getJavaFXEditorEnglishName().getStringValue() == null ) {
			editObj.setRequiredEnglishName( "" );
		}
		else {
			editObj.setRequiredEnglishName( getJavaFXEditorEnglishName().getStringValue() );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFSecISOLangObj focus = getJavaFXFocusAsISOLang();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFSecISOLangEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFSecISOLangEditObj)focus.getEdit();
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
								editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
							editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
							editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
							editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
						focus = (ICFSecISOLangObj)editObj.create();
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
								editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
								editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
							editObj = (ICFSecISOLangEditObj)focus.beginEdit();
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
			ICFSecISOLangObj focus = getJavaFXFocusAsISOLang();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxEditorISOLangId != null ) {
			javafxEditorISOLangId.setDisable( true );
		}
		if( javafxEditorISO6392Code != null ) {
			javafxEditorISO6392Code.setDisable( ! isEditing );
		}
		if( javafxEditorISO6391Code != null ) {
			javafxEditorISO6391Code.setDisable( ! isEditing );
		}
		if( javafxEditorEnglishName != null ) {
			javafxEditorEnglishName.setDisable( ! isEditing );
		}
	}
}
