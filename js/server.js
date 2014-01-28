var express		= require('express'),
	data		= require('./routes/data'),
	nano		= require('nano')('http://localhost:5984/rpi_data'),
	db_name		= "rpi_data",
	db			= nano.use(db_name),
	app			= express();

app.get('/sys_stat/:tstamp', data.sysStatus);
app.get('/rpi_temp/:tstamp', data.rpiTemp);
app.get('/rpi_volt/:tstamp', data.rpiVolt);

app.listen(3000)
console.log("Server listening on port 3000");
