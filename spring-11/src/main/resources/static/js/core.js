'use strict';

const util = new (function () {
    const self = this;
    const memory = {};
    self.memory = new Proxy(memory, {
        set(obj, prop, value) {
            obj[prop] = value;
            localStorage.memory = JSON.stringify(memory);
            return true;
        }
    });
    self.setMemory = (obj) => {
        for (let el in obj) {
            if (obj.hasOwnProperty(el)) {
                memory[el] = obj[el];
            }
        }
    };
    self.version = {};
    self.users = [];
    self.latest = Date.now();
    let aSync = 0;
    self.asyncLoad = (afunc, callback) => {
        if (typeof afunc == "function") {
            aSync++;
            afunc(() => {
                self.checkAsyncLoad(callback);
            });
        }
    };
    self.checkAsyncLoad = (callback) => {
        aSync--;
        if (aSync <= 0 && typeof callback == 'function') {
            aSync = 0;
            callback();
        }
    };
    self.setVersion = (version) => {
        self.latest = version;
        localStorage.setItem("latest", version);
    };
    self.setTitle = (title) => {
        self.title = title;
    };
    self.title = null;
    self.icon = null;
    self.setIcon = (img) => {
        if (img !== '' && img !== undefined) {
            var icon = img.substr(0, img.lastIndexOf('.')) + ".ico";
            var link = document.createElement('link');
            link.type = 'image/x-icon';
            link.rel = 'shortcut icon';
            link.href = '/get/' + icon;
            self.icon = '/get/' + img.substr(0, img.lastIndexOf('.')) + "_thumb.jpg";
            document.getElementsByTagName('head')[0].appendChild(link);
        }
    };
    self.load = (url, callback) => {
        if (url === undefined)
            return;
        jQuery.ajax({
            type: "GET",
            url: '/html/' + url + ".html?" + self.latest,
            dataType: "html",
            success: (data) => {
                self.version['html.' + url] = self.latest;
                localStorage.setItem("version", JSON.stringify(self.version));
                if (typeof callback == 'function') callback(data)
            }
        });
    };
    self.loadJs = (url, callback) => {
        if (url == undefined)
            return;
        jQuery.ajax({
            type: "GET",
            url: '/js/' + url + ".js?" + self.latest,
            dataType: "text",
            success: (data) => {
                self.version['js.' + url] = self.latest;
                localStorage.setItem("version", JSON.stringify(self.version));
                window.eval(data);
                if (typeof callback == 'function') {
                    callback();
                }
            }
        });
    };
    self.module = "";
    self.token = null;
    self.modules = {};
    self.afterView = () => {
        if (self.modules[self.module] != undefined && typeof self.modules[self.module].afterView == 'function') {
            self.modules[self.module].afterView();
        }
    };
    self.get = (url, callback) => {
        return new Promise((resolve, reject) => {
            jQuery.ajax({
                type: "GET",
                url: '/api' + url,
                headers: {
                    "Authorization": self.token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: "json",
                success: (data, textStatus, request) => {
                    if (typeof callback == "function")
                        callback(data, textStatus, request);
                    resolve(data);
                },
                error: (request, status, errorThrown) => {
                    if (errorThrown == 'Unauthorized') {
                        if (url.indexOf('/user/token') != 0) {
                            location.href = document.location.href;
                        }
                    }
                    if (typeof callback == "function")
                        callback(null, 'error', request);
                    reject(request);
                }
            });
        });
    };
    self.put = (url,data, callback) => {
        return new Promise((resolve, reject) => {
            jQuery.ajax({
                type: "PUT",
                url: '/api' + url,
                headers: {
                    "Authorization": self.token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: "json",
                data: JSON.stringify(data),
                success: (data, textStatus, request) => {
                    if (typeof callback == "function")
                        callback(data, textStatus, request);
                    resolve(data);
                },
                error: (request, status) => {
                    console.error('delete error', status, request);
                    if (typeof callback == "function")
                        callback(null, 'error', request);
                    reject(request);
                },
            });
        });
    };
    self.delete = (url, callback) => {
        return new Promise((resolve, reject) => {
            jQuery.ajax({
                type: "DELETE",
                url: '/api' + url,
                headers: {
                    "Authorization": self.token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: "text",
                success: (data, textStatus, request) => {
                    console.log("delete success");
                    if (typeof callback == "function")
                        callback(data, textStatus, request);
                    resolve(data);
                },
                error: (request, status) => {
                    console.log("delete error");
                    console.error('delete error', status, request);
                    if (typeof callback == "function")
                        callback(null, 'error', request);
                    reject(request);
                },
            });
        });
    };
    self.loadimage = (url, id, callback) => {
        var $input = $(id);
        var fd = new FormData;
        fd.append('img', $input.prop('files')[0]);
        return jQuery.ajax({
            type: "POST",
            url: '/api' + url,
            data: fd,
            processData: false,
            contentType: false,
            headers: {
                "Authorization": self.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: "json",
            success: (data, textStatus, request) => {
                console.log("post success");
                if (typeof callback == "function")
                    callback(data, textStatus, request)
            },
            error: (request, status, errorThrown) => {
                console.log("post error");
                if (errorThrown == 'Unauthorized') {
                    location.href = document.location.href;
                }
                if (typeof callback == "function")
                    callback(null, 'error', request)
            },
        });
    }
    self.postSrc = (url, data, callback) => {
        return new Promise((resolve, reject) => {
            jQuery.ajax({
                type: "POST",
                url: '/api' + url,
                data: data,
                contentType: false,
                processData: false,
                headers: {
                    "Authorization": self.token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: "json",
                success: (data, textStatus, request) => {
                    console.log("post success");
                    if (typeof callback == "function")
                        callback(data, textStatus, request);
                    resolve(data);
                },
                error: (request, status, errorThrown) => {
                    console.log("post error");
                    if (errorThrown == 'Unauthorized') {
                        location.href = document.location.href;
                    }
                    if (typeof callback == "function") {
                        callback(null, 'error', request);
                    }
                    reject(request);
                }
            });
        });
    };
    self.post = (url, data, callback) => {
        return self.postSrc(url, JSON.stringify(data), callback);
    };
    self.sync = 0;
    self.tplContent = (name, callback) => {
        self.hideAnimation("#container");
        self.module = name;
        if (!self.tplMap[name]) {
            self.sync = 2;
            self.load(name, (data) => {
                self.tplMap[name] = data;
                self.sync--;
                if (self.sync === 0) {
                    callback();
                }
            });
            self.loadJs(name, () => {
                self.sync--;
                if (self.sync === 0) {
                    callback();
                }
            });
        } else {
            callback();
        }
    };
    self.tpl = (name, params) => {
        $('#container').off('click', 'a');
        $('#container').off('click', '.action');
        $('#container a').off('click');
        $('#container .action').off('click');
        self.hideAnimation("#container", () => {
            if (!self.tplMap[name]) {
                self.load(name, (data) => {
                    self.tplMap[name] = data;
                    self.view(self.parseTpl(name, params));
                    self.loadJs(name);
                });
            } else {
                self.view(self.parseTpl(name, params));
            }
        });
        self.module = name;
    };
    self.hide = (id) => {
        self.id(id).addClass('slideHide');
    };
    self.show = (id) => {
        self.id(id).removeClass('slideHide');
    };
    self.hideAnimation = (id, callback) => {
        self.id(id).addClass('slideUp');
        setTimeout(() => {
            if (typeof callback == 'function')
                callback();
        }, 10);
    };
    self.showAnimation = (id) => {
        self.id(id).addClass("slideTop");
        setTimeout(() => {
            self.id(id).removeClass("slideUp");
            self.id(id).removeClass("slideTop");
        }, 700);
    };
    self.view = (html) => {
        self.id("#container").html(html);
        self.showAnimation("#container");
        setTimeout(() => {
            self.reLink();
            self.afterView();
        }, 0);
    };
    self.reLink = () => {
        $('#container').on('click', 'a', function (event) {
            event.preventDefault();
            const _this = $(this);
            let mod = self.modules[self.module][_this.attr("href")];
            if (typeof mod == 'function') {
                mod(_this);
            } else {
                if (_this.attr("href") != '#' && _this.attr("href").substring(0, 10) != 'javascript')
                    console.error('action a', _this.attr("href"));
            }
        });
        $('#container').on('click', '.action', function (event) {
            const _this = $(this);
            let mod = self.modules[self.module][_this.data('action')];
            if (typeof mod == 'function') {
                mod(_this.data('value') != undefined ? _this.data('value') : _this.data());
            } else {
                console.error('action data', _this.data('action'));
            }
        });
    };
    self.viewNext = (html) => {
        self.id("#nextContainer").html(html);
    };
    self.tplMap = {};
    self.parse = (content, params) => {
        let param = Object.assign({}, params);
        return content.replace(/{{(\w+)}}/g, (str) => {
            str = str.substring(2, str.length - 2);
            if (param[str] === undefined)
                return '';
            return param[str];
        });
    };
    self.parseList = (tplContent, lst) => {
        let resp = [];
        for (let el in lst) {
            resp.push(self.parse(tplContent, lst[el]));
        }
        return resp.join('');
    }
    self.parseTpl = (name, params) => {
        return self.parse(self.tplMap[name], params);
    };
    self.id = (id) => {
        if (self.idMap[id] == undefined) {
            self.idMap[id] = jQuery(id);
        }
        return self.idMap[id];
    };
    self.dateToInt = (date) => {
        if (!(date instanceof Date)) {
            if (date == "") return 0;
            date = new Date(date);
        }
        return Math.round(date.getTime() / 1000);
    };
    self.intToDate = (intDate) => {
        let date = new Date();
        date.setTime(intDate * 1000);
        return date;
    };
    self.stringToDate = (str) => {

    };
    self.dateToForm = (date) => {
        var day = date.getDate();
        if (day <= 9)
            day = '0' + day;
        var monthIndex = date.getMonth() + 1;
        if (monthIndex <= 9)
            monthIndex = '0' + monthIndex;
        var year = date.getFullYear();
        return year + '-' + monthIndex + '-' + day;
    };
    self.dateToString = (date) => {
        var day = date.getDate();
        if (day <= 9)
            day = '0' + day;
        var monthIndex = date.getMonth() + 1;
        if (monthIndex <= 9)
            monthIndex = '0' + monthIndex;
        var year = date.getFullYear();
        return day + '.' + monthIndex + '.' + year;
    };
    self.idMap = {};
    self.loadContent = (href) => {
        if (href == undefined) return;
        if (href.substr(0, 1) == '/')
            href = href.substr(1);
        let subHref = href.split('/');
        self.tplContent(subHref[0], () => {
            if (typeof self.modules[subHref[0]].init == 'function') {
                self.modules[subHref[0]].init();
            }
            if (subHref.length < 2)
                self.modules[subHref[0]].view((data) => {
                    self.tpl(subHref[0], data);
                });
            else {
                let newSub = subHref.slice(1).join('/');
                self.modules[subHref[0]].viewNext(newSub, (data) => {
                    self.tpl(subHref[0], data);
                });
            }
        });
    };


    $('#navbarContent').on('click', 'a', function (event) {
        if ($(this).attr('href') == '#') return;
        event.preventDefault();
        event.stopPropagation();
        self.loadContent($(this).attr('href'));
        $('#main').removeClass('in');
        $(this).closest('.dropdown').removeClass('open');
    });

    return self;
})
();
const core = new (function () {
    const self = this;
    let name = "core";
    let url = "/" + name;
    let modalId = "#" + name + "Modify";
    let activeObj = null;
    self.set = (newName, actObj) => {
        name = newName;
        url = "/" + name;
        modalId = "#" + name + "Modify";
        activeObj = actObj;
    };
    self.reload = () => {
        util.loadContent(name);
    };
    self.viewControls = () => {
        let lst = [];
        if (util.memory.steam != undefined) {
            lst.push(util.parse(util.mainMenuElementComponent, {Url: "steam", Title: util.memory.steam.Title}));
        }
        if (util.memory.subscribe != undefined) {
            lst.push(util.parse(util.mainMenuElementComponent, {Url: "subscribe", Title: util.memory.subscribe.Title}));
        }
        if (util.memory.subscribeTower != undefined) {
            lst.push(util.parse(util.mainMenuElementComponent, {
                Url: "subscribe",
                Title: util.memory.subscribeTower.groupInfo.Title
            }));
        }
        $('#controls').html(lst.join(''));
    };
    self.removeSub = () => {
        if (confirm("Вы уверены в удалении элемента?")) {
            util.get(url + '/rm/' + $('#' + name + "Id").val(), (data) => {
                var isParse = activeObj.parse(data);
                if (isParse != null) {
                    util.tpl(name, isParse);
                }
            });
        }
    };
    self.remove = () => {
        if ($(modalId).hasClass('modal')) {
            $(modalId).one('hidden.bs.modal', function (event) {
                self.removeSub();
            });
            $(modalId).modal('hide');
        } else {
            self.removeSub();
        }
    }
    self.addSub = (sName, title) => {
        let modalIdS = "#" + sName + "Modify";
        $(modalIdS + ' form')[0].reset();
        $(modalIdS + ' .modal-title').text(title);
        $(modalIdS + ' .noncreate').hide();
        $(modalIdS).modal('show');
        if (typeof $('#' + sName + 'Id').val == 'function') {
            $('#' + sName + 'Id').val(0);
        }
    };
    self.add = (title) => {
        return self.addSub(name, title);
    };
    self.editSub = (sName, element, title) => {
        let modalIdS = "#" + sName + "Modify";
        $(modalIdS + ' .noncreate').show();
        let obj = typeof element.data == 'function' ? element.data('value') : element;
        for (var sub in obj) {
            if (typeof $('#' + sName + sub).val == 'function') {
                if ((obj[sub] + "").indexOf(':') >= 0 && $('#' + sName + sub + '2')[0] != undefined) {
                    let spl = obj[sub].split(':');
                    for (let nm in spl) {
                        let name = nm - (-1);
                        if (nm < 1) name = "";
                        if (typeof $('#' + sName + sub + name).val == 'function') {
                            $('#' + sName + sub + name).val(spl[nm]);
                        }
                    }
                } else {
                    if ($('#' + sName + sub).attr('type') === 'checkbox') {
                        if (obj[sub]) {
                            $('#' + sName + sub).prop('checked', true);
                        } else {
                            $('#' + sName + sub).prop('checked', false);
                        }
                    } else {
                        $('#' + sName + sub).val(obj[sub]);
                    }
                }
            }
        }
        $(modalIdS + ' .modal-title').text(title);
        $(modalIdS).modal('show');
    };
    self.edit = (element, title) => {
        return self.editSub(name, element, title);
    };
    self.submitSend = (sName, object, callback) => {
        util.post("/" + sName, object, (data, status, res) => {
            if (status == "error") {
                console.error(res);
            } else {
                if (typeof callback == 'function') {
                    callback(data);
                } else {
                    let rParse = activeObj.parse(data);
                    if (rParse != null)
                        util.tpl(sName, rParse);
                }
            }
        });
    };
    self.validate = (sName, formId) => {
        let modalIdS = "#" + sName + "Modify";
        let formObject;
        if (formId != undefined) {
            formObject = $('#' + formId)[0];
        } else
            formObject = $(modalIdS + ' form')[0];
        let link = null;
        let noValid = false;
        for (var el in formObject.elements) {
            var map = formObject.elements[el];
            if (typeof map != "object")
                continue;
            if (!map.validity.valid || (map.readOnly && map.required && map.value == "")) {
                noValid = true;
                if (link == null)
                    link = $(map);
                $(map).parents('.form-group').addClass('has-error')
            } else {
                $(map).parents('.form-group').removeClass('has-error')
            }
        }
        if (!formObject.checkValidity() || noValid) {
            link.focus();
            return false;
        }
        return true;
    };
    self.formData = (formId, struct) => {
        const object = {};
        let formObject = $(formId + ' form')[0];
        let fd = new FormData(formObject);
        fd.forEach(function (value, key) {
            if (object[key] != undefined) {
                if (value != '')
                    object[key] = object[key] + ":" + value;
            } else
                object[key] = value;
            if (struct[key] != undefined && struct[key] == 'int')
                object[key] = parseInt(value);
            if (struct[key] != undefined && struct[key] == 'dateint')
                object[key] = util.dateToInt(value);
            if (struct[key] != undefined && struct[key] == "bool")
                object[key] = ((value + "") == "1" || (value + "") == "true") ? true : false;
        });
        return object;
    };
    self.submitSub = (sName, struct, callback) => {
        if (!self.validate(sName)) {
            return;
        }
        let modalIdS = "#" + sName + "Modify";
        const object = self.formData(modalIdS, struct);
        let modalLink = $(modalIdS);
        if (modalLink.hasClass('modal')) {
            modalLink.one('hidden.bs.modal', function (event) {
                self.submitSend(sName, object, callback);
            });
            modalLink.modal('hide');
        } else {
            self.submitSend(sName, object, callback);
        }
    };
    self.submit = (struct) => {
        self.submitSub(name, struct);
    };
    self.view = (callback) => {
        console.log(callback);
        util.get(url, (data) => {
            console.log(callback);
            let rParse = activeObj.parse(data)
            if (rParse != null)
                callback(rParse);
            else callback({});
        });
    };
    self.convertNull = (data) => {
        let result = {};
        for (let el in data) {
            if (typeof data[el] == 'object' && data[el].Valid === undefined) {
                result[el] = self.convertNull(data[el]);
                continue;
            }
            if (typeof data[el] == 'object' && data[el].Valid !== undefined) {
                if (data[el].Int64 !== undefined) {
                    result[el] = data[el].Int64;
                    continue;
                }
                if (data[el].String != undefined) {
                    result[el] = data[el].String;
                    continue;
                }
            } else
                result[el] = data[el];
        }
        return result;
    };
    return self;
})();

$(() => {
    $('#navbarContent a')[0].click();
});