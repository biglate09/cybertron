(function(CHART, selection) {
	var container = $("<div>").addClass('ct-chart');
	var isIE = !(detectIE() == false);
	var slabel = [ 'Life', 'HC', 'DAB', 'CI', 'ACC', 'PA' ];
	var label = [ 'เงินเอาประกันภัย', 'ค่ารักษาพยาบาลเหมาจ่าย', 'ค่ารักษาพยาบาลแยกค่าใช้จ่าย', 'โรคร้ายแรง', 'สัญญาเพิ่มเติมอุบัติเหตุ', 'อุบัติเหตุส่วนบุคคล' ];
	var maxgraph = [1000000, 4000, 2500,1000000, 1000000, 2000000];
	var suggest = [750000, 2500,1000 ,1000000, 500000, 1000000];
	var compare = [];
	var percent = [];
	for(i=0;i<suggest.length;i++){
		var p = RIDER_GRAPH[i]/maxgraph[i]*100;
		var c = suggest[i]/maxgraph[i]*100;
		
		p = p>100?100:p;
		
		compare.push(p);
		percent.push(c);
	}
	var series = [];
		series.push(percent);
		series.push(compare);
	var substitute = [suggest,RIDER_GRAPH]
	container.appendTo($(selection));
	var OFFSET = 30;
	var data = {
		labels : slabel,
		series : series
	};
	var options = {
		height : '300px',
		horizontalBars: true, 
		axisX : {
			offset : 0,
			scaleMinSpace : 50,
			showLabel : false,
		},
		axisY : {
			offset : OFFSET,
			maxTicksLimit: 0,
			scaleMinSpace : 0
		}
	};
	var responsiveOptions = [];

	window.chart = new CHART.Bar(container.get(0), data, options, responsiveOptions).on('draw', function(data) {
		// add value at the top of bars.
		var barHorizontalCenter, barVerticalCenter, label, value, height,labelLength,containerStart,fullWidth,style=0;
		if (data.type === "bar") {
			var index = data.index, seriesIndex = data.seriesIndex;
			value = data.element.attr('ct:value');
//			if (value !== '0') {
				value = commars(substitute[seriesIndex][index]);
				barHorizontalCenter = data.x1 + (data.element.width()) -(isIE?25:0);
				barVerticalCenter = data.y1 + 5;
				label = new Chartist.Svg('text', {
					x : barHorizontalCenter,
					y : barVerticalCenter,
					'text-anchor' : 'middle'
				});
				label.text(value);
				label.addClass("ct-barlabel " + (height > 20 ? "rotate" : ""));
				if (height > 50) {

				}
				data.group.append(label);
				setTimeout(function(data,label,start,end){
					var barLength = end - start;
					var center = (label.width()/2);
					label._node.x.baseVal.getItem(0).value += center;
					
					var rightbound = label._node.x.baseVal.getItem(0).value +label.width();
					if(rightbound>end){
						label._node.x.baseVal.getItem(0).value -= label.width();
					}
					var leftbound = label._node.x.baseVal.getItem(0).value - center;
					if(leftbound<start){
						label._node.x.baseVal.getItem(0).value = start+center;
					}
				}.bind(this,data,label,parseInt($('.ct-vertical').attr('x1')),parseInt($('.ct-vertical').attr('x2'))),1);
//			}
		} else if (data.type === 'grid') {
			if(data.index !== 0)
			data.element.remove();
		}
	});
	// render char label;
	var template1 = '<ul class="cart-label-box">' + '<li class="chart-lbl-hold a">ความคุ้มครองที่มีอยู่</li><li class="chart-lbl-hold b">ค่าเฉลี่ยความคุ้มครองที่แนะนำ</li></ul>'
	$(selection).prepend(template1);
	
//	var template2 = '<div class="right" style="padding-right:15px;color:black;">หน่วย : บาท</div>'
//	$(selection).append(template2);
	
	var template3 = '<ul class="cart-label-box-noborder">';
		for(var o = label.length-1;o>=0;o--){
			template3 += '<li class="chart-lbl"><h3>'+slabel[o]+"</h3> "+label[o]+'</li>'
		}
	template3 += '</ul>';
	$(selection).append(template3);
//	if (RIDER_GRAPH) {
//		var data = chart.data;
//		data.series = RIDER_GRAPH;
		// data.series=[10000,5000,5000,10,0,1500];
//		chart.update(data);
//	}
}) (Chartist,'#chart1');

function commars(input) {
	if (input) {
		return input.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	return input;
}
function detectIE() {
	  var ua = window.navigator.userAgent;

	  // Test values; Uncomment to check result …

	  // IE 10
	  // ua = 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)';
	  
	  // IE 11
	  // ua = 'Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko';
	  
	  // Edge 12 (Spartan)
	  // ua = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0';
	  
	  // Edge 13
	  // ua = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586';

	  var msie = ua.indexOf('MSIE ');
	  if (msie > 0) {
	    // IE 10 or older => return version number
	    return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
	  }

	  var trident = ua.indexOf('Trident/');
	  if (trident > 0) {
	    // IE 11 => return version number
	    var rv = ua.indexOf('rv:');
	    return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
	  }

	  var edge = ua.indexOf('Edge/');
	  if (edge > 0) {
	    // Edge (IE 12+) => return version number
	    return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
	  }

	  // other browser
	  return false;
	}

function regisEasyLoanAgain(policyNo,policyType) {
	CSSDialog.confirm("ท่านได้ดำเนินการยื่นขอกู้เงินตามกรมธรรม์ไปแล้ว ซึ่งอยู่ระหว่างการพิจารณาอนุมัติ หากท่านต้องการยื่นกู้ใหม่ โดยยกเลิกคำร้องขอกู้เดิมตามกรมธรรม์เพิ่มเติมกรุณากดตกลง").on('confirm',function(){
		window.location = ACTIONS.easyloan+'?params.policyNo='+policyNo+'&params.policyType='+policyType+'&params.refPage=Info';
	});
}