// Description: Java 25 JavaFX List of Obj Pane implementation for SecUser.

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
import java.util.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.inz.Inz;
import io.github.msobkow.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecUserListPane JavaFX List of Obj Pane implementation
 *	for SecUser.
 */
public class CFSecJavaFXSecUserListPane
extends CFBorderPane
implements ICFSecJavaFXSecUserPaneList
{
	public static String S_FormName = "List Security User";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXSecUserPageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecUserObj> observableListOfSecUser = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddSecUser = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFSecSecUserObj> dataTable = null;
	protected TableColumn<ICFSecSecUserObj, CFLibDbKeyHash256> tableColumnSecUserId = null;
	protected TableColumn<ICFSecSecUserObj, String> tableColumnLoginId = null;
	protected TableColumn<ICFSecSecUserObj, String> tableColumnEMailAddress = null;
	protected TableColumn<ICFSecSecUserObj, CFLibUuid6> tableColumnEMailConfirmUuid6 = null;
	protected TableColumn<ICFSecSecUserObj, String> tableColumnPasswordHash = null;
	protected TableColumn<ICFSecSecUserObj, CFLibUuid6> tableColumnPasswordResetUuid6 = null;
	protected TableColumn<ICFSecSecUserObj, ICFSecSecDeviceObj> tableColumnLookupDefDev = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFLibAnyObj javafxContainer = null;
	protected ICFRefreshCallback javafxRefreshCallback = null;
	class ViewEditClosedCallback implements ICFFormClosedCallback {
		public ViewEditClosedCallback() {
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected ViewEditClosedCallback viewEditClosedCallback = null;

	public ICFFormClosedCallback getViewEditClosedCallback() {
		if( viewEditClosedCallback == null ) {
			viewEditClosedCallback = new ViewEditClosedCallback();
		}
		return( viewEditClosedCallback );
	}

	class DeleteCallback implements ICFDeleteCallback {
		public DeleteCallback() {
		}
		@Override
		public void deleted( ICFLibAnyObj deletedObject ) {
			if( deletedObject != null ) {
				refreshMe();
			}
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected DeleteCallback deleteCallback = null;

	public ICFDeleteCallback getDeleteCallback() {
		if( deleteCallback == null ) {
			deleteCallback = new DeleteCallback();
		}
		return( deleteCallback );
	}


	public CFSecJavaFXSecUserListPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFLibAnyObj argContainer,
		ICFSecSecUserObj argFocus,
		ICFSecJavaFXSecUserPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		super();
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
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		javafxRefreshCallback = refreshCallback;
		javafxSortByChain = sortByChain;
		pageCallback = argPageCallback;
		dataTable = new TableView<ICFSecSecUserObj>();
		tableColumnSecUserId = new TableColumn<ICFSecSecUserObj,CFLibDbKeyHash256>( "Security User Id" );
		tableColumnSecUserId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecUserObj, CFLibDbKeyHash256> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecUserId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecUserId.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,CFLibDbKeyHash256>,TableCell<ICFSecSecUserObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecUserObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecUserObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecUserId );
		tableColumnLoginId = new TableColumn<ICFSecSecUserObj,String>( "Login Id" );
		tableColumnLoginId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecUserObj, String> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredLoginId();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLoginId.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,String>,TableCell<ICFSecSecUserObj,String>>() {
			@Override public TableCell<ICFSecSecUserObj,String> call(
				TableColumn<ICFSecSecUserObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLoginId );
		tableColumnEMailAddress = new TableColumn<ICFSecSecUserObj,String>( "EMail Address" );
		tableColumnEMailAddress.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecUserObj, String> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredEMailAddress();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnEMailAddress.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,String>,TableCell<ICFSecSecUserObj,String>>() {
			@Override public TableCell<ICFSecSecUserObj,String> call(
				TableColumn<ICFSecSecUserObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnEMailAddress );
		tableColumnEMailConfirmUuid6 = new TableColumn<ICFSecSecUserObj,CFLibUuid6>( "EMail Confirm UUID6" );
		tableColumnEMailConfirmUuid6.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,CFLibUuid6>,ObservableValue<CFLibUuid6> >() {
			public ObservableValue<CFLibUuid6> call( CellDataFeatures<ICFSecSecUserObj, CFLibUuid6> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibUuid6 value = obj.getOptionalEMailConfirmUuid6();
					ReadOnlyObjectWrapper<CFLibUuid6> observable = new ReadOnlyObjectWrapper<CFLibUuid6>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnEMailConfirmUuid6.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,CFLibUuid6>,TableCell<ICFSecSecUserObj,CFLibUuid6>>() {
			@Override public TableCell<ICFSecSecUserObj,CFLibUuid6> call(
				TableColumn<ICFSecSecUserObj,CFLibUuid6> arg)
			{
				return new CFUuid6TableCell<ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnEMailConfirmUuid6 );
		tableColumnPasswordHash = new TableColumn<ICFSecSecUserObj,String>( "Password Hash" );
		tableColumnPasswordHash.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecUserObj, String> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredPasswordHash();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnPasswordHash.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,String>,TableCell<ICFSecSecUserObj,String>>() {
			@Override public TableCell<ICFSecSecUserObj,String> call(
				TableColumn<ICFSecSecUserObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPasswordHash );
		tableColumnPasswordResetUuid6 = new TableColumn<ICFSecSecUserObj,CFLibUuid6>( "Password Reset UUID6" );
		tableColumnPasswordResetUuid6.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,CFLibUuid6>,ObservableValue<CFLibUuid6> >() {
			public ObservableValue<CFLibUuid6> call( CellDataFeatures<ICFSecSecUserObj, CFLibUuid6> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibUuid6 value = obj.getOptionalPasswordResetUuid6();
					ReadOnlyObjectWrapper<CFLibUuid6> observable = new ReadOnlyObjectWrapper<CFLibUuid6>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnPasswordResetUuid6.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,CFLibUuid6>,TableCell<ICFSecSecUserObj,CFLibUuid6>>() {
			@Override public TableCell<ICFSecSecUserObj,CFLibUuid6> call(
				TableColumn<ICFSecSecUserObj,CFLibUuid6> arg)
			{
				return new CFUuid6TableCell<ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPasswordResetUuid6 );
		tableColumnLookupDefDev = new TableColumn<ICFSecSecUserObj, ICFSecSecDeviceObj>( "Default Security Device" );
		tableColumnLookupDefDev.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserObj,ICFSecSecDeviceObj>,ObservableValue<ICFSecSecDeviceObj> >() {
			public ObservableValue<ICFSecSecDeviceObj> call( CellDataFeatures<ICFSecSecUserObj, ICFSecSecDeviceObj> p ) {
				ICFSecSecUserObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecSecDeviceObj ref = obj.getOptionalLookupDefDev();
					ReadOnlyObjectWrapper<ICFSecSecDeviceObj> observable = new ReadOnlyObjectWrapper<ICFSecSecDeviceObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupDefDev.setCellFactory( new Callback<TableColumn<ICFSecSecUserObj,ICFSecSecDeviceObj>,TableCell<ICFSecSecUserObj,ICFSecSecDeviceObj>>() {
			@Override public TableCell<ICFSecSecUserObj,ICFSecSecDeviceObj> call(
				TableColumn<ICFSecSecUserObj,ICFSecSecDeviceObj> arg)
			{
				return new CFReferenceTableCell<ICFSecSecUserObj,ICFSecSecDeviceObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefDev );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecUserObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecUserObj> observable,
					ICFSecSecUserObj oldValue,
					ICFSecSecUserObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfSecUser != null ) {
			dataTable.setItems( observableListOfSecUser );
		}
		adjustListButtons();
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

	public void setPaneMode( CFPane.PaneMode value ) {
		super.setPaneMode( value );
		adjustListButtons();
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
		adjustListButtons();
	}

	public ICFSecSecUserObj getJavaFXFocusAsSecUser() {
		return( (ICFSecSecUserObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecUser( ICFSecSecUserObj value ) {
		setJavaFXFocus( value );
	}

	public class SecUserByQualNameComparator
	implements Comparator<ICFSecSecUserObj>
	{
		public SecUserByQualNameComparator() {
		}

		public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				String lhsValue = lhs.getObjQualifiedName();
				String rhsValue = rhs.getObjQualifiedName();
				if( lhsValue == null ) {
					if( rhsValue == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhsValue == null ) {
					return( 1 );
				}
				else {
					return( lhsValue.compareTo( rhsValue ) );
				}
			}
		}
	}

	protected SecUserByQualNameComparator compareSecUserByQualName = new SecUserByQualNameComparator();

	public Collection<ICFSecSecUserObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecUserObj> value ) {
		// Use page data instead
	}

	protected class CompareCFButtonByText
	implements Comparator<CFButton>
	{
		public CompareCFButtonByText() {
		}

		@Override public int compare( CFButton lhs, CFButton rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				int retval = lhs.getText().compareTo( rhs.getText() );
				return( retval );
			}
		}
	}

	public CFHBox getPanelHBoxMenu() {
		if( hboxMenu == null ) {
			hboxMenu = new CFHBox( 10 );
			buttonRefresh = new CFButton();
			buttonRefresh.setMinWidth( 200 );
			buttonRefresh.setText( "Refresh" );
			buttonRefresh.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						refreshMe();
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonRefresh );

			buttonMoreData = new CFButton();
			buttonMoreData.setMinWidth( 200 );
			buttonMoreData.setText( "MoreData" );
			buttonMoreData.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSecUserObj lastObj = null;
						if( ( observableListOfSecUser != null ) && ( observableListOfSecUser.size() > 0 ) ) {
							lastObj = observableListOfSecUser.get( observableListOfSecUser.size() - 1 );
						}
						List<ICFSecSecUserObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecUserId() );
						}
						else {
							page = pageCallback.pageData( null );
						}
						Iterator<ICFSecSecUserObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecUser.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecUser.sort( compareSecUserByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecUser );
							// Hack from stackoverflow to fix JavaFX TableView refresh issue
							((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
							((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
						}
						adjustListButtons();
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonMoreData );

			buttonAddSecUser = new CFButton();
			buttonAddSecUser.setMinWidth( 200 );
			buttonAddSecUser.setText( "Add Security User" );
			buttonAddSecUser.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						ICFSecSecUserObj obj = (ICFSecSecUserObj)schemaObj.getSecUserTableObj().newInstance();
						ICFSecSecUserEditObj edit = (ICFSecSecUserEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						CFBorderPane frame = javafxSchema.getSecUserFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFSecJavaFXSecUserPaneCommon jpanelCommon = (ICFSecJavaFXSecUserPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddSecUser );
			buttonViewSelected = new CFButton();
			buttonViewSelected.setMinWidth( 200 );
			buttonViewSelected.setText( "View Selected" );
			buttonViewSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFSecSecUserObj selectedInstance = getJavaFXFocusAsSecUser();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecUser.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecUserFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecUserPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecUserObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonViewSelected );

			buttonEditSelected = new CFButton();
			buttonEditSelected.setMinWidth( 200 );
			buttonEditSelected.setText( "Edit Selected" );
			buttonEditSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFSecSecUserObj selectedInstance = getJavaFXFocusAsSecUser();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecUser.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecUserFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecUserPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecUserObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonEditSelected );

			buttonDeleteSelected = new CFButton();
			buttonDeleteSelected.setMinWidth( 200 );
			buttonDeleteSelected.setText( "Delete Selected" );
			buttonDeleteSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFSecSecUserObj selectedInstance = getJavaFXFocusAsSecUser();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecUser.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecUserFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFSecJavaFXSecUserPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecUserObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonDeleteSelected );

		}
		return( hboxMenu );
	}

	public ICFLibAnyObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFLibAnyObj value ) {
		javafxContainer = value;
	}

	public void refreshMe() {
		final String S_ProcName = "refreshMe";
		observableListOfSecUser = FXCollections.observableArrayList();
		if( javafxContainer != null ) {
			List<ICFSecSecUserObj> page = pageCallback.pageData( null );
			Iterator<ICFSecSecUserObj> iter = page.iterator();
			while( iter.hasNext() ) {
				observableListOfSecUser.add( iter.next() );
			}
			if( page.size() < 25 ) {
				observableListOfSecUser.sort( compareSecUserByQualName );
				endOfData = true;
			}
			else {
				endOfData = false;
			}
		}
		else {
			endOfData = true;
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecUser );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
		adjustListButtons();
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean inEditState;
		boolean allowAdds;
		boolean inAddMode = false;
		ICFSecSecUserObj selectedObj = getJavaFXFocusAsSecUser();
		CFPane.PaneMode mode = getPaneMode();
		if( mode == CFPane.PaneMode.Edit ) {
			inEditState = true;
			allowAdds = false;
		}
		else {
			inEditState = false;
			if( getJavaFXContainer() != null ) {
				if( getLeft() != null ) {
					allowAdds = false;
					inAddMode = true;
				}
				else {
					allowAdds = true;
				}
			}
			else {
				allowAdds = false;
			}
		}
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			if( ( ! inAddMode ) && ( ! inEditState ) ) {
				enableState = true;
			}
			else {
				enableState = false;
			}
		}

		if( buttonRefresh != null ) {
			buttonRefresh.setDisable( false );
		}
		if( buttonMoreData != null ) {
			buttonMoreData.setDisable( endOfData );
		}
		if( buttonViewSelected != null ) {
			buttonViewSelected.setDisable( ! enableState );
		}
		if( buttonEditSelected != null ) {
			if( inEditState ) {
				buttonEditSelected.setDisable( true );
			}
			else {
				buttonEditSelected.setDisable( ! enableState );
			}
		}
		if( buttonDeleteSelected != null ) {
			buttonDeleteSelected.setDisable( ! enableState );
		}
		if( buttonAddSecUser != null ) {
			buttonAddSecUser.setDisable( ! allowAdds );
		}

	}
}
