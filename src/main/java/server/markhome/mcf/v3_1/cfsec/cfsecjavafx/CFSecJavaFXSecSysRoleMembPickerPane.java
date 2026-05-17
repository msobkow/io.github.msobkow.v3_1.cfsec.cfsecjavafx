// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecSysRoleMemb.

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
 *	CFSecJavaFXSecSysRoleMembPickerPane JavaFX Pick Obj Pane implementation
 *	for SecSysRoleMemb.
 */
public class CFSecJavaFXSecSysRoleMembPickerPane
extends CFBorderPane
implements ICFSecJavaFXSecSysRoleMembPaneList
{
	public static String S_FormName = "Choose Security System Role Member";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXSecSysRoleMembPageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecSysRoleMembObj> observableListOfSecSysRoleMemb = null;
	protected TableColumn<ICFSecSecSysRoleMembObj, ICFSecSecUserObj> tableColumnParentUser = null;
	protected TableView<ICFSecSecSysRoleMembObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecSysRoleMembChosen invokeWhenChosen = null;
	protected ICFSecSecSysRoleObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecSysRoleMembPickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecSysRoleMembObj argFocus,
		ICFSecSecSysRoleObj argContainer,
		ICFSecJavaFXSecSysRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecSysRoleMembChosen whenChosen )
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
		dataTable = new TableView<ICFSecSecSysRoleMembObj>();
		tableColumnParentUser = new TableColumn<ICFSecSecSysRoleMembObj, ICFSecSecUserObj>( "User" );
		tableColumnParentUser.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecSysRoleMembObj,ICFSecSecUserObj>,ObservableValue<ICFSecSecUserObj> >() {
			public ObservableValue<ICFSecSecUserObj> call( CellDataFeatures<ICFSecSecSysRoleMembObj, ICFSecSecUserObj> p ) {
				ICFSecSecSysRoleMembObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecSecUserObj ref = obj.getRequiredParentUser();
					ReadOnlyObjectWrapper<ICFSecSecUserObj> observable = new ReadOnlyObjectWrapper<ICFSecSecUserObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnParentUser.setCellFactory( new Callback<TableColumn<ICFSecSecSysRoleMembObj,ICFSecSecUserObj>,TableCell<ICFSecSecSysRoleMembObj,ICFSecSecUserObj>>() {
			@Override public TableCell<ICFSecSecSysRoleMembObj,ICFSecSecUserObj> call(
				TableColumn<ICFSecSecSysRoleMembObj,ICFSecSecUserObj> arg)
			{
				return new CFReferenceTableCell<ICFSecSecSysRoleMembObj,ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnParentUser );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecSysRoleMembObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecSysRoleMembObj> observable,
					ICFSecSecSysRoleMembObj oldValue,
					ICFSecSecSysRoleMembObj newValue )
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
						observableListOfSecSysRoleMemb = FXCollections.observableArrayList();
						List<ICFSecSecSysRoleMembObj> page = pageCallback.pageData( null,
							null );
						Iterator<ICFSecSecSysRoleMembObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecSysRoleMemb.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecSysRoleMemb.sort( compareSecSysRoleMembByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecSysRoleMemb );
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
						ICFSecSecSysRoleMembObj lastObj = null;
						if( ( observableListOfSecSysRoleMemb != null ) && ( observableListOfSecSysRoleMemb.size() > 0 ) ) {
							lastObj = observableListOfSecSysRoleMemb.get( observableListOfSecSysRoleMemb.size() - 1 );
						}
						List<ICFSecSecSysRoleMembObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecSysRoleId(),
							lastObj.getRequiredLoginId() );
						}
						else {
							page = pageCallback.pageData( null,
							null );
						}
						Iterator<ICFSecSecSysRoleMembObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecSysRoleMemb.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecSysRoleMemb.sort( compareSecSysRoleMembByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecSysRoleMemb );
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
					invokeWhenChosen.choseSecSysRoleMemb( null );
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
					ICFSecSecSysRoleMembObj selectedInstance = getJavaFXFocusAsSecSysRoleMemb();
					invokeWhenChosen.choseSecSysRoleMemb( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecSecSysRoleMembObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecSysRoleMembObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecSysRoleMembObj getJavaFXFocusAsSecSysRoleMemb() {
		return( (ICFSecSecSysRoleMembObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecSysRoleMemb( ICFSecSecSysRoleMembObj value ) {
		setJavaFXFocus( value );
	}

	public class SecSysRoleMembByQualNameComparator
	implements Comparator<ICFSecSecSysRoleMembObj>
	{
		public SecSysRoleMembByQualNameComparator() {
		}

		public int compare( ICFSecSecSysRoleMembObj lhs, ICFSecSecSysRoleMembObj rhs ) {
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

	protected SecSysRoleMembByQualNameComparator compareSecSysRoleMembByQualName = new SecSysRoleMembByQualNameComparator();

	public Collection<ICFSecSecSysRoleMembObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecSysRoleMembObj> value ) {
		// Use page data instead
	}

	public ICFSecSecSysRoleObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecSecSysRoleObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecSecSysRoleMembObj selectedObj = getJavaFXFocusAsSecSysRoleMemb();
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

