// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecDevice.

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
 *	CFSecJavaFXSecDevicePickerPane JavaFX Pick Obj Pane implementation
 *	for SecDevice.
 */
public class CFSecJavaFXSecDevicePickerPane
extends CFBorderPane
implements ICFSecJavaFXSecDevicePaneList
{
	public static String S_FormName = "Choose Security Device";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXSecDevicePageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecDeviceObj> observableListOfSecDevice = null;
	protected TableColumn<ICFSecSecDeviceObj, String> tableColumnDevName = null;
	protected TableColumn<ICFSecSecDeviceObj, String> tableColumnPubKey = null;
	protected TableView<ICFSecSecDeviceObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecDeviceChosen invokeWhenChosen = null;
	protected ICFSecSecUserObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecDevicePickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecDeviceObj argFocus,
		ICFSecSecUserObj argContainer,
		ICFSecJavaFXSecDevicePageCallback argPageCallback,
		ICFSecJavaFXSecDeviceChosen whenChosen )
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
		dataTable = new TableView<ICFSecSecDeviceObj>();
		tableColumnDevName = new TableColumn<ICFSecSecDeviceObj,String>( "Device Name" );
		tableColumnDevName.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecDeviceObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecDeviceObj, String> p ) {
				ICFSecSecDeviceObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredDevName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDevName.setCellFactory( new Callback<TableColumn<ICFSecSecDeviceObj,String>,TableCell<ICFSecSecDeviceObj,String>>() {
			@Override public TableCell<ICFSecSecDeviceObj,String> call(
				TableColumn<ICFSecSecDeviceObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecDeviceObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDevName );
		tableColumnPubKey = new TableColumn<ICFSecSecDeviceObj,String>( "Device Public Key" );
		tableColumnPubKey.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecDeviceObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecDeviceObj, String> p ) {
				ICFSecSecDeviceObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalPubKey();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnPubKey.setCellFactory( new Callback<TableColumn<ICFSecSecDeviceObj,String>,TableCell<ICFSecSecDeviceObj,String>>() {
			@Override public TableCell<ICFSecSecDeviceObj,String> call(
				TableColumn<ICFSecSecDeviceObj,String> arg)
			{
				return new CFTextTableCell<ICFSecSecDeviceObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPubKey );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecDeviceObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecDeviceObj> observable,
					ICFSecSecDeviceObj oldValue,
					ICFSecSecDeviceObj newValue )
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
						observableListOfSecDevice = FXCollections.observableArrayList();
						List<ICFSecSecDeviceObj> page = pageCallback.pageData( null,
							null );
						Iterator<ICFSecSecDeviceObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecDevice.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecDevice.sort( compareSecDeviceByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecDevice );
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
						ICFSecSecDeviceObj lastObj = null;
						if( ( observableListOfSecDevice != null ) && ( observableListOfSecDevice.size() > 0 ) ) {
							lastObj = observableListOfSecDevice.get( observableListOfSecDevice.size() - 1 );
						}
						List<ICFSecSecDeviceObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecUserId(),
							lastObj.getRequiredDevName() );
						}
						else {
							page = pageCallback.pageData( null,
							null );
						}
						Iterator<ICFSecSecDeviceObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecDevice.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecDevice.sort( compareSecDeviceByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecDevice );
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
					invokeWhenChosen.choseSecDevice( null );
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
					ICFSecSecDeviceObj selectedInstance = getJavaFXFocusAsSecDevice();
					invokeWhenChosen.choseSecDevice( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecSecDeviceObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecDeviceObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecDeviceObj getJavaFXFocusAsSecDevice() {
		return( (ICFSecSecDeviceObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecDevice( ICFSecSecDeviceObj value ) {
		setJavaFXFocus( value );
	}

	public class SecDeviceByQualNameComparator
	implements Comparator<ICFSecSecDeviceObj>
	{
		public SecDeviceByQualNameComparator() {
		}

		public int compare( ICFSecSecDeviceObj lhs, ICFSecSecDeviceObj rhs ) {
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

	protected SecDeviceByQualNameComparator compareSecDeviceByQualName = new SecDeviceByQualNameComparator();

	public Collection<ICFSecSecDeviceObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecDeviceObj> value ) {
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
		ICFSecSecDeviceObj selectedObj = getJavaFXFocusAsSecDevice();
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

