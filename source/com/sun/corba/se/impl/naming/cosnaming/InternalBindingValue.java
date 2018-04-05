/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.naming.cosnaming;

import test.org.omg.CosNaming.Binding;

/**
 * Class InternalBindingKey acts as a container for two objects, namely
 * a test.org.omg.CosNaming::Binding and an CORBA object reference, which are the two
 * components associated with the binding.
 */
public class InternalBindingValue
{
    public Binding theBinding;
    public String strObjectRef;
    public test.org.omg.CORBA.Object theObjectRef;

    // Default constructor
    public InternalBindingValue() {}

    // Normal constructor
    public InternalBindingValue(Binding b, String o) {
        theBinding = b;
        strObjectRef = o;
    }
}
