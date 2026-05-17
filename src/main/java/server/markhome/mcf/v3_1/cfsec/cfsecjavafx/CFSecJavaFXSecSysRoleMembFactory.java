// Description: Java 25 JavaFX Display Element Factory for SecSysRoleMemb.

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
 *	CFSecJavaFXSecSysRoleMembFactory JavaFX Display Element Factory
 *	for SecSysRoleMemb.
 */
public class CFSecJavaFXSecSysRoleMembFactory
implements ICFSecJavaFXSecSysRoleMembFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecSysRoleMembFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus ) {
		CFSecJavaFXSecSysRoleMembAttrPane retnew = new CFSecJavaFXSecSysRoleMembAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecSecSysRoleObj argContainer,
		ICFSecSecSysRoleMembObj argFocus,
		ICFSecJavaFXSecSysRoleMembPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecSysRoleMembListPane retnew = new CFSecJavaFXSecSysRoleMembListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecSysRoleMembObj argFocus,
		ICFSecSecSysRoleObj argContainer,
		ICFSecJavaFXSecSysRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecSysRoleMembChosen whenChosen )
	{
		CFSecJavaFXSecSysRoleMembPickerPane retnew = new CFSecJavaFXSecSysRoleMembPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus ) {
		CFSecJavaFXSecSysRoleMembEltTabPane retnew = new CFSecJavaFXSecSysRoleMembEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus ) {
		CFSecJavaFXSecSysRoleMembAddPane retnew = new CFSecJavaFXSecSysRoleMembAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus ) {
		CFSecJavaFXSecSysRoleMembViewEditPane retnew = new CFSecJavaFXSecSysRoleMembViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecSysRoleMembAskDeleteForm retnew = new CFSecJavaFXSecSysRoleMembAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecSysRoleMembObj argFocus,
		ICFSecSecSysRoleObj argContainer,
		ICFSecJavaFXSecSysRoleMembPageCallback argPageCallback,
		ICFSecJavaFXSecSysRoleMembChosen whenChosen )
	{
		CFSecJavaFXSecSysRoleMembPickerForm retnew = new CFSecJavaFXSecSysRoleMembPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecSysRoleMembAddForm retnew = new CFSecJavaFXSecSysRoleMembAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecSysRoleMembObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecSysRoleMembViewEditForm retnew = new CFSecJavaFXSecSysRoleMembViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
