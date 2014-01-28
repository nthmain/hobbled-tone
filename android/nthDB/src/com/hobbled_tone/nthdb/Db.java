package com.hobbled_tone.nthdb;

import android.net.Uri;

/*
 * Currently has database fields and constants to support utilizing a 'data' table.
 * 
 * Future:
 * - implement log table for system logs / messages to be saved on the device?
 */
public class Db {

	private static final String TAG = "Db";
	
	//Database constants.
	public static final String DB_NAME = "local.db";
	public static final int DB_VER = 1;
	public static final String DATA_TABLE = "data";
	public static final String BASE_URI_NAME = "content://com.hobbled_tone.nthdb.DbContentProvider/";
	
	public static final Uri DATA_URI = Uri.parse(BASE_URI_NAME + DATA_TABLE);
	
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

}
