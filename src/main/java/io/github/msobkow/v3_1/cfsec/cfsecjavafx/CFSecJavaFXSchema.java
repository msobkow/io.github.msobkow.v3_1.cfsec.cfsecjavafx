// Description: Java 25 JavaFX Schema for CFSec.

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

public class CFSecJavaFXSchema
implements ICFSecJavaFXSchema
{
	protected ICFSecSchemaObj schema = null;
	protected String clusterName = "system";
	protected ICFSecClusterObj clusterObj = null;
	protected String tenantName = "system";
	protected ICFSecTenantObj tenantObj = null;
	protected String secUserName = "system";
	protected ICFSecSecUserObj secUserObj = null;
	protected ICFSecSecSessionObj secSessionObj = null;
	protected ICFSecJavaFXClusterFactory factoryCluster = null;
	protected ICFSecJavaFXHostNodeFactory factoryHostNode = null;
	protected ICFSecJavaFXISOCcyFactory factoryISOCcy = null;
	protected ICFSecJavaFXISOCtryFactory factoryISOCtry = null;
	protected ICFSecJavaFXISOCtryCcyFactory factoryISOCtryCcy = null;
	protected ICFSecJavaFXISOCtryLangFactory factoryISOCtryLang = null;
	protected ICFSecJavaFXISOLangFactory factoryISOLang = null;
	protected ICFSecJavaFXISOTZoneFactory factoryISOTZone = null;
	protected ICFSecJavaFXSecDeviceFactory factorySecDevice = null;
	protected ICFSecJavaFXSecGroupFactory factorySecGroup = null;
	protected ICFSecJavaFXSecGrpIncFactory factorySecGrpInc = null;
	protected ICFSecJavaFXSecGrpMembFactory factorySecGrpMemb = null;
	protected ICFSecJavaFXSecSessionFactory factorySecSession = null;
	protected ICFSecJavaFXSecUserFactory factorySecUser = null;
	protected ICFSecJavaFXServiceFactory factoryService = null;
	protected ICFSecJavaFXServiceTypeFactory factoryServiceType = null;
	protected ICFSecJavaFXSysClusterFactory factorySysCluster = null;
	protected ICFSecJavaFXTSecGroupFactory factoryTSecGroup = null;
	protected ICFSecJavaFXTSecGrpIncFactory factoryTSecGrpInc = null;
	protected ICFSecJavaFXTSecGrpMembFactory factoryTSecGrpMemb = null;
	protected ICFSecJavaFXTenantFactory factoryTenant = null;

	public CFSecJavaFXSchema() {
		CFSecJavaFX.init();
	}

	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	public void setSchema( ICFSecSchemaObj argSchema ) {
		final String S_ProcName = "setSchema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		if( ! ( argSchema instanceof ICFSecSchemaObj ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"argSchema",
				argSchema,
				"ICFSecSchemaObj" );
		}
		schema = (ICFSecSchemaObj)argSchema;
	}

	public String getClusterName() {
		return( clusterName );
	}

	public void setClusterName( String argClusterName ) {
		final String S_ProcName = "setClusterName";
		if( ( argClusterName == null ) || ( argClusterName.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argClusterName" );
		}
		clusterName = argClusterName;
		clusterObj = null;
	}

	public ICFSecClusterObj getClusterObj() {
		if( clusterObj == null ) {
			if( schema != null ) {
				clusterObj = schema.getClusterTableObj().readClusterByUDomNameIdx( clusterName );
			}
			if( clusterObj == null ) {
				throw new CFLibDataNotFoundException( getClass(),
					"getClusterObj",
					"UniqueDomainName",
					"UniqueDomainName",
					clusterName );
			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getClusterObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "Cluster"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "Cluster") );
		}
		return( clusterObj );
	}

	public String getTenantName() {
		return( tenantName );
	}

	public void setTenantName( String argTenantName ) {
		final String S_ProcName = "setTenantName";
		if( ( argTenantName == null ) || ( argTenantName.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argTenantName" );
		}
		tenantName = argTenantName;
		tenantObj = null;
	}

	public ICFSecTenantObj getTenantObj() {
		if( tenantObj == null ) {
			if( schema != null ) {
				tenantObj = schema.getTenantTableObj().readTenantByUNameIdx( getClusterObj().getRequiredId(), tenantName );
			}
			if( tenantObj == null ) {
				throw new CFLibDataNotFoundException( getClass(),
					"getTenantObj",
					"UniqueTenantName",
					"UniqueTenantName",
					new Object() {	protected long clusterId = getClusterObj().getRequiredId();
						protected String name = tenantName; } );
			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getTenantObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "Tenant"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "Tenant") );
		}
		return( tenantObj );
	}

	public String getSecUserName() {
		return( secUserName );
	}

	public void setSecUserName( String argSecUserName ) {
		final String S_ProcName = "setSecUserName";
		if( ( argSecUserName == null ) || ( argSecUserName.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argSecUserName" );
		}
		secUserName = argSecUserName;
		secUserObj = null;
	}

	public ICFSecSecUserObj getSecUserObj() {
		if( secUserObj == null ) {
			if( schema != null ) {
				secUserObj = schema.getSecUserTableObj().readSecUserByULoginIdx( secUserName );
			}
			if( secUserObj == null ) {
				throw new CFLibDataNotFoundException( getClass(),
					"getSecUserObj",
					"UniqueLogin",
					"UniqueLogin",
					secUserName );

			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getSecUserObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "SecUser"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "SecUser") );
		}
		return( secUserObj );
	}

	public ICFSecSecSessionObj getSecSessionObj() {
		if( secSessionObj == null ) {
			if( schema != null ) {
				getClusterObj();
				getTenantObj();
				getSecUserObj();
				secSessionObj = schema.getSecSessionTableObj().newInstance();
				ICFSecSecSessionEditObj sessionEdit = secSessionObj.beginEdit();
				sessionEdit.setRequiredContainerSecUser( secUserObj );
				sessionEdit.setRequiredStart( LocalDateTime.now() );
				sessionEdit.setOptionalFinish( null );
				secSessionObj = sessionEdit.create();
				sessionEdit = null;
			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getSecSessionObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "SecSession"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "SecSession") );
		}
		return( secSessionObj );
	}

	public ICFSecJavaFXClusterFactory getClusterFactory() {
		if( factoryCluster == null ) {
			factoryCluster = new CFSecJavaFXClusterFactory( this );
		}
		return( factoryCluster );
	}

	public ICFSecJavaFXHostNodeFactory getHostNodeFactory() {
		if( factoryHostNode == null ) {
			factoryHostNode = new CFSecJavaFXHostNodeFactory( this );
		}
		return( factoryHostNode );
	}

	public ICFSecJavaFXISOCcyFactory getISOCcyFactory() {
		if( factoryISOCcy == null ) {
			factoryISOCcy = new CFSecJavaFXISOCcyFactory( this );
		}
		return( factoryISOCcy );
	}

	public ICFSecJavaFXISOCtryFactory getISOCtryFactory() {
		if( factoryISOCtry == null ) {
			factoryISOCtry = new CFSecJavaFXISOCtryFactory( this );
		}
		return( factoryISOCtry );
	}

	public ICFSecJavaFXISOCtryCcyFactory getISOCtryCcyFactory() {
		if( factoryISOCtryCcy == null ) {
			factoryISOCtryCcy = new CFSecJavaFXISOCtryCcyFactory( this );
		}
		return( factoryISOCtryCcy );
	}

	public ICFSecJavaFXISOCtryLangFactory getISOCtryLangFactory() {
		if( factoryISOCtryLang == null ) {
			factoryISOCtryLang = new CFSecJavaFXISOCtryLangFactory( this );
		}
		return( factoryISOCtryLang );
	}

	public ICFSecJavaFXISOLangFactory getISOLangFactory() {
		if( factoryISOLang == null ) {
			factoryISOLang = new CFSecJavaFXISOLangFactory( this );
		}
		return( factoryISOLang );
	}

	public ICFSecJavaFXISOTZoneFactory getISOTZoneFactory() {
		if( factoryISOTZone == null ) {
			factoryISOTZone = new CFSecJavaFXISOTZoneFactory( this );
		}
		return( factoryISOTZone );
	}

	public ICFSecJavaFXSecDeviceFactory getSecDeviceFactory() {
		if( factorySecDevice == null ) {
			factorySecDevice = new CFSecJavaFXSecDeviceFactory( this );
		}
		return( factorySecDevice );
	}

	public ICFSecJavaFXSecGroupFactory getSecGroupFactory() {
		if( factorySecGroup == null ) {
			factorySecGroup = new CFSecJavaFXSecGroupFactory( this );
		}
		return( factorySecGroup );
	}

	public ICFSecJavaFXSecGrpIncFactory getSecGrpIncFactory() {
		if( factorySecGrpInc == null ) {
			factorySecGrpInc = new CFSecJavaFXSecGrpIncFactory( this );
		}
		return( factorySecGrpInc );
	}

	public ICFSecJavaFXSecGrpMembFactory getSecGrpMembFactory() {
		if( factorySecGrpMemb == null ) {
			factorySecGrpMemb = new CFSecJavaFXSecGrpMembFactory( this );
		}
		return( factorySecGrpMemb );
	}

	public ICFSecJavaFXSecSessionFactory getSecSessionFactory() {
		if( factorySecSession == null ) {
			factorySecSession = new CFSecJavaFXSecSessionFactory( this );
		}
		return( factorySecSession );
	}

	public ICFSecJavaFXSecUserFactory getSecUserFactory() {
		if( factorySecUser == null ) {
			factorySecUser = new CFSecJavaFXSecUserFactory( this );
		}
		return( factorySecUser );
	}

	public ICFSecJavaFXServiceFactory getServiceFactory() {
		if( factoryService == null ) {
			factoryService = new CFSecJavaFXServiceFactory( this );
		}
		return( factoryService );
	}

	public ICFSecJavaFXServiceTypeFactory getServiceTypeFactory() {
		if( factoryServiceType == null ) {
			factoryServiceType = new CFSecJavaFXServiceTypeFactory( this );
		}
		return( factoryServiceType );
	}

	public ICFSecJavaFXSysClusterFactory getSysClusterFactory() {
		if( factorySysCluster == null ) {
			factorySysCluster = new CFSecJavaFXSysClusterFactory( this );
		}
		return( factorySysCluster );
	}

	public ICFSecJavaFXTSecGroupFactory getTSecGroupFactory() {
		if( factoryTSecGroup == null ) {
			factoryTSecGroup = new CFSecJavaFXTSecGroupFactory( this );
		}
		return( factoryTSecGroup );
	}

	public ICFSecJavaFXTSecGrpIncFactory getTSecGrpIncFactory() {
		if( factoryTSecGrpInc == null ) {
			factoryTSecGrpInc = new CFSecJavaFXTSecGrpIncFactory( this );
		}
		return( factoryTSecGrpInc );
	}

	public ICFSecJavaFXTSecGrpMembFactory getTSecGrpMembFactory() {
		if( factoryTSecGrpMemb == null ) {
			factoryTSecGrpMemb = new CFSecJavaFXTSecGrpMembFactory( this );
		}
		return( factoryTSecGrpMemb );
	}

	public ICFSecJavaFXTenantFactory getTenantFactory() {
		if( factoryTenant == null ) {
			factoryTenant = new CFSecJavaFXTenantFactory( this );
		}
		return( factoryTenant );
	}
}
