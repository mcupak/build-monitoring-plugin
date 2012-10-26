function(k,vals) { 
	 var projects = 0;
	 var builds = 0;
	 var slaves = 0;
	 var offline = 0;
	 var idle = 0;
	 
	 for(var i in vals) {
		 projects += vals[i]["projects"];
		 builds += vals[i]["builds"];
		 slaves += vals[i]["slaves"];
		 offline += vals[i]["offline"];
		 idle += vals[i]["idle"];
	 }
	 
	 var date = k["year"] + "-" + (k["month"]+1) + "-" + k["day"] + " 0:00AM";
	 var avgProjects = projects/vals.length;
	 var avgBuilds = builds/vals.length;
	 var avgSlaves = slaves/vals.length;
	 var avgOffline = offline/vals.length;
	 var avgIdle = idle/vals.length;
	 
	 return {date: date, projects: avgProjects, builds: avgBuilds, slaves: avgSlaves, offline: avgOffline, idle: avgIdle};
}