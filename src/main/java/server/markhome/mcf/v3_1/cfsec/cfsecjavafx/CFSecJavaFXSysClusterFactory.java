// Description: Java 25 JavaFX Display Element Factory for SysCluster.

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
 *	CFSecJavaFXSysClusterFactory JavaFX Display Element Factory
 *	for SysCluster.
 */
public class CFSecJavaFXSysClusterFactory
implements ICFSecJavaFXSysClusterFactory
{
	protected ICFSecJavaFXSchema javafxSchema = null;

	public CFSecJavaFXSysClusterFactory( ICFSecJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSysClusterObj argFocus ) {
		CFSecJavaFXSysClusterAttrPane retnew = new CFSecJavaFXSysClusterAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecClusterObj argContainer,
		ICFSecSysClusterObj argFocus,
		Collection<ICFSecSysClusterObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFSecJavaFXSysClusterListPane retnew = new CFSecJavaFXSysClusterListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSysClusterObj argFocus,
		ICFSecClusterObj argContainer,
		Collection<ICFSecSysClusterObj> argDataCollection,
		ICFSecJavaFXSysClusterChosen whenChosen )
	{
		CFSecJavaFXSysClusterPickerPane retnew = new CFSecJavaFXSysClusterPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFSecSysClusterObj argFocus ) {
		CFSecJavaFXSysClusterEltTabPane retnew = new CFSecJavaFXSysClusterEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFSecSysClusterObj argFocus ) {
		CFSecJavaFXSysClusterAddPane retnew = new CFSecJavaFXSysClusterAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFSecSysClusterObj argFocus ) {
		CFSecJavaFXSysClusterViewEditPane retnew = new CFSecJavaFXSysClusterViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSysClusterObj argFocus, ICFDeleteCallback callback ) {
		CFSecJavaFXSysClusterAskDeleteForm retnew = new CFSecJavaFXSysClusterAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newFinderForm( ICFFormManager formManager ) {
		CFSecJavaFXSysClusterFinderForm retnew = new CFSecJavaFXSysClusterFinderForm( formManager, javafxSchema );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSysClusterObj argFocus,
		ICFSecClusterObj argContainer,
		Collection<ICFSecSysClusterObj> argDataCollection,
		ICFSecJavaFXSysClusterChosen whenChosen )
	{
		CFSecJavaFXSysClusterPickerForm retnew = new CFSecJavaFXSysClusterPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSysClusterObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFSecJavaFXSysClusterAddForm retnew = new CFSecJavaFXSysClusterAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSysClusterObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFSecJavaFXSysClusterViewEditForm retnew = new CFSecJavaFXSysClusterViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
