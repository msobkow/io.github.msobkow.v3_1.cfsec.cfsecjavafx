// Description: Java 25 JavaFX Picker of Obj Pane implementation for ISOTZone.

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
 *	CFSecJavaFXISOTZonePickerPane JavaFX Pick Obj Pane implementation
 *	for ISOTZone.
 */
public class CFSecJavaFXISOTZonePickerPane
extends CFBorderPane
implements ICFSecJavaFXISOTZonePaneList
{
	public static String S_FormName = "Choose ISO Timezone";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecISOTZoneObj> javafxDataCollection = null;
	protected ObservableList<ICFSecISOTZoneObj> observableListOfISOTZone = null;
	protected TableColumn<ICFSecISOTZoneObj, Short> tableColumnISOTZoneId = null;
	protected TableColumn<ICFSecISOTZoneObj, String> tableColumnIso8601 = null;
	protected TableColumn<ICFSecISOTZoneObj, String> tableColumnTZName = null;
	protected TableColumn<ICFSecISOTZoneObj, Short> tableColumnTZHourOffset = null;
	protected TableColumn<ICFSecISOTZoneObj, Short> tableColumnTZMinOffset = null;
	protected TableColumn<ICFSecISOTZoneObj, String> tableColumnDescription = null;
	protected TableColumn<ICFSecISOTZoneObj, Boolean> tableColumnVisible = null;
	protected TableView<ICFSecISOTZoneObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXISOTZoneChosen invokeWhenChosen = null;
	protected ICFLibAnyObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXISOTZonePickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecISOTZoneObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecISOTZoneObj> argDataCollection,
		ICFSecJavaFXISOTZoneChosen whenChosen )
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
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFSecISOTZoneObj>();
		tableColumnISOTZoneId = new TableColumn<ICFSecISOTZoneObj,Short>( "ISO Timezone Id" );
		tableColumnISOTZoneId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,Short>,ObservableValue<Short> >() {
			public ObservableValue<Short> call( CellDataFeatures<ICFSecISOTZoneObj, Short> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					short value = obj.getRequiredISOTZoneId();
					Short wrapped = Short.valueOf( value );
					ReadOnlyObjectWrapper<Short> observable = new ReadOnlyObjectWrapper<Short>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnISOTZoneId.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,Short>,TableCell<ICFSecISOTZoneObj,Short>>() {
			@Override public TableCell<ICFSecISOTZoneObj,Short> call(
				TableColumn<ICFSecISOTZoneObj,Short> arg)
			{
				return new CFInt16TableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnISOTZoneId );
		tableColumnIso8601 = new TableColumn<ICFSecISOTZoneObj,String>( "ISO8601" );
		tableColumnIso8601.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecISOTZoneObj, String> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredIso8601();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnIso8601.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,String>,TableCell<ICFSecISOTZoneObj,String>>() {
			@Override public TableCell<ICFSecISOTZoneObj,String> call(
				TableColumn<ICFSecISOTZoneObj,String> arg)
			{
				return new CFStringTableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIso8601 );
		tableColumnTZName = new TableColumn<ICFSecISOTZoneObj,String>( "Timezone Name" );
		tableColumnTZName.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecISOTZoneObj, String> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredTZName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnTZName.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,String>,TableCell<ICFSecISOTZoneObj,String>>() {
			@Override public TableCell<ICFSecISOTZoneObj,String> call(
				TableColumn<ICFSecISOTZoneObj,String> arg)
			{
				return new CFStringTableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTZName );
		tableColumnTZHourOffset = new TableColumn<ICFSecISOTZoneObj,Short>( "Timezone Hour Offset" );
		tableColumnTZHourOffset.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,Short>,ObservableValue<Short> >() {
			public ObservableValue<Short> call( CellDataFeatures<ICFSecISOTZoneObj, Short> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					short value = obj.getRequiredTZHourOffset();
					Short wrapped = Short.valueOf( value );
					ReadOnlyObjectWrapper<Short> observable = new ReadOnlyObjectWrapper<Short>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnTZHourOffset.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,Short>,TableCell<ICFSecISOTZoneObj,Short>>() {
			@Override public TableCell<ICFSecISOTZoneObj,Short> call(
				TableColumn<ICFSecISOTZoneObj,Short> arg)
			{
				return new CFInt16TableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTZHourOffset );
		tableColumnTZMinOffset = new TableColumn<ICFSecISOTZoneObj,Short>( "Timezone Minute Offset" );
		tableColumnTZMinOffset.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,Short>,ObservableValue<Short> >() {
			public ObservableValue<Short> call( CellDataFeatures<ICFSecISOTZoneObj, Short> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					short value = obj.getRequiredTZMinOffset();
					Short wrapped = Short.valueOf( value );
					ReadOnlyObjectWrapper<Short> observable = new ReadOnlyObjectWrapper<Short>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnTZMinOffset.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,Short>,TableCell<ICFSecISOTZoneObj,Short>>() {
			@Override public TableCell<ICFSecISOTZoneObj,Short> call(
				TableColumn<ICFSecISOTZoneObj,Short> arg)
			{
				return new CFInt16TableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTZMinOffset );
		tableColumnDescription = new TableColumn<ICFSecISOTZoneObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecISOTZoneObj, String> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,String>,TableCell<ICFSecISOTZoneObj,String>>() {
			@Override public TableCell<ICFSecISOTZoneObj,String> call(
				TableColumn<ICFSecISOTZoneObj,String> arg)
			{
				return new CFStringTableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnVisible = new TableColumn<ICFSecISOTZoneObj,Boolean>( "Visible" );
		tableColumnVisible.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOTZoneObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFSecISOTZoneObj, Boolean> p ) {
				ICFSecISOTZoneObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredVisible();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnVisible.setCellFactory( new Callback<TableColumn<ICFSecISOTZoneObj,Boolean>,TableCell<ICFSecISOTZoneObj,Boolean>>() {
			@Override public TableCell<ICFSecISOTZoneObj,Boolean> call(
				TableColumn<ICFSecISOTZoneObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFSecISOTZoneObj>();
			}
		});
		dataTable.getColumns().add( tableColumnVisible );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecISOTZoneObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecISOTZoneObj> observable,
					ICFSecISOTZoneObj oldValue,
					ICFSecISOTZoneObj newValue )
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
					invokeWhenChosen.choseISOTZone( null );
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
					ICFSecISOTZoneObj selectedInstance = getJavaFXFocusAsISOTZone();
					invokeWhenChosen.choseISOTZone( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecISOTZoneObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecISOTZoneObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecISOTZoneObj getJavaFXFocusAsISOTZone() {
		return( (ICFSecISOTZoneObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsISOTZone( ICFSecISOTZoneObj value ) {
		setJavaFXFocus( value );
	}

	public class ISOTZoneByQualNameComparator
	implements Comparator<ICFSecISOTZoneObj>
	{
		public ISOTZoneByQualNameComparator() {
		}

		public int compare( ICFSecISOTZoneObj lhs, ICFSecISOTZoneObj rhs ) {
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

	protected ISOTZoneByQualNameComparator compareISOTZoneByQualName = new ISOTZoneByQualNameComparator();

	public Collection<ICFSecISOTZoneObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecISOTZoneObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfISOTZone = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecISOTZoneObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfISOTZone.add( iter.next() );
				}
				observableListOfISOTZone.sort( compareISOTZoneByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfISOTZone );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	public ICFLibAnyObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFLibAnyObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecISOTZoneObj selectedObj = getJavaFXFocusAsISOTZone();
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			enableState = true;
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

