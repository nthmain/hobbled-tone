/*
Copyright (c) 2014 Douglas Long

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.newtonehome.hobbled_tone.db;

import android.net.Uri;

/*
 * Currently has database fields and constants to support utilizing a 'data' table.
 * 
 * Future:
 * - implement log table for system logs / messages to be saved on the device?
 */
public class Db {

//	private static final String TAG = "Db";
	
	//Database constants.
	public static final String DB_NAME = "local.db";
	public static final int DB_VER = 1;
	public static final String DATA_TABLE = "data";
	public static final String AUTHORITY = "com.newtonehome.hobbled_tone.db.contentprovider";
	
	public static final Uri DATA_URI = Uri.parse("content://" + AUTHORITY + "/" + DATA_TABLE);
	public static final Uri CLIENT_URI = Uri.parse("content://" + AUTHORITY + "/" + "data/client");
	
	//DB Fields (must mach the Database creation schema!)
	public static final String _ID = "_id";
	public static final String TIMESTAMP = "time_stamp";
	public static final String SYS_ID = "sys_id";
	public static final String SYS_STATUS = "sys_status";
	public static final String RPI_TEMP = "rpi_temp";
	public static final String RPI_VOLT = "rpi_volt";
	
	//System Status Field Types.
	public static final String SYS_STAT_OK	= "sys_stat_ok";
	public static final String SYS_STAT_WARN = "sys_stat_warn";
	public static final String SYS_STAT_ERR	= "sys_stat_err";
	
	//Uri matches.
	public static final int DATA_URI_MATCH = 1;
	public static final int DATA_ID_URI_MATCH = 2;
	public static final int CLIENT_URI_MATCH = 3;

}
