// Description: Java 25 JavaFX Display Element Factory for SecTentRoleMemb.

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
 *	CFSecJavaFXSecTentRoleMembFactory JavaFX Display Element Factory
 *	for SecTentRoleMemb.
 */
public class CFSecJavaFXSecTentRoleMembFactory
implements ICFSecJavaFXSecTentRoleMembFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecTentRoleMembFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus ) {
		CFSecJavaFXSecTentRoleMembAttrPane retnew = new CFSecJavaFXSecTentRoleMembAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecSecTentRoleObj argContainer,
		ICFSecSecTentRoleMembObj argFocus,
		ICFSecJavaFXSecTentRoleMembPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecTentRoleMembListPane retnew = new CFSecJavaFXSecTentRoleMembListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecTentRoleMembObj argFocus,
		ICFSecSecTentRoleObj argContainer,
		ICFSecJavaFXSecTentRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecTentRoleMembChosen whenChosen )
	{
		CFSecJavaFXSecTentRoleMembPickerPane retnew = new CFSecJavaFXSecTentRoleMembPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus ) {
		CFSecJavaFXSecTentRoleMembEltTabPane retnew = new CFSecJavaFXSecTentRoleMembEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus ) {
		CFSecJavaFXSecTentRoleMembAddPane retnew = new CFSecJavaFXSecTentRoleMembAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus ) {
		CFSecJavaFXSecTentRoleMembViewEditPane retnew = new CFSecJavaFXSecTentRoleMembViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecTentRoleMembAskDeleteForm retnew = new CFSecJavaFXSecTentRoleMembAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecTentRoleMembObj argFocus,
		ICFSecSecTentRoleObj argContainer,
		ICFSecJavaFXSecTentRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecTentRoleMembChosen whenChosen )
	{
		CFSecJavaFXSecTentRoleMembPickerForm retnew = new CFSecJavaFXSecTentRoleMembPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecTentRoleMembAddForm retnew = new CFSecJavaFXSecTentRoleMembAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecTentRoleMembObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecTentRoleMembViewEditForm retnew = new CFSecJavaFXSecTentRoleMembViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
