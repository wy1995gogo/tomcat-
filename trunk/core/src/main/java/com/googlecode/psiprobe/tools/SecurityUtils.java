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
package com.googlecode.psiprobe.tools;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean hasAttributeValueRole(ServletContext servletContext, HttpServletRequest request) {
        String privilegedRole = getPrivilegedRole(servletContext);
        return request.isUserInRole(privilegedRole);
    }

    /*
    public static boolean hasAttributeValueRole(ServletContext servletContext) {
        String privelegedRole = getPrivilegedRole(servletContext);
        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean result = false;
        for (int i = 0; i < authorities.length; i++) {
            if (privelegedRole.equals(authorities[i].getAuthority())) {
                result = true;
                break;
            }
        }
        return result;
    }
     */

    private static String getPrivilegedRole(ServletContext servletContext) {
        return servletContext.getInitParameter("attribute.value.role");
    }

}