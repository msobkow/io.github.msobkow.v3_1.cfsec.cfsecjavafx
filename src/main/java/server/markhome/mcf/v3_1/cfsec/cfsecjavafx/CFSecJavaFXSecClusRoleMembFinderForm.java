// Description: Java 25 JavaFX Finder Form implementation for SecClusRoleMemb.

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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
 *	CFSecJavaFXSecClusRoleMembFinderForm JavaFX Finder Form implementation
 *	for SecClusRoleMemb.
 */
public class CFSecJavaFXSecClusRoleMembFinderForm
extends CFBorderPane
implements ICFSecJavaFXSecClusRoleMembPaneCommon,
	ICFForm
{
	public static String S_FormName = "Find Cluster Security Role Member";
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFVBox vboxMenuAdd = null;
	protected ScrollPane scrollMenuAdd = null;
	protected CFButton buttonAdd = null;
	protected CFButton buttonCancelAdd = null;
	protected CFButton buttonAddSecClusRoleMemb = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonClose = null;
	protected CFButton buttonDeleteSelected = null;
	protected ICFSecJavaFXSecClusRoleMembPageCallback pageCallback = null;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecClusRoleMembObj> observableListOfSecClusRoleMemb = null;
	protected TableColumn<ICFSecSecClusRoleMembObj, CFLibDbKeyHash256> tableColumnSecClusRoleId = null;
	protected TableColumn<ICFSecSecClusRoleMembObj, String> tableColumnLoginId = null;
	protected TableView<ICFSecSecClusRoleMembObj> dataTable = null;

	protected class PageDataSecClusRoleMembList
	implements ICFSecJavaFXSecClusRoleMembPageCallback
	{
		public PageDataSecClusRoleMembList() {
		}

		public List<ICFSecSecClusRoleMembObj> pageData( CFLibDbKeyHash256 priorSecClusRoleId,
		String priorLoginId )
		{
			List<ICFSecSecClusRoleMembObj> dataList;
			ICFSecSchemaObj schemaObj = (ICFSecSchemaObj)javafxSchema.getSchema();
			dataList = schemaObj.getSecClusRoleMembTableObj().pageAllSecClusRoleMemb(priorSecClusRoleId,
					priorLoginId );
			return( dataList );
		}
	}

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

	public CFSecJavaFXSecClusRoleMembFinderForm( ICFFormManager formManager, ICFSecJavaFXSchema argSchema ) {
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
		javafxSchema = argSchema;
		pageCallback = new PageDataSecClusRoleMembList();
		dataTable = new TableView<ICFSecSecClusRoleMembObj>();
		tableColumnSecClusRoleId = new TableColumn<ICFSecSecClusRoleMembObj,CFLibDbKeyHash256>( "Cluster Security Role Id" );
		tableColumnSecClusRoleId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecClusRoleMembObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecClusRoleMembObj, CFLibDbKeyHash256> p ) {
				ICFSecSecClusRoleMembObj obj = p.getValue();
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
		tableColumnSecClusRoleId.setCellFactory( new Callback<TableColumn<ICFSecSecClusRoleMembObj,CFLibDbKeyHash256>,TableCell<ICFSecSecClusRoleMembObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecClusRoleMembObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecClusRoleMembObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecClusRoleMembObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecClusRoleId );
		tableColumnLoginId = new TableColumn<ICFSecSecClusRoleMembObj,String>( "Login Id" );
		tableColumnLoginId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecClusRoleMembObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFSecSecClusRoleMembObj, String> p ) {
				ICFSecSecClusRoleMembObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredLoginId();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLoginId.setCellFactory( new Callback<TableColumn<ICFSecSecClusRoleMembObj,String>,TableCell<ICFSecSecClusRoleMembObj,String>>() {
			@Override public TableCell<ICFSecSecClusRoleMembObj,String> call(
				TableColumn<ICFSecSecClusRoleMembObj,String> arg)
			{
				return new CFStringTableCell<ICFSecSecClusRoleMembObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLoginId );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecClusRoleMembObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecClusRoleMembObj> observable,
					ICFSecSecClusRoleMembObj oldValue,
					ICFSecSecClusRoleMembObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );

		refreshMe();
		javafxIsInitializing = false;
		adjustFinderButtons();
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

	public void forceCancelAndClose() {
		if( cfFormManager != null ) {
			if( cfFormManager.getCurrentForm() == this ) {
				cfFormManager.closeCurrentForm();
			}
		}
	}

	public ICFSecJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
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

	public CFHBox getHBoxMenu() {
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
						ICFSecSecClusRoleMembObj lastObj = null;
						if( ( observableListOfSecClusRoleMemb != null ) && ( observableListOfSecClusRoleMemb.size() > 0 ) ) {
							lastObj = observableListOfSecClusRoleMemb.get( observableListOfSecClusRoleMemb.size() - 1 );
						}
						List<ICFSecSecClusRoleMembObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecClusRoleId(),
							lastObj.getRequiredLoginId() );
						}
						else {
							page = pageCallback.pageData( null,
							null );
						}
						Iterator<ICFSecSecClusRoleMembObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecClusRoleMemb.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecClusRoleMemb.sort( compareSecClusRoleMembByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecClusRoleMemb );
							// Hack from stackoverflow to fix JavaFX TableView refresh issue
							((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
							((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
						}
						adjustFinderButtons();
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonMoreData );

			buttonAddSecClusRoleMemb = new CFButton();
			buttonAddSecClusRoleMemb.setMinWidth( 200 );
			buttonAddSecClusRoleMemb.setText( "Add Cluster Security Role Member..." );
			buttonAddSecClusRoleMemb.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFSecSecClusRoleMembObj obj = (ICFSecSecClusRoleMembObj)schemaObj.getSecClusRoleMembTableObj().newInstance();
						ICFSecSecClusRoleMembEditObj edit = (ICFSecSecClusRoleMembEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						CFBorderPane frame = javafxSchema.getSecClusRoleMembFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFSecJavaFXSecClusRoleMembPaneCommon jpanelCommon = (ICFSecJavaFXSecClusRoleMembPaneCommon)frame;
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddSecClusRoleMemb );

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
						ICFSecSecClusRoleMembObj selectedInstance = getJavaFXFocusAsSecClusRoleMemb();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecClusRoleMemb.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecClusRoleMembFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecClusRoleMembPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecClusRoleMembObj" );
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
						ICFSecSecClusRoleMembObj selectedInstance = getJavaFXFocusAsSecClusRoleMemb();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecClusRoleMemb.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecClusRoleMembFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFSecJavaFXSecClusRoleMembPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecClusRoleMembObj" );
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
						ICFSecSecClusRoleMembObj selectedInstance = getJavaFXFocusAsSecClusRoleMemb();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFSecSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFSec") && backingClassCode == ICFSecSecClusRoleMemb.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getSecClusRoleMembFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFSecJavaFXSecClusRoleMembPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFSecSecClusRoleMembObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonDeleteSelected );

			buttonClose = new CFButton();
			buttonClose.setMinWidth( 200 );
			buttonClose.setText( "Close" );
			buttonClose.setOnAction( new EventHandler<ActionEvent>() {
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
			hboxMenu.getChildren().add( buttonClose );
		}
		return( hboxMenu );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFSecSecClusRoleMembObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecClusRoleMembObj" );
		}
		adjustFinderButtons();
	}

	public ICFSecSecClusRoleMembObj getJavaFXFocusAsSecClusRoleMemb() {
		return( (ICFSecSecClusRoleMembObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecClusRoleMemb( ICFSecSecClusRoleMembObj value ) {
		setJavaFXFocus( value );
	}

	public void adjustFinderButtons() {
		ICFSecSecClusRoleMembObj selectedObj = getJavaFXFocusAsSecClusRoleMemb();
		boolean disableState;
		if( selectedObj == null ) {
			disableState = true;
		}
		else {
			disableState = false;
		}
		boolean inAddMode;
		if( getLeft() == null ) {
			inAddMode = false;
		}
		else {
			inAddMode = true;
			disableState = true;
		}

		if( buttonRefresh != null ) {
			buttonRefresh.setDisable( false );
		}
		if( buttonMoreData != null ) {
			buttonMoreData.setDisable( endOfData );
		}
		if( buttonViewSelected != null ) {
			buttonViewSelected.setDisable( disableState );
		}
		if( buttonEditSelected != null ) {
			buttonEditSelected.setDisable( disableState );
		}
		if( buttonDeleteSelected != null ) {
			buttonDeleteSelected.setDisable( disableState );
		}
		if( buttonAddSecClusRoleMemb != null ) {
			buttonAddSecClusRoleMemb.setDisable( false );
		}

	}

	public class SecClusRoleMembByQualNameComparator
	implements Comparator<ICFSecSecClusRoleMembObj>
	{
		public SecClusRoleMembByQualNameComparator() {
		}

		public int compare( ICFSecSecClusRoleMembObj lhs, ICFSecSecClusRoleMembObj rhs ) {
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

	protected SecClusRoleMembByQualNameComparator compareSecClusRoleMembByQualName = new SecClusRoleMembByQualNameComparator();

	public void refreshMe() {
		final String S_ProcName = "refreshMe";
		observableListOfSecClusRoleMemb = FXCollections.observableArrayList();
		List<ICFSecSecClusRoleMembObj> page = pageCallback.pageData( null,
							null );
		Iterator<ICFSecSecClusRoleMembObj> iter = page.iterator();
		while( iter.hasNext() ) {
			observableListOfSecClusRoleMemb.add( iter.next() );
		}
		if( page.size() < 25 ) {
				observableListOfSecClusRoleMemb.sort( compareSecClusRoleMembByQualName );
			endOfData = true;
		}
		else {
			endOfData = false;
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfSecClusRoleMemb );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
		adjustFinderButtons();
	}
}
