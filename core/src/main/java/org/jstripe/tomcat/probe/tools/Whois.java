/*
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://probe.jstripe.com/d/license.shtml
 *
 *  THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.jstripe.tomcat.probe.tools;

import org.jstripe.tomcat.probe.tools.url.URLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class Whois {

    public static Response lookup(String server, int port, String query) throws IOException {
        return lookup(server, port, query, 5);
    }

    public static Response lookup(String server, int port, String query, long timeout) throws IOException {
        return lookup(server, port, query, timeout, System.getProperty("line.separator"));
    }

    public static Response lookup(String server, int port, String query, long timeout, String lineSeparator) throws IOException {

        if (query == null) return null;

        Response response = new Response();

        response.server = server;
        response.port = port;

        Socket connection = AsyncSocketFactory.createSocket(server, port, timeout);
        try {
            PrintStream out = new PrintStream(connection.getOutputStream());
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                try {
                    out.println(query);
                    StringBuffer sb = new StringBuffer();

                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line).append(lineSeparator);
                        line = line.trim();
                        if (! line.startsWith("%") && ! line.startsWith("#")) {
                            int fs = line.indexOf(":");
                            if (fs > 0) {
                                String name = line.substring(0, fs);
                                String value = line.substring(fs + 1).trim();
                                response.data.put(name, value);
                            }
                        }
                    }
                    response.summary = sb.toString();

                    Response newResponse = null;
                    String referral = (String) response.getData().get("ReferralServer");

                    if (referral != null) {
                        try {
                            URLParser url = new URLParser(referral);
                            if ("whois".equals(url.getProtocol())) {
                                newResponse = lookup(url.getHost(), url.getPort() == -1 ? 43 : url.getPort(), query,
                                        timeout, lineSeparator);
                            }
                        } catch (IOException e) {
                            System.out.println("Could not contact " + referral);
                        }
                    }
                    if (newResponse != null) response = newResponse;
                } finally {
                    in.close();
                }
            } finally {
                out.close();
            }
        } finally {
            connection.close();
        }

        return response;

    }

    public static class Response {

        private String summary;
        private Map data = new TreeMap();
        private String server;
        private int port;

        public String getSummary() {
            return summary;
        }

        public Map getData() {
            return data;
        }

        public String getServer() {
            return server;
        }

        public int getPort() {
            return port;
        }
    }

    public static void main(String[] args) throws IOException {
/*
        Response response = Whois.lookup("whois.arin.net", 43, args[0], 5);
        System.out.println("[" + response.server + ":" + response.port + "]");
        System.out.println(response.getSummary());
*/
        System.out.println(InetAddress.getByName("62.252.064.32").getHostName());
//        System.out.println(InetAddress.getByAddress("062.252.064.032".getBytes()).getHostName());
    }
}