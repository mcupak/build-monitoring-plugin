function doDatePlot(jq, title, contentHolder, series) {
  plot1 = jq.jqplot(contentHolder, [series], {
    title:title,
    axes:{
    	xaxis:{renderer:$jq.jqplot.DateAxisRenderer}
    	//yaxis: {min:0.0, max: 2.0}
    },
    series:[{lineWidth:4, markerOptions:{style:'square'}}]
  });
}