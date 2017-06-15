<script>
	var ACTIONS={
		completesignup:'${contextPath}/pub/page/complete/completesignup.html'
	}
	var SESSION={
		sessionid: '${sessionId?html}'
	}
	
	$(document).ready(function(){
		var sessionId = SESSION.sessionid;
		if(sessionId != ''){
			$.ajax({
  method: "POST",
				url : ACTIONS.completesignup,
				data : {'sessionId' : sessionId},
				success: function(json){
					if(json.data.success){
						CSSDialog.alert(json.data.resultsMsg).on('ok', 
							function(){
								window.location = '${contextPath}/secure/home.html';
							}
						);
					}else{
						CSSDialog.warn(json.data.resultsMsg).on('ok', 
							function(){
								window.location = '${contextPath}/secure/home.html';
							}
						);
					}
				},
				error: function(err){
					
				},
				complete: function(data){
					
				}
			});
		}
		
	});
</script>