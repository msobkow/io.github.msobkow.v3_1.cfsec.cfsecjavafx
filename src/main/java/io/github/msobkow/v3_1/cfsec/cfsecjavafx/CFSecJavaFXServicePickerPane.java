// Description: Java 25 JavaFX Picker of Obj Pane implementation for Service.

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
 *	CFSecJavaFXServicePickerPane JavaFX Pick Obj Pane implementation
 *	for Service.
 */
public class CFSecJavaFXServicePickerPane
extends CFBorderPane
implements ICFSecJavaFXServicePaneList
{
	public static String S_FormName = "Choose Service";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXServicePageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecServiceObj> observableListOfService = null;
	protected TableColumn<ICFSecServiceObj, CFLibDbKeyHash256> tableColumnServiceId = null;
	protected TableColumn<ICFSecServiceObj, Short> tableColumnHostPort = null;
	protected TableColumn<ICFSecServiceObj, ICFSecServiceTypeObj> tableColumnParentServiceType = null;
	protected TableView<ICFSecServiceObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXServiceChosen invokeWhenChosen = null;
	protected ICFSecHostNodeObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXServicePickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecServiceObj argFocus,
		ICFSecHostNodeObj argContainer,
		ICFSecJavaFXServicePageCallback argPageCallback,
		ICFSecJavaFXServiceChosen whenChosen )
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
		dataTable = new TableView<ICFSecServiceObj>();
		tableColumnServiceId = new TableColumn<ICFSecServiceObj,CFLibDbKeyHash256>( "Service Id" );
		tableColumnServiceId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecServiceObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecServiceObj, CFLibDbKeyHash256> p ) {
				ICFSecServiceObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredServiceId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnServiceId.setCellFactory( new Callback<TableColumn<ICFSecServiceObj,CFLibDbKeyHash256>,TableCell<ICFSecServiceObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecServiceObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecServiceObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecServiceObj>();
			}
		});
		dataTable.getColumns().add( tableColumnServiceId );
		tableColumnHostPort = new TableColumn<ICFSecServiceObj,Short>( "Host Port" );
		tableColumnHostPort.setCellValueFactory( new Callback<CellDataFeatures<ICFSecServiceObj,Short>,ObservableValue<Short> >() {
			public ObservableValue<Short> call( CellDataFeatures<ICFSecServiceObj, Short> p ) {
				ICFSecServiceObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					short value = obj.getRequiredHostPort();
					Short wrapped = Short.valueOf( value );
					ReadOnlyObjectWrapper<Short> observable = new ReadOnlyObjectWrapper<Short>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnHostPort.setCellFactory( new Callback<TableColumn<ICFSecServiceObj,Short>,TableCell<ICFSecServiceObj,Short>>() {
			@Override public TableCell<ICFSecServiceObj,Short> call(
				TableColumn<ICFSecServiceObj,Short> arg)
			{
				return new CFInt16TableCell<ICFSecServiceObj>();
			}
		});
		dataTable.getColumns().add( tableColumnHostPort );
		tableColumnParentServiceType = new TableColumn<ICFSecServiceObj, ICFSecServiceTypeObj>( "Service Type" );
		tableColumnParentServiceType.setCellValueFactory( new Callback<CellDataFeatures<ICFSecServiceObj,ICFSecServiceTypeObj>,ObservableValue<ICFSecServiceTypeObj> >() {
			public ObservableValue<ICFSecServiceTypeObj> call( CellDataFeatures<ICFSecServiceObj, ICFSecServiceTypeObj> p ) {
				ICFSecServiceObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecServiceTypeObj ref = obj.getOptionalParentServiceType();
					ReadOnlyObjectWrapper<ICFSecServiceTypeObj> observable = new ReadOnlyObjectWrapper<ICFSecServiceTypeObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnParentServiceType.setCellFactory( new Callback<TableColumn<ICFSecServiceObj,ICFSecServiceTypeObj>,TableCell<ICFSecServiceObj,ICFSecServiceTypeObj>>() {
			@Override public TableCell<ICFSecServiceObj,ICFSecServiceTypeObj> call(
				TableColumn<ICFSecServiceObj,ICFSecServiceTypeObj> arg)
			{
				return new CFReferenceTableCell<ICFSecServiceObj,ICFSecServiceTypeObj>();
			}
		});
		dataTable.getColumns().add( tableColumnParentServiceType );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecServiceObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecServiceObj> observable,
					ICFSecServiceObj oldValue,
					ICFSecServiceObj newValue )
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
						observableListOfService = FXCollections.observableArrayList();
						List<ICFSecServiceObj> page = pageCallback.pageData( null );
						Iterator<ICFSecServiceObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfService.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfService.sort( compareServiceByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfService );
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
						ICFSecServiceObj lastObj = null;
						if( ( observableListOfService != null ) && ( observableListOfService.size() > 0 ) ) {
							lastObj = observableListOfService.get( observableListOfService.size() - 1 );
						}
						List<ICFSecServiceObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredServiceId() );
						}
						else {
							page = pageCallback.pageData( null );
						}
						Iterator<ICFSecServiceObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfService.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfService.sort( compareServiceByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfService );
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
					invokeWhenChosen.choseService( null );
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
					ICFSecServiceObj selectedInstance = getJavaFXFocusAsService();
					invokeWhenChosen.choseService( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecServiceObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecServiceObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecServiceObj getJavaFXFocusAsService() {
		return( (ICFSecServiceObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsService( ICFSecServiceObj value ) {
		setJavaFXFocus( value );
	}

	public class ServiceByQualNameComparator
	implements Comparator<ICFSecServiceObj>
	{
		public ServiceByQualNameComparator() {
		}

		public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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

	protected ServiceByQualNameComparator compareServiceByQualName = new ServiceByQualNameComparator();

	public Collection<ICFSecServiceObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecServiceObj> value ) {
		// Use page data instead
	}

	public ICFSecHostNodeObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecHostNodeObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecServiceObj selectedObj = getJavaFXFocusAsService();
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

