/*
 * @author chalermpong.ch@ocean.co.th
 */


(function() {
    YUI({ filter: 'raw', combine: false }).use('gallery-layout', function(Y) {
        "use strict"; //?!
        var layout = new Y.PageLayout();

        layout.set('mode', Y.PageLayout.FIT_TO_CONTENT);
        layout.set('matchColumnHeights', false);
        layout.set('stickyFooter', true);
    });
    document.on('dom:loaded', function(event) {
        var loginButton = $$('button[name=loginButton]')[0];
        var username = $$('input[name=j_username]')[0];
        var password = $$('input[name=j_password]')[0];
        var login = function() {
            username.value = username.value.toLowerCase();
            loginButton.disabled = true;
            $(document.forms[0]).request({
                onComplete: function(transport) {
                    var resp = transport.responseText;
                    if (resp.include('meta name="keywords" content="LOGIN_ERROR_PAGE')) {
                        alert('บัญชีผู้ใช้หรือรหัสผ่านไม่ถูกต้อง');
                        username.select();
                        loginButton.disabled = false;
                    }
                    else if (resp.include('meta name="keywords" content="ACCESS_FORBIDDEN')) {
                        alert('สิทธิการใช้งานไม่เพียงพอ');
                        username.select();
                        loginButton.disabled = false;
                    }
                    else if (resp.include('meta name="keywords" content="ACCESS_DENIED')) {
                        alert('ไม่สามารถเข้าใช้งานระบบได้ในช่วงเวลานี้');
                        username.select();
                        loginButton.disabled = false;
                    }
                    else {
                        location.reload();
                        /*
                        transport.responseText.scan(/meta name=\"URL\" content=\"(http\:\/\/.*)\"/, function(match) {
                            location.href = match[1];
                        });
                        */
                    }
                },
                onFailure: function(transport) {
                    alert('Server error#' + transport.status);
                    loginButton.disabled = false;
                }
            });
        };
        new Form.Observer(document.forms[0], 0.3, function(form, value) {
            form['loginButton'].disabled = (
                $F(username).blank() || $F(password).blank()
            );
        });
        document.on('keypress', function(event) {
            try {
                var charcode = (Prototype.Browser.IE || Prototype.Browser.Gecko || Prototype.Browser.Opera) ? event.keyCode : event.charCode;
                if (Event.KEY_RETURN == charcode) {
                    event.stop();
                    if ($F(username).blank()) username.focus();
                    else if ($F(password).blank()) password.focus();
                    else login();
                }
            } catch (e) { }
        });
        loginButton.on('click', function(event) {
            event.stop();
            login();
        });
    });
})();
