// Description: Java 25 JavaFX Display Element Factory Interface for SecUser.

/*
 *	io.github.msobkow.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	This file is part of Mark's Code Fractal CFSec.
 *	
 *	Mark's Code Fractal CFSec is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFSec is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFSec is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFSec.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package io.github.msobkow.v3_1.cfsec.cfsecjavafx;

import java.math.*;
import java.time.*;
import java.text.*;
import java.util.*;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.inz.Inz;
import io.github.msobkow.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;

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
