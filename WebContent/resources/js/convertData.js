BRIGADERIA.convertData = new Object();

BRIGADERIA.convertData.strToDate = function(data) {
	var from = data.split("/");
	var to = (from[2] + "-" + from[1] + "-" + from[0]);
	return to;
	
};

BRIGADERIA.convertData.dateToStr = function(data) {
	var from = data.split("-");
	var to = (from[2] + "/" + from[1] + "/" + from[0]);
	return to;
};