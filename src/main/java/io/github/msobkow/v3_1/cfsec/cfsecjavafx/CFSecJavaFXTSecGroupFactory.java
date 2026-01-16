// Description: Java 25 JavaFX Display Element Factory for TSecGroup.

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
 *	CFSecJavaFXTSecGroupFactory JavaFX Display Element Factory
 *	for TSecGroup.
 */
public class CFSecJavaFXTSecGroupFactory
implements ICFSecJavaFXTSecGroupFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXTSecGroupFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecTSecGroupObj argFocus ) {
		CFSecJavaFXTSecGroupAttrPane retnew = new CFSecJavaFXTSecGroupAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecTenantObj argContainer,
		ICFSecTSecGroupObj argFocus,
		Collection<ICFSecTSecGroupObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXTSecGroupListPane retnew = new CFSecJavaFXTSecGroupListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecTSecGroupObj argFocus,
		ICFSecTenantObj argContainer,
		Collection<ICFSecTSecGroupObj> argDataCollection,
		ICFSecJavaFXTSecGroupChosen whenChosen )
	{
		CFSecJavaFXTSecGroupPickerPane retnew = new CFSecJavaFXTSecGroupPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecTSecGroupObj argFocus ) {
		CFSecJavaFXTSecGroupEltTabPane retnew = new CFSecJavaFXTSecGroupEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecTSecGroupObj argFocus ) {
		CFSecJavaFXTSecGroupAddPane retnew = new CFSecJavaFXTSecGroupAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecTSecGroupObj argFocus ) {
		CFSecJavaFXTSecGroupViewEditPane retnew = new CFSecJavaFXTSecGroupViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecTSecGroupObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXTSecGroupAskDeleteForm retnew = new CFSecJavaFXTSecGroupAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXTSecGroupFinderForm retnew = new CFSecJavaFXTSecGroupFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecTSecGroupObj argFocus,
		ICFSecTenantObj argContainer,
		Collection<ICFSecTSecGroupObj> argDataCollection,
		ICFSecJavaFXTSecGroupChosen whenChosen )
	{
		CFSecJavaFXTSecGroupPickerForm retnew = new CFSecJavaFXTSecGroupPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecTSecGroupObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXTSecGroupAddForm retnew = new CFSecJavaFXTSecGroupAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecTSecGroupObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXTSecGroupViewEditForm retnew = new CFSecJavaFXTSecGroupViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
