function regisEasyLoanAgain(policyNo,policyType) {
	CSSDialog.confirm("ท่านได้ดำเนินการยื่นขอกู้เงินตามกรมธรรม์ไปแล้ว ซึ่งอยู่ระหว่างการพิจารณาอนุมัติ หากท่านต้องการยื่นกู้ใหม่ โดยยกเลิกคำร้องขอกู้เดิมตามกรมธรรม์เพิ่มเติมกรุณากดตกลง").on('confirm',function(){
		window.location = ACTIONS.easyloan+'?params.policyNo='+policyNo+'&params.policyType='+policyType+'&params.refPage=Detail';
	});
}