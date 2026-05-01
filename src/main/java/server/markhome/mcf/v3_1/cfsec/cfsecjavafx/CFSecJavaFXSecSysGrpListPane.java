// Description: Java 25 JavaFX List of Obj Pane implementation for SecSysGrp.

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
 *	CFSecJavaFXSecSysGrpListPane JavaFX List of Obj Pane implementation
 *	for SecSysGrp.
 */
public class CFSecJavaFXSecSysGrpListPane
extends CFBorderPane
implements ICFSecJavaFXSecSysGrpPaneList
{
	public static String S_FormName = "List System Security Group";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecSecSysGrpObj> javafxDataCollection = null;
	protected ObservableList<ICFSecSecSysGrpObj> observableListOfSecSysGrp = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddSecSysGrp = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFSecSecSysGrpObj> dataTable = null;
	protected TableColumn<ICFSecSecSysGrpObj, String> tableColumnName = null;
	protected TableColumn<ICFSecSecSysGrpObj, ICFSecSchema.SecLevelEnum> tableColumnSecLevel = null;

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


	public CFSecJavaFXSecSysGrpListPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFLibAnyObj argContainer,
		ICFSecSecSysGrpObj argFocus,
		Collection<ICFSecSecSysGrpObj> argDataCollection,
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
		dataTable = new TableView<ICFSecSecSysGrpObj>();
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
		if( observableListOfSecSysGrp != null ) {
			dataTable.setItems( observableListOfSecSysGrp );
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
		adjustListButtons();
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
			buttonAddSecSysGrp = new CFButton();
			buttonAddSecSysGrp.setMinWidth( 200 );
			buttonAddSecSysGrp.setText( "Add System Security Group" );
			buttonAddSecSysGrp.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
						ICFSecSecSysGrpObj obj = (ICFSecSecSysGrpObj)schemaObj.getSecSysGrpTableObj().newInstance();
						ICFSecSecSysGrpEditObj edit = (ICFSecSecSysGrpEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						CFBorderPane frame = javafxSchema.getSecSysGrpFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFSecJavaFXSecSysGrpPaneCommon jpanelCommon = (ICFSecJavaFXSecSysGrpPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddSecSysGrp );
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
						ICFSecSecSysGrpObj selectedInstance = getJavaFXFocusAsSecSysGrp();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysGrp.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecSysGrpFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecSysGrpPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecSysGrpObj" );
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
						ICFSecSecSysGrpObj selectedInstance = getJavaFXFocusAsSecSysGrp();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysGrp.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecSysGrpFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecSysGrpPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecSysGrpObj" );
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
						ICFSecSecSysGrpObj selectedInstance = getJavaFXFocusAsSecSysGrp();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecSysGrp.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecSysGrpFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFSecJavaFXSecSysGrpPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecSysGrpObj" );
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
		ICFSecSecSysGrpObj selectedObj = getJavaFXFocusAsSecSysGrp();
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
		if( buttonAddSecSysGrp != null ) {
			buttonAddSecSysGrp.setDisable( ! allowAdds );
		}

	}
}
