// Description: Java 25 JavaFX Display Element Factory for SecTentGrp.

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
 *	CFSecJavaFXSecTentGrpFactory JavaFX Display Element Factory
 *	for SecTentGrp.
 */
public class CFSecJavaFXSecTentGrpFactory
implements ICFSecJavaFXSecTentGrpFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecTentGrpFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus ) {
		CFSecJavaFXSecTentGrpAttrPane retnew = new CFSecJavaFXSecTentGrpAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecTentGrpObj argFocus,
		Collection<ICFSecSecTentGrpObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecTentGrpListPane retnew = new CFSecJavaFXSecTentGrpListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecTentGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecTentGrpObj> argDataCollection,
		ICFSecJavaFXSecTentGrpChosen whenChosen )
	{
		CFSecJavaFXSecTentGrpPickerPane retnew = new CFSecJavaFXSecTentGrpPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus ) {
		CFSecJavaFXSecTentGrpEltTabPane retnew = new CFSecJavaFXSecTentGrpEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus ) {
		CFSecJavaFXSecTentGrpAddPane retnew = new CFSecJavaFXSecTentGrpAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus ) {
		CFSecJavaFXSecTentGrpViewEditPane retnew = new CFSecJavaFXSecTentGrpViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecTentGrpAskDeleteForm retnew = new CFSecJavaFXSecTentGrpAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecTentGrpFinderForm retnew = new CFSecJavaFXSecTentGrpFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecTentGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecTentGrpObj> argDataCollection,
		ICFSecJavaFXSecTentGrpChosen whenChosen )
	{
		CFSecJavaFXSecTentGrpPickerForm retnew = new CFSecJavaFXSecTentGrpPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecTentGrpAddForm retnew = new CFSecJavaFXSecTentGrpAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecTentGrpObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecTentGrpViewEditForm retnew = new CFSecJavaFXSecTentGrpViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
