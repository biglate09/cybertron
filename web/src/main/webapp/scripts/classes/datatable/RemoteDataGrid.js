/**
 * @author chalermpong.ch@ocean.co.th
 * 
 * [ E X A M P L E ]
 *
 * APP.namespace('components.myGrid', new APP.classes.datatable.RemoteDataGrid({
 *     pagelayout: event.memo.layout,
 *     columns: [
 *         { key: 'filename', label: 'File', sortable: true },
 *         { key: 'size', label: 'Size', sortable: true, width: '180px' },
 *         { key: 'lastupdated', label: 'Last Updated', sortable: true, width: '180px' }
 *     ],
 *     request: {
 *         url: '/nbscore/secure/maintenance/datafile/test/mockup.html'
 *     },
 *     clickEventName: 'file:selected',
 *     containers: {
 *         datatable: $$('.layout-m-bd')[1],
 *         paginator: $$('.layout-m-ft')[1].appendChild(new Element('div'))
 *     },
 *     paginator: {
 *         options: [ 10, 20, 30, 'All' ]
 *     }
 * })).render()
 *    .load({ 'params.a': 'A', 'params.b': 'B' });
 */


(function() {
    var package = APP.namespace('classes.datatable');
    var RemoteDataGrid = Class.create({
        initialize: function(config) {
            this.initialized = false;
            this.config = {
                classSelected: 'yui3-datatable-selected',
                classHighlighted: 'yui3-datatable-highlighted',
                columns: [],
                requestStringTemplate: 'result.data.totalItems={totalItems}&result.data.itemsPerPage={itemsPerPage}&result.data.page={page}',
                resultListLocator: 'data.results'
            };
            config && Object.extend(this.config, config);
        },
        render: function() {
            if (!this.config.containers || !this.config.containers.datatable)
                throw 'Missing reference to datatable\'s container element';
            var config = this.config,
                containers = config.containers,
                request = config.request;
            YUI({ skin: 'sam', combine: false, throwFail: true }).use(
                'gallery-datatable-paginator', 'gallery-paginator-view',
                'console', 'gallery-layout', 'gallery-layout-datatable',
                'datatable-sort', 'datatable-message', 'datasource-jsonschema',
                'datasource-function', 'datatable-datasource',
            (function(Y) {
                var table_options = {
                    columns: this.config.columns,
                    sortable: true,
                    scrollable: 'y',
                    width: '100%'
                };
                if (config.paginator) {
                    var paginator = config.paginator;
                    if (!containers.paginator) throw 'Missing reference to container element of paginator';
                    Object.extend(table_options, {
                        paginatorResize: true === paginator.resize,
                        paginationSource: 'server',
                        requestStringTemplate: request.parametersTemplate 
                            || config.requestStringTemplate,
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
                    source: (function(request, ds, event) {
                        var data = {},
                            handlers = APP.response.handlers,
                            pagstate = this.table.get('paginationState'),
                            sortby = this.table.get('sortBy') || {},
                            params = Object.clone(config.request.parameters || {});
                        if (Y.Lang.isArray(sortby)) {
                            $A(sortby).each(function(item, index) {
                                for (var p in item) 
                                    params['result.data.sortBy.' + p] = item[p];
                            });
                        }
                        pagstate.totalItems = pagstate.totalItems || 0;
                        params = Object.toQueryString(params);
                        params = Y.Lang.sub(config.requestStringTemplate, pagstate) + (params.empty() ? '' : '&' + params);
                        document.fire('state:busy');
                        new Ajax.Request(config.request.url, {
                            method: config.request.method || 'POST',
                            asynchronous: false,
                            encoding: 'UTF-8',
                            evalJSON: true,
                            parameters: params,
                            onSuccess: (function(transport) {
                                handlers.success(transport, (function(json) {
                                    data = json;
                                }).bind(this));
                            }).bind(this),
                            onFailure: function(transport) {
                                alert('ERR-#{status}: Unrecognized server response.\r\n\r\n#{responseText}'.interpolate(transport));
                            }
                        });
                        return data;
                    }).bind(this)
                });
                if (Object.isFunction(config.onData)) {
                    ds.on('data', function(event) { config.onData(event.data); });
                }
                ds.plug(Y.Plugin.DataSourceJSONSchema, {
                    schema: { 
                        resultListLocator: request.resultListLocator || config.resultListLocator || 'data.results',
                        metaFields: request.metaFields || {
                            totalItems: 'data.totalItems',
                            itemsPerPage: 'data.itemsPerPage'
                        }
                    }
                });
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
    RemoteDataGrid.addMethods({
        toString: function() { return 'RemoteDataGrid'; },
        reset: function() {
            var config = this.config,
                table = this.table,
                containers = config.containers;
            if (this.initialized) {
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
            var config = this.config,
                initialized = this.initialized,
                containers = config.containers;
            if (initialized) {
                if (config.request) {
                    config.request.parameters = request ? Object.clone(request) : request;
                    this.reload();
                }
            }
            /* late execution */
            else {
                $(containers.datatable).on(this + ':initialized', (function(event) {
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
                initialized = this.initialized,
                datasource = table.datasource,
                request = config.request;
            if (initialized) {
                datasource.load.bind(datasource).delay(0.2, { request: request.parameters });
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
    package.RemoteDataGrid = RemoteDataGrid;
})();
