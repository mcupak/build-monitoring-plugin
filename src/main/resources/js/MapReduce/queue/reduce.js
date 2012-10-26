function(k,vals) { 
	 var queueSize = 0;
	 var buildableSize = 0;
	 var pendingSize = 0;
	 var blockedSize = 0;
	 var waitingSize = 0;
	 
	 for(var i in vals) {
		queueSize += vals[i]["queueSize"];
		buildableSize += vals[i]["buildableSize"];
		pendingSize += vals[i]["pendingSize"];
		blockedSize += vals[i]["blockedSize"];
		waitingSize += vals[i]["waitingSize"];
	 }
	 
	 var date = k["year"] + "-" + (k["month"]+1) + "-" + k["day"] + " 0:00AM";
	 var avgQueueSize = queueSize/vals.length;
	 var avgBuildableSize = buildableSize/vals.length;
	 var avgPendingSize = pendingSize/vals.length;
	 var avgBlockedSize = blockedSize/vals.length;
	 var avgWaitingSize = waitingSize/vals.length;
	 
	 return {date: date, queueSize: avgQueueSize, buildableSize: avgBuildableSize, pendingSize: avgPendingSize, blockedSize: avgBlockedSize, waitingSize: avgWaitingSize};
}