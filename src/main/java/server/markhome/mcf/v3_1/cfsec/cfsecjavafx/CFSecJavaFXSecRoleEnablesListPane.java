// Description: Java 25 JavaFX List of Obj Pane implementation for SecRoleEnables.

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
 *	CFSecJavaFXSecRoleEnablesListPane JavaFX List of Obj Pane implementation
 *	for SecRoleEnables.
 */
public class CFSecJavaFXSecRoleEnablesListPane
extends CFBorderPane
implements ICFSecJavaFXSecRoleEnablesPaneList
{
	public static String S_FormName = "List Security Role Enables Group";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXSecRoleEnablesPageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecRoleEnablesObj> observableListOfSecRoleEnables = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddSecRoleEnables = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFSecSecRoleEnablesObj> dataTable = null;
	protected TableColumn<ICFSecSecRoleEnablesObj, ICFSecSecSysGrpObj> tableColumnParentEnableGroup = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFSecSecRoleObj javafxContainer = null;
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


	public CFSecJavaFXSecRoleEnablesListPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecRoleObj argContainer,
		ICFSecSecRoleEnablesObj argFocus,
		ICFSecJavaFXSecRoleEnablesPageCallback argPageCallback,
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
		dataTable = new TableView<ICFSecSecRoleEnablesObj>();
		tableColumnParentEnableGroup = new TableColumn<ICFSecSecRoleEnablesObj, ICFSecSecSysGrpObj>( "Enable Group" );
		tableColumnParentEnableGroup.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecRoleEnablesObj,ICFSecSecSysGrpObj>,ObservableValue<ICFSecSecSysGrpObj> >() {
			public ObservableValue<ICFSecSecSysGrpObj> call( CellDataFeatures<ICFSecSecRoleEnablesObj, ICFSecSecSysGrpObj> p ) {
				ICFSecSecRoleEnablesObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecSecSysGrpObj ref = obj.getRequiredParentEnableGroup();
					ReadOnlyObjectWrapper<ICFSecSecSysGrpObj> observable = new ReadOnlyObjectWrapper<ICFSecSecSysGrpObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnParentEnableGroup.setCellFactory( new Callback<TableColumn<ICFSecSecRoleEnablesObj,ICFSecSecSysGrpObj>,TableCell<ICFSecSecRoleEnablesObj,ICFSecSecSysGrpObj>>() {
			@Override public TableCell<ICFSecSecRoleEnablesObj,ICFSecSecSysGrpObj> call(
				TableColumn<ICFSecSecRoleEnablesObj,ICFSecSecSysGrpObj> arg)
			{
				return new CFReferenceTableCell<ICFSecSecRoleEnablesObj,ICFSecSecSysGrpObj>();
			}
		});
		dataTable.getColumns().add( tableColumnParentEnableGroup );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecRoleEnablesObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecRoleEnablesObj> observable,
					ICFSecSecRoleEnablesObj oldValue,
					ICFSecSecRoleEnablesObj newValue )
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
		if( observableListOfSecRoleEnables != null ) {
			dataTable.setItems( observableListOfSecRoleEnables );
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
		if( ( value == null ) || ( value instanceof ICFSecSecRoleEnablesObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecRoleEnablesObj" );
		}
		adjustListButtons();
	}

	public ICFSecSecRoleEnablesObj getJavaFXFocusAsSecRoleEnables() {
		return( (ICFSecSecRoleEnablesObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecRoleEnables( ICFSecSecRoleEnablesObj value ) {
		setJavaFXFocus( value );
	}

	public class SecRoleEnablesByQualNameComparator
	implements Comparator<ICFSecSecRoleEnablesObj>
	{
		public SecRoleEnablesByQualNameComparator() {
		}

		public int compare( ICFSecSecRoleEnablesObj lhs, ICFSecSecRoleEnablesObj rhs ) {
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

	protected SecRoleEnablesByQualNameComparator compareSecRoleEnablesByQualName = new SecRoleEnablesByQualNameComparator();

	public Collection<ICFSecSecRoleEnablesObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecRoleEnablesObj> value ) {
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
						ICFSecSecRoleEnablesObj lastObj = null;
						if( ( observableListOfSecRoleEnables != null ) && ( observableListOfSecRoleEnables.size() > 0 ) ) {
							lastObj = observableListOfSecRoleEnables.get( observableListOfSecRoleEnables.size() - 1 );
						}
						List<ICFSecSecRoleEnablesObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecRoleId(),
							lastObj.getRequiredEnableName() );
						}
						else {
							page = pageCallback.pageData( null,
							null );
						}
						Iterator<ICFSecSecRoleEnablesObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecRoleEnables.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecRoleEnables.sort( compareSecRoleEnablesByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecRoleEnables );
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

			buttonAddSecRoleEnables = new CFButton();
			buttonAddSecRoleEnables.setMinWidth( 200 );
			buttonAddSecRoleEnables.setText( "Add Security Role Enables Group" );
			buttonAddSecRoleEnables.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						ICFSecSecRoleEnablesObj obj = (ICFSecSecRoleEnablesObj)schemaObj.getSecRoleEnablesTableObj().newInstance();
						ICFSecSecRoleEnablesEditObj edit = (ICFSecSecRoleEnablesEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
								ICFSecSecRoleObj container = (ICFSecSecRoleObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerRole( container );
						CFBorderPane frame = javafxSchema.getSecRoleEnablesFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFSecJavaFXSecRoleEnablesPaneCommon jpanelCommon = (ICFSecJavaFXSecRoleEnablesPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddSecRoleEnables );
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
						ICFSecSecRoleEnablesObj selectedInstance = getJavaFXFocusAsSecRoleEnables();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecRoleEnables.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecRoleEnablesFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecRoleEnablesPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecRoleEnablesObj" );
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
						ICFSecSecRoleEnablesObj selectedInstance = getJavaFXFocusAsSecRoleEnables();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecRoleEnables.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecRoleEnablesFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecRoleEnablesPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecRoleEnablesObj" );
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
						ICFSecSecRoleEnablesObj selectedInstance = getJavaFXFocusAsSecRoleEnables();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecRoleEnables.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecRoleEnablesFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFSecJavaFXSecRoleEnablesPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecRoleEnablesObj" );
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

	public ICFSecSecRoleObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecSecRoleObj value ) {
		javafxContainer = value;
	}

	public void refreshMe() {
		final String S_ProcName = "refreshMe";
		observableListOfSecRoleEnables = FXCollections.observableArrayList();
		if( javafxContainer != null ) {
			List<ICFSecSecRoleEnablesObj> page = pageCallback.pageData( null,
							null );
			Iterator<ICFSecSecRoleEnablesObj> iter = page.iterator();
			while( iter.hasNext() ) {
				observableListOfSecRoleEnables.add( iter.next() );
			}
			if( page.size() < 25 ) {
				observableListOfSecRoleEnables.sort( compareSecRoleEnablesByQualName );
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
			dataTable.setItems( observableListOfSecRoleEnables );
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
		ICFSecSecRoleEnablesObj selectedObj = getJavaFXFocusAsSecRoleEnables();
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
		if( buttonAddSecRoleEnables != null ) {
			buttonAddSecRoleEnables.setDisable( ! allowAdds );
		}

	}
}
