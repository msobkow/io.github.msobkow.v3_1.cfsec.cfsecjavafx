// Description: Java 25 JavaFX Display Element Factory for SecSysGrp.

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
 *	CFSecJavaFXSecSysGrpFactory JavaFX Display Element Factory
 *	for SecSysGrp.
 */
public class CFSecJavaFXSecSysGrpFactory
implements ICFSecJavaFXSecSysGrpFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecSysGrpFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus ) {
		CFSecJavaFXSecSysGrpAttrPane retnew = new CFSecJavaFXSecSysGrpAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecSysGrpObj argFocus,
		Collection<ICFSecSecSysGrpObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecSysGrpListPane retnew = new CFSecJavaFXSecSysGrpListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecSysGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecSysGrpObj> argDataCollection,
		ICFSecJavaFXSecSysGrpChosen whenChosen )
	{
		CFSecJavaFXSecSysGrpPickerPane retnew = new CFSecJavaFXSecSysGrpPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus ) {
		CFSecJavaFXSecSysGrpEltTabPane retnew = new CFSecJavaFXSecSysGrpEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus ) {
		CFSecJavaFXSecSysGrpAddPane retnew = new CFSecJavaFXSecSysGrpAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus ) {
		CFSecJavaFXSecSysGrpViewEditPane retnew = new CFSecJavaFXSecSysGrpViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecSysGrpAskDeleteForm retnew = new CFSecJavaFXSecSysGrpAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecSysGrpFinderForm retnew = new CFSecJavaFXSecSysGrpFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecSysGrpObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecSysGrpObj> argDataCollection,
		ICFSecJavaFXSecSysGrpChosen whenChosen )
	{
		CFSecJavaFXSecSysGrpPickerForm retnew = new CFSecJavaFXSecSysGrpPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecSysGrpAddForm retnew = new CFSecJavaFXSecSysGrpAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecSysGrpObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecSysGrpViewEditForm retnew = new CFSecJavaFXSecSysGrpViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
