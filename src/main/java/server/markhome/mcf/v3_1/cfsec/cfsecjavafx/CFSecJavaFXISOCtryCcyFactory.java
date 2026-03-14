// Description: Java 25 JavaFX Display Element Factory for ISOCtryCcy.

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
 *	CFSecJavaFXISOCtryCcyFactory JavaFX Display Element Factory
 *	for ISOCtryCcy.
 */
public class CFSecJavaFXISOCtryCcyFactory
implements ICFSecJavaFXISOCtryCcyFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXISOCtryCcyFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus ) {
		CFSecJavaFXISOCtryCcyAttrPane retnew = new CFSecJavaFXISOCtryCcyAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecISOCtryObj argContainer,
		ICFSecISOCtryCcyObj argFocus,
		Collection<ICFSecISOCtryCcyObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXISOCtryCcyListPane retnew = new CFSecJavaFXISOCtryCcyListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecISOCtryCcyObj argFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryCcyObj> argDataCollection,
		ICFSecJavaFXISOCtryCcyChosen whenChosen )
	{
		CFSecJavaFXISOCtryCcyPickerPane retnew = new CFSecJavaFXISOCtryCcyPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus ) {
		CFSecJavaFXISOCtryCcyEltTabPane retnew = new CFSecJavaFXISOCtryCcyEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus ) {
		CFSecJavaFXISOCtryCcyAddPane retnew = new CFSecJavaFXISOCtryCcyAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus ) {
		CFSecJavaFXISOCtryCcyViewEditPane retnew = new CFSecJavaFXISOCtryCcyViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXISOCtryCcyAskDeleteForm retnew = new CFSecJavaFXISOCtryCcyAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecISOCtryCcyObj argFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryCcyObj> argDataCollection,
		ICFSecJavaFXISOCtryCcyChosen whenChosen )
	{
		CFSecJavaFXISOCtryCcyPickerForm retnew = new CFSecJavaFXISOCtryCcyPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXISOCtryCcyAddForm retnew = new CFSecJavaFXISOCtryCcyAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecISOCtryCcyObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXISOCtryCcyViewEditForm retnew = new CFSecJavaFXISOCtryCcyViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
