// Description: Java 25 JavaFX Display Element Factory for SecClusGrp.

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
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;

/**
 *	CFSecJavaFXSecClusGrpFactory JavaFX Display Element Factory
 *	for SecClusGrp.
 */
public class CFSecJavaFXSecClusGrpFactory
implements ICFSecJavaFXSecClusGrpFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecClusGrpFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus ) {
		CFSecJavaFXSecClusGrpAttrPane retnew = new CFSecJavaFXSecClusGrpAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecClusGrpObj argFocus,
		Collection<ICFSecSecClusGrpObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecClusGrpListPane retnew = new CFSecJavaFXSecClusGrpListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecClusGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecClusGrpObj> argDataCollection,
		ICFSecJavaFXSecClusGrpChosen whenChosen )
	{
		CFSecJavaFXSecClusGrpPickerPane retnew = new CFSecJavaFXSecClusGrpPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus ) {
		CFSecJavaFXSecClusGrpEltTabPane retnew = new CFSecJavaFXSecClusGrpEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus ) {
		CFSecJavaFXSecClusGrpAddPane retnew = new CFSecJavaFXSecClusGrpAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus ) {
		CFSecJavaFXSecClusGrpViewEditPane retnew = new CFSecJavaFXSecClusGrpViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecClusGrpAskDeleteForm retnew = new CFSecJavaFXSecClusGrpAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecClusGrpFinderForm retnew = new CFSecJavaFXSecClusGrpFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecClusGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecClusGrpObj> argDataCollection,
		ICFSecJavaFXSecClusGrpChosen whenChosen )
	{
		CFSecJavaFXSecClusGrpPickerForm retnew = new CFSecJavaFXSecClusGrpPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecClusGrpAddForm retnew = new CFSecJavaFXSecClusGrpAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecClusGrpObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecClusGrpViewEditForm retnew = new CFSecJavaFXSecClusGrpViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
