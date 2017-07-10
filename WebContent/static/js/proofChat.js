var proofData = null;
proofData = JSON.parse(storage.getItem("swap"));

//blog
//为data准备的数据


$(function() {
	var vlabel = new Array();
	for(var i=0; i<proofData.length;i++){
		vlabel[i] = proofData[i].proofName;
	}
	
	var vtime = new Array();
	var baseValue =  proofData[0].recordTime;
	for(var i=0; i<proofData.length; i++){
		vtime[i] = (proofData[i].recordTime-baseValue);
	}
	
	console.log(vlabel);
	console.log(vtime);
	
	var ctx = document.getElementById("myChart").getContext("2d");
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
             labels :vlabel,
             datasets : [
                 {
                     fillColor : "rgba(220,220,220,0.5)",
                     strokeColor : "rgba(220,220,220,1)",
                     pointColor : "rgba(220,220,220,1)",
                     pointStrokeColor : "#fff",
                     data : vtime
                 }
             ]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true,
                    }
                }]
            }
        }
    });

})// ready go
