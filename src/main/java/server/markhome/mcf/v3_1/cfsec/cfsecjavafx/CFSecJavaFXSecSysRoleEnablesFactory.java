// Description: Java 25 JavaFX Display Element Factory for SecSysRoleEnables.

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
 *	CFSecJavaFXSecSysRoleEnablesFactory JavaFX Display Element Factory
 *	for SecSysRoleEnables.
 */
public class CFSecJavaFXSecSysRoleEnablesFactory
implements ICFSecJavaFXSecSysRoleEnablesFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecSysRoleEnablesFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus ) {
		CFSecJavaFXSecSysRoleEnablesAttrPane retnew = new CFSecJavaFXSecSysRoleEnablesAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecSecSysRoleObj argContainer,
		ICFSecSecSysRoleEnablesObj argFocus,
		ICFSecJavaFXSecSysRoleEnablesPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecSysRoleEnablesListPane retnew = new CFSecJavaFXSecSysRoleEnablesListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecSysRoleEnablesObj argFocus,
		ICFSecSecSysRoleObj argContainer,
		ICFSecJavaFXSecSysRoleEnablesPageCallback argPageCallback,
		ICFSecJavaFXSecSysRoleEnablesChosen whenChosen )
	{
		CFSecJavaFXSecSysRoleEnablesPickerPane retnew = new CFSecJavaFXSecSysRoleEnablesPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus ) {
		CFSecJavaFXSecSysRoleEnablesEltTabPane retnew = new CFSecJavaFXSecSysRoleEnablesEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus ) {
		CFSecJavaFXSecSysRoleEnablesAddPane retnew = new CFSecJavaFXSecSysRoleEnablesAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus ) {
		CFSecJavaFXSecSysRoleEnablesViewEditPane retnew = new CFSecJavaFXSecSysRoleEnablesViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecSysRoleEnablesAskDeleteForm retnew = new CFSecJavaFXSecSysRoleEnablesAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecSysRoleEnablesObj argFocus,
		ICFSecSecSysRoleObj argContainer,
		ICFSecJavaFXSecSysRoleEnablesPageCallback argPageCallback,
		ICFSecJavaFXSecSysRoleEnablesChosen whenChosen )
	{
		CFSecJavaFXSecSysRoleEnablesPickerForm retnew = new CFSecJavaFXSecSysRoleEnablesPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecSysRoleEnablesAddForm retnew = new CFSecJavaFXSecSysRoleEnablesAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecSysRoleEnablesObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecSysRoleEnablesViewEditForm retnew = new CFSecJavaFXSecSysRoleEnablesViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
