// Description: Java 25 JavaFX Picker of Obj Pane implementation for SecGrpMemb.

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
 *	CFSecJavaFXSecGrpMembPickerPane JavaFX Pick Obj Pane implementation
 *	for SecGrpMemb.
 */
public class CFSecJavaFXSecGrpMembPickerPane
extends CFBorderPane
implements ICFSecJavaFXSecGrpMembPaneList
{
	public static String S_FormName = "Choose Security Group Member";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected ICFSecJavaFXSecGrpMembPageCallback pageCallback;
	protected CFButton buttonRefresh = null;
	protected CFButton buttonMoreData = null;
	protected boolean endOfData = true;
	protected ObservableList<ICFSecSecGrpMembObj> observableListOfSecGrpMemb = null;
	protected TableColumn<ICFSecSecGrpMembObj, CFLibDbKeyHash256> tableColumnSecGrpMembId = null;
	protected TableColumn<ICFSecSecGrpMembObj, ICFSecSecUserObj> tableColumnParentUser = null;
	protected TableView<ICFSecSecGrpMembObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXSecGrpMembChosen invokeWhenChosen = null;
	protected ICFSecSecGroupObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXSecGrpMembPickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecSecGrpMembObj argFocus,
		ICFSecSecGroupObj argContainer,
		ICFSecJavaFXSecGrpMembPageCallback argPageCallback,
		ICFSecJavaFXSecGrpMembChosen whenChosen )
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
		dataTable = new TableView<ICFSecSecGrpMembObj>();
		tableColumnSecGrpMembId = new TableColumn<ICFSecSecGrpMembObj,CFLibDbKeyHash256>( "Security Group Member Id" );
		tableColumnSecGrpMembId.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecGrpMembObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFSecSecGrpMembObj, CFLibDbKeyHash256> p ) {
				ICFSecSecGrpMembObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredSecGrpMembId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecGrpMembId.setCellFactory( new Callback<TableColumn<ICFSecSecGrpMembObj,CFLibDbKeyHash256>,TableCell<ICFSecSecGrpMembObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFSecSecGrpMembObj,CFLibDbKeyHash256> call(
				TableColumn<ICFSecSecGrpMembObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFSecSecGrpMembObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSecGrpMembId );
		tableColumnParentUser = new TableColumn<ICFSecSecGrpMembObj, ICFSecSecUserObj>( "User" );
		tableColumnParentUser.setCellValueFactory( new Callback<CellDataFeatures<ICFSecSecGrpMembObj,ICFSecSecUserObj>,ObservableValue<ICFSecSecUserObj> >() {
			public ObservableValue<ICFSecSecUserObj> call( CellDataFeatures<ICFSecSecGrpMembObj, ICFSecSecUserObj> p ) {
				ICFSecSecGrpMembObj obj = p.getValue();
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
		tableColumnParentUser.setCellFactory( new Callback<TableColumn<ICFSecSecGrpMembObj,ICFSecSecUserObj>,TableCell<ICFSecSecGrpMembObj,ICFSecSecUserObj>>() {
			@Override public TableCell<ICFSecSecGrpMembObj,ICFSecSecUserObj> call(
				TableColumn<ICFSecSecGrpMembObj,ICFSecSecUserObj> arg)
			{
				return new CFReferenceTableCell<ICFSecSecGrpMembObj,ICFSecSecUserObj>();
			}
		});
		dataTable.getColumns().add( tableColumnParentUser );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecSecGrpMembObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecSecGrpMembObj> observable,
					ICFSecSecGrpMembObj oldValue,
					ICFSecSecGrpMembObj newValue )
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
						observableListOfSecGrpMemb = FXCollections.observableArrayList();
						List<ICFSecSecGrpMembObj> page = pageCallback.pageData( null );
						Iterator<ICFSecSecGrpMembObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecGrpMemb.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecGrpMemb.sort( compareSecGrpMembByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecGrpMemb );
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
						ICFSecSecGrpMembObj lastObj = null;
						if( ( observableListOfSecGrpMemb != null ) && ( observableListOfSecGrpMemb.size() > 0 ) ) {
							lastObj = observableListOfSecGrpMemb.get( observableListOfSecGrpMemb.size() - 1 );
						}
						List<ICFSecSecGrpMembObj> page;
						if( lastObj != null ) {
							page = pageCallback.pageData( lastObj.getRequiredSecGrpMembId() );
						}
						else {
							page = pageCallback.pageData( null );
						}
						Iterator<ICFSecSecGrpMembObj> iter = page.iterator();
						while( iter.hasNext() ) {
							observableListOfSecGrpMemb.add( iter.next() );
						}
						if( page.size() < 25 ) {
							observableListOfSecGrpMemb.sort( compareSecGrpMembByQualName );
							endOfData = true;
						}
						else {
							endOfData = false;
						}
						if( dataTable != null ) {
							dataTable.setItems( observableListOfSecGrpMemb );
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
					invokeWhenChosen.choseSecGrpMemb( null );
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
					ICFSecSecGrpMembObj selectedInstance = getJavaFXFocusAsSecGrpMemb();
					invokeWhenChosen.choseSecGrpMemb( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecSecGrpMembObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecSecGrpMembObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecSecGrpMembObj getJavaFXFocusAsSecGrpMemb() {
		return( (ICFSecSecGrpMembObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSecGrpMemb( ICFSecSecGrpMembObj value ) {
		setJavaFXFocus( value );
	}

	public class SecGrpMembByQualNameComparator
	implements Comparator<ICFSecSecGrpMembObj>
	{
		public SecGrpMembByQualNameComparator() {
		}

		public int compare( ICFSecSecGrpMembObj lhs, ICFSecSecGrpMembObj rhs ) {
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

	protected SecGrpMembByQualNameComparator compareSecGrpMembByQualName = new SecGrpMembByQualNameComparator();

	public Collection<ICFSecSecGrpMembObj> getJavaFXDataCollection() {
		return( null );
	}

	public void setJavaFXDataCollection( Collection<ICFSecSecGrpMembObj> value ) {
		// Use page data instead
	}

	public ICFSecSecGroupObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecSecGroupObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecSecGrpMembObj selectedObj = getJavaFXFocusAsSecGrpMemb();
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

