// Description: Java 25 JavaFX Display Element Factory Interface for SecUser.

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
 *	ICFSecJavaFXSecUserFactory JavaFX Display Element Factory Interface
 *	for SecUser.
 */
public interface ICFSecJavaFXSecUserFactory
{
	public CFGridPane newAttrPane( ICFFormManager formManager, ICFSecSecUserObj javaFXFocus );

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFLibAnyObj argContainer,
		ICFSecSecUserObj argFocus,
		ICFSecJavaFXSecUserPageCallback argPageCallback,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain );

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFSecSecUserObj argFocus,
		ICFLibAnyObj argContainer,
		ICFSecJavaFXSecUserPageCallback argPageCallback,
		ICFSecJavaFXSecUserChosen whenChosen );

	public CFTabPane newEltTabPane( ICFFormManager formManger, ICFSecSecUserObj javaFXFocus );

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFSecSecUserObj javaFXFocus, ICFDeleteCallback callback );

	public CFSplitPane newAddPane( ICFFormManager formManger, ICFSecSecUserObj javaFXFocus );

	public CFSplitPane newViewEditPane( ICFFormManager formManger, ICFSecSecUserObj javaFXFocus );

	public CFBorderPane newFinderForm( ICFFormManager formManager );

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFSecSecUserObj javaFXFocus,
		ICFLibAnyObj argContainer,
		ICFSecJavaFXSecUserPageCallback argPageCallback,
		ICFSecJavaFXSecUserChosen whenChosen );

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFSecSecUserObj javaFXFocus, ICFFormClosedCallback closeCallback, boolean allowSave );

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFSecSecUserObj javaFXFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd );
}
