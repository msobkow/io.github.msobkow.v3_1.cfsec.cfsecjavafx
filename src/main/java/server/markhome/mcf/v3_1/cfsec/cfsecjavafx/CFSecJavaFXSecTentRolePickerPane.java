// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecTentRole.

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
 *	CFSecJavaFXSecTentRolePickerPane JavaFX Pick Obj Pane implementation
 *	for SecTentRole.
 */
public class CFSecJavaFXSecTentRolePickerPane
extends CFBorderPane
implements ICFSecJavaFXSecTentRolePaneList
{
	public static String S_FormName = "Choose Tenant Security Role";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecTentRoleObj> javafxDataCollection = null;
	protected ObservableList<ICFSecSecTentRoleObj> observableListOfSecTentRole = null;
	protected TableColumn<ICFSecSecTentRoleObj, CFLibDbKeyHash256> tableColumnSecTentRoleId = null;
	protected TableView<ICFSecSecTentRoleObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecTentRoleChosen invokeWhenChosen = null;
	protected ICFSecSecSysGrpObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecTentRolePickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecTentRoleObj argFocus,
		ICFSecSecSysGrpObj argContainer,
		Collection<ICFSecSecTentRoleObj> argDataCollection,
		ICFSecJavaFXSecTentRoleChosen whenChosen )
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
		dataTable = new TableView<ICFSecSecTentRoleObj>();
		tableColumnSecTentRoleId = new TableColumn<ICFSecSecTentRoleObj,CFLibDbKeyHash256>( "Tenant Security Role Id" );
		tableColumnSecTentRoleId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecTentRoleObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecTentRoleObj, CFLibDbKeyHash256> p ) {
				ICFSecSecTentRoleObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecTentRoleId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecTentRoleId.setCellFactory( new Callback<TableColumn<ICFSecSecTentRoleObj,CFLibDbKeyHash256>,TableCell<ICFSecSecTentRoleObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecTentRoleObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecTentRoleObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecTentRoleObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecTentRoleId );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecTentRoleObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecTentRoleObj> observable,
					ICFSecSecTentRoleObj oldValue,
					ICFSecSecTentRoleObj newValue )
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
					invokeWhenChosen.choseSecTentRole( null );
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
					ICFSecSecTentRoleObj selectedInstance = getJavaFXFocusAsSecTentRole();
					invokeWhenChosen.choseSecTentRole( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecSecTentRoleObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecTentRoleObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecTentRoleObj getJavaFXFocusAsSecTentRole() {
		return( (ICFSecSecTentRoleObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecTentRole( ICFSecSecTentRoleObj value ) {
		setJavaFXFocus( value );
	}

	public class SecTentRoleByQualNameComparator
	implements Comparator<ICFSecSecTentRoleObj>
	{
		public SecTentRoleByQualNameComparator() {
		}

		public int compare( ICFSecSecTentRoleObj lhs, ICFSecSecTentRoleObj rhs ) {
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

	protected SecTentRoleByQualNameComparator compareSecTentRoleByQualName = new SecTentRoleByQualNameComparator();

	public Collection<ICFSecSecTentRoleObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecTentRoleObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfSecTentRole = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecSecTentRoleObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfSecTentRole.add( iter.next() );
				}
				observableListOfSecTentRole.sort( compareSecTentRoleByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecTentRole );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	public ICFSecSecSysGrpObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecSecSysGrpObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecSecTentRoleObj selectedObj = getJavaFXFocusAsSecTentRole();
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

