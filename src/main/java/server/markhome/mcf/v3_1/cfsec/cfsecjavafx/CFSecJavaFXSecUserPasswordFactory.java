// Description: Java 25 JavaFX Display Element Factory for SecUserPassword.

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
 *	CFSecJavaFXSecUserPasswordFactory JavaFX Display Element Factory
 *	for SecUserPassword.
 */
public class CFSecJavaFXSecUserPasswordFactory
implements ICFSecJavaFXSecUserPasswordFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecUserPasswordFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus ) {
		CFSecJavaFXSecUserPasswordAttrPane retnew = new CFSecJavaFXSecUserPasswordAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecUserPasswordObj argFocus,
		Collection<ICFSecSecUserPasswordObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecUserPasswordListPane retnew = new CFSecJavaFXSecUserPasswordListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecUserPasswordObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecUserPasswordObj> argDataCollection,
		ICFSecJavaFXSecUserPasswordChosen whenChosen )
	{
		CFSecJavaFXSecUserPasswordPickerPane retnew = new CFSecJavaFXSecUserPasswordPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus ) {
		CFSecJavaFXSecUserPasswordEltTabPane retnew = new CFSecJavaFXSecUserPasswordEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus ) {
		CFSecJavaFXSecUserPasswordAddPane retnew = new CFSecJavaFXSecUserPasswordAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus ) {
		CFSecJavaFXSecUserPasswordViewEditPane retnew = new CFSecJavaFXSecUserPasswordViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecUserPasswordAskDeleteForm retnew = new CFSecJavaFXSecUserPasswordAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecUserPasswordFinderForm retnew = new CFSecJavaFXSecUserPasswordFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecUserPasswordObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecUserPasswordObj> argDataCollection,
		ICFSecJavaFXSecUserPasswordChosen whenChosen )
	{
		CFSecJavaFXSecUserPasswordPickerForm retnew = new CFSecJavaFXSecUserPasswordPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecUserPasswordAddForm retnew = new CFSecJavaFXSecUserPasswordAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecUserPasswordObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecUserPasswordViewEditForm retnew = new CFSecJavaFXSecUserPasswordViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
