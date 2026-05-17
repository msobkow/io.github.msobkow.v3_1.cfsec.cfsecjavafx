// Description: Java 25 JavaFX List of Obj Pane implementation for SecSysRole.

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
 *	CFSecJavaFXSecSysRoleListPane JavaFX List of Obj Pane implementation
 *	for SecSysRole.
 */
public class CFSecJavaFXSecSysRoleListPane
extends CFBorderPane
implements ICFSecJavaFXSecSysRolePaneList
{
	public static String S_FormName = "List Security System Role";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecSysRoleObj> javafxDataCollection = null;
	protected ObservableList<ICFSecSecSysRoleObj> observableListOfSecSysRole = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddSecSysRole = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFSecSecSysRoleObj> dataTable = null;
	protected TableColumn<ICFSecSecSysRoleObj, CFLibDbKeyHash256> tableColumnSecSysRoleId = null;
	protected TableColumn<ICFSecSecSysRoleObj, String> tableColumnName = null;

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


	public CFSecJavaFXSecSysRoleListPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFLibAnyObj argContainer,
		ICFSecSecSysRoleObj argFocus,
		Collection<ICFSecSecSysRoleObj> argDataCollection,
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
		dataTable = new TableView<ICFSecSecSysRoleObj>();
		tableColumnSecSysRoleId = new TableColumn<ICFSecSecSysRoleObj,CFLibDbKeyHash256>( "Security System Role Id" );
		tableColumnSecSysRoleId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSysRoleObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecSysRoleObj, CFLibDbKeyHash256> p ) {
				ICFSecSecSysRoleObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecSysRoleId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecSysRoleId.setCellFactory( new Callback<TableColumn<ICFSecSecSysRoleObj,CFLibDbKeyHash256>,TableCell<ICFSecSecSysRoleObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecSysRoleObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecSysRoleObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecSysRoleObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecSysRoleId );
		tableColumnName = new TableColumn<ICFSecSecSysRoleObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSysRoleObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecSysRoleObj, String> p ) {
				ICFSecSecSysRoleObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFSecSecSysRoleObj,String>,TableCell<ICFSecSecSysRoleObj,String>>() {
			@Override public TableCell<ICFSecSecSysRoleObj,String> call(
				TableColumn<ICFSecSecSysRoleObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecSysRoleObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecSysRoleObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecSysRoleObj> observable,
					ICFSecSecSysRoleObj oldValue,
					ICFSecSecSysRoleObj newValue )
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
		if( observableListOfSecSysRole != null ) {
			dataTable.setItems( observableListOfSecSysRole );
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
		if( ( value == null ) || ( value instanceof ICFSecSecSysRoleObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSysRoleObj" );
		}
		adjustListButtons();
	}

	public ICFSecSecSysRoleObj getJavaFXFocusAsSecSysRole() {
		return( (ICFSecSecSysRoleObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSysRole( ICFSecSecSysRoleObj value ) {
		setJavaFXFocus( value );
	}

	public class SecSysRoleByQualNameComparator
	implements Comparator<ICFSecSecSysRoleObj>
	{
		public SecSysRoleByQualNameComparator() {
		}

		public int compare( ICFSecSecSysRoleObj lhs, ICFSecSecSysRoleObj rhs ) {
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

	protected SecSysRoleByQualNameComparator compareSecSysRoleByQualName = new SecSysRoleByQualNameComparator();

	public Collection<ICFSecSecSysRoleObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecSysRoleObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfSecSysRole = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecSecSysRoleObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfSecSysRole.add( iter.next() );
				}
				observableListOfSecSysRole.sort( compareSecSysRoleByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecSysRole );
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
			buttonAddSecSysRole = new CFButton();
			buttonAddSecSysRole.setMinWidth( 200 );
			buttonAddSecSysRole.setText( "Add Security System Role" );
			buttonAddSecSysRole.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						ICFSecSecSysRoleObj obj = (ICFSecSecSysRoleObj)schemaObj.getSecSysRoleTableObj().newInstance();
						ICFSecSecSysRoleEditObj edit = (ICFSecSecSysRoleEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						CFBorderPane frame = javafxSchema.getSecSysRoleFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFSecJavaFXSecSysRolePaneCommon jpanelCommon = (ICFSecJavaFXSecSysRolePaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddSecSysRole );
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
						ICFSecSecSysRoleObj selectedInstance = getJavaFXFocusAsSecSysRole();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysRole.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecSysRoleFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecSysRolePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecSysRoleObj" );
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
						ICFSecSecSysRoleObj selectedInstance = getJavaFXFocusAsSecSysRole();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysRole.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecSysRoleFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecSysRolePaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecSysRoleObj" );
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
						ICFSecSecSysRoleObj selectedInstance = getJavaFXFocusAsSecSysRole();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysRole.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecSysRoleFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFSecJavaFXSecSysRolePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecSysRoleObj" );
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
		ICFSecSecSysRoleObj selectedObj = getJavaFXFocusAsSecSysRole();
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
		if( buttonAddSecSysRole != null ) {
			buttonAddSecSysRole.setDisable( ! allowAdds );
		}

	}
}
