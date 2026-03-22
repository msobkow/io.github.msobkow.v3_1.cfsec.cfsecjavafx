// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecUserPWHistory.

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
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecUserPWHistoryPickerPane JavaFX Pick Obj Pane implementation
 *	for SecUserPWHistory.
 */
public class CFSecJavaFXSecUserPWHistoryPickerPane
extends CFBorderPane
implements ICFSecJavaFXSecUserPWHistoryPaneList
{
	public static String S_FormName = "Choose Security User Password History";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecUserPWHistoryObj> javafxDataCollection = null;
	protected ObservableList<ICFSecSecUserPWHistoryObj> observableListOfSecUserPWHistory = null;
	protected TableColumn<ICFSecSecUserPWHistoryObj, CFLibDbKeyHash256> tableColumnSecUserId = null;
	protected TableColumn<ICFSecSecUserPWHistoryObj, LocalDateTime> tableColumnPWSetStamp = null;
	protected TableColumn<ICFSecSecUserPWHistoryObj, LocalDateTime> tableColumnPWReplacedStamp = null;
	protected TableColumn<ICFSecSecUserPWHistoryObj, String> tableColumnPasswordHash = null;
	protected TableView<ICFSecSecUserPWHistoryObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecUserPWHistoryChosen invokeWhenChosen = null;
	protected ICFLibAnyObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecUserPWHistoryPickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecUserPWHistoryObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecUserPWHistoryObj> argDataCollection,
		ICFSecJavaFXSecUserPWHistoryChosen whenChosen )
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
		dataTable = new TableView<ICFSecSecUserPWHistoryObj>();
		tableColumnSecUserId = new TableColumn<ICFSecSecUserPWHistoryObj,CFLibDbKeyHash256>( "Security User Id" );
		tableColumnSecUserId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserPWHistoryObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecUserPWHistoryObj, CFLibDbKeyHash256> p ) {
				ICFSecSecUserPWHistoryObj obj = p.getValue();
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
		tableColumnSecUserId.setCellFactory( new Callback<TableColumn<ICFSecSecUserPWHistoryObj,CFLibDbKeyHash256>,TableCell<ICFSecSecUserPWHistoryObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecUserPWHistoryObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecUserPWHistoryObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecUserPWHistoryObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecUserId );
		tableColumnPWSetStamp = new TableColumn<ICFSecSecUserPWHistoryObj,LocalDateTime>( "Password set at" );
		tableColumnPWSetStamp.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserPWHistoryObj,LocalDateTime>,ObservableValue<LocalDateTime> >() {
			public ObservableValue<LocalDateTime> call( CellDataFeatures<ICFSecSecUserPWHistoryObj, LocalDateTime> p ) {
				ICFSecSecUserPWHistoryObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					LocalDateTime value = obj.getRequiredPWSetStamp();
					ReadOnlyObjectWrapper<LocalDateTime> observable = new ReadOnlyObjectWrapper<LocalDateTime>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnPWSetStamp.setCellFactory( new Callback<TableColumn<ICFSecSecUserPWHistoryObj,LocalDateTime>,TableCell<ICFSecSecUserPWHistoryObj,LocalDateTime>>() {
			@Override public TableCell<ICFSecSecUserPWHistoryObj,LocalDateTime> call(
				TableColumn<ICFSecSecUserPWHistoryObj,LocalDateTime> arg)
			{
				return new CFTimestampTableCell<ICFSecSecUserPWHistoryObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPWSetStamp );
		tableColumnPWReplacedStamp = new TableColumn<ICFSecSecUserPWHistoryObj,LocalDateTime>( "Password set at" );
		tableColumnPWReplacedStamp.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserPWHistoryObj,LocalDateTime>,ObservableValue<LocalDateTime> >() {
			public ObservableValue<LocalDateTime> call( CellDataFeatures<ICFSecSecUserPWHistoryObj, LocalDateTime> p ) {
				ICFSecSecUserPWHistoryObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					LocalDateTime value = obj.getRequiredPWReplacedStamp();
					ReadOnlyObjectWrapper<LocalDateTime> observable = new ReadOnlyObjectWrapper<LocalDateTime>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnPWReplacedStamp.setCellFactory( new Callback<TableColumn<ICFSecSecUserPWHistoryObj,LocalDateTime>,TableCell<ICFSecSecUserPWHistoryObj,LocalDateTime>>() {
			@Override public TableCell<ICFSecSecUserPWHistoryObj,LocalDateTime> call(
				TableColumn<ICFSecSecUserPWHistoryObj,LocalDateTime> arg)
			{
				return new CFTimestampTableCell<ICFSecSecUserPWHistoryObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPWReplacedStamp );
		tableColumnPasswordHash = new TableColumn<ICFSecSecUserPWHistoryObj,String>( "Password Hash" );
		tableColumnPasswordHash.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecUserPWHistoryObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecUserPWHistoryObj, String> p ) {
				ICFSecSecUserPWHistoryObj obj = p.getValue();
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
		tableColumnPasswordHash.setCellFactory( new Callback<TableColumn<ICFSecSecUserPWHistoryObj,String>,TableCell<ICFSecSecUserPWHistoryObj,String>>() {
			@Override public TableCell<ICFSecSecUserPWHistoryObj,String> call(
				TableColumn<ICFSecSecUserPWHistoryObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecUserPWHistoryObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPasswordHash );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecUserPWHistoryObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecUserPWHistoryObj> observable,
					ICFSecSecUserPWHistoryObj oldValue,
					ICFSecSecUserPWHistoryObj newValue )
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
					invokeWhenChosen.choseSecUserPWHistory( null );
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
					ICFSecSecUserPWHistoryObj selectedInstance = getJavaFXFocusAsSecUserPWHistory();
					invokeWhenChosen.choseSecUserPWHistory( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecSecUserPWHistoryObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecUserPWHistoryObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecUserPWHistoryObj getJavaFXFocusAsSecUserPWHistory() {
		return( (ICFSecSecUserPWHistoryObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecUserPWHistory( ICFSecSecUserPWHistoryObj value ) {
		setJavaFXFocus( value );
	}

	public class SecUserPWHistoryByQualNameComparator
	implements Comparator<ICFSecSecUserPWHistoryObj>
	{
		public SecUserPWHistoryByQualNameComparator() {
		}

		public int compare( ICFSecSecUserPWHistoryObj lhs, ICFSecSecUserPWHistoryObj rhs ) {
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

	protected SecUserPWHistoryByQualNameComparator compareSecUserPWHistoryByQualName = new SecUserPWHistoryByQualNameComparator();

	public Collection<ICFSecSecUserPWHistoryObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecUserPWHistoryObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfSecUserPWHistory = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecSecUserPWHistoryObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfSecUserPWHistory.add( iter.next() );
				}
				observableListOfSecUserPWHistory.sort( compareSecUserPWHistoryByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecUserPWHistory );
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
		ICFSecSecUserPWHistoryObj selectedObj = getJavaFXFocusAsSecUserPWHistory();
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

