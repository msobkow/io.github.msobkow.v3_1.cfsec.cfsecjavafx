// Description: Java 25 JavaFX Display Element Factory for SecClusRoleMemb.

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
 *	CFSecJavaFXSecClusRoleMembFactory JavaFX Display Element Factory
 *	for SecClusRoleMemb.
 */
public class CFSecJavaFXSecClusRoleMembFactory
implements ICFSecJavaFXSecClusRoleMembFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecClusRoleMembFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus ) {
		CFSecJavaFXSecClusRoleMembAttrPane retnew = new CFSecJavaFXSecClusRoleMembAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecClusRoleMembObj argFocus,
		ICFSecJavaFXSecClusRoleMembPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecClusRoleMembListPane retnew = new CFSecJavaFXSecClusRoleMembListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecClusRoleMembObj argFocus,
		ICFLibAnyObj argContainer,
		ICFSecJavaFXSecClusRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecClusRoleMembChosen whenChosen )
	{
		CFSecJavaFXSecClusRoleMembPickerPane retnew = new CFSecJavaFXSecClusRoleMembPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus ) {
		CFSecJavaFXSecClusRoleMembEltTabPane retnew = new CFSecJavaFXSecClusRoleMembEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus ) {
		CFSecJavaFXSecClusRoleMembAddPane retnew = new CFSecJavaFXSecClusRoleMembAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus ) {
		CFSecJavaFXSecClusRoleMembViewEditPane retnew = new CFSecJavaFXSecClusRoleMembViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecClusRoleMembAskDeleteForm retnew = new CFSecJavaFXSecClusRoleMembAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecClusRoleMembFinderForm retnew = new CFSecJavaFXSecClusRoleMembFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecClusRoleMembObj argFocus,
		ICFLibAnyObj argContainer,
		ICFSecJavaFXSecClusRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecClusRoleMembChosen whenChosen )
	{
		CFSecJavaFXSecClusRoleMembPickerForm retnew = new CFSecJavaFXSecClusRoleMembPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecClusRoleMembAddForm retnew = new CFSecJavaFXSecClusRoleMembAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecClusRoleMembObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecClusRoleMembViewEditForm retnew = new CFSecJavaFXSecClusRoleMembViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
