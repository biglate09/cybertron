/*
 * @author chalermpong.ch@ocean.co.th
 */


(function() {
    // increase when failure of file submit is detected, reset when successfully submit
    var retryCountWhenSubmitFile = 0;

    document.on('layout:init', function(event) {
        YUI({
            combine: false, throwFail: true, lang: 'th-TH',
            modules: {
                'gallery-aui-base': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-base/gallery-aui-base-min.js', type: 'js' },
                'gallery-aui-progressbar-css': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-progressbar/assets/skins/sam/gallery-aui-progressbar.css', type: 'css' },
                'gallery-aui-selector': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-selector/gallery-aui-selector-min.js', type: 'js' },
                'gallery-aui-delayed-task': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-delayed-task/gallery-aui-delayed-task-min.js', type: 'js' },
                'gallery-aui-node-base': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-node-base/gallery-aui-node-base-min.js', type: 'js' },
                'gallery-aui-component': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-component/gallery-aui-component-min.js', type: 'js' },
                'gallery-aui-progressbar': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-progressbar/gallery-aui-progressbar-min.js', type: 'js' },
                'gallery-aui-skin-base': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-skin-base/css/gallery-aui-skin-base-min.css', type: 'css' },
                'gallery-aui-skin-classic': { fullpath: APP.config.yuiGalleryPath + '/2013-05-15/gallery-aui-skin-classic/css/gallery-aui-skin-classic-min.css', type: 'css', requires: ['gallery-aui-skin-base'] }
            }
        }).use('datatype-number', 'gallery-aui-progressbar', 'gallery-aui-progressbar-css', function(Y) {
            document.on('file:submit', function(event) {
                var memo = event.memo,
                    url = memo.url,
                    params = memo.parameters,
                    data = $A(memo.data),
                    index = params['params.fileIndex'],
                    progbar = APP.components.progressBar;
                if (index < data.size()) {
                    var file = data[index],
                        info = $$('.info-bar')[0],
                        handlers = APP.response.handlers;
                    document.fire('state:busy');
                    params['file.fullName'] = file.fullName;
                    params['file.size'] = file.size;
                    params['file.lastWriteTime'] = file.lastWriteTime;
                    params['file.lastAccessTime'] = file.lastAccessTime;
                    params['file.creationTime'] = file.creationTime;
                    info.update('กำลังส่งไฟล์ที่ #{fileNumber} จากทั้งหมด #{totalFiles} ไฟล์ คิดเป็น #{percentComplete}% (<span style="font-family:courier">#{fullName}</span>)'
                        .interpolate({ 
                            fullName: file.fullName,
                            fileNumber: Y.DataType.Number.format(index + 1, { thousandsSeparator: ',' }),
                            totalFiles: Y.DataType.Number.format(data.size(), { thousandsSeparator: ',' }),
                            percentComplete: parseInt(((index + 1) / data.size()) * 100)
                        })
                    );
                    new Ajax.Request(url, {
                        method: 'POST',
                        asynchronous: true,
                        encoding: 'UTF-8',
                        parameters: Object.toQueryString(params),
                        onSuccess: (function(transport) {
                            handlers.success(transport, null, null, false, function(errorMessage) {
                                if (errorMessage.include('Unable to establish connection to branch')) {
                                    alert('กรุณาตรวจสอบว่าได้เปิดเครื่อง Remote API และเชื่อมต่อสาย LAN ถูกต้องแล้วหรือไม่');
                                }
                            });
                            if (!transport.error) {
                                var idx = ++this.parameters['params.fileIndex'];
                                var percent = parseInt(idx * 100 / data.length);
                                progbar.set('label', percent + '%');
                                progbar.set('value', percent);
                                retryCountWhenSubmitFile = 0;
                                document.fire('file:submit', this);
                            } else {
                                if (retryCountWhenSubmitFile > 5) {
                                    //Time to reset
                                    retryCountWhenSubmitFile = 0;
                                    alert('ไม่สามารถส่งข้อมูลได้');
                                    $('submitFiles').disabled = false;
                                    document.fire('state:normal');
                                } else {
                                    retryCountWhenSubmitFile++;
                                    document.fire('file:submit', this);
                                }
                            }
                        }).bind(memo),
                        onFailure: (function(transport) {
                            if (retryCountWhenSubmitFile > 5) {
                                retryCountWhenSubmitFile = 0;
                                alert('HTTP Error #{status} ~ Unable to complete request'
                                    .interpolate(transport));
                                $('submitFiles').disabled = false;
                                document.fire('state:normal');
                            } else {
                                retryCountWhenSubmitFile++;
                                document.fire('file:submit', this);
                            }
                        }).bind(memo)
                    });
                }
                else document.fire('file:submission:done', memo);
            });
            var params = APP.request.parameters;
            $('file-root').update(params['params.rootDir']);
            $('file-pattern').update(params['params.filePattern']);
            APP.namespace('components.mainGrid', new APP.classes.datatable.RemoteInitDataGrid({
                pagelayout: event.memo.layout,
                columns: APP.grid.files.columns,
                clickEventName: 'file:selected',
                resultListLocator: 'results',
                containers: {
                    datatable: $$('.layout-m-bd')[1],
                    paginator: $$('.layout-m-ft')[1].appendChild(new Element('div'))
                },
                requestHandler: function(request) {
                    $$('.info-bar')[0].update();
                    return APP.data;
                },
                onData: function(data) {
                    $('submitFiles').disabled = !(data && data.results && data.results.length);
                    if (APP.data) delete APP.data;
                    APP.namespace('data', data);
                    $('file-root').update((APP.data.dataHome || '') + APP.request.parameters['params.rootDir']);
                },
                paginator: {
                    options: [ 10, 20, 30, 'All' ]
                }
            })).render().load(/*{ url: APP.actions.listFiles, parameters: APP.request.parameters }*/);
            APP.namespace('components.progressBar', new Y.ProgressBar({
                boundingBox: '#progressBar',
                label: '0%'
            })).render();
            document.fire('file:query');
        });
    });
    document.on('file:query', function(event) {
        var grid = APP.components.mainGrid,
            url = APP.actions.listFiles,
            params = Object.clone(APP.request.parameters),
            data = $A([]),
            map = {},
            criteria = APP.query.criteria;
        grid.reset();
        var func = function(criteria, idx, url, params, data, map) {
            if (idx < criteria.length) {
                var c = criteria[idx],
                    error = false,
                    ignore_confirmation = false;
                document.fire('state:busy');
                $$('.info-bar')[0].update('กำลังค้นหาไฟล์อัพเดทจาก <span style="font-family:courier">' + c['params.rootDir'] + '\\' + c['params.filePattern'] + '</span> ...');
                for (var p in c) params[p] = c[p];
                new Ajax.Request(url, {
                    method: 'POST',
                    encoding: 'UTF-8',
                    asynchronous: false,
                    evalJSON: true,
                    parameters: Object.toQueryString(params),
                    onSuccess: (function(transport) {
                        APP.response.handlers.success(transport, function(json) {
                            var results = json.data.results;
                            APP.namespace("data", { dataHome: json.data.dataHome });
                            for (var i = 0; i < results.length; ++i) {
                                var e = results[i];
                                for (var j = 0; j < c['validate'].length; ++j) {
                                    if (c['validate'][j].test(e.fullName.toLowerCase())) {
                                        if (!map[e.fullName]) { map[e.fullName] = true; data.push(e); }
                                        break;
                                    }
                                }
                            }
                        }, this, false, function(errorMessage) {
                            ignore_confirmation = false;
                            if (errorMessage.include('Unable to establish connection to branch')) {
                                alert('กรุณาตรวจสอบว่าได้เปิดเครื่อง Remote API และเชื่อมต่อสาย LAN ถูกต้องแล้วหรือไม่');
                            }
                            else if (errorMessage.include('Could not send Message')) {
                                // just retry
                                ignore_confirmation = true;
                            }
                        });
                        error = transport.error;
                    }).bind(this),
                    onFailure: function(transport) { error = true; alert('HTTP-#{status} error!'.interpolate(transport)); }
                });
                if (error) {
                    $$('.info-bar')[0].update();
                    if (ignore_confirmation) {
                        func.delay(0.2, criteria, idx, url, params, data, map);
                    } else if (confirm('ต้องการที่จะพยายามอีกครั้งหรือไม่?')) func.delay(0.2, criteria, idx, url, params, data, map);
                    else document.fire('state:normal');
                }
                else {
                    func.delay(0.2, criteria, ++idx, url, params, data, map);
                    var progress = parseInt(idx / criteria.length * 100);
                    var progbar = APP.components.progressBar;
                    progbar.set('value', progress);
                    progbar.set('label', progress + '%');
                }
            }
            else {
                var progbar = APP.components.progressBar;
                progbar.set('label', '');
                progbar.set('value', 0);
                APP.namespace('data', { 'results': data });
                grid.load({ url: url, parameters: params });
                if (APP.data.results.size() > 0) {
                    if (confirm('พบไฟล์ที่ถูกแก้ไขทั้งสิ้น #{total} ไฟล์\r\rต้องการส่งไฟล์ทั้งหมดนี้เข้าสู่ สนญ. ทันทีหรือไม่?'.interpolate({ total: APP.data.results.size() }))) {
                        var submitFile = APP.actions.submitFile,
                            listFiles = APP.actions.listFiles,
                            params = APP.request.parameters,
                            data = APP.data,
                            datahome = data.dataHome,
                            progbar = APP.components.progressBar;
                        this.disabled = true;
                        params['params.dataHome'] = datahome;
                        params['params.fileIndex'] = 0;
                        params['params.totalFiles'] = data.results.size();
                        progbar.set('label', '0%');
                        progbar.set('value', 0);
                        document.fire('file:submit', { url: submitFile, parameters: params, data: data.results });
                    }
                    else document.fire('state:normal');
                }
                else {
                    alert('ไม่พบไฟล์ข้อมูลที่ถูกแก้ไข');
                    document.fire('state:normal');
                }
            }
        };
        document.fire('state:busy');
        func.delay(0.2, criteria, 0, url, params, data, map);
    });
    document.on('file:submission:drop', function(event) {
        var progbar = APP.components.progressBar;
        $('submitFiles').disabled = (0 == event.memo.data.length);
        progbar.set('label', '');
        progbar.set('value', 0);
    });
    document.on('file:submission:done', function(event) {
        var progbar = APP.components.progressBar;
        alert('ส่งไฟล์ข้อมูลรวมทั้งสิ้น #{total} ไฟล์ เสร็จเรียบร้อยแล้ว'.interpolate({ total: event.memo.data.size() }));
        $$('.info-bar')[0].update();
        APP.components.mainGrid.reset();
        delete APP.data;
        $('submitFiles').disabled = true;
        progbar.set('label', '');
        progbar.set('value', 0);
        document.fire('state:normal');
    });
    document.on('dom:loaded', function(event) {
        $$('.info-bar')[0].setStyle({ color: 'navy', fontWeight: 'bold' });
        $('submitFiles').on('click', function(event) {
            var submitFile = APP.actions.submitFile,
                listFiles = APP.actions.listFiles,
                params = APP.request.parameters,
                grid = APP.components.mainGrid,
                data = APP.data,
                datahome = data.dataHome;
            this.disabled = true;
            params['params.dataHome'] = datahome;
            params['params.fileIndex'] = 0;
            params['params.totalFiles'] = data.results.size();
            document.fire('file:submit', { url: submitFile, parameters: params, data: data.results });
        });
    })
})();
