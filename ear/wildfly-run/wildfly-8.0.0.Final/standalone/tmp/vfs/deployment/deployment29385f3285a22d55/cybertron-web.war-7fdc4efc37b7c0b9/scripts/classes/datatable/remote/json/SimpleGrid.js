/**
 * @author chalermpong.ch@ocean.co.th
 */


(function() {
    var package = APP.namespace('classes.datatable.remote.json');
    var SimpleGrid = Class.create({
        initialize: function(config) {
            this.initialized = false;
            this.config = {
                classSelected: 'yui3-datatable-selected',
                classHighlighted: 'yui3-datatable-highlighted',
                columns: [],
                resultListLocator: 'data'
            };
            config && Object.extend(this.config, config);
        },
        render: function() {
            if (!this.config.containers || !this.config.containers.datatable)
                throw 'Missing reference to datatable\'s container element';
            var config = this.config,
                containers = config.containers;
            YUI({ skin: 'sam', combine: false, filter: 'raw' }).use(
                'gallery-datatable-paginator', 'gallery-paginator-view',
                'console', 'gallery-layout', 'gallery-layout-datatable',
                'datatable-sort', 'datatable-message', (function(Y)
            {
                var table_options = {
                    columns: this.config.columns,
                    sortBy: this.config.sortBy || null,
                    sortable: true,
                    scrollable: 'y',
                    width: '100%'
                };
                if (config.paginator) {
                    var paginator = config.paginator;
                    if (!config.containers.paginator) throw 'Missing reference to container of paginator';
                    Object.extend(table_options, {
                        paginatorResize: true === paginator.resize,
                        paginationSource: 'client',
                        paginator: new Y.PaginatorView({
                            model: new Y.PaginatorModel({
                                page: config.paginator.page,
                                itemsPerPage: paginator.pageSize
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
                var table = this.table = new Y.DataTable(table_options);
                table.plug(Y.Plugin.PageLayoutDataTableModule, { layout: config.pagelayout });
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
                            document.fire(config.clickEventName, { data: table.getRecord(cur), owner: this });
                        }
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
                APP.namespace('debug', this);
            }).bind(this));
            return this;
        }
    });
    SimpleGrid.addMethods({
        toString: function() { return 'SimpleGrid'; },
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
            if (request) {
                this.config.request = Object.clone(request);
                this.reload();
            }
            return this;
        },
        reload: function() {
            var config = this.config,
                table = this.table,
                containers = config.containers;
            if (config && config.request && config.request.url) {
                if (this.initialized) {
                    document.fire('state:busy');
                    new Ajax.Request(config.request.url, {
                        method: 'post',
                        encoding: 'UTF-8',
                        synchronous: true,
                        evalJSON: true,
                        parameters: config.request.parameters || Object.toQueryString(config.request.parameters),
                        onSuccess: (function(transport) {
                            APP.response.handlers.success(transport, function(json) {
                                var locator = config.resultListLocator;
                                table.resetLocalData(locator ? eval('json.' + locator) : []);
                                $(containers.datatable).fire(this + ':reload', { responseJson: json, owner: this, request: config.request });
                            }, this);
                        }).bind(this)
                    });
                }
                else {
                    $(containers.datatable).on(this + ':initialized', (function(event) {
                        this.reload();
                    }).bind(this));
                }
            }
            return this;
        },
        on: function(eventName, callback) { $(this.config.containers.datatable).on(eventName, callback); return this; },
        onReload: function(callback) { return this.on(this + ':reload', callback); }
    });
    package.SimpleGrid = SimpleGrid;
})();
