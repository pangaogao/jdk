/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

package test.org.omg.CORBA;

/**
 * @deprecated test.org.omg.CORBA.DynamicImplementation
 */
@Deprecated
public class DynamicImplementation extends test.org.omg.CORBA.portable.ObjectImpl {

    /**
      * @deprecated Deprecated by Portable Object Adapter
      */
    @Deprecated
    public void invoke(ServerRequest request) {
        throw new test.org.omg.CORBA.NO_IMPLEMENT();
    }

    public String[] _ids() {
        throw new test.org.omg.CORBA.NO_IMPLEMENT();
    }
}
