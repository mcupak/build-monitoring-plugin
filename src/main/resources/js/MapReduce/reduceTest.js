function(k,vals) { 
	 var slaves = 0;
	 var offline = 0;
	 var idle = 0;
	 
	 for(var i in vals) {
		 slaves += vals[i]["slaves"];
		 offline += vals[i]["offlineSlaves"];
		 idle += vals[i]["idleSlaves"];
	 }
	 
	 var date = k["year"] + "-" + (k["month"]+1) + "-" + k["day"] + " 0:00AM";
	 var avgSlaves = slaves/vals.length;
	 var avgOffline = offline/vals.length;
	 var avgIdle = idle/vals.length;
	 
	 return {date: date, slaves: avgSlaves, offline: avgOffline, idle: avgIdle};
}