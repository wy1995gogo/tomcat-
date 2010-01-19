/*
 * Licensed under the GPL License.  You may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package com.googlecode.psiprobe.tools.logging.jdk;

import com.googlecode.psiprobe.tools.logging.LogDestination;
import java.io.File;

public class Jdk14HandlerAccessor extends BaseJdk14HandlerAccessor implements LogDestination {
    public String getName() {
        return null;
    }

    public String getConversionPattern() {
        return null;
    }

    public File getFile() {
        return getStdoutFile();
    }
}
