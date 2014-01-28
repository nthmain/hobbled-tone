exports.sysStatus = function(req, res) {
	var timestamp = req.params.tstamp;
	
	//If timestamp is 0, return only current status.
	if ( timestamp == 0 ) {
		console.log("Returning current system status");
	}
	
	res.send([{timestamp:'timestamp'},{sysId:'1'},{sysStat:'green'}]);
};

exports.rpiTemp = function(req, res) {
	res.send([{timestamp:'timestamp'},{sysId:'1'},{rpiTemp:'45'}]);
};

exports.rpiVolt = function(req, res) {
	res.send([{timestamp:'timestamp'},{sysId:'1'},{rpiVolt:'1.20'}]);
};
