/**
 * @author santi.li@ocean.co.th
 */

(function() {
	YUI({ combine: false, throwFail: true, lang: 'th-TH' }).use('node','dd-plugin', 'panel','node-event-simulate', function(Y) {
	var _msg,_conf;
	var _timeoutTime = 100;	
	var _zIndex = 100000;
	var _callbackLocal = "callback:local:"+_ranDomFunc();
	function _ranDomFunc(){
		return Math.floor((Math.random()*1000000000)+1);
	}
	function _getZindex(){
		return _zIndex++;
	}
	function _resetValue(inputArrReset,type){
		var arrReset = inputArrReset.toString().split(",");
		for(var a=0;a<arrReset.length;a++){		
			arrReset[a] = (arrReset[a].substring(0,1)!="#")?"#"+arrReset[a]:arrReset[a];
			if(Y.one(arrReset[a])!=null){
				Y.one(arrReset[a]).set(type,'');
			}			
		}
	}
	document.on(_callbackLocal,function(event){		
		//process--------------------------------------------------------------------
		
		//close
		var localSrc = event.memo.src;
		if(localSrc=='ok' || localSrc=='cancel'){
			_conf.hide();
			_conf.destroy();
		}else if(localSrc=='msg'){
			_msg.hide();
			_msg.destroy();
		}
		
		//set background
		var localBeforeZid = event.memo.beforeZid;
		if(localBeforeZid!=''){
			Y.one('.yui3-widget-mask').setStyle('display','block');
			Y.one('.yui3-widget-mask').setStyle('z-index',(localBeforeZid-1));								
		}
		//reset input
		var localResetInput = event.memo.resetInput;
		if(localResetInput!=null){
			var sendInput = [];
			if( typeof localResetInput === 'string' ) {//'input1'
				sendInput.push(localResetInput);
			}else if( typeof localResetInput == 'object'){//['input1','input2']
				sendInput = localResetInput;
			}
			_resetValue(sendInput,'value');
		}
		//reset text
		var localResetText = event.memo.resetText;
		if(localResetText!=null){
			var sendText = [];
			if( typeof localResetText === 'string' ) {//'input1'
				sendText.push(localResetText);
			}else if( typeof localResetText == 'object'){//['input1','input2']
				sendText = localResetText;
			}
			_resetValue(sendText,'text');
		}
		//focus
		var localFocus = event.memo.focus;
		if(localFocus!=null){
			localFocus = (localFocus.substring(0,1)!="#")?"#"+localFocus:localFocus;
			if(Y.one(localFocus)!=null){
				Y.one(localFocus).focus();
			}			
		}
		
		//push callback event and data
		var localEventCallbackOk = event.memo.callbackClient;
		var localData = event.memo.data;
		document.fire(localEventCallbackOk,{data:localData});
		
		//process--------------------------------------------------------------------
	});

		APP.namespace('widget', {
			Confirm : function(o) {
				
				//confirm input parameter-----------------------------------------------------------
				var headerText = o.headerText!=null?o.headerText:"หน้าต่างยืนยันการทำงาน";
				var bodyText = o.bodyText!=null?o.bodyText:"ยืนยันการบันทึกข้อมูล ?";
				var buttonOkText = o.buttonOkText!=null?o.buttonOkText:"ตกลง";
				var buttonCancelText = o.buttonCancelText!=null?o.buttonCancelText:"ยกเลิก";
				var width = o.width!=null?o.width:"inherit";
				var zIndex = o.zIndex!=null?o.zIndex:_getZindex();
				var eventCallbackOk = o.eventCallbackOk!=null?o.eventCallbackOk:"confirm:ok";
				var eventCallbackCancel = o.eventCallbackCancel!=null?o.eventCallbackCancel:"confirm:cancel";
				var data = o.data!=null?o.data:"callbackData";
				var resetInput = o.resetInput!=null?o.resetInput:null;
				var resetText = o.resetText!=null?o.resetText:null;
				var focus = o.focus!=null?o.focus:null;
				//confirm input parameter-----------------------------------------------------------
				
				//local parameter-----------------------------------------------------------
				var beforeZid='';
				//local parameter-----------------------------------------------------------
				
					var id = "panel_"+_ranDomFunc();
					var avalible = false;
					for(var i=0;avalible==false;i++){
						if(Y.one("#"+id)==null){
							avalible=true;
						}else{
							id = "panel_"+_ranDomFunc();
						}
					}
					
					if((Y.one('.yui3-widget-mask')!=null&&Y.one('.yui3-widget-mask').getStyle('display')=="block")){
						beforeZid = Y.one('.yui3-widget-mask').getStyle('z-index');
					}
						
					var str = "<div class='yui3-skin-sam'>" +
					"<div id="+id+">" +
					"<div class='yui3-widget-bd'>" +
					"<table width='90%' align='center'>" +
					"<tr>" +
					"<td colspan='2' align='center' >" +
					"<img src='"+APP.config.contextPath+"/images/MessageBox/confirm.jpg' style=\"margin-right:10px;\" >" +
					bodyText +
					"</td>" +
					"</tr>" +
					"<tr>" +
					"<td>" +
					"<button onclick=\"javascript:document.fire('"+_callbackLocal+"',{src:'ok',beforeZid:'"+beforeZid+"',resetInput:'"+resetInput+"',resetText:'"+resetText+"',focus:'"+focus+"',callbackClient:'"+eventCallbackOk+"',data:'"+data+"'})\" >"+buttonOkText+"</button>" +
					"</td>" +
					"<td align='right' >" +
					"<button onclick=\"javascript:document.fire('"+_callbackLocal+"',{src:'cancel',beforeZid:'"+beforeZid+"',resetInput:'"+resetInput+"',resetText:'"+resetText+"',focus:'"+focus+"',callbackClient:'"+eventCallbackCancel+"',data:'"+data+"'})\" >"+buttonCancelText+"</button>" +
					"</td>" +
					"</tr>" +
					"</table>" +
					"</div>" +
					"</div>" +
					"</div>";
					Y.one(document.body).append(str);
					
					_conf = new Y.Panel({
						srcNode : '#'+id,
						headerContent : headerText,
						width : width,
						zIndex : zIndex,
						centered : true,
						modal : true,
						visible : false,
						render : true,
						buttons: [],
						hideOn:[],
						plugins : [ Y.Plugin.Drag ]
					});					
					_conf.plug(Y.Plugin.Drag,{handles:['.yui3-widget-hd']});	
					
					window.setTimeout(function(){
						_conf.show();
					},_timeoutTime);
			},
			MessageBox : function(o){
				
				//messagebox input parameter-----------------------------------------------------------
				var headerText = o.headerText!=null?o.headerText:"หน้าต่างข้อความ";
				var bodyText = o.bodyText!=null?o.bodyText:"ข้อความแจ้งเตือน !";
				var buttonOkText = o.buttonOkText!=null?o.buttonOkText:"ตกลง";
				var width = o.width!=null?o.width:"inherit";
				var zIndex = o.zIndex!=null?o.zIndex:_getZindex();
				var eventCallbackOk = o.eventCallbackOk!=null?o.eventCallbackOk:"msg:ok";
				var data = o.data!=null?o.data:"callbackData";
				var resetInput = o.resetInput!=null?o.resetInput:null;
				var resetText = o.resetText!=null?o.resetText:null;
				var focus = o.focus!=null?o.focus:null;
				//messagebox input parameter-----------------------------------------------------------
				
				//local parameter-----------------------------------------------------------
				var beforeZid='';
				//local parameter-----------------------------------------------------------
				
				var id = "panel_"+_ranDomFunc();
				var avalible = false;
				for(var i=0;avalible==false;i++){
					if(Y.one("#"+id)==null){
						avalible=true;
					}else{
						id = "panel_"+_ranDomFunc();
					}
				}
				
				if((Y.one('.yui3-widget-mask')!=null&&Y.one('.yui3-widget-mask').getStyle('display')=="block")){
					beforeZid = Y.one('.yui3-widget-mask').getStyle('z-index');
				}
					
				var str = "<div class='yui3-skin-sam'>" +
				"<div id="+id+">" +
				"<div class='yui3-widget-bd'>" +
				"<table id=\"_tableMessageBox\" width='90%' align='center'>" +
				"<tr>" +
				"<td align='center' >" +
				"<img src='"+APP.config.contextPath+"/images/MessageBox/message.jpg' style=\"margin-right:10px;\" >" +
				bodyText +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td align='center' >" +
				"<button id=_classespopmessageboxbuttonasdf onclick=\"javascript:document.fire('"+_callbackLocal+"',{src:'msg',beforeZid:'"+beforeZid+"',resetInput:'"+resetInput+"',resetText:'"+resetText+"',focus:'"+focus+"',callbackClient:'"+eventCallbackOk+"',data:'"+data+"'})\" >"+buttonOkText+"</button>" +
				"</td>" +							
				"</tr>" +
				"</table>" +
				"</div>" +
				"</div>" +
				"</div>";
				Y.one(document.body).append(str);
				
				_msg = new Y.Panel({
					srcNode : '#'+id,
					headerContent : headerText,
					width : width,
					zIndex : zIndex,
					centered : true,
					modal : true,
					visible : false,
					render : true,
					buttons: [],
					hideOn:[],
					plugins : [ Y.Plugin.Drag ]
				});					
				_msg.plug(Y.Plugin.Drag,{handles:['.yui3-widget-hd']});	
				
				window.setTimeout(function(){
					_msg.show();
					
					var sub1 = Y.one('body').on('key', function(e) {							
						sub1.detach();
						Y.one('#_classespopmessageboxbuttonasdf').simulate('click');
					}, 'enter');
					
				},_timeoutTime);					
			}

		});
		//old version handler
		APP.namespace('classes.popup',{
			Confirm : function(o){
				return APP.widget.Confirm({
					headerText:o.htext,
					bodyText:o.btext,
					buttonOkText:o.boktext,
					buttonCancelText:o.bcanceltext,
					width:o.width,
					zIndex:o.zindex,
					eventCallbackOk:o.ok,
					eventCallbackCancel:o.cancel,
					data:o.data
				});
			},
			MessageBox : function(o){
				return APP.widget.MessageBox({
					headerText:o.htext,
					bodyText:o.btext,
					buttonOkText:o.boktext,
					width:o.width,
					zIndex:o.zindex,
					eventCallbackOk:o.ok,
					data:o.data
				})
			}
		});
	});	
})();

