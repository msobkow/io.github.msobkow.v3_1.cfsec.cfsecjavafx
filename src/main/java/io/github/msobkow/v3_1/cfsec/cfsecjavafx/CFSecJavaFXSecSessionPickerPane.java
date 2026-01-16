// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecSession.

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
import java.util.List;
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
 *	CFSecJavaFXSecSessionPickerPane JavaFX Pick Obj Pane implementation
 *	for SecSession.
 */
public class CFSecJavaFXSecSessionPickerPane
extends CFBorderPane
implements ICFSecJavaFXSecSessionPaneList
{
	public static String S_FormName = "Choose Security Session";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXSecSessionPageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecSessionObj> observableListOfSecSession = null;
	protected TableColumn<ICFSecSecSessionObj, CFLibDbKeyHash256> tableColumnSecSessionId = null;
	protected TableColumn<ICFSecSecSessionObj, String> tableColumnSecDevName = null;
	protected TableColumn<ICFSecSecSessionObj, LocalDateTime> tableColumnStart = null;
	protected TableColumn<ICFSecSecSessionObj, LocalDateTime> tableColumnFinish = null;
	protected TableColumn<ICFSecSecSessionObj, ICFSecSecUserObj> tableColumnParentSecProxy = null;
	protected TableView<ICFSecSecSessionObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecSessionChosen invokeWhenChosen = null;
	protected ICFSecSecUserObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecSessionPickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecSessionObj argFocus,
		ICFSecSecUserObj argContainer,
		ICFSecJavaFXSecSessionPageCallback argPageCallback,
		ICFSecJavaFXSecSessionChosen whenChosen )
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
		if( whenChosen == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				6,
				"whenChosen" );
		}
		invokeWhenChosen = whenChosen;
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		pageCallback = argPageCallback;
		dataTable = new TableView<ICFSecSecSessionObj>();
		tableColumnSecSessionId = new TableColumn<ICFSecSecSessionObj,CFLibDbKeyHash256>( "Security Session Id" );
		tableColumnSecSessionId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSessionObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecSessionObj, CFLibDbKeyHash256> p ) {
				ICFSecSecSessionObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecSessionId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecSessionId.setCellFactory( new Callback<TableColumn<ICFSecSecSessionObj,CFLibDbKeyHash256>,TableCell<ICFSecSecSessionObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecSessionObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecSessionObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecSessionObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecSessionId );
		tableColumnSecDevName = new TableColumn<ICFSecSecSessionObj,String>( "Sesion Device Name" );
		tableColumnSecDevName.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSessionObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecSessionObj, String> p ) {
				ICFSecSecSessionObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalSecDevName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecDevName.setCellFactory( new Callback<TableColumn<ICFSecSecSessionObj,String>,TableCell<ICFSecSecSessionObj,String>>() {
			@Override public TableCell<ICFSecSecSessionObj,String> call(
				TableColumn<ICFSecSecSessionObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecSessionObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecDevName );
		tableColumnStart = new TableColumn<ICFSecSecSessionObj,LocalDateTime>( "Start" );
		tableColumnStart.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSessionObj,LocalDateTime>,ObservableValue<LocalDateTime> >() {
			public ObservableValue<LocalDateTime> call( CellDataFeatures<ICFSecSecSessionObj, LocalDateTime> p ) {
				ICFSecSecSessionObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					LocalDateTime value = obj.getRequiredStart();
					ReadOnlyObjectWrapper<LocalDateTime> observable = new ReadOnlyObjectWrapper<LocalDateTime>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnStart.setCellFactory( new Callback<TableColumn<ICFSecSecSessionObj,LocalDateTime>,TableCell<ICFSecSecSessionObj,LocalDateTime>>() {
			@Override public TableCell<ICFSecSecSessionObj,LocalDateTime> call(
				TableColumn<ICFSecSecSessionObj,LocalDateTime> arg)
			{
				return new CFTimestampTableCell<ICFSecSecSessionObj>();
			}
		});
		dataTable.getColumns().add( tableColumnStart );
		tableColumnFinish = new TableColumn<ICFSecSecSessionObj,LocalDateTime>( "Finish" );
		tableColumnFinish.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSessionObj,LocalDateTime>,ObservableValue<LocalDateTime> >() {
			public ObservableValue<LocalDateTime> call( CellDataFeatures<ICFSecSecSessionObj, LocalDateTime> p ) {
				ICFSecSecSessionObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					LocalDateTime value = obj.getOptionalFinish();
					ReadOnlyObjectWrapper<LocalDateTime> observable = new ReadOnlyObjectWrapper<LocalDateTime>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnFinish.setCellFactory( new Callback<TableColumn<ICFSecSecSessionObj,LocalDateTime>,TableCell<ICFSecSecSessionObj,LocalDateTime>>() {
			@Override public TableCell<ICFSecSecSessionObj,LocalDateTime> call(
				TableColumn<ICFSecSecSessionObj,LocalDateTime> arg)
			{
				return new CFTimestampTableCell<ICFSecSecSessionObj>();
			}
		});
		dataTable.getColumns().add( tableColumnFinish );
		tableColumnParentSecProxy = new TableColumn<ICFSecSecSessionObj, ICFSecSecUserObj>( "Security Proxy User" );
		tableColumnParentSecProxy.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSessionObj,ICFSecSecUserObj>,ObservableValue<ICFSecSecUserObj> >() {
			public ObservableValue<ICFSecSecUserObj> call( CellDataFeatures<ICFSecSecSessionObj, ICFSecSecUserObj> p ) {
				ICFSecSecSessionObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecSecUserObj ref = obj.getRequiredParentSecProxy();
					ReadOnlyObjectWrapper<ICFSecSecUserObj> observable = new ReadOnlyObjectWrapper<ICFSecSecUserObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnParentSecProxy.setCellFactory( new Callback<TableColumn<ICFSecSecSessionObj,ICFSecSecUserObj>,TableCell<ICFSecSecSessionObj,ICFSecSecUserObj>>() {
			@Override public TableCell<ICFSecSecSessionObj,ICFSecSecUserObj> call(
				TableColumn<ICFSecSecSessionObj,ICFSecSecUserObj> arg)
			{
				return new CFReferenceTableCell<ICFSecSecSessionObj,ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnParentSecProxy );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecSessionObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecSessionObj> observable,
					ICFSecSecSessionObj oldValue,
					ICFSecSecSessionObj newValue )
				{
					setJavaFXFocus( newValue );
					if( buttonChooseSelected != null ) {
						if( newValue != null ) {
							buttonChooseSelected.setDisable( false );
						}
						else {
							buttonChooseSelected.setDisable( true );
						}
					}
				}
			});
		hboxMenu = new CFHBox( 10 );
			buttonRefresh = new CFButton();
			buttonRefresh.setMinWidth( 200 );
			buttonRefresh.setText( "Refresh" );
			buttonRefresh.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						observableListOfSecSession = FXCollections.observableArrayList();
						List<ICFSecSecSessionObj> page = pageCallback.pageData( null );
						Iterator<ICFSecSecSessionObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecSession.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecSession.sort( compareSecSessionByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecSession );
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
			hboxMenu.getChildren().add( buttonRefresh );

			buttonMoreData = new CFButton();
			buttonMoreData.setMinWidth( 200 );
			buttonMoreData.setText( "MoreData" );
			buttonMoreData.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSecSessionObj lastObj = null;
						if( ( observableListOfSecSession != null ) && ( observableListOfSecSession.size() > 0 ) ) {
							lastObj = observableListOfSecSession.get( observableListOfSecSession.size() - 1 );
						}
						List<ICFSecSecSessionObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecSessionId() );
						}
						else {
							page = pageCallback.pageData( null );
						}
						Iterator<ICFSecSecSessionObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecSession.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecSession.sort( compareSecSessionByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecSession );
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

		buttonCancel = new CFButton();
		buttonCancel.setMinWidth( 200 );
		buttonCancel.setText( "Cancel" );
		buttonCancel.setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				final String S_ProcName = "handle";
				try {
					cfFormManager.closeCurrentForm();
				}
				catch( Throwable t ) {
					CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
				}
			}
		});
		hboxMenu.getChildren().add( buttonCancel );
		buttonChooseNone = new CFButton();
		buttonChooseNone.setMinWidth( 200 );
		buttonChooseNone.setText( "ChooseNone" );
		buttonChooseNone.setOnAction( new EventHandler<ActionEvent>() {
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
					invokeWhenChosen.choseSecSession( null );
					cfFormManager.closeCurrentForm();
				}
				catch( Throwable t ) {
					CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
				}
			}
		});
		hboxMenu.getChildren().add( buttonChooseNone );
		buttonChooseSelected = new CFButton();
		buttonChooseSelected.setMinWidth( 200 );
		buttonChooseSelected.setText( "ChooseSelected" );
		buttonChooseSelected.setOnAction( new EventHandler<ActionEvent>() {
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
					ICFSecSecSessionObj selectedInstance = getJavaFXFocusAsSecSession();
					invokeWhenChosen.choseSecSession( selectedInstance );
					cfFormManager.closeCurrentForm();
				}
				catch( Throwable t ) {
					CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
				}
			}
		});
		hboxMenu.getChildren().add( buttonChooseSelected );
		if( argFocus != null ) {
			dataTable.getSelectionModel().select( argFocus );
		}

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( hboxMenu );

		setTop( scrollMenu );
		setCenter( dataTable );
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
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecSessionObj getJavaFXFocusAsSecSession() {
		return( (ICFSecSecSessionObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSession( ICFSecSecSessionObj value ) {
		setJavaFXFocus( value );
	}

	public class SecSessionByQualNameComparator
	implements Comparator<ICFSecSecSessionObj>
	{
		public SecSessionByQualNameComparator() {
		}

		public int compare( ICFSecSecSessionObj lhs, ICFSecSecSessionObj rhs ) {
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

	protected SecSessionByQualNameComparator compareSecSessionByQualName = new SecSessionByQualNameComparator();

	public Collection<ICFSecSecSessionObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecSessionObj> value ) {
		// Use page data instead
	}

	public ICFSecSecUserObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecSecUserObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecSecSessionObj selectedObj = getJavaFXFocusAsSecSession();
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			enableState = true;
		}

		if( buttonRefresh != null ) {
			buttonRefresh.setDisable( false );
		}
		if( buttonMoreData != null ) {
			buttonMoreData.setDisable( endOfData );
		}
		if( buttonChooseSelected != null ) {
			buttonChooseSelected.setDisable( ! enableState );
		}
		if( buttonChooseNone != null ) {
			buttonChooseNone.setDisable( false );
		}
		if( buttonCancel != null ) {
			buttonCancel.setDisable( false );
		}

	}
}

