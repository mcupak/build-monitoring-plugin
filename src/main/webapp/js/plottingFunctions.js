function doDatePlot(jq, contentHolder, series) {
  plot1 = jq.jqplot(contentHolder, [series], {
    title:'Default Date Axis',
    axes:{
    	xaxis:{renderer:$jq.jqplot.DateAxisRenderer}
    	//yaxis: {min:0.0, max: 2.0}
    },
    series:[{lineWidth:4, markerOptions:{style:'square'}}]
  });
}