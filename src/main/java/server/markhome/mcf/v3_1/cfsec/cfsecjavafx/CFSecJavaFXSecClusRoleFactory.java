// Description: Java 25 JavaFX Display Element Factory for SecClusRole.

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
 *	CFSecJavaFXSecClusRoleFactory JavaFX Display Element Factory
 *	for SecClusRole.
 */
public class CFSecJavaFXSecClusRoleFactory
implements ICFSecJavaFXSecClusRoleFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecClusRoleFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus ) {
		CFSecJavaFXSecClusRoleAttrPane retnew = new CFSecJavaFXSecClusRoleAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecClusRoleObj argFocus,
		Collection<ICFSecSecClusRoleObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecClusRoleListPane retnew = new CFSecJavaFXSecClusRoleListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecClusRoleObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecClusRoleObj> argDataCollection,
		ICFSecJavaFXSecClusRoleChosen whenChosen )
	{
		CFSecJavaFXSecClusRolePickerPane retnew = new CFSecJavaFXSecClusRolePickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus ) {
		CFSecJavaFXSecClusRoleEltTabPane retnew = new CFSecJavaFXSecClusRoleEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus ) {
		CFSecJavaFXSecClusRoleAddPane retnew = new CFSecJavaFXSecClusRoleAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus ) {
		CFSecJavaFXSecClusRoleViewEditPane retnew = new CFSecJavaFXSecClusRoleViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecClusRoleAskDeleteForm retnew = new CFSecJavaFXSecClusRoleAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecClusRoleFinderForm retnew = new CFSecJavaFXSecClusRoleFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecClusRoleObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecClusRoleObj> argDataCollection,
		ICFSecJavaFXSecClusRoleChosen whenChosen )
	{
		CFSecJavaFXSecClusRolePickerForm retnew = new CFSecJavaFXSecClusRolePickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecClusRoleAddForm retnew = new CFSecJavaFXSecClusRoleAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecClusRoleObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecClusRoleViewEditForm retnew = new CFSecJavaFXSecClusRoleViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
