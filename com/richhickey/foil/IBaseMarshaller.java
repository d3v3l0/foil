/*
 * Created on Dec 10, 2004
 *
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
 *   which can be found in the file CPL.TXT at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 */

package com.richhickey.foil;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Rich
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IBaseMarshaller
    {
    static final int MARSHALL_ID = 1;
    static final int MARSHALL_TYPE = 1;
    static final int MARSHALL_HASH = 1;

	void marshallAtom(Object o,Writer w, int flags,int depth) throws IOException;
	boolean canMarshallAsList(Object o);
	void marshallAsList(Object o,Writer w, int flags,int depth) throws IOException;
	void marshallAsVector(Object o,Writer w, int flags,int depth) throws IOException;
    }