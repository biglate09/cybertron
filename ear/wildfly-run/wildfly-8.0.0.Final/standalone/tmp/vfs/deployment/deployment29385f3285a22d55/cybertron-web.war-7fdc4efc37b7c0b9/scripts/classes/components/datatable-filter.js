YUI.add(
    
    'datatable-filter',
    
    function(Y) { 
	
	/**
	   Adds support for filtering the table data by API methods
	   
	   @module datatable
	   @submodule datatable-filter
	   @since 3.5.0
	**/
	var YLang = Y.Lang,
	isBoolean = YLang.isBoolean,
	isString = YLang.isString,
	isArray = YLang.isArray,
	isObject = YLang.isObject,

	toArray = Y.Array,
	sub = YLang.sub;

	/**
	   <pre><code>
	   var table = new Y.DataTable({
	   columns: [ 'id', 'username', 'name', 'birthdate' ],
	   data: [ ... ],
	   filters: true // this will render a filter input box for every column
	   });
	   
	   table.render('#table');
	   </code></pre>
	   
	   Setting `filters` to `true` will enable UI filtering for all columns. To enable
	   UI filtering for certain columns only, set `filters` to an array of column keys,
	   or just add `filters: true` to the respective column configuration objects.
	   This uses the default setting of `filters: auto` for the DataTable instance.
	   
	   <pre><code>
	   var table = new Y.DataTable({
	   columns: [
	   'id',
	   { key: 'username', renderFilter: true },
	   { key: 'name' },
	   { key: 'birthdate', renderFilter: true, filter : "=123", filterFn : customFilterFn }
	   ],
	   data: [ ... ]
	   // filters: 'auto' is the default
	   });
	   
	   To disable UI filtering for all columns, set `filters` to `false`. This still
	   permits filtering via the API methods.

	   filter via api:

	   var filterBy = 
	   {  
	   username : "=student",
	   name       : "%richard",
	   birthdate  : ">01-01-1975"
	   }
		    
	table.set("filterBy",filterBy);
	
	As new records are inserted into the table's `data` ModelList, they will be checked against the filter to determine if they will be rendered
	
	The current filters are stored in the `filterBy` attribute. Assigning this value at instantiation will automatically filter your data.
	
	Filtering is done by a simple value comparison using '=', '!=', '<', '<=', '>', '>='  on the field value. 
	
	If you need custom filtering, add a filter function in the column's `filterFn` property. // TODO...
	
	<pre><code>
	function applePiesFilter(a) {
	return a.get("type") == 'apple' ? true : false;
	}
	
	var table = new Y.DataTable({
	columns: [ 'id', 'username', { key: name, filterFn: nameFilter }, 'birthdate' ],
	data: [ ... ],
	filters: [ 'username', 'name', 'birthdate' ]
	});
	</code></pre>
	
	See the user guide for more details.
	
	@class DataTable.Filters
	@for DataTable
	@since 3.5.0
	**/
	function Filters() {}

	Filters.ATTRS = {
	    
	    // Which columns in the UI should suggest and respond to filtering interaction
	    // pass an empty array if no UI columns should show filters, but you want the
	    // table.filter(...) API
	    
	    /**
	       Controls which columns can show a filtering input
	       
	       Acceptable values are:
	       
	       * "auto" - (default) looks for `renderFilter: true` in the column configurations
	       * "true" - all columns have a filter field rendered
	       * "false" - no UI filters are enabled
	       * {String[]} - array of key names to give filter fields
	       
	       @attribute filters
	       @type {String|String[]|Boolean}
	       @default "auto"
	       @since 3.5.0
	    **/
	    filters: {
		value: 'auto',
		validator: '_validateFilters'
	    },
	    
	    /**
	       The current filter configuration to maintain in the data.
	       
	       Accepts column `key` objects with a single property the value of the filter
	       E.g. `{ username: '%student' }`
	       E.g. `[{ username : '%student' }, { age : '<40'}]
	       
	       @attribute filterBy
	       @type {String|String[]|Object|Object[]}
	       @since 3.5.0
	    **/
	    filterBy: {
		validator: '_validateFilterBy'
	    },
	    
	    /**
	       Strings containing language for filtering tooltips.
	       
	       @attribute strings
	       @type {Object}
	       @default (strings for current lang configured in the YUI instance config)
	       @since 3.5.0
	    **/
	    strings: {}
	};

	Y.mix(Filters.prototype, {
	    
	    /**
	       Filter the data in the `data` ModelList and refresh the table with the new
	       order.
	       
	       @method filter
	       @param {String|String[]|Object|Object[]} fields The field(s) to filter by
	       @param {Object} [payload] Extra `filter` event payload you want to send along
	       @return {DataTable}
	       @chainable
	       @since 3.5.0
	    **/
	    filter : function (fields, payload) {

		/**
		   Notifies of an impending filter, either from changing the filter in the UI
		   header, or from a call to the `filter`  method.
		   
		   The requested filter is available in the `filterBy` property of the event.
		   
		   The default behavior of this event sets the table's `filterBy` attribute.
		   
		   @event filter
		   @param {String|String[]|Object|Object[]} filterBy The requested filter
		   @preventable _defFilterFn
		**/
		return this.fire('filter', Y.merge((payload || {}), {
		    filterBy: fields || this.get('filterBy')
		}));
	    },
	    
	    /**
	       Template for the row that will hold the filter inputs
	       @property FILTERS_HEADER_CELL_TEMPLATE
	       @type {HTML}
	       @value 
	    **/
	    
	    FILTERS_HEADER_ROW_TEMPLATE : '<tr class="{filterRowClassName}" tabindex="0"></tr>',
	    
	    /**
	       Template for the row that will hold the filter inputs
	       @property FILTERS_HEADER_CELL_TEMPLATE
	       @type {HTML}
	       @value 
	       //<select><option value="=">=</option><option value="%">%</option><option value="!=">!=</option><option value="!%">!%</option><option value="&gt;">%gt</option><option value="&gt;=">&gt;=</option><option value="&lt;">&lt;</option><option value="&lt;=">&lt;=</option></select>
	       **/
	    
	    FILTERS_HEADER_CELL_TEMPLATE : '<th class="{className}" tabindex="0"  rowspan="1" colspan="1" title="Filter by {colName}">' + 
		'<div class="{linerClass}" tabindex="0"><input type="text" data-yui3-col-key="{colKey}" class="{inputClass}"/></div></th>',

	    /**
	       Template for the row that will doesn't have filter inputs
	       @property FILTERS_HEADER_CELL_TEMPLATE_NONE
	       @type {HTML}
	       @value 
	    **/
	    
	    FILTERS_HEADER_CELL_TEMPLATE_NONE : '<th class="{className}" tabindex="0"  rowspan="1" colspan="1" title="Filtering unavailable on this field"></th>',
	    
	    //--------------------------------------------------------------------------
	    // Protected properties and methods
	    //--------------------------------------------------------------------------
	    /**
	       Filters the `data` ModelList based on the new `filterBy` configuration.
	       
	       @method _afterFilterByChange
	       @param {EventFacade} e The `filterByChange` event
	       @protected
	       @since 3.5.0
	    **/
	    _afterFilterByChange: function (e) {
		
		var filters;

		// Can't use a setter because it's a chicken and egg problem. The
		// columns need to be set up to translate, but columns are initialized
		// from Core's initializer. So construction-time assignment would
		// fail.
		// WHAT DOES THIS MEAN??

		this._setFilterBy();

		if (this._filterBy.length) {
		    
		    // build the filter function
		    this._buildFilterFn();
		    
		    // get the filtered data
		    this._filteredData = this.data.filter( { asList : true }, this._filterFn);

		} else {
		    this._filteredData = this.data;
		}
		// 'hide' the filtered rows
		this._hideFilteredData();
		
	    },
	    
	    /**
	       @description if the row is not in the filtered data hide it, otherwise show it
	       @method _hideFilteredData
	       @protected
	       @since 3.5.0
	    **/
	    _hideFilteredData: function () {

		var i,len,clientId;

		for(i=0, len = this.data.size();  this._filteredData.getById && i < len; ++i) {
		    
		    clientId = this.data.item(i).get("clientId");
		    
		    if(this._filteredData.getByClientId(clientId)) {
				$$('.yui3-datatable-data tr[data-yui3-record="'+clientId+'"]')[0].style.display = 'table-row';
			} else {
				$$('.yui3-datatable-data tr[data-yui3-record="'+clientId+'"]')[0].style.display = 'none';
			}
		}
	    },
	    
	    /**
	       Applies the filtering logic to the new ModelList if the `newVal` is a new
	       ModelList.
	       
	       @method _afterFilterDataChange
	       @param {EventFacade} e the `dataChange` event
	       @protected
	       @since 3.5.0
	    **/
	    _afterFilterDataChange: function (e) {
		// object values always trigger a change event, but we only want to
		// call _initFilterFn if the value passed to the `data` attribute was a
		// new ModelList, not a set of new data as an array, or even the same
		// ModelList.
		if (e.prevVal !== e.newVal || e.newVal.hasOwnProperty('_compare')) {
		    //		    this._initFilterFn();
		}
	    },
	    
	    /**
	       Checks if any of the fields in the modified record are fields that are
	       currently being filtered by, and if so, refilters the `data` ModelList.
	       
	       @method _afterFilterRecordChange
	       @param {EventFacade} e The Model's `change` event
	       @protected
	       @since 3.5.0
	    **/
	    _afterFilterRecordChange: function (e) {
		var i, len;
		
		for (i = 0, len = this._filterBy.length; i < len; ++i) {
		    if (e.changed[this._filterBy[i].key]) {
			this.data.filter();
			break;
		    }
		}
	    },
	    
	    /**
	       Subscribes to state changes that warrant updating the UI, and adds the
	       click handler for triggering the filter operation from the UI.
	       
	       @method _bindFilterUI
	       @protected
	       @since 3.5.0
	    **/
	    _bindFilterUI: function () {

		var handles = this._eventHandles;
		
		//  'filterByChange' -> need to update UI 
		
		if (!handles.filterAttrs) {
		    handles.filterAttrs = this.after(
			['filtersChange', 'columnsChange'],
			Y.bind('_uiSetFilters', this));
		}
		
		if (!handles.filterUITrigger && this._theadNode) {
		    handles.filterUITrigger = this.delegate(['keyup','blur'],
							    Y.rbind('_onUITriggerFilter', this),
							    '.' + this.getClassName('filter', 'input'));
		}
	    },
	    
	    /**
	       Sets the `filterBy` attribute from the `filter` event's `e.filterBy` value.
	       
	       @method _defFilterFn
	       @param {EventFacade} e The `filter` event
	       @protected
	       @since 3.5.0
	    **/
	    _defFilterFn: function (e) {
		this.set.apply(this, ['filterBy', e.filterBy].concat(e.details));
	    },
	    
	    /**
	       Sets up the initial filter state and instance properties. Publishes events
	       and subscribes to attribute change events to maintain internal state.
	       
	       @method initializer
	       @protected
	       @since 3.5.0
	    **/
	    initializer: function () {

		var boundParseFilter = Y.bind('_parseFilter', this);
		
		this._parseFilter();
		this._setFilterBy();
		
		this._initFilterStrings();

		//		    dataChange : Y.bind('_afterFilterDataChange', this),		
		//		
		//		filterChange : boundParseFilter

		
		this.after({
		    'sort' :  this._hideFilteredData,
		    'columnsChange' : boundParseFilter,
		    'filterByChange' : Y.bind('_afterFilterByChange', this),
		    'table:renderHeader': Y.bind('_renderFilters', this)});
		
		
		//		this.data.after(this.data.model.NAME + ":change",
		//				Y.bind('_afterFilterRecordChange', this));
		
		// TODO: this event needs magic, allowing async remote filtering
//		this.publish('filter', {
//		    defaultFn: Y.bind('_defFilterFn', this)
//		});
	    },
	    
	    /**
	       Add the filter related strings to the `strings` map.
	       @method _initFilterStrings
	       @protected
	       @since 3.5.0
	    **/
	    _initFilterStrings: function () {
		// Not a valueFn because other class extensions will want to add to it
		this.set('strings', Y.mix((this.get('strings') || {}),
					  Y.Intl.get('datatable-filter')));
	    },
	    
	    /**
	       @description Fires the `filter` event in response to user changing the UI filters
	       @method _onUITriggerFilter
	       @param {DOMEventFacade} e The `mouseout` event
	       @protected
	       @since 3.5.0
	    **/
	    _onUITriggerFilter: function (e) {

		var colKey = e.currentTarget.getAttribute('data-yui3-col-key'),
		column = colKey && this.getColumn(colKey),
		filterBy = this.get("filterBy") || {},
		i, len;
		
		e.halt(); // not doing anything anyway?

		if (column) {
		    filterBy[colKey] = e.currentTarget.get("value");
		}
		
		this.set("filterBy",filterBy);

	    },
	    
	    /**
	       @description Build the filter funtion from the column config, this function is passed to the model list fiter() method
	       @method _buildFilterFn
	       @protected
	       @since 3.5.0
	    **/
	    _buildFilterFn: function () {
		
		var i,len,op1,op2, key, filter, filterFn;
		
		filterFn = function(model,index,modelList) {
		    
		    var key,filter,op1,op2,val,filter,passesFilter = true;
		    
		    for(i=0,len = this._filterBy.length; i< len && passesFilter; ++i) {
			
			key = this._filterBy[i].key;
			filter = this._filterBy[i].filter;
		
			val = model.get(key) || '';
			
			op1 = filter.substr(0,1);
			op2 = filter.substr(1,1);
			
			if(op2 == '=') {
			    switch(op1) {
			    case '!':
				// not equal
				if(val.toLowerCase() == filter.substr(2).toLowerCase()) {
				    passesFilter = false;
				}
				break;
				
			    case '>':
				// greater or equal
				if(parseInt(val) < parseInt(filter.substr(2))) {
				    passesFilter = false;
				}
				break;				
				
			    case '<':
				// less than or equal
				if(parseInt(val) > parseInt(filter.substr(2))) {
				    passesFilter = false;
				}
				break;
			    }
			    
			} else if (op2 == '%' && op1 =='!') {
			    
			    // not like
			    if((val.toLowerCase().indexOf(filter.substr(2).toLowerCase()) > -1)) {
				passesFilter = false;
			    }
			    break;
			    
			} else {
			    switch(op1) {
			    case '=':
				// equal
				if(val.toLowerCase() != filter.substr(1).toLowerCase()) {
				    passesFilter = false;
				}
				break;
				
			    case '>':
				// greater than
				if(parseInt(val) <= parseInt(filter.substr(1))) {
				    passesFilter = false;
				}
				break;
				
			    case '<':
				// less than
				if(parseInt(val) >= parseInt(filter.substr(1))) {
				    passesFilter = false;
				}
				break;
				
			    case '%':
				// like
				if((val.toLowerCase().indexOf(filter.substr(1).toLowerCase()) === -1)) {
				    passesFilter = false;
				}
				break;
				
			    default:
				// consider to be like
				if((String(val).toLowerCase().indexOf(String(filter).toLowerCase()) === -1)) {
				    passesFilter = false;
				}
				break;
			    }
			}
		    }
		    return passesFilter;
		};
		
		this._filterFn = Y.bind(filterFn,this);
		
	    },
		
	    /**
	       @description Normalizes the possible input values for the `filter` attribute setting up the column config as appropriate
	       @method _parseFilter
	       @protected
	       @since 3.5.0
	    **/
	    _parseFilter: function () {

		var filters = this.get('filters'),

		columns = [],
		i, len, col;

		col = this._displayColumns.slice();

		this._uiFilters = false;

		if(filters === 'auto') {
		    
		    // look for key on columns
		    col = this._displayColumns.slice();
		    
		    for(i = 0; i < col.length; i++) {
			if(col[i].renderFilter) {
			    this._uiFilters = true;
			}
		    }
		} else if(filters === true) {

		    // provide UI filters for all cols
		    col = this._displayColumns.slice();
		    
		    for(i = 0; i < col.length; i++) {
			this._uiFilters = true;
			this._displayColumns[i].renderFilter = true;
		    }
		} else if (isArray(filters)) {

		    // provide UI filters on the specified cols (plural)
		    for (i = 0, len=filters.length; i < len; ++i) {
			if(col = this.getColumn(filters[i])) {
			    this._uiFilters = true;
			    col.renderFilter = true;
			}
		    }
		    
		} else if (filters) {

		    // provide UI filter on the specifed 'COL' (singular)
		    for (i = 0, len = col.length; i < len; ++i) {
			if (col[i].key === filters) {
			    this._uiFilters = true;
			    this._displayColumns[i].renderFilter = true;
			    i = len;
			}
		    }
		}
	    },
	    
	    /**
	       Initial application of the filters UI.
	       
	       @method _renderFilters
	       @protected
	       @since 3.5.0
	    **/
	    _renderFilters: function () {

		this._uiSetFilters();
		this._bindFilterUI();
	    },
	    
	    /**
	       @description Parses the current `filterBy` attribute and updates the columns
	       @method _setFilterBy
	       @protected
	       @since 3.5.0
	    **/
	    _setFilterBy: function () {
		
		var columns = this._displayColumns,
		filterBy = this.get('filterBy') || {},
		filteredClass = ' ' + this.getClassName('filtered'),
		i, len, name, dir, field, column;
		
		this._filterBy = [];
		
		// Purge current filter state from column configs
		for (i = 0, len = columns.length; i < len; ++i) {
		    
		    column = columns[i];		    
		    delete columns[i].filter;
		    
		    if (column.className) {
			// TODO: be more thorough
			column.className = column.className.replace(filteredClass, '');
		    }
		}
		
		for (key in filterBy) {
		    
		    if(filterBy[key]!='') {

			// Allow filtering of any model field and any column
			column = this.getColumn(key) || { _id: key, key: key };
			
			if (column) {
			    
			    column.filter = filterBy[key];
			    
			    if (!column.className) {
				column.className = '';
			    }
			    
			    column.className += filteredClass;
			    
			    this._filterBy.push(column);
			}
		    } 
		}
	    },
	    
	    /**
	       Array of column configuration objects of those columns that need UI setup
	       for user interaction.
	       
	       @property _filters
	       @type {Object[]}
	       @protected
	       @since 3.5.0
	    **/
	    //_filters: null,
	    
	    /**
	       Array of column configuration objects for those columns that are currently
	       being used to filter the data. Fake column objects are used for fields that
	       are not rendered as columns.
	       
	       @property _filterBy
	       @type {Object[]}
	       @protected
	       @since 3.5.0
	    **/
	    //_filterBy: null,
	    
	    /**
	       Replacement `comparator` for the `data` ModelList that defers filtering logic
	       to the `_compare` method. The deferral is accomplished by returning `this`.
	       
	       @method _filterComparator
	       @param {Model} item The record being evaluated for filter position
	       @return {Model} The record
	       @protected
	       @since 3.5.0
	    **/
	    _filterComparator: function (item) {
		// Defer filtering to ModelList's _compare
		return item;
	    },
	    
	    /**
	       Applies the appropriate classes to the `boundingBox` and column headers to
	       indicate filter state and filterability.
	       
	       Also currently wraps the header content of filters columns in a `<div>`
	       liner to give a CSS anchor for filter indicators.
	       
	       @method _uiSetFilters
	       @protected
	       @since 3.5.0
	    **/
	    _uiSetFilters: function () {
		
		var columns = this._displayColumns.slice(),
		filtersClass = this.getClassName('filters', 'column'),
		filtersHidden = this.getClassName("filters","hidden"),
		filterRowClass = this.getClassName("filters","row"),
		filteredClass = this.getClassName('filtered'),
		linerClass = this.getClassName('filter', 'liner'),
		i, len, col, node, liner, title, desc;
		
		this.get('boundingBox').toggleClass(
		    this.getClassName('filters'),
		    columns.length);
		

		/// NEED TO ADDRESS
		if((node = this._theadNode.one("." + filterRowClass))) {
		    node.remove(true);
		}
		
		if(columns.length>0 && this._uiFilters) {
		    tr = this._theadNode.appendChild(Y.Lang.sub(
			this.FILTERS_HEADER_ROW_TEMPLATE, {
			    filterRowClassName: filterRowClass + " " + filtersHidden}));
		    
		    for (i = 0, len = columns.length; i < len; ++i) {
			
			if(columns[i].renderFilter) {
			    tr.append(Y.Lang.sub(
				this.FILTERS_HEADER_CELL_TEMPLATE, {
				    className: this.getClassName("filter","cell"),
				    colKey : columns[i].key,
				    colName : columns[i].label || columns[i].key,
				    inputClass : this.getClassName("filter","input"),
				    linerClass: linerClass
				}));
			} else {
			    tr.append(Y.Lang.sub(
				this.FILTERS_HEADER_CELL_TEMPLATE_NONE, {
				    className: this.getClassName("no-filter")
				}));
			}
		    }
		} 
	    },
	    
	    /**
	       Allows values `true`, `false`, "auto", or arrays of column names through.
	       
	       @method _validateFilters
	       @param {Any} val The input value to `set("filters", VAL)`
	       @return {Boolean}
	       @protected
	       @since 3.5.0
	    **/
	    _validateFilters: function (val) {
		return val === 'auto' || isBoolean(val) || isArray(val);
	    },
	    
	    /**
	       Allows strings, arrays of strings, objects, or arrays of objects.
	       
	       @method _validateFilterBy
	       @param {String|String[]|Object|Object[]} val The new `filterBy` value
	       @return {Boolean}
	       @protected
	       @since 3.5.0
	    **/
	    _validateFilterBy: function (val) {
		return val;
		return val === null ||
		    isString(val) ||
		    isObject(val, true) ||
		    (isArray(val) && (isString(val[0]) || isObject(val, true)));
	    }
	    
	}, true);
	
	Y.DataTable.Filters = Filters;
	
	Y.Base.mix(Y.DataTable, [Filters]);
    },"0.1", {
	requires : []});
