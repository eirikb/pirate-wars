/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client;

import com.allen_sauer.gwt.log.client.FirebugLogger;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class Log {

    private static FirebugLogger firebugLogger = new FirebugLogger();

    public static void info(String message) {
        firebugLogger.info(message, null);
    }
}