// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecSysGrp.

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
 *	CFSecJavaFXSecSysGrpPickerPane JavaFX Pick Obj Pane implementation
 *	for SecSysGrp.
 */
public class CFSecJavaFXSecSysGrpPickerPane
extends CFBorderPane
implements ICFSecJavaFXSecSysGrpPaneList
{
	public static String S_FormName = "Choose System Security Group";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecSysGrpObj> javafxDataCollection = null;
	protected ObservableList<ICFSecSecSysGrpObj> observableListOfSecSysGrp = null;
	protected TableColumn<ICFSecSecSysGrpObj, CFLibDbKeyHash256> tableColumnSecSysGrpId = null;
	protected TableColumn<ICFSecSecSysGrpObj, String> tableColumnName = null;
	protected TableColumn<ICFSecSecSysGrpObj, ICFSecSchema.SecLevelEnum> tableColumnSecLevel = null;
	protected TableView<ICFSecSecSysGrpObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecSysGrpChosen invokeWhenChosen = null;
	protected ICFLibAnyObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecSysGrpPickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecSysGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecSysGrpObj> argDataCollection,
		ICFSecJavaFXSecSysGrpChosen whenChosen )
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
		dataTable = new TableView<ICFSecSecSysGrpObj>();
		tableColumnSecSysGrpId = new TableColumn<ICFSecSecSysGrpObj,CFLibDbKeyHash256>( "System Security Group Id" );
		tableColumnSecSysGrpId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSysGrpObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecSysGrpObj, CFLibDbKeyHash256> p ) {
				ICFSecSecSysGrpObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecSysGrpId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecSysGrpId.setCellFactory( new Callback<TableColumn<ICFSecSecSysGrpObj,CFLibDbKeyHash256>,TableCell<ICFSecSecSysGrpObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecSysGrpObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecSysGrpObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecSysGrpObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecSysGrpId );
		tableColumnName = new TableColumn<ICFSecSecSysGrpObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSysGrpObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecSysGrpObj, String> p ) {
				ICFSecSecSysGrpObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFSecSecSysGrpObj,String>,TableCell<ICFSecSecSysGrpObj,String>>() {
			@Override public TableCell<ICFSecSecSysGrpObj,String> call(
				TableColumn<ICFSecSecSysGrpObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecSysGrpObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnSecLevel = new TableColumn<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum>( "Group Level" );
		tableColumnSecLevel.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum>,ObservableValue<ICFSecSchema.SecLevelEnum> >() {
			public ObservableValue<ICFSecSchema.SecLevelEnum> call( CellDataFeatures<ICFSecSecSysGrpObj, ICFSecSchema.SecLevelEnum> p ) {
				ICFSecSecSysGrpObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecSchema.SecLevelEnum value = obj.getRequiredSecLevel();
					ReadOnlyObjectWrapper<ICFSecSchema.SecLevelEnum> observable = new ReadOnlyObjectWrapper<ICFSecSchema.SecLevelEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecLevel.setCellFactory( new Callback<TableColumn<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum>,TableCell<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum>>() {
			@Override public TableCell<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum> call(
				TableColumn<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum> arg)
			{
				return new CFEnumTableCell<ICFSecSecSysGrpObj,ICFSecSchema.SecLevelEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnSecLevel );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecSysGrpObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecSysGrpObj> observable,
					ICFSecSecSysGrpObj oldValue,
					ICFSecSecSysGrpObj newValue )
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
					invokeWhenChosen.choseSecSysGrp( null );
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
					ICFSecSecSysGrpObj selectedInstance = getJavaFXFocusAsSecSysGrp();
					invokeWhenChosen.choseSecSysGrp( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecSecSysGrpObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSysGrpObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecSysGrpObj getJavaFXFocusAsSecSysGrp() {
		return( (ICFSecSecSysGrpObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSysGrp( ICFSecSecSysGrpObj value ) {
		setJavaFXFocus( value );
	}

	public class SecSysGrpByQualNameComparator
	implements Comparator<ICFSecSecSysGrpObj>
	{
		public SecSysGrpByQualNameComparator() {
		}

		public int compare( ICFSecSecSysGrpObj lhs, ICFSecSecSysGrpObj rhs ) {
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

	protected SecSysGrpByQualNameComparator compareSecSysGrpByQualName = new SecSysGrpByQualNameComparator();

	public Collection<ICFSecSecSysGrpObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecSysGrpObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfSecSysGrp = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecSecSysGrpObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfSecSysGrp.add( iter.next() );
				}
				observableListOfSecSysGrp.sort( compareSecSysGrpByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecSysGrp );
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
		ICFSecSecSysGrpObj selectedObj = getJavaFXFocusAsSecSysGrp();
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

