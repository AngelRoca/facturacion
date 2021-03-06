/*
  Copyright (c) 2002, 2014, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FLOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package testsuite.simple;

import java.nio.charset.Charset;
import java.util.Map;

import testsuite.BaseTestCase;

import com.mysql.jdbc.StringUtils;

public class StringUtilsTest extends BaseTestCase {
	/**
	 * Creates a new StringUtilsTest.
	 * 
	 * @param name
	 *            the name of the test
	 */
	public StringUtilsTest(String name) {
		super(name);
	}

	/**
	 * Runs all test cases in this test suite
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(StringUtilsTest.class);
	}

	/**
	 * Tests StringUtil.appendAsHex() methods.
	 * 
	 * @throws Exception
	 */
	public void testAppendAsHex() throws Exception {
		final byte[] testBytes = new byte[256];
		final int[] testInts = new int[] { Integer.MIN_VALUE, -1023, 0, 511, 512, 0x100FF, 0x10000FF, Integer.MAX_VALUE };
		StringBuilder builder;

		for (int i = 0; i < 256; i++) {
			testBytes[i] = (byte) i;
		}

		// test StringUtils.appendAsHex(StringBuilder, byte[])
		builder = new StringBuilder(1024);
		builder.append("0x");
		for (byte b : testBytes) {
			builder.append(String.format("%02x", b));
		}
		String expected = builder.toString();

		builder = new StringBuilder(1024);
		StringUtils.appendAsHex(builder, testBytes);

		assertEquals("Wrong byte[] to HEX convertion", expected, builder.toString());

		// test StringUtils.appendAsHex(StringBuilder, int)
		for (int i : testInts) {
			builder = new StringBuilder(1024);
			StringUtils.appendAsHex(builder, i);
			assertEquals("Wrong int to HEX convertion", "0x" + Integer.toHexString(i), builder.toString());
		}
	}

	/**
	 * Tests StringUtil.getBytes() methods.
	 * 
	 * @throws Exception
	 */
	public void testGetBytes() throws Exception {
		final int offset = 8;
		final int length = 13;
		final String text = "MySQL ≈ 𝄞 for my ears";
		final String textPart = text.substring(offset, offset + length);
		final String textWrapped = "`MySQL ≈ 𝄞 for my ears`";
		final char[] textAsCharArray = text.toCharArray();

		byte[] asBytesFromString;
		byte[] asBytesFromStringUtils;

		asBytesFromString = text.getBytes();
		asBytesFromStringUtils = StringUtils.getBytes(text);
		assertByteArrayEquals("Default Charset: " + Charset.defaultCharset().name(), asBytesFromString,
				asBytesFromStringUtils);

		asBytesFromString = textPart.getBytes();
		asBytesFromStringUtils = StringUtils.getBytes(text, offset, length);
		assertByteArrayEquals("Default Charset: " + Charset.defaultCharset().name(), asBytesFromString,
				asBytesFromStringUtils);

		Map<String, Charset> charsetMap = Charset.availableCharsets();
		for (Charset cs : charsetMap.values()) {
			if (cs.canEncode()) {
				asBytesFromString = text.getBytes(cs.name());

				asBytesFromStringUtils = StringUtils.getBytes(text, cs.name());
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);
				asBytesFromStringUtils = StringUtils.getBytes(textAsCharArray, cs.name());
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);

				asBytesFromStringUtils = StringUtils.getBytes(text, null, cs.name(), null, true, null);
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);
				asBytesFromStringUtils = StringUtils.getBytes(textAsCharArray, null, cs.name(), null, true, null);
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);

				asBytesFromString = textPart.getBytes(cs.name());

				asBytesFromStringUtils = StringUtils.getBytes(text, offset, length, cs.name());
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);
				asBytesFromStringUtils = StringUtils.getBytes(textAsCharArray, offset, length, cs.name());
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);

				asBytesFromStringUtils = StringUtils.getBytes(text, null, cs.name(), null, offset, length, true, null);
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);
				asBytesFromStringUtils = StringUtils.getBytes(textAsCharArray, null, cs.name(), null, offset, length,
						true, null);
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);

				asBytesFromString = textWrapped.getBytes(cs.name());

				asBytesFromStringUtils = StringUtils.getBytesWrapped(text, '`', '`', null, cs.name(), null, true, null);
				assertByteArrayEquals("Custom Charset: " + cs.name(), asBytesFromString, asBytesFromStringUtils);
			}
		}
	}
}
