// Description: Java 25 JavaFX Picker of Obj Pane implementation for ISOCtryLang.

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
 *	CFSecJavaFXISOCtryLangPickerPane JavaFX Pick Obj Pane implementation
 *	for ISOCtryLang.
 */
public class CFSecJavaFXISOCtryLangPickerPane
extends CFBorderPane
implements ICFSecJavaFXISOCtryLangPaneList
{
	public static String S_FormName = "Choose ISOCtryLang";
	protected ICFSecJavaFXSchema javafxSchema = null;
	protected Collection<ICFSecISOCtryLangObj> javafxDataCollection = null;
	protected ObservableList<ICFSecISOCtryLangObj> observableListOfISOCtryLang = null;
	protected TableColumn<ICFSecISOCtryLangObj, ICFSecISOLangObj> tableColumnParentLang = null;
	protected TableView<ICFSecISOCtryLangObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFSecJavaFXISOCtryLangChosen invokeWhenChosen = null;
	protected ICFSecISOCtryObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFSecJavaFXISOCtryLangPickerPane( ICFFormManager formManager,
		ICFSecJavaFXSchema argSchema,
		ICFSecISOCtryLangObj argFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryLangObj> argDataCollection,
		ICFSecJavaFXISOCtryLangChosen whenChosen )
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
		dataTable = new TableView<ICFSecISOCtryLangObj>();
		tableColumnParentLang = new TableColumn<ICFSecISOCtryLangObj, ICFSecISOLangObj>( "Lang" );
		tableColumnParentLang.setCellValueFactory( new Callback<CellDataFeatures<ICFSecISOCtryLangObj,ICFSecISOLangObj>,ObservableValue<ICFSecISOLangObj> >() {
			public ObservableValue<ICFSecISOLangObj> call( CellDataFeatures<ICFSecISOCtryLangObj, ICFSecISOLangObj> p ) {
				ICFSecISOCtryLangObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFSecISOLangObj ref = obj.getRequiredParentLang();
					ReadOnlyObjectWrapper<ICFSecISOLangObj> observable = new ReadOnlyObjectWrapper<ICFSecISOLangObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnParentLang.setCellFactory( new Callback<TableColumn<ICFSecISOCtryLangObj,ICFSecISOLangObj>,TableCell<ICFSecISOCtryLangObj,ICFSecISOLangObj>>() {
			@Override public TableCell<ICFSecISOCtryLangObj,ICFSecISOLangObj> call(
				TableColumn<ICFSecISOCtryLangObj,ICFSecISOLangObj> arg)
			{
				return new CFReferenceTableCell<ICFSecISOCtryLangObj,ICFSecISOLangObj>();
			}
		});
		dataTable.getColumns().add( tableColumnParentLang );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFSecISOCtryLangObj>() {
				@Override public void changed( ObservableValue<? extends ICFSecISOCtryLangObj> observable,
					ICFSecISOCtryLangObj oldValue,
					ICFSecISOCtryLangObj newValue )
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
					invokeWhenChosen.choseISOCtryLang( null );
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
					ICFSecISOCtryLangObj selectedInstance = getJavaFXFocusAsISOCtryLang();
					invokeWhenChosen.choseISOCtryLang( selectedInstance );
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
		if( ( value == null ) || ( value instanceof ICFSecISOCtryLangObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFSecISOCtryLangObj" );
		}
		if( dataTable == null ) {
			return;
		}
	}

	public ICFSecISOCtryLangObj getJavaFXFocusAsISOCtryLang() {
		return( (ICFSecISOCtryLangObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsISOCtryLang( ICFSecISOCtryLangObj value ) {
		setJavaFXFocus( value );
	}

	public class ISOCtryLangByQualNameComparator
	implements Comparator<ICFSecISOCtryLangObj>
	{
		public ISOCtryLangByQualNameComparator() {
		}

		public int compare( ICFSecISOCtryLangObj lhs, ICFSecISOCtryLangObj rhs ) {
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

	protected ISOCtryLangByQualNameComparator compareISOCtryLangByQualName = new ISOCtryLangByQualNameComparator();

	public Collection<ICFSecISOCtryLangObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFSecISOCtryLangObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfISOCtryLang = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFSecISOCtryLangObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfISOCtryLang.add( iter.next() );
				}
				observableListOfISOCtryLang.sort( compareISOCtryLangByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfISOCtryLang );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	public ICFSecISOCtryObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFSecISOCtryObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFSecISOCtryLangObj selectedObj = getJavaFXFocusAsISOCtryLang();
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

