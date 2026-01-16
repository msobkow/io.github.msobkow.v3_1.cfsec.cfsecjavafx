// Description: Java 25 JavaFX Display Element Factory for ISOTZone.

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
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.inz.Inz;
import io.github.msobkow.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXISOTZoneFactory JavaFX Display Element Factory
 *	for ISOTZone.
 */
public class CFSecJavaFXISOTZoneFactory
implements ICFSecJavaFXISOTZoneFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXISOTZoneFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecISOTZoneObj argFocus ) {
		CFSecJavaFXISOTZoneAttrPane retnew = new CFSecJavaFXISOTZoneAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecISOTZoneObj argFocus,
		Collection<ICFSecISOTZoneObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXISOTZoneListPane retnew = new CFSecJavaFXISOTZoneListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecISOTZoneObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecISOTZoneObj> argDataCollection,
		ICFSecJavaFXISOTZoneChosen whenChosen )
	{
		CFSecJavaFXISOTZonePickerPane retnew = new CFSecJavaFXISOTZonePickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecISOTZoneObj argFocus ) {
		CFSecJavaFXISOTZoneEltTabPane retnew = new CFSecJavaFXISOTZoneEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecISOTZoneObj argFocus ) {
		CFSecJavaFXISOTZoneAddPane retnew = new CFSecJavaFXISOTZoneAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecISOTZoneObj argFocus ) {
		CFSecJavaFXISOTZoneViewEditPane retnew = new CFSecJavaFXISOTZoneViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecISOTZoneObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXISOTZoneAskDeleteForm retnew = new CFSecJavaFXISOTZoneAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXISOTZoneFinderForm retnew = new CFSecJavaFXISOTZoneFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecISOTZoneObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecISOTZoneObj> argDataCollection,
		ICFSecJavaFXISOTZoneChosen whenChosen )
	{
		CFSecJavaFXISOTZonePickerForm retnew = new CFSecJavaFXISOTZonePickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecISOTZoneObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXISOTZoneAddForm retnew = new CFSecJavaFXISOTZoneAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecISOTZoneObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXISOTZoneViewEditForm retnew = new CFSecJavaFXISOTZoneViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
