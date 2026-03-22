// Description: Java 25 JavaFX Display Element Factory for SecUserPWHistory.

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
 *	CFSecJavaFXSecUserPWHistoryFactory JavaFX Display Element Factory
 *	for SecUserPWHistory.
 */
public class CFSecJavaFXSecUserPWHistoryFactory
implements ICFSecJavaFXSecUserPWHistoryFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSecUserPWHistoryFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus ) {
		CFSecJavaFXSecUserPWHistoryAttrPane retnew = new CFSecJavaFXSecUserPWHistoryAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecUserPWHistoryObj argFocus,
		Collection<ICFSecSecUserPWHistoryObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSecUserPWHistoryListPane retnew = new CFSecJavaFXSecUserPWHistoryListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecUserPWHistoryObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecUserPWHistoryObj> argDataCollection,
		ICFSecJavaFXSecUserPWHistoryChosen whenChosen )
	{
		CFSecJavaFXSecUserPWHistoryPickerPane retnew = new CFSecJavaFXSecUserPWHistoryPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus ) {
		CFSecJavaFXSecUserPWHistoryEltTabPane retnew = new CFSecJavaFXSecUserPWHistoryEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus ) {
		CFSecJavaFXSecUserPWHistoryAddPane retnew = new CFSecJavaFXSecUserPWHistoryAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus ) {
		CFSecJavaFXSecUserPWHistoryViewEditPane retnew = new CFSecJavaFXSecUserPWHistoryViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSecUserPWHistoryAskDeleteForm retnew = new CFSecJavaFXSecUserPWHistoryAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSecUserPWHistoryFinderForm retnew = new CFSecJavaFXSecUserPWHistoryFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecUserPWHistoryObj argFocus,
		ICFLibAnyObj argContainer,
		Collection<ICFSecSecUserPWHistoryObj> argDataCollection,
		ICFSecJavaFXSecUserPWHistoryChosen whenChosen )
	{
		CFSecJavaFXSecUserPWHistoryPickerForm retnew = new CFSecJavaFXSecUserPWHistoryPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSecUserPWHistoryAddForm retnew = new CFSecJavaFXSecUserPWHistoryAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecUserPWHistoryObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSecUserPWHistoryViewEditForm retnew = new CFSecJavaFXSecUserPWHistoryViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
