// Description: Java 25 JavaFX Display Element Factory for SecTentGrpMemb.

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
 *	CFSecJavaFXSecTentGrpMembFactory JavaFX Display Element Factory
 *	for SecTentGrpMemb.
 */
public class CFSecJavaFXSecTentGrpMembFactory
implements ICFSecJavaFXSecTentGrpMembFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecTentGrpMembFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus ) {
		CFSecJavaFXSecTentGrpMembAttrPane retnew = new CFSecJavaFXSecTentGrpMembAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecSecTentGrpObj argContainer,
		ICFSecSecTentGrpMembObj argFocus,
		ICFSecJavaFXSecTentGrpMembPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecTentGrpMembListPane retnew = new CFSecJavaFXSecTentGrpMembListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecTentGrpMembObj argFocus,
		ICFSecSecTentGrpObj argContainer,
		ICFSecJavaFXSecTentGrpMembPageCallback argPageCallback,
		ICFSecJavaFXSecTentGrpMembChosen whenChosen )
	{
		CFSecJavaFXSecTentGrpMembPickerPane retnew = new CFSecJavaFXSecTentGrpMembPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus ) {
		CFSecJavaFXSecTentGrpMembEltTabPane retnew = new CFSecJavaFXSecTentGrpMembEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus ) {
		CFSecJavaFXSecTentGrpMembAddPane retnew = new CFSecJavaFXSecTentGrpMembAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus ) {
		CFSecJavaFXSecTentGrpMembViewEditPane retnew = new CFSecJavaFXSecTentGrpMembViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecTentGrpMembAskDeleteForm retnew = new CFSecJavaFXSecTentGrpMembAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecTentGrpMembObj argFocus,
		ICFSecSecTentGrpObj argContainer,
		ICFSecJavaFXSecTentGrpMembPageCallback argPageCallback,
		ICFSecJavaFXSecTentGrpMembChosen whenChosen )
	{
		CFSecJavaFXSecTentGrpMembPickerForm retnew = new CFSecJavaFXSecTentGrpMembPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecTentGrpMembAddForm retnew = new CFSecJavaFXSecTentGrpMembAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecTentGrpMembObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecTentGrpMembViewEditForm retnew = new CFSecJavaFXSecTentGrpMembViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
