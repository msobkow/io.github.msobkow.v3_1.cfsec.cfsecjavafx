// Description: Java 25 JavaFX Display Element Factory for ISOCtryLang.

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
 *	CFSecJavaFXISOCtryLangFactory JavaFX Display Element Factory
 *	for ISOCtryLang.
 */
public class CFSecJavaFXISOCtryLangFactory
implements ICFSecJavaFXISOCtryLangFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXISOCtryLangFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus ) {
		CFSecJavaFXISOCtryLangAttrPane retnew = new CFSecJavaFXISOCtryLangAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecISOCtryObj argContainer,
		ICFSecISOCtryLangObj argFocus,
		Collection<ICFSecISOCtryLangObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXISOCtryLangListPane retnew = new CFSecJavaFXISOCtryLangListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecISOCtryLangObj argFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryLangObj> argDataCollection,
		ICFSecJavaFXISOCtryLangChosen whenChosen )
	{
		CFSecJavaFXISOCtryLangPickerPane retnew = new CFSecJavaFXISOCtryLangPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus ) {
		CFSecJavaFXISOCtryLangEltTabPane retnew = new CFSecJavaFXISOCtryLangEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus ) {
		CFSecJavaFXISOCtryLangAddPane retnew = new CFSecJavaFXISOCtryLangAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus ) {
		CFSecJavaFXISOCtryLangViewEditPane retnew = new CFSecJavaFXISOCtryLangViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXISOCtryLangAskDeleteForm retnew = new CFSecJavaFXISOCtryLangAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecISOCtryLangObj argFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryLangObj> argDataCollection,
		ICFSecJavaFXISOCtryLangChosen whenChosen )
	{
		CFSecJavaFXISOCtryLangPickerForm retnew = new CFSecJavaFXISOCtryLangPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXISOCtryLangAddForm retnew = new CFSecJavaFXISOCtryLangAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecISOCtryLangObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXISOCtryLangViewEditForm retnew = new CFSecJavaFXISOCtryLangViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
