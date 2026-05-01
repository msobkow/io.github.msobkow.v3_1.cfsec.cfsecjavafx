// Description: Java 25 JavaFX List of Obj Pane implementation for SecClusRole.

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
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecClusRoleListPane JavaFX List of Obj Pane implementation
 *	for SecClusRole.
 */
public class CFSecJavaFXSecClusRoleListPane
extends CFBorderPane
implements ICFSecJavaFXSecClusRolePaneList
{
	public static String S_FormName = "List Cluster Security Role";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecClusRoleObj> javafxDataCollection = null;
	protected ObservableList<ICFSecSecClusRoleObj> observableListOfSecClusRole = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddSecClusRole = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFSecSecClusRoleObj> dataTable = null;
	protected TableColumn<ICFSecSecClusRoleObj, CFLibDbKeyHash256> tableColumnSecClusRoleId = null;
	protected TableColumn<ICFSecSecClusRoleObj, CFLibDbKeyHash256> tableColumnClusterId = null;
	protected TableColumn<ICFSecSecClusRoleObj, String> tableColumnName = null;

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


	public CFSecJavaFXSecClusRoleListPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFLibAnyObj argContainer,
		ICFSecSecClusRoleObj argFocus,
		Collection<ICFSecSecClusRoleObj> argDataCollection,
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
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFSecSecClusRoleObj>();
		tableColumnSecClusRoleId = new TableColumn<ICFSecSecClusRoleObj,CFLibDbKeyHash256>( "Cluster Security Role Id" );
		tableColumnSecClusRoleId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecClusRoleObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecClusRoleObj, CFLibDbKeyHash256> p ) {
				ICFSecSecClusRoleObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecClusRoleId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecClusRoleId.setCellFactory( new Callback<TableColumn<ICFSecSecClusRoleObj,CFLibDbKeyHash256>,TableCell<ICFSecSecClusRoleObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecClusRoleObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecClusRoleObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecClusRoleObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecClusRoleId );
		tableColumnClusterId = new TableColumn<ICFSecSecClusRoleObj,CFLibDbKeyHash256>( "Cluster Id" );
		tableColumnClusterId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecClusRoleObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecClusRoleObj, CFLibDbKeyHash256> p ) {
				ICFSecSecClusRoleObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredClusterId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnClusterId.setCellFactory( new Callback<TableColumn<ICFSecSecClusRoleObj,CFLibDbKeyHash256>,TableCell<ICFSecSecClusRoleObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecClusRoleObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecClusRoleObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecClusRoleObj>();
			}
		});
		dataTable.getColumns().add( tableColumnClusterId );
		tableColumnName = new TableColumn<ICFSecSecClusRoleObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecClusRoleObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecClusRoleObj, String> p ) {
				ICFSecSecClusRoleObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFSecSecClusRoleObj,String>,TableCell<ICFSecSecClusRoleObj,String>>() {
			@Override public TableCell<ICFSecSecClusRoleObj,String> call(
				TableColumn<ICFSecSecClusRoleObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecClusRoleObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecClusRoleObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecClusRoleObj> observable,
					ICFSecSecClusRoleObj oldValue,
					ICFSecSecClusRoleObj newValue )
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
		if( observableListOfSecClusRole != null ) {
			dataTable.setItems( observableListOfSecClusRole );
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
		if( ( value == null ) || ( value instanceof ICFSecSecClusRoleObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecClusRoleObj" );
		}
		adjustListButtons();
	}

	public ICFSecSecClusRoleObj getJavaFXFocusAsSecClusRole() {
		return( (ICFSecSecClusRoleObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecClusRole( ICFSecSecClusRoleObj value ) {
		setJavaFXFocus( value );
	}

	public class SecClusRoleByQualNameComparator
	implements Comparator<ICFSecSecClusRoleObj>
	{
		public SecClusRoleByQualNameComparator() {
		}

		public int compare( ICFSecSecClusRoleObj lhs, ICFSecSecClusRoleObj rhs ) {
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

	protected SecClusRoleByQualNameComparator compareSecClusRoleByQualName = new SecClusRoleByQualNameComparator();

	public Collection<ICFSecSecClusRoleObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecClusRoleObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfSecClusRole = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecSecClusRoleObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfSecClusRole.add( iter.next() );
				}
				observableListOfSecClusRole.sort( compareSecClusRoleByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecClusRole );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
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
			buttonAddSecClusRole = new CFButton();
			buttonAddSecClusRole.setMinWidth( 200 );
			buttonAddSecClusRole.setText( "Add Cluster Security Role" );
			buttonAddSecClusRole.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						ICFSecSecClusRoleObj obj = (ICFSecSecClusRoleObj)schemaObj.getSecClusRoleTableObj().newInstance();
						ICFSecSecClusRoleEditObj edit = (ICFSecSecClusRoleEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						CFBorderPane frame = javafxSchema.getSecClusRoleFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFSecJavaFXSecClusRolePaneCommon jpanelCommon = (ICFSecJavaFXSecClusRolePaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddSecClusRole );
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
						ICFSecSecClusRoleObj selectedInstance = getJavaFXFocusAsSecClusRole();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecClusRole.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecClusRoleFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecClusRolePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecClusRoleObj" );
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
						ICFSecSecClusRoleObj selectedInstance = getJavaFXFocusAsSecClusRole();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecClusRole.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecClusRoleFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecClusRolePaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecClusRoleObj" );
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
						ICFSecSecClusRoleObj selectedInstance = getJavaFXFocusAsSecClusRole();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecClusRole.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecClusRoleFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFSecJavaFXSecClusRolePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecClusRoleObj" );
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
		if( javafxRefreshCallback != null ) {
			javafxRefreshCallback.refreshMe();
		}
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean inEditState;
		boolean allowAdds;
		boolean inAddMode = false;
		ICFSecSecClusRoleObj selectedObj = getJavaFXFocusAsSecClusRole();
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
		if( buttonAddSecClusRole != null ) {
			buttonAddSecClusRole.setDisable( ! allowAdds );
		}

	}
}
