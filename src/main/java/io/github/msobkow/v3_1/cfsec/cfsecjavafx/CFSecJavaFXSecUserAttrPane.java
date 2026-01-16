// Description: Java 25 JavaFX Attribute Pane implementation for SecUser.

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

	protected class SecUserDefDevCFLabel
		extends CFLabel
	{
		public SecUserDefDevCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.LookupDefDev.EffLabel"));
		}
	}

	protected class CallbackSecUserDefDevChosen
	implements ICFSecJavaFXSecDeviceChosen
	{
		public CallbackSecUserDefDevChosen() {
		}

		public void choseSecDevice( ICFSecSecDeviceObj value ) {
			if( javafxReferenceLookupDefDev != null ) {
				ICFSecSecUserObj cur = getJavaFXFocusAsSecUser();
				if( cur != null ) {
					ICFSecSecUserEditObj editObj = (ICFSecSecUserEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupDefDev.setReferencedObject( value );
							editObj.setOptionalLookupDefDev( value );
						}
					}
				}
			}
		}
	}

	protected class PageDataLookupDefDevList
	implements ICFSecJavaFXSecDevicePageCallback
	{
		public PageDataLookupDefDevList() {
		}

		public List<ICFSecSecDeviceObj> pageData( CFLibDbKeyHash256 priorSecUserId,
		String priorDevName )
		{
			java.util.List<ICFSecSecDeviceObj> listOfSecDevice = null;
			ICFSecSecUserObj focus = (ICFSecSecUserObj)getEffJavaFXFocus();
			if( focus != null ) {
				ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			Collection<ICFSecSecDeviceObj> cltn = null;
			}
			else {
				listOfSecDevice = new ArrayList<ICFSecSecDeviceObj>();
			}
			return( listOfSecDevice  );
		}
	}

	protected class SecUserDefDevReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			ICFSecSecUserObj focus = getEffJavaFXFocus();
			ICFSecSecDeviceObj referencedObj = (ICFSecSecDeviceObj)javafxReferenceLookupDefDev.getReferencedObject();
			CFBorderPane form = javafxSchema.getSecDeviceFactory().newPickerForm( cfFormManager, referencedObj, null, new PageDataLookupDefDevList(), new CallbackSecUserDefDevChosen() );
			((ICFSecJavaFXSecDevicePaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFSecSecUserObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFSecSecDeviceObj referencedObj = (ICFSecSecDeviceObj)javafxReferenceLookupDefDev.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecDevice.CLASS_CODE ) {
						form = javafxSchema.getSecDeviceFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFSecJavaFXSecDevicePaneCommon spec = (ICFSecJavaFXSecDevicePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFSecSecDeviceObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class SecUserDefDevCFReferenceEditor
		extends CFReferenceEditor
	{
		public SecUserDefDevCFReferenceEditor() {
			super( new SecUserDefDevReferenceCallback() );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.SecUserDefDev.EffLabel" );
		}
	}

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

	protected class EMailConfirmUuid6CFLabel
		extends CFLabel
	{
		public EMailConfirmUuid6CFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.EMailConfirmUuid6.EffLabel"));
		}
	}

	protected class EMailConfirmUuid6Editor
		extends CFUuid6Editor
	{
		public EMailConfirmUuid6Editor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.EMailConfirmUuid6.EffLabel" );
		}
	}

	protected class PasswordHashCFLabel
		extends CFLabel
	{
		public PasswordHashCFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.PasswordHash.EffLabel"));
		}
	}

	protected class PasswordHashEditor
		extends CFStringEditor
	{
		public PasswordHashEditor() {
			super();
			setMaxLen( 256 );
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.PasswordHash.EffLabel" );
		}
	}

	protected class PasswordResetUuid6CFLabel
		extends CFLabel
	{
		public PasswordResetUuid6CFLabel() {
			super();
			setText(Inz.s("cfsec.javafx.SecUser.AttrPane.PasswordResetUuid6.EffLabel"));
		}
	}

	protected class PasswordResetUuid6Editor
		extends CFUuid6Editor
	{
		public PasswordResetUuid6Editor() {
			super();
			setFieldNameInzTag( "cfsec.javafx.SecUser.AttrPane.PasswordResetUuid6.EffLabel" );
		}
	}

	protected ICFSecSecDeviceObj javafxLookupDefDevObj = null;
	protected SecUserDefDevCFLabel javafxLabelLookupDefDev = null;
	protected SecUserDefDevCFReferenceEditor javafxReferenceLookupDefDev = null;
	protected SecUserIdCFLabel javafxLabelSecUserId = null;
	protected SecUserIdEditor javafxEditorSecUserId = null;
	protected LoginIdCFLabel javafxLabelLoginId = null;
	protected LoginIdEditor javafxEditorLoginId = null;
	protected EMailAddressCFLabel javafxLabelEMailAddress = null;
	protected EMailAddressEditor javafxEditorEMailAddress = null;
	protected EMailConfirmUuid6CFLabel javafxLabelEMailConfirmUuid6 = null;
	protected EMailConfirmUuid6Editor javafxEditorEMailConfirmUuid6 = null;
	protected PasswordHashCFLabel javafxLabelPasswordHash = null;
	protected PasswordHashEditor javafxEditorPasswordHash = null;
	protected PasswordResetUuid6CFLabel javafxLabelPasswordResetUuid6 = null;
	protected PasswordResetUuid6Editor javafxEditorPasswordResetUuid6 = null;

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
		label = getJavaFXLabelLookupDefDev();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupDefDev();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
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

		label = getJavaFXLabelLoginId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorLoginId();
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

		label = getJavaFXLabelEMailConfirmUuid6();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorEMailConfirmUuid6();
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

		label = getJavaFXLabelPasswordResetUuid6();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorPasswordResetUuid6();
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

	public ICFSecSecDeviceObj getJavaFXLookupDefDevObj() {
		return( javafxLookupDefDevObj );
	}

	public void setJavaFXLookupDefDevObj( ICFSecSecDeviceObj value ) {
		javafxLookupDefDevObj = value;
	}

	public CFLabel getJavaFXLabelLookupDefDev() {
		if( javafxLabelLookupDefDev == null ) {
			javafxLabelLookupDefDev = new SecUserDefDevCFLabel();
		}
		return( javafxLabelLookupDefDev );
	}

	public CFReferenceEditor getJavaFXReferenceLookupDefDev() {
		if( javafxReferenceLookupDefDev == null ) {
			javafxReferenceLookupDefDev = new SecUserDefDevCFReferenceEditor();
		}
		return( javafxReferenceLookupDefDev );
	}

	public void setJavaFXReferenceLookupDefDev( SecUserDefDevCFReferenceEditor value ) {
		javafxReferenceLookupDefDev = value;
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

	public EMailConfirmUuid6CFLabel getJavaFXLabelEMailConfirmUuid6() {
		if( javafxLabelEMailConfirmUuid6 == null ) {
			javafxLabelEMailConfirmUuid6 = new EMailConfirmUuid6CFLabel();
		}
		return( javafxLabelEMailConfirmUuid6 );
	}

	public void setJavaFXLabelEMailConfirmUuid6( EMailConfirmUuid6CFLabel value ) {
		javafxLabelEMailConfirmUuid6 = value;
	}

	public EMailConfirmUuid6Editor getJavaFXEditorEMailConfirmUuid6() {
		if( javafxEditorEMailConfirmUuid6 == null ) {
			javafxEditorEMailConfirmUuid6 = new EMailConfirmUuid6Editor();
		}
		return( javafxEditorEMailConfirmUuid6 );
	}

	public void setJavaFXEditorEMailConfirmUuid6( EMailConfirmUuid6Editor value ) {
		javafxEditorEMailConfirmUuid6 = value;
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

	public void populateFields()
	{
		ICFSecSecUserObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxLookupDefDevObj = null;
		}
		else {
			javafxLookupDefDevObj = (ICFSecSecDeviceObj)popObj.getOptionalLookupDefDev( javafxIsInitializing );
		}
		if( javafxReferenceLookupDefDev != null ) {
			javafxReferenceLookupDefDev.setReferencedObject( javafxLookupDefDevObj );
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
			getJavaFXEditorEMailAddress().setStringValue( null );
		}
		else {
			getJavaFXEditorEMailAddress().setStringValue( popObj.getRequiredEMailAddress() );
		}

		if( popObj == null ) {
			getJavaFXEditorEMailConfirmUuid6().setUuid6Value( null );
		}
		else {
			getJavaFXEditorEMailConfirmUuid6().setUuid6Value( popObj.getOptionalEMailConfirmUuid6() );
		}

		if( popObj == null ) {
			getJavaFXEditorPasswordHash().setStringValue( null );
		}
		else {
			getJavaFXEditorPasswordHash().setStringValue( popObj.getRequiredPasswordHash() );
		}

		if( popObj == null ) {
			getJavaFXEditorPasswordResetUuid6().setUuid6Value( null );
		}
		else {
			getJavaFXEditorPasswordResetUuid6().setUuid6Value( popObj.getOptionalPasswordResetUuid6() );
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

		javafxLookupDefDevObj = (ICFSecSecDeviceObj)( javafxReferenceLookupDefDev.getReferencedObject() );
		editObj.setOptionalLookupDefDev( javafxLookupDefDevObj );

		if( getJavaFXEditorLoginId().getStringValue() == null ) {
			editObj.setRequiredLoginId( "" );
		}
		else {
			editObj.setRequiredLoginId( getJavaFXEditorLoginId().getStringValue() );
		}

		if( getJavaFXEditorEMailAddress().getStringValue() == null ) {
			editObj.setRequiredEMailAddress( "" );
		}
		else {
			editObj.setRequiredEMailAddress( getJavaFXEditorEMailAddress().getStringValue() );
		}

		editObj.setOptionalEMailConfirmUuid6( getJavaFXEditorEMailConfirmUuid6().getUuid6Value() );

		if( getJavaFXEditorPasswordHash().getStringValue() == null ) {
			editObj.setRequiredPasswordHash( "" );
		}
		else {
			editObj.setRequiredPasswordHash( getJavaFXEditorPasswordHash().getStringValue() );
		}

		editObj.setOptionalPasswordResetUuid6( getJavaFXEditorPasswordResetUuid6().getUuid6Value() );
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
		if( javafxReferenceLookupDefDev != null ) {
			javafxReferenceLookupDefDev.setCustomDisable( ! isEditing );
		}
		if( javafxEditorSecUserId != null ) {
			javafxEditorSecUserId.setDisable( true );
		}
		if( javafxEditorLoginId != null ) {
			javafxEditorLoginId.setDisable( ! isEditing );
		}
		if( javafxEditorEMailAddress != null ) {
			javafxEditorEMailAddress.setDisable( ! isEditing );
		}
		if( javafxEditorEMailConfirmUuid6 != null ) {
			javafxEditorEMailConfirmUuid6.setDisable( ! isEditing );
		}
		if( javafxEditorPasswordHash != null ) {
			javafxEditorPasswordHash.setDisable( ! isEditing );
		}
		if( javafxEditorPasswordResetUuid6 != null ) {
			javafxEditorPasswordResetUuid6.setDisable( ! isEditing );
		}
	}
}
