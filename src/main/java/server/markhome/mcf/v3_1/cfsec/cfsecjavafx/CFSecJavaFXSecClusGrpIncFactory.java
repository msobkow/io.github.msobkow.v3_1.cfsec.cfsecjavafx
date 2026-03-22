// Description: Java 25 JavaFX Display Element Factory for SecClusGrpInc.

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
 *	CFSecJavaFXSecClusGrpIncFactory JavaFX Display Element Factory
 *	for SecClusGrpInc.
 */
public class CFSecJavaFXSecClusGrpIncFactory
implements ICFSecJavaFXSecClusGrpIncFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecClusGrpIncFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus ) {
		CFSecJavaFXSecClusGrpIncAttrPane retnew = new CFSecJavaFXSecClusGrpIncAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecClusGrpIncObj argFocus,
		ICFSecJavaFXSecClusGrpIncPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecClusGrpIncListPane retnew = new CFSecJavaFXSecClusGrpIncListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecClusGrpIncObj argFocus,
		ICFLibAnyObj argContainer,
		ICFSecJavaFXSecClusGrpIncPageCallback argPageCallback,
		ICFSecJavaFXSecClusGrpIncChosen whenChosen )
	{
		CFSecJavaFXSecClusGrpIncPickerPane retnew = new CFSecJavaFXSecClusGrpIncPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus ) {
		CFSecJavaFXSecClusGrpIncEltTabPane retnew = new CFSecJavaFXSecClusGrpIncEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus ) {
		CFSecJavaFXSecClusGrpIncAddPane retnew = new CFSecJavaFXSecClusGrpIncAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus ) {
		CFSecJavaFXSecClusGrpIncViewEditPane retnew = new CFSecJavaFXSecClusGrpIncViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecClusGrpIncAskDeleteForm retnew = new CFSecJavaFXSecClusGrpIncAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecClusGrpIncFinderForm retnew = new CFSecJavaFXSecClusGrpIncFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecClusGrpIncObj argFocus,
		ICFLibAnyObj argContainer,
		ICFSecJavaFXSecClusGrpIncPageCallback argPageCallback,
		ICFSecJavaFXSecClusGrpIncChosen whenChosen )
	{
		CFSecJavaFXSecClusGrpIncPickerForm retnew = new CFSecJavaFXSecClusGrpIncPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecClusGrpIncAddForm retnew = new CFSecJavaFXSecClusGrpIncAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecClusGrpIncObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecClusGrpIncViewEditForm retnew = new CFSecJavaFXSecClusGrpIncViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
