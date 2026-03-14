// Description: Java 25 JavaFX Display Element Factory Interface for ISOCtryCcy.

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
 *	ICFSecJavaFXISOCtryCcyFactory JavaFX Display Element Factory Interface
 *	for ISOCtryCcy.
 */
public interface ICFSecJavaFXISOCtryCcyFactory
{
	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecISOCtryCcyObj javaFXFocus );

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFSecISOCtryObj argContainer,
		ICFSecISOCtryCcyObj argFocus,
		Collection<ICFSecISOCtryCcyObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain );

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecISOCtryCcyObj argFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryCcyObj> argDataCollection,
		ICFSecJavaFXISOCtryCcyChosen whenChosen );

	public CFTabPane newEltTabPane( ICFFormManager formManger, ICFSecISOCtryCcyObj javaFXFocus );

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecISOCtryCcyObj javaFXFocus, ICFDeleteCallback callback );

	public CFSplitPane newAddPane( ICFFormManager formManger, ICFSecISOCtryCcyObj javaFXFocus );

	public CFSplitPane newViewEditPane( ICFFormManager formManger, ICFSecISOCtryCcyObj javaFXFocus );

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecISOCtryCcyObj javaFXFocus,
		ICFSecISOCtryObj argContainer,
		Collection<ICFSecISOCtryCcyObj> argDataCollection,
		ICFSecJavaFXISOCtryCcyChosen whenChosen );

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecISOCtryCcyObj javaFXFocus, ICFFormClosedCallback closeCallback, boolean allowSave );

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecISOCtryCcyObj javaFXFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd );
}
