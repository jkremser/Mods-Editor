/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.user.client.DOM;

import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusTypeClient;

/**
 * @author Jiri Kremser
 */
public class ClientUtils {

    /**
     * <p>
     * Converts a String to a boolean (optimised for performance).
     * </p>
     * <p>
     * <code>'true'</code>, <code>'on'</code> or <code>'yes'</code> (case
     * insensitive) will return <code>true</code>. Otherwise, <code>false</code>
     * is returned.
     * </p>
     * <p>
     * This method performs 4 times faster (JDK1.4) than
     * <code>Boolean.valueOf(String)</code>. However, this method accepts 'on'
     * and 'yes' as true values.
     * 
     * <pre>
	 *   BooleanUtils.toBoolean(null)    = false
	 *   BooleanUtils.toBoolean("true")  = true
	 *   BooleanUtils.toBoolean("TRUE")  = true
	 *   BooleanUtils.toBoolean("tRUe")  = true
	 *   BooleanUtils.toBoolean("on")    = true
	 *   BooleanUtils.toBoolean("yes")   = true
	 *   BooleanUtils.toBoolean("false") = false
	 *   BooleanUtils.toBoolean("x gti") = false
	 * </pre>
     * 
     * @param str
     *        the String to check
     * @return the boolean value of the string, <code>false</code> if no match
     */
    public static boolean toBoolean(String str) {
        // Previously used equalsIgnoreCase, which was fast for interned 'true'.
        // Non interned 'true' matched 15 times slower.
        //
        // Optimisation provides same performance as before for interned 'true'.
        // Similar performance for null, 'false', and other strings not length
        // 2/3/4.
        // 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower.
        if (str == "true") {
            return true;
        }
        if (str == null) {
            return false;
        }
        switch (str.length()) {
            case 2: {
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                return (ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N');
            }
            case 3: {
                char ch = str.charAt(0);
                if (ch == 'y') {
                    return (str.charAt(1) == 'e' || str.charAt(1) == 'E')
                            && (str.charAt(2) == 's' || str.charAt(2) == 'S');
                }
                if (ch == 'Y') {
                    return (str.charAt(1) == 'E' || str.charAt(1) == 'e')
                            && (str.charAt(2) == 'S' || str.charAt(2) == 's');
                }
                return false;
            }
            case 4: {
                char ch = str.charAt(0);
                if (ch == 't') {
                    return (str.charAt(1) == 'r' || str.charAt(1) == 'R')
                            && (str.charAt(2) == 'u' || str.charAt(2) == 'U')
                            && (str.charAt(3) == 'e' || str.charAt(3) == 'E');
                }
                if (ch == 'T') {
                    return (str.charAt(1) == 'R' || str.charAt(1) == 'r')
                            && (str.charAt(2) == 'U' || str.charAt(2) == 'u')
                            && (str.charAt(3) == 'E' || str.charAt(3) == 'e');
                }
            }
        }
        return false;
    }

    /**
     * Escape html.
     * 
     * @param maybeHtml
     *        the maybe html
     * @return the string
     */
    public static String escapeHtml(String maybeHtml) {
        final com.google.gwt.user.client.Element div = DOM.createDiv();
        DOM.setInnerText(div, maybeHtml);
        return DOM.getInnerHTML(div);
    }

    /**
     * To list of list of strings.
     * 
     * @param frequency
     *        the frequency
     * @return the list
     */
    public static List<List<String>> toListOfListOfStrings(List<StringPlusAuthorityClient> frequency) {
        if (frequency == null) return null;
        List<List<String>> outerList = new ArrayList<List<String>>(frequency.size());
        for (StringPlusAuthorityClient value : frequency) {
            if (value == null) continue;
            List<String> innerList = new ArrayList<String>(2);
            innerList.add(value.getValue());
            innerList.add(value.getAuthority());
            outerList.add(innerList);
        }
        return outerList;
    }

    /**
     * To list of list of strings.
     * 
     * @param toConvert
     *        the to convert
     * @param something
     *        the something
     * @return the list
     */
    public static List<List<String>> toListOfListOfStrings(List<StringPlusAuthorityPlusTypeClient> toConvert,
                                                           boolean something) {
        if (toConvert == null) return null;
        List<List<String>> outerList = new ArrayList<List<String>>(toConvert.size());
        for (StringPlusAuthorityPlusTypeClient value : toConvert) {
            if (value == null) continue;
            List<String> innerList = new ArrayList<String>(2);
            innerList.add(value.getValue());
            innerList.add(value.getType());
            innerList.add(value.getAuthority());
            outerList.add(innerList);
        }
        return outerList;
    }

    /**
     * String.format is not accessible on the gwt client-side
     * 
     * @param format
     * @param args
     * @return formatted string
     */
    public static String format(final String format, final char escapeCharacter, final Object... args) {
        final RegExp regex = RegExp.compile("%" + escapeCharacter);
        final SplitResult split = regex.split(format);
        final StringBuffer msg = new StringBuffer();
        for (int pos = 0; pos < split.length() - 1; pos += 1) {
            msg.append(split.get(pos));
            msg.append(args[pos].toString());
        }
        msg.append(split.get(split.length() - 1));
        return msg.toString();
    }

    public static class SimpleDateFormat {

        private DateTimeFormat format;

        public SimpleDateFormat() {
            super();
        }

        protected SimpleDateFormat(DateTimeFormat format) {
            this.format = format;
        }

        public SimpleDateFormat(String pattern) {
            applyPattern(pattern);
        }

        public void applyPattern(String pattern) {
            this.format = DateTimeFormat.getFormat(pattern);
        }

        public String format(Date date) {
            return format.format(date);
        }

        /**
         * Parses text and returns the corresponding date object.
         */
        public Date parse(String source) {
            return format.parse(source);
        }

    }

}
