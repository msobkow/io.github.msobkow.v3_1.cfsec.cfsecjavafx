// Description: Java 25 JavaFX Display Element Factory for TSecGrpInc.

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
 *	CFSecJavaFXTSecGrpIncFactory JavaFX Display Element Factory
 *	for TSecGrpInc.
 */
public class CFSecJavaFXTSecGrpIncFactory
implements ICFSecJavaFXTSecGrpIncFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXTSecGrpIncFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus ) {
		CFSecJavaFXTSecGrpIncAttrPane retnew = new CFSecJavaFXTSecGrpIncAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecTSecGroupObj argContainer,
		ICFSecTSecGrpIncObj argFocus,
		ICFSecJavaFXTSecGrpIncPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXTSecGrpIncListPane retnew = new CFSecJavaFXTSecGrpIncListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecTSecGrpIncObj argFocus,
		ICFSecTSecGroupObj argContainer,
		ICFSecJavaFXTSecGrpIncPageCallback argPageCallback,
		ICFSecJavaFXTSecGrpIncChosen whenChosen )
	{
		CFSecJavaFXTSecGrpIncPickerPane retnew = new CFSecJavaFXTSecGrpIncPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus ) {
		CFSecJavaFXTSecGrpIncEltTabPane retnew = new CFSecJavaFXTSecGrpIncEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus ) {
		CFSecJavaFXTSecGrpIncAddPane retnew = new CFSecJavaFXTSecGrpIncAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus ) {
		CFSecJavaFXTSecGrpIncViewEditPane retnew = new CFSecJavaFXTSecGrpIncViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXTSecGrpIncAskDeleteForm retnew = new CFSecJavaFXTSecGrpIncAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecTSecGrpIncObj argFocus,
		ICFSecTSecGroupObj argContainer,
		ICFSecJavaFXTSecGrpIncPageCallback argPageCallback,
		ICFSecJavaFXTSecGrpIncChosen whenChosen )
	{
		CFSecJavaFXTSecGrpIncPickerForm retnew = new CFSecJavaFXTSecGrpIncPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXTSecGrpIncAddForm retnew = new CFSecJavaFXTSecGrpIncAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecTSecGrpIncObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXTSecGrpIncViewEditForm retnew = new CFSecJavaFXTSecGrpIncViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
