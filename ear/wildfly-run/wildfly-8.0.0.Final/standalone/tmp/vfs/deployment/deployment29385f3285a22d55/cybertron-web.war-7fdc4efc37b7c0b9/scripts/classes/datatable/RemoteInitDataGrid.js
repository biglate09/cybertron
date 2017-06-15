/**
 * @author chalermpong.ch@ocean.co.th
 *
 * [ E X A M P L E ]
 *
 * APP.namespace('components.myGrid', new APP.classes.datatable.RemoteInitDataGrid({ 
 *     pagelayout: event.memo.layout,
 *     columns: [ 
 *         { key: 'code', label: 'รหัสสาขา', width: '50px', sortable: true }, 
 *         { key: 'name', label: 'ชื่อสาขา', sortable: true }
 *     ],
 *     clickEventName: 'branch:selected',
 *     doubleClickEventName: 'branch:doubleClick',
 *     containers: {
 *         datatable: $('side-body'),
 *         paginator: $('side-footer').appendChild(new Element('div'))
 *     },
 *     onData: function(data) {
 *         YUI().use('console', function(Y) { Y.log(data); });
 *     },
 *     paginator: {
 *         options: [ 15, 30, 45, 60, 'All' ]
 *     }
 * })).render()
 *    .load({ url: '/nbscore/maintenance/datafile/test/mockup.html', parameters: null });
 */


(function() {
    var package = APP.namespace('classes.datatable');
    var RemoteInitDataGrid = Class.create({
        initialize: function(config) {
            this.initialized = false;
            this.config = {
                classSelected: 'yui3-datatable-selected',
                classHighlighted: 'yui3-datatable-highlighted',
                columns: [],
                resultListLocator: null
            };
            config && Object.extend(this.config, config);
        },
        render: function() {
            if (!this.config.containers || !this.config.containers.datatable)
                throw 'Missing reference to datatable\'s container element';
            var config = this.config,
                containers = config.containers;
            YUI({ skin: 'sam', combine: false, throwFail: true }).use(
                'gallery-datatable-paginator', 'gallery-paginator-view',
                'console', 'gallery-layout', 'gallery-layout-datatable',
                'datatable-sort', 'datatable-message', 'datasource-jsonschema',
                'datasource-function', 'datatable-datasource', //'datatable-filter',
            (function(Y) {
                var table_options = {
                    columns: this.config.columns,
                    sortBy: null, /* IMPORTANT: DO NOT set this attribute otherwise local paginator will not be working at all */
                    sortable: true,
                    scrollable: 'y',
                    width: '100%'
                    //filters : 'auto'
                };
                if (config.paginator) {
                    var paginator = config.paginator;
                    if (!config.containers.paginator) throw 'Missing reference to container element of paginator';
                    Object.extend(table_options, {
                        paginatorResize: true === paginator.resize,
                        paginationSource: 'client',
                        paginator: new Y.PaginatorView({
                            model: new Y.PaginatorModel({
                                page: config.paginator.page,
                                itemsPerPage: Y.Lang.isArray(paginator.options)
                                    ? paginator.options[0]
                                    : paginator.pageSize || 10
                            }),
                            container: $(containers.paginator).addClassName('yui3-pagview-bar'),
                            maxPageLinks: paginator.maxPageLinks,
                            paginatorTemplate: paginator.template || (
                                '<img src="#{contextPath}/images/SimpleGrid/first.png" class="pgControls {pageLinkClass}" data-pglink="first" title="หน้าแรกสุด" border="0">'
                                + '<img src="#{contextPath}/images/SimpleGrid/previous.png" class="pgControls {pageLinkClass}" data-pglink="prev" title="หน้าก่อนนี้" border="0">'
                                + 'หน้า {inputPage} จาก {totalPages} หน้า '
                                + '<img src="#{contextPath}/images/SimpleGrid/next.png" class="pgControls {pageLinkClass}" data-pglink="next" title="หน้าถัดไป" border="0">'
                                + '<img src="#{contextPath}/images/SimpleGrid/last.png" class="pgControls {pageLinkClass}" data-pglink="last" title="หน้าท้ายสุด" border="0">'
                                + '<span style="float:right;margin-right:20px;margin-top:2px;">หน้าละ: {selectRowsPerPage} รายการ</span>'
                            ).interpolate(APP.config),
                            pageOptions: paginator.options || null
                        })
                    });
                }
                var ds = new Y.DataSource.Function({
                    source: Object.isFunction(config.requestHandler) ? config.requestHandler.bind(this) : (function(request) {
                        var data = config.resultListLocator ? eval('{ #{resultListLocator}: [] }'.interpolate(config)) : [];
                        if (request && request.url) {
                            new Ajax.Request(request.url, {
                                method: 'POST',
                                encoding: 'UTF-8',
                                asynchronous: false,
                                evalJSON: true,
                                parameters: request.parameters
                                    ? Object.toQueryString(request.parameters)
                                    : null,
                                onSuccess: (function(transport) {
                                    APP.response.handlers.success(transport, (function(json) {
                                        data = json.data;
                                    }).bind(this));
                                }).bind(this),
                                onFailure: function(transport) { alert('HTTP-#{status} error!'.interpolate(transport)); }
                            });
                        }
                        else document.fire('state:normal');
                        return data;
                    }).bind(this)
                });
                if (Object.isFunction(config.onData)) {
                    ds.on('data', function(event) { config.onData(event.data); });
                }
                if (config.resultListLocator) {
                    ds.plug(Y.Plugin.DataSourceJSONSchema, {
                        schema: { resultListLocator: config.resultListLocator }
                    });
                }
                var table = this.table = new Y.DataTable(table_options);
                if (config.pagelayout) {
                    ds.on('response', function(e) {
                        config.pagelayout.elementResized(containers.datatable);
                    });
                }
                table.plug(Y.Plugin.PageLayoutDataTableModule, { layout: config.pagelayout });
                table.plug(Y.Plugin.DataTableDataSource, {datasource: ds});
                if (config.doubleClickEventName) {
                    table.delegate('dblclick', (function(event) {
                        document.fire(config.doubleClickEventName, { 
                            owner: this, 
                            data: table.getRecord(event.currentTarget)
                        });
                    }).bind(this), '.yui3-datatable-data tr');
                }
                if (config.clickEventName) {
                    table.delegate('click', (function(event) {
                        var content = table.get('contentBox'),
                            cur = event.currentTarget,
                            selected = content.one('td.' + config.classSelected);
                        if (!selected || (selected.ancestor() != cur)) {
                            content.all('td.' + config.classSelected).removeClass(config.classSelected);
                            cur.all('td').addClass(config.classSelected);
                        }
                        document.fire(config.clickEventName, { data: table.getRecord(cur), owner: this });
                    }).bind(this), '.yui3-datatable-data tr');
                }
                table.delegate('mouseover', (function(event) {
                    var cur = event.currentTarget;
                    if (!cur.one('td.' + config.classHighlighted)) {
                        cur.ancestor().all('td.' + config.classHighlighted).removeClass(config.classHighlighted);
                        cur.all('td').addClass(config.classHighlighted);
                    }
                }).bind(this), '.yui3-datatable-data tr');
                table.render(containers.datatable);
                this.initialized = true;
                config.pagelayout && config.pagelayout.elementResized(containers.datatable);
                $(containers.datatable).fire(this + ':initialized', { owner: this });
            }).bind(this));
            return this;
        }
    });
    RemoteInitDataGrid.addMethods({
        toString: function() { return 'RemoteInitDataGrid'; },
        reset: function() {
            var config = this.config,
                table = this.table,
                containers = config.containers;
            if (this.initialized) {
                table.data.reset();
                table.paginator.reset();
                table.reset();
                config.pagelayout.elementResized(containers.datatable);
            }
            else {
                $(containers.datatable).on(this + ':initialized', (function(event) {
                    this.reset();
                }).bind(this));
            }
            return this;
        },
        load: function(request) {
            if (this.initialized) {
                if (request) this.config.request = Object.clone(request);
                this.reload();
            }
            /* late execution */
            else {
                $(this.config.containers.datatable).on(this + ':initialized', (function(event) {
                    this.load(request);
                }).bind(this));
            }
            return this;
        },
        reload: function() {
            document.fire('state:busy');
            var config = this.config,
                table = this.table,
                containers = config.containers,
                request = config.request;
            if (this.initialized) {
                var datasource = table.datasource;
                datasource.load.bind(datasource).delay(0.2, { request: (request && request.url) ? request : { url: null } });
            }
            else {
                $(containers.datatable).on(this + ':initialized', (function(event) {
                    this.reload();
                }).bind(this));
            }
            return this;
        },
        on: function(eventName, callback) { $(this.config.containers.datatable).on(eventName, callback); return this; }
    });
    package.RemoteInitDataGrid = RemoteInitDataGrid;
})();
