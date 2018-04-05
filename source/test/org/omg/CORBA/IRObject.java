/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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
/*
 * File: ./test.org/omg/CORBA/IRObject.java
 * From: ./ir.idl
 * Date: Fri Aug 28 16:03:31 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 */

package test.org.omg.CORBA;
/**
An IRObject IDL interface represents the most generic interface
from which all other Interface Repository interfaces are derived,
even the Repository itself.
*/

public interface IRObject extends IRObjectOperations, test.org.omg.CORBA.Object,
    test.org.omg.CORBA.portable.IDLEntity
{
}
