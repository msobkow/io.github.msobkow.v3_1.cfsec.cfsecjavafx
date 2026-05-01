// Description: Java 25 JavaFX Display Element Factory for SecTentRole.

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
 *	CFSecJavaFXSecTentRoleFactory JavaFX Display Element Factory
 *	for SecTentRole.
 */
public class CFSecJavaFXSecTentRoleFactory
implements ICFSecJavaFXSecTentRoleFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecTentRoleFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus ) {
		CFSecJavaFXSecTentRoleAttrPane retnew = new CFSecJavaFXSecTentRoleAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecSecSysGrpObj argContainer,
		ICFSecSecTentRoleObj argFocus,
		Collection<ICFSecSecTentRoleObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecTentRoleListPane retnew = new CFSecJavaFXSecTentRoleListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecTentRoleObj argFocus,
		ICFSecSecSysGrpObj argContainer,
		Collection<ICFSecSecTentRoleObj> argDataCollection,
		ICFSecJavaFXSecTentRoleChosen whenChosen )
	{
		CFSecJavaFXSecTentRolePickerPane retnew = new CFSecJavaFXSecTentRolePickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus ) {
		CFSecJavaFXSecTentRoleEltTabPane retnew = new CFSecJavaFXSecTentRoleEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus ) {
		CFSecJavaFXSecTentRoleAddPane retnew = new CFSecJavaFXSecTentRoleAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus ) {
		CFSecJavaFXSecTentRoleViewEditPane retnew = new CFSecJavaFXSecTentRoleViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecTentRoleAskDeleteForm retnew = new CFSecJavaFXSecTentRoleAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecTentRoleObj argFocus,
		ICFSecSecSysGrpObj argContainer,
		Collection<ICFSecSecTentRoleObj> argDataCollection,
		ICFSecJavaFXSecTentRoleChosen whenChosen )
	{
		CFSecJavaFXSecTentRolePickerForm retnew = new CFSecJavaFXSecTentRolePickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecTentRoleAddForm retnew = new CFSecJavaFXSecTentRoleAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecTentRoleObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecTentRoleViewEditForm retnew = new CFSecJavaFXSecTentRoleViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
