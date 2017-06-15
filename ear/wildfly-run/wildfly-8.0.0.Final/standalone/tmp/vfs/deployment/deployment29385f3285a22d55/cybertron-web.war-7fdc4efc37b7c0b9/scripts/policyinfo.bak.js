//bar chart 1
!function(CHART, selection) {
	var container = $("<canvas>").addClass('ct-chart');
	container.appendTo($(selection));
	var data = {
		labels : [ 'เงินเอาประกันชีวิต', 'ค่ารักษาพยาบาล เหมาจ่าย', 'ค่ารักษาพยาบาล แยกค่าใช้จ่าย', 'โรคร้ายแรง', 'สัญญาเพิ่มเติมอุบัติเหตุ', 'อุบัติเหตุส่วนบุคคล' ],
		datasets : [ {
			data : [ 500, 100, 300, 50, 100, 120 ],
			backgroundColor : "#00B5CC",
		} ]
	};
	var options = {
		maintainAspectRatio : false,
		showLabelsOnBars:true,
		tooltips: {
            callbacks: {
                label: function(tooltipItems, data) { 
                    return commars(tooltipItems.yLabel);
                }
            }
        },
        animation:{
        	onComplete: function () {
        		this.chart.ctx;
            }
        },
		scales:{
			xAxes : [{
				barPercentage: 0.6,
		        scaleShowLabels : true,
    			gridLines: {
    				display:false,
    			},
			      ticks: {
			          minRotation: 80
			        }
			}],
			yAxes : [{
                ticks: {
                    beginAtZero:false,
                    callback: function(lable){return  commars(lable);}
                },
    			gridLines: {
    				display:false,
    			}
			}]
		},
		legend : {
			display : false
		}
	};
	window.chart = new Chart(container.get(0).getContext("2d"), {
		type : 'bar',
		data : data,
		options : options
	});
	if (RIDER_GRAPH) {
//		window.chart.data.datasets[0].data = [ 10000, 5000, 5000, 1000, 1000, 1500 ];
		 window.chart.data.datasets[0].data=RIDER_GRAPH;
		window.chart.update();
	}
}(Chart, '#chart1');
// doughnut chart
!function(CHART, selection) {
	var container = $("<canvas>").addClass('ct-chart');
	container.appendTo($(selection));
	var data = {
		labels : [ 'เงินเอาประกันชีวิต', 'ค่ารักษาพยาบาล เหมาจ่าย', 'ค่ารักษาพยาบาล แยกค่าใช้จ่าย', 'โรคร้ายแรง', 'สัญญาเพิ่มเติมอุบัติเหตุ', 'อุบัติเหตุส่วนบุคคล' ],
		datasets : [ {
			data : [ 500, 100, 300, 50, 100, 120 ],
			backgroundColor : [ "#00B5CC", "#49A22D", "#93E678", "#FF6384", "#FFCE56" ],
			hoverBackgroundColor : [ "#00B5CC", "#49A22D", "#93E678", "#FF6384", "#FFCE56" ]
		} ]
	};
	var options = {
		maintainAspectRatio : false,
		rotation : -1.1 * Math.PI,
		legend : {
			position : 'bottom'
		}
	};
	window.chart = new Chart(container.get(0), {
		type : 'doughnut',
		data : data,
		options : options
	});
	if (RIDER_GRAPH) {
		// window.chart.data.datasets[0].data=[10000,5000,5000,1000,1000,1500];
		window.chart.data.datasets[0].data = RIDER_GRAPH;
		window.chart.update();
	}
}// (Chart,'#chart1');
// bar chart2
!function(CHART, selection) {
	var container = $("<div>").addClass('ct-chart');
	container.appendTo($(selection));
	var data = {
		labels : [ 'เงินเอาประกันชีวิต', 'ค่ารักษาพยาบาลเหมาจ่าย', 'ค่ารักษาพยาบาลแยกค่าใช้จ่าย', 'โรคร้ายแรง', 'สัญญาเพิ่มเติมอุบัติเหตุ', 'อุบัติเหตุส่วนบุคคล' ]
	};
	var options = {
		height : '250px',
		distributeSeries : true,
		axisX : {
			offset : 15,
			scaleMinSpace : 15,
			showLabel : false,
		},
		axisY : {
			offset : 50,
			labelInterpolationFnc : function(value) {
				return commars(value);
			},
			maxTicksLimit: 0,
			scaleMinSpace : 15
		}
	};
	var responsiveOptions = [ [ 'screen and (min-width: 641px) and (max-width: 1024px)', {
		showPoint : false,
		axisX : {
			labelInterpolationFnc : function(value) {
				// Will return Mon, Tue, Wed etc. on medium screens
				switch (value) {
				case 'เงินเอาประกันชีวิต':
					return 'เอาประกัน';
				case 'ค่ารักษาพยาบาลเหมาจ่าย':
					return 'เหมาจ่าย';
				case 'ค่ารักษาพยาบาลแยกค่าใช้จ่าย':
					return 'แยกค่าใช้จ่าย';
				case 'โรคร้ายแรง':
					return 'ร้ายแรง';
				case 'สัญญาเพิ่มเติมอุบัติเหตุ':
					return 'สัญญาเพิ่มเติม';
				case 'อุบัติเหตุส่วนบุคคล':
					return 'อุบัติเหตุ';
				}
			}
		}
	} ], [ 'screen and (max-width: 640px)', {
		showLine : false,
		axisX : {
			labelInterpolationFnc : function(value) {
				switch (value) {
				case 'เงินเอาประกันชีวิต':
					return 'ประกัน';
				case 'ค่ารักษาพยาบาลเหมาจ่าย':
					return 'เหมา';
				case 'ค่ารักษาพยาบาลแยกค่าใช้จ่าย':
					return 'แยก';
				case 'โรคร้ายแรง':
					return 'ร้าย';
				case 'สัญญาเพิ่มเติมอุบัติเหตุ':
					return 'เพิ่มเติม';
				case 'อุบัติเหตุส่วนบุคคล':
					return 'อุบัติเหตุ';
				}
			}
		}
	} ] ];

	window.chart = new CHART.Bar(container.get(0), data, options, responsiveOptions).on('draw', function(data) {
		// add value at the top of bars.
		var barHorizontalCenter, barVerticalCenter, label, value, height;
		if (data.type === "bar") {
			barHorizontalCenter = data.x1 + (data.element.width() * .5);
			height = data.element.height();
			barVerticalCenter = data.y1 - data.element.height() - 2;
			value = data.element.attr('ct:value');
			if (value !== '0') {
				label = new Chartist.Svg('text', {
					x : barHorizontalCenter,
					y : barVerticalCenter,
					'text-anchor' : 'middle'
				});
				label.text(commars(value));
				label.addClass("ct-barlabel " + (height > 20 ? "rotate" : ""));
				if (height > 50) {

				}
				data.group.append(label);
			}
		} else if (data.type === 'grid' && data.index !== 0) {
			data.element.remove();
		}
	});
	// render char label;
	var template = '<ul class="cart-label-box">' + '<li class="chart-lbl-hold a">เงินเอาประกันชีวิต</li>' + '<li class="chart-lbl-hold b">ค่ารักษาพยาบาลเหมาจ่าย</li>' + '<li class="chart-lbl-hold c">ค่ารักษาพยาบาลแยกค่าใช้จ่าย</li>'
			+ '<li class="chart-lbl-hold d">โรคร้ายแรง</li>' + '<li class="chart-lbl-hold e">สัญญาเพิ่มเติมอุบัติเหตุ</li>' + '<li class="chart-lbl-hold f">อุบัติเหตุส่วนบุคคล</li>' + '</ul>'
	$(selection).append(template);
	if (RIDER_GRAPH) {
		var data = chart.data;
		data.series = RIDER_GRAPH;
		// data.series=[10000,5000,5000,1000,1000,1500];
		chart.update(data);
	}
}// (Chartist,'#chart1');

function commars(input) {
	if (input) {
		return input.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	return input;
}