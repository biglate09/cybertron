/* @author chalermpong.ch@ocean.co.th */

(function() {
    var getURL = function(uri) {
        var uri = uri || 'javascript:void(0)';
        if (uri.startsWith('http') || uri.startsWith('javascript')) return uri;
        else if (uri.startsWith('/')) return APP.config.contextPath + uri + '?uuid=' + APP.yui.uuid.Crypto.UUID();
        else return APP.config.contextPath + '/' + uri + '?uuid=' + APP.yui.uuid.Crypto.UUID();
    };
    var createMenu = function(base, items, id) {
        var result = {
            base: $(base),
            id: id,
            bounding: new Element('div', { id: id }).addClassName('yui3-menu yui3-menu-horizontal yui3-menubuttonnav'),
            content: new Element('div').addClassName('yui3-menu-content'),
            ul: new Element('ul')
        };
        result.base.appendChild(result.bounding)
            .appendChild(result.content)
            .appendChild(result.ul);
        createMenuItem(result.ul, items);
        $A(items.children).each(function(item, idx) {
            if ($A(item.children).size() > 0) createSubMenu(result.ul, item, true);
            else createMenuItem(result.ul, item);
        });
        return result;
    };
    var createSubMenu = function(ul, data, topLevel) {
        var result = {
            baseUL: $(ul),
            data: data,
            li: new Element('li', { title: data.description }),
            a: new Element('a', { href: '#menu-item-' + data.id }).addClassName('yui3-menu-label'),
            bounding: new Element('div').addClassName('yui3-menu'),
            content: new Element('div').addClassName('yui3-menu-content'),
            ul: new Element('ul')
        };
        if (topLevel) result.a.appendChild(new Element('em')).update(data.name);
        else result.a.update(data.name);
        result.baseUL.appendChild(result.li).appendChild(result.a);
        result.li.appendChild(result.bounding).appendChild(result.content).appendChild(result.ul);
        $A(data.children).each(function(item, idx) {
            if (!item.visible) return;
            if ($A(item.children).size() == 0) {
                createMenuItem(result.ul, item);
            }
            else {
                createSubMenu(result.ul, item);
            }
        });
        return result;
    };
    var createMenuItem = function(ul, data) {
        var result = { 
            ul: $(ul),
            data: data,
            li: new Element('li').addClassName('yui3-menuitem'),
            a: new Element('a', { href: getURL(data.command), title: data.description }).addClassName('yui3-menuitem-content')
        };
        result.ul.appendChild(result.li).appendChild(result.a).update(data.name);
        return result;
    };
    document.on('dom:loaded', function(event) {
        YUI({ skin: 'sam' }).use('node-menunav', 'gallery-uuid', 'gallery-layout', 'gallery-busyoverlay', function(Y) {
            APP.namespace('yui.uuid', Y);
            var m = createMenu($('mainmenu'), APP.config.menu, 'mainmenubar'),
                menu = Y.one('#mainmenubar'),
                layout = new Y.PageLayout(),
                body = Y.one('div.layout-bd');
            layout.set('stickyFooter', true);
            body.plug(Y.Plugin.BusyOverlay);
            document.on('state:busy', function(event) { body.busy.show(); });
            document.on('state:normal', function(event) { body.busy.hide(); });
            APP.namespace('yui.layout', layout);
            document.fire('layout:init', { layout: layout })
            layout.set('mode', Y.PageLayout.FIT_TO_VIEWPORT);
            menu.plug(Y.Plugin.NodeMenuNav);
            menu.get('ownerDocument').get('documentElement').removeClass('yui3-loading');
        });
    });
})();
