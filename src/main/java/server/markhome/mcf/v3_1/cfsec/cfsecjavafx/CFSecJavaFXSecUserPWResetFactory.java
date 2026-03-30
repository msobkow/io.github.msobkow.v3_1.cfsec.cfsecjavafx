// Description: Java 25 JavaFX Display Element Factory for SecUserPWReset.

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
 *	CFSecJavaFXSecUserPWResetFactory JavaFX Display Element Factory
 *	for SecUserPWReset.
 */
public class CFSecJavaFXSecUserPWResetFactory
implements ICFSecJavaFXSecUserPWResetFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecUserPWResetFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus ) {
		CFSecJavaFXSecUserPWResetAttrPane retnew = new CFSecJavaFXSecUserPWResetAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecSecUserObj argContainer,
		ICFSecSecUserPWResetObj argFocus,
		ICFSecJavaFXSecUserPWResetPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecUserPWResetListPane retnew = new CFSecJavaFXSecUserPWResetListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argPageCallback,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecUserPWResetObj argFocus,
		ICFSecSecUserObj argContainer,
		ICFSecJavaFXSecUserPWResetPageCallback argPageCallback,
		ICFSecJavaFXSecUserPWResetChosen whenChosen )
	{
		CFSecJavaFXSecUserPWResetPickerPane retnew = new CFSecJavaFXSecUserPWResetPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus ) {
		CFSecJavaFXSecUserPWResetEltTabPane retnew = new CFSecJavaFXSecUserPWResetEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus ) {
		CFSecJavaFXSecUserPWResetAddPane retnew = new CFSecJavaFXSecUserPWResetAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus ) {
		CFSecJavaFXSecUserPWResetViewEditPane retnew = new CFSecJavaFXSecUserPWResetViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecUserPWResetAskDeleteForm retnew = new CFSecJavaFXSecUserPWResetAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecUserPWResetObj argFocus,
		ICFSecSecUserObj argContainer,
		ICFSecJavaFXSecUserPWResetPageCallback argPageCallback,
		ICFSecJavaFXSecUserPWResetChosen whenChosen )
	{
		CFSecJavaFXSecUserPWResetPickerForm retnew = new CFSecJavaFXSecUserPWResetPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argPageCallback,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecUserPWResetAddForm retnew = new CFSecJavaFXSecUserPWResetAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecUserPWResetObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecUserPWResetViewEditForm retnew = new CFSecJavaFXSecUserPWResetViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
