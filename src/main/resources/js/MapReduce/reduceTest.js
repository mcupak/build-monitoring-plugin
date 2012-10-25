function(k,vals) { 
	 var sum=0;
	 for(var i in vals) {
		 sum += vals[i];
	 }
	 var date = k["year"] + "-" + (k["month"]+1) + "-" + k["day"];
	 var avg = sum/vals.length;
	 return {date: date, avg: avg};
}