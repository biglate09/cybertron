/**
 * @author kittipong.su
 */
(function() {
	APP.namespace('constants', {
		'zipcode': []
	});
	
	var package = APP.namespace('classes.components.selection');
	var NBSTreeAddress = Class.create({
		initialize: function(config) {
            this.initialized = false;
            this.zipcode=[];
            this.config = {
            		provinceId: '',
            		districtId: '',
            		subDistrictId: '',
            		zipcodeId: '',
                    afterChange : undefined
            };
            config && Object.extend(this.config, config);
        },
        init: function() {
        	var zipcode = this.zipcode;
        	zipcode.clear();
        	var config = this.config;

    		document.fire('province:list', {provinceId: config.provinceId});
    		
    		if(config.provinceId)
    		{
    			$(config.provinceId).on('change',function(event){
        			if(config.districtId) document.fire('options:remove', {id: config.districtId});
        			if(config.subDistrictId) document.fire('options:remove', {id: config.subDistrictId}); 
        			$(config.zipcodeId).value='';
        			if(config.provinceId && config.districtId) document.fire('district:list', {afterChange: config.afterChange, provinceId: config.provinceId, districtId: config.districtId});
        		});
    		}
    		
    		if(config.districtId)
    		{
    			$(config.districtId).on('change',function(event){
        			if(config.subDistrictId) document.fire('options:remove', {id: config.subDistrictId});
        			$(config.zipcodeId).value='';
        			if(config.provinceId && config.districtId && config.subDistrictId) document.fire('subdistrict:list', {afterChange: config.afterChange, provinceId: config.provinceId, districtId: config.districtId, subDistrictId: config.subDistrictId , zipcode:zipcode});
        		});
    		}
    		
    		if(config.subDistrictId && config.zipcodeId)
    		{
	    		$(config.subDistrictId).on('change',function(event)
				{
	    			$(config.zipcodeId).value = zipcode[$(config.subDistrictId).selectedIndex-1]||'';
                    if(config.afterChange){
                        config.afterChange();
                    }
				});
    		}
    		
        	document.on('options:remove', function(event){
        		var id=event.memo.id;
        		if(id)
        		{
        			while($(id).length>1){
        				$(id).remove(1);
                	}
        		}
        		
//        		if(config.zipcodeId)
//        		{
//        			$(config.zipcodeId).value='';
//        		}
        	});
        },
        set: function(provinceValue, districtValue, subdistrictValue, zipcodeValue) {
        	var config = this.config;
        	var zipcode = this.zipcode;
        	zipcode.clear();

        	if (config.provinceId && provinceValue) {
        		$(config.provinceId).value = provinceValue;
        		if ($(config.provinceId).value.blank()) {
        			$(config.provinceId).value = $(config.provinceId).firstChild.value;
        		}
                if(config.afterChange){
                    config.afterChange();
                }
        	}

            if(config.districtId) document.fire('options:remove', {id: config.districtId});
            if(config.subDistrictId) document.fire('options:remove', {id: config.subDistrictId}); 
        	
        	if (config.provinceId && config.districtId && districtValue) {
        		document.fire('district:list', {afterChange: config.afterChange, provinceId: config.provinceId, districtId: config.districtId, selectValue: districtValue});
        	}
        	
        	if (config.provinceId && config.subDistrictId && districtValue && subdistrictValue) {
        		document.fire('subdistrict:list', {afterChange: config.afterChange, provinceId: config.provinceId, subDistrictId: config.subDistrictId, districtValue: districtValue, selectValue: subdistrictValue, zipcode:zipcode});
        	}
        	
        	if (config.zipcodeId && zipcodeValue) {
        		$(config.zipcodeId).value = zipcodeValue;
        	}else{
                $(config.zipcodeId).value = '';
            }
        }
	});
	
	document.on('province:list', function(event){
		var provinceId = event.memo.provinceId,
			url = APP.config.contextPath+'/secure/iserve/components/tree/address/provincenbs.html';
		
		handlers = APP.response.handlers;
        new Ajax.Request(url, {
            method: 'POST',
            asynchronous: true,
            encoding: 'UTF-8',
            evalJSON: true,
            parameters: null,
            onSuccess: (function(transport) {
                handlers.success(transport, function(json){
                	if(json.data && json.data.province)
                	{
                    	$A(json.data.province).each(function(o){
                    		$(provinceId).insert(new Element('option', {value: o.value}).update(o.value));
                    	});
                	}
                });
            }),
        });
	});
	
	document.on('district:list', function(event){
		var provinceId = event.memo.provinceId,
			districtId = event.memo.districtId,
			selectValue = event.memo.selectValue,
			url = APP.config.contextPath+'/secure/iserve/components/tree/address/districtnbs.html';
		
		handlers = APP.response.handlers;
        new Ajax.Request(url, {
            method: 'POST',
            asynchronous: true,
            encoding: 'UTF-8',
            evalJSON: true,
            parameters: Object.toQueryString({provinceId: $(provinceId).value}),
            onSuccess: (function(transport) {
                handlers.success(transport, function(json){
                	if(json.data && json.data.district)
                	{
                		var isSelected = false;
                    	$A(json.data.district).each(function(o){
                    		var selectConfig = {value: o.value};
                    		if (selectValue == o.value) {
                    			selectConfig.selected = 'selected';
                    			isSelected = true;
                    		}
                    		$(districtId).insert(new Element('option', selectConfig).update(o.value));
                    	});
                    	if (!isSelected) {
//                    		$(districtId).value = $(districtId).firstChild.value;
                    		$(districtId).value =$$('#'+districtId)[0].value
                    	}
                	}
                    if(event.memo.afterChange){
                        event.memo.afterChange();
                    }
                });
            }),
        });
	});
	
	document.on('subdistrict:list', function(event){
		var provinceId = event.memo.provinceId,
		 	districtId = event.memo.districtId,
		 	subDistrictId = event.memo.subDistrictId,
		 	zipcodeId = event.memo.zipcodeId,
		 	districtValue = event.memo.districtValue,
		 	selectValue = event.memo.selectValue,
		 	url = APP.config.contextPath+'/secure/iserve/components/tree/address/subdistrictnbs.html';
		
		handlers = APP.response.handlers;
        new Ajax.Request(url, {
            method: 'POST',
            asynchronous: true,
            encoding: 'UTF-8',
            evalJSON: true,
            parameters: Object.toQueryString({provinceId: $(provinceId).value, districtId: (districtValue || $(districtId).value) }),
            onSuccess: (function(transport) {
                handlers.success(transport, function(json){
                	if(json.data && json.data.subdistrict)
                	{
                		event.memo.zipcode.clear();
                		var i=0;
                		var isSelected = false;
                    	$A(json.data.subdistrict).each(function(o){
                    		var selectConfig = {value: o.value};
                    		if (selectValue == o.value) {
                    			selectConfig.selected = 'selected';
                    			isSelected = true;
                    		}
                    		$(subDistrictId).insert(new Element('option', selectConfig).update(o.value));
                    		event.memo.zipcode[i++]=o.zipcode;
                    	});
                    	
                    	if (!isSelected) {
//                    		$(subDistrictId).value = $(subDistrictId).firstChild.value;
                    		$(subDistrictId).value =$$('#'+subDistrictId)[0].value
                    	}
                        if(event.memo.afterChange){
                            event.memo.afterChange();
                        }
                	}
                });
            }),
        });
	});
	
	package.NBSTreeAddress = NBSTreeAddress;
})();
