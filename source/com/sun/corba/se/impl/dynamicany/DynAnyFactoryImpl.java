/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.corba.se.impl.dynamicany;

import com.sun.corba.se.spi.orb.ORB ;

public class DynAnyFactoryImpl
    extends test.org.omg.CORBA.LocalObject
    implements test.org.omg.DynamicAny.DynAnyFactory
{
    //
    // Instance variables
    //

    private ORB orb;

    //
    // Constructors
    //

    private DynAnyFactoryImpl() {
        this.orb = null;
    }

    public DynAnyFactoryImpl(ORB orb) {
        this.orb = orb;
    }

    //
    // DynAnyFactory interface methods
    //

    // Returns the most derived DynAny type based on the Anys TypeCode.
    public test.org.omg.DynamicAny.DynAny create_dyn_any (test.org.omg.CORBA.Any any)
        throws test.org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
    {
        return DynAnyUtil.createMostDerivedDynAny(any, orb, true);
    }

    // Returns the most derived DynAny type based on the TypeCode.
    public test.org.omg.DynamicAny.DynAny create_dyn_any_from_type_code (test.org.omg.CORBA.TypeCode type)
        throws test.org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
    {
        return DynAnyUtil.createMostDerivedDynAny(type, orb);
    }

    // Needed for test.org.omg.CORBA.Object

    private String[] __ids = { "IDL:omg.test.org/DynamicAny/DynAnyFactory:1.0" };

    public String[] _ids() {
        return (String[]) __ids.clone();
    }
}
