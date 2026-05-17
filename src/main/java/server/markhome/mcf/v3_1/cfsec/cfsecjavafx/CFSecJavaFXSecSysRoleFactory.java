// Description: Java 25 JavaFX Display Element Factory for SecSysRole.

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
 *	CFSecJavaFXSecSysRoleFactory JavaFX Display Element Factory
 *	for SecSysRole.
 */
public class CFSecJavaFXSecSysRoleFactory
implements ICFSecJavaFXSecSysRoleFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecSysRoleFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus ) {
		CFSecJavaFXSecSysRoleAttrPane retnew = new CFSecJavaFXSecSysRoleAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecSysRoleObj argFocus,
		Collection<ICFSecSecSysRoleObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecSysRoleListPane retnew = new CFSecJavaFXSecSysRoleListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecSysRoleObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecSysRoleObj> argDataCollection,
		ICFSecJavaFXSecSysRoleChosen whenChosen )
	{
		CFSecJavaFXSecSysRolePickerPane retnew = new CFSecJavaFXSecSysRolePickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus ) {
		CFSecJavaFXSecSysRoleEltTabPane retnew = new CFSecJavaFXSecSysRoleEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus ) {
		CFSecJavaFXSecSysRoleAddPane retnew = new CFSecJavaFXSecSysRoleAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus ) {
		CFSecJavaFXSecSysRoleViewEditPane retnew = new CFSecJavaFXSecSysRoleViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecSysRoleAskDeleteForm retnew = new CFSecJavaFXSecSysRoleAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecSysRoleFinderForm retnew = new CFSecJavaFXSecSysRoleFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecSysRoleObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecSysRoleObj> argDataCollection,
		ICFSecJavaFXSecSysRoleChosen whenChosen )
	{
		CFSecJavaFXSecSysRolePickerForm retnew = new CFSecJavaFXSecSysRolePickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecSysRoleAddForm retnew = new CFSecJavaFXSecSysRoleAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecSysRoleObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecSysRoleViewEditForm retnew = new CFSecJavaFXSecSysRoleViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
