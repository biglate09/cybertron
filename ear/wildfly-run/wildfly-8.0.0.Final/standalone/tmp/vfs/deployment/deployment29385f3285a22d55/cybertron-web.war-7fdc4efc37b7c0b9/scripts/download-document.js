(function($){
	var df = /\d{1,2}\/\d{1,2}\/\d{4}/;
	// assign event for elements, have attribute data-email, data-download
	$(document).on('click','[data-email]',openPopup.bind(this,'mail'));
	$(document).on('click','[data-download]',openPopup.bind(this,'load'));

	// open popup
	function openPopup(mode,event){
		var config = readconfig(mode,event);
		if(config) buildPopupTemplate(mode,config,event);
	}

	// read config from element data
	function readconfig(mode,e){
		var data = null;
		var keyword = mode === 'mail'?'email':'download';
		var _max_retry = 5,_retry_count = 0;
		var target = $(e.target);
		do{	
			if(target.data(keyword)){
				data = target.data(keyword).split(",");
			}else{
				target = target.parent();
			}
		}while(!data&&_retry_count++<_max_retry);
		
		var config = {
			type: data[0]||'',
			name: data[1]||'',
			no: data[2]||0,
		}
		config.email = mode === 'mail'?data[3]||'':'';

		config.checker = mode === 'mail'?df.test(data[4])&&data[4].split('/')[2]||data[4]||''
						:df.test(data[3])&&data[3].split('/')[2]||data[3]||'';

		return config;
	}
	// build template
	function buildPopupTemplate(mode, config, e){
		var now = new Date();
		var template = topBlock();
		if(mode === 'mail' && !config.email){
			template+='<h3 class="text-hilight">ขออภัยค่ะ ไม่พบอีเมลในระบบ กรุณาเพิ่มอีเมลในข้อมูลที่อยู่ติดต่อของท่าน  โดยทำการคลิก </h3><a href="'+MENU_LINK.editAddress+'?policyNo='+config.no+'" class="btn btn-default" >เพิ่มอีเมล</a>';
		}else	if(config.type=='notify'){
			template  += notiButton();
		}else{
			var years = config.checker.split("|");
			var matcher = config.checker.match(/\d{4}\-/);
			var offset = matcher?4:2;
			for(var y in years){
				if(/^\d{4}$/.test(years[y])){
					template  += certButton(years[y],offset);
					offset = null;
				}
			}
			template += '<div class="col-xs-12 col-md-12" style="margin-top:10px;">'+
			'<p><span class="form-label-notice-popup">หนังสือรับรองการชำระเบี้ยประกันภัยงวดปีการชำระปัจจุบัน จะเปิดให้ดาวน์โหลดตั้งแต่วันที่ 15 มกราคม ของปีถัดไป</span></p>'+
			'<p><span class="form-label-notice-popup">การดาวน์โหลดหนังสือรับรองการชำระเบี้ยประกันต้องเป็นแบบประกันที่สามารถนำไปลดหย่อนภาษีได้</span></p>'+
			'</div>';
		}

		template+='</div></div>';

		CSSDialog.custom(template,{label:"ปิดหน้าจอ"},'dialog-box-white');

		// support functions
		function topBlock(){
			var template = '';
			template += '<div class="download-content"><h1 style="margin-top:1px;margin-bottom:4px">';
			template += mode === 'load'?'ดาวน์โหลด':'ส่งอีเมล';
			template += config.type=='notify'?'หนังสือแจ้งเตือนกรมธรรม์':'หนังสือรับรองการชำระเบี้ยประกันภัย';
			template += '<span>'+config.name+' </span> </h1> <h2 style="margin-top:1px">กรมธรรม์เลขที่ <span>'+config.no+'</span> </h2>';
			if(mode ==='mail' && config.email){
					template += '<div id="dalog-email" class="input-group"   style="margin-bottom:8px;"><span class="input-group-addon bigger" id="sizing-addon1">อีเมล</span>';
					template += '<input type="text" class="form-control bigger" id="txt-email" value="'+config.email+'" data-value="'+config.email+'" disabled></div><br/>';
			}
			template += '<div class="row">';
			return template;
		}

		function notiButton(){
			var template = '';
			var iconStyle = mode === 'mail'?'email':'download';
			template += '<div class="col-xs-6 col-xs-offset-3 col-md-4 col-md-offset-4">';
			if(config.checker==='0'){
				template += '<button class="dialog-button '+iconStyle+' disabled" disabled>งวดล่าสุด ที่ไม่เกินกำหนดชำระ</button>'
				template +='</div><div class="col-xs-12 disabled text-dark">ขออภัย ไม่สามารถดาวน์โหลดหนังสือแจ้งเตือนการชำระเบี้ยประกันภัยได้<br/>เนื่องจากไม่อยู่ในรอบกำหนดการชำระเบี้ยประกันภัย<br/>กรุณาติดต่อสาขาไทยสมุทรฯ หรือศูนย์ลูกค้าสัมพันธ์</div>';
			}else{
				var btnId = 'btn-dl-'+new Date().getTime();
				template += '<div id="'+btnId+'"></div>';
				checkAvialable(config,
					/* fail */		function(id){$("#"+id).html('<button class="dialog-button '+iconStyle+' disabled" disabled>งวดล่าสุด ที่ไม่เกินกำหนดชำระ</button>');}.bind(this,btnId),
					/* success */	function(id){
													if(mode === 'mail'){
														$("#"+id).html('<button class="dialog-button '+iconStyle+'" data-click-'+iconStyle+'="'+config.no+',0,'+config.type+'">'+
														"งวดล่าสุด ที่ไม่เกินกำหนดชำระ"+
														'</button>');
													}else{
														var url = REPORT_URLS.download[config.type]+"?policyNo="+config.no;
														$("#"+id).html('<a target="_blank" class="dialog-button '+iconStyle+'" href="'+url+'" downlaod>งวดล่าสุด ที่ไม่เกินกำหนดชำระ</a>');
													}
												}.bind(this,btnId));
				template +='</div>';
			}
			return template;
		}

		function certButton(year, offset){
			var template = '';
			var iconStyle = mode === 'mail'?'email':'download';
			 if(year.indexOf("-")>=0){
			 	template +=  '<div class="col-xs-6 col-sm-4 '+(offset?'col-sm-offset-'+offset:'')+( offset===4?' col-xs-offset-3':'')+'">';
			 	template += '<button class="dialog-button '+iconStyle+' disabled" disabled>งวดปี พ.ศ. '+year.replace("-","")+'</button>';
			 	template +=  '</div>';
			 }else{
				var btnId = 'btn-dl-'+year+new Date().getTime();
				year = year.replace("-","")||year;
				var _config = { type: config.type,no: config.no, year: year};

				template +=  '<div id="'+btnId+'" class="col-xs-6 col-sm-4 '+(offset?'col-sm-offset-'+offset:'')+( offset===4?' col-xs-offset-3':'')+'">';
				checkAvialable(_config,
					/* fail */		function(id){$("#"+id).html('<button class="dialog-button '+iconStyle+' disabled" disabled>งวดปี พ.ศ. '+year.replace("-","")+'</button>');}.bind(this,btnId),
					/* success */	function(id){
													if(mode === 'mail'){
														$("#"+id).html('<button class="dialog-button '+iconStyle+'" data-click-'+iconStyle+'="'+config.no+','+year+','+config.type+'">'+
														'งวดปี พ.ศ. '+year+
														'</button>');
													}else{
														var url = REPORT_URLS.download[config.type]+"?policyNo="+config.no+"&taxYear="+year;
														$("#"+id).html('<a target="_blank" class="dialog-button '+iconStyle+'" href="'+url+'" downlaod>งวดปี พ.ศ. '+year+'</a>');
													}
												}.bind(this,btnId));
				template += '</div>';
			 }

			return template;
		}

		function checkAvialable(config,error,success){
			var url = REPORT_URLS.available;
			var data = {docType: config.type,policyNo: config.no,taxYear:config.year||'' };
			$.ajax({
				method: "POST",url : url,data : data,
				beforeSend: function() {error()},
				success : function(json) {json.data&&json.data.SUCCESS?success():error(); },
				error: function(){ error();}
		});
	}
}
// send email on click button
$(document).on('click','[data-click-email]',function(e){
	if(e.target){
		if(CSSValidate($("#dalog-email"))){
			var data = $(e.target).data('clickEmail').split(","),
			no=data[0]||'',year=data[1]||'',type=data[2]||'',
			url = REPORT_URLS.email[type];
			var email = $("#txt-email").val();
			var oldMail = $("#txt-email").data('value');
			var check = $("#_flag").is(":checked")||false;
				$.ajax({
						method: "POST",
					url : url,
					data : {
						email: email,
						policyNo:no,
						taxYear: year,
						update: check
					},
					success : function(json) {
						if (json.data.SUCCESS) {
							if(json.data.message)
									CSSDialog.alert(json.data.message).on('ok',function(){window.location = window.location;});
						} else {
							CSSDialog.warn('<h1>'+(json.data.error||'ไม่สามารถส่งอีเมลได้')+'</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>').on('ok',function(){CSSDialog.dismiss()});;
						}
					}
				});
		}
	}
});
})(jQuery);
