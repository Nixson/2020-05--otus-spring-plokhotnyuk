const URL = location.origin;


const form = new (function (util) {
    this.user = null;
    const self = this;
    const menu = [
        {
            label: "График сгорания",
            url: 'burn',
            roles: ['USER', 'ADMIN']
        },
        {
            label: "Задачи",
            url: 'task',
            roles: ['USER', 'ADMIN']
        },
        {
            label: "Спринты",
            url: 'sprint',
            roles: ['USER', 'ADMIN']
        },
        {
            label: "Сотрудники",
            url: 'user',
            roles: ['ADMIN']
        },
        {
            label: "Команды",
            url: 'command',
            roles: ['ADMIN']
        },
        {
            label: "Выход",
            url: 'exit',
            roles: ['USER','ADMIN']
        }
    ];
    self.model = {};
    util.ajax("GET", URL + '/api/user').then(data => {
        this.user = data;
        this.user.roles = this.user.roles.map(name => name.substring(5))
        this.genMenu();
    });
    this.genMenu = () => {
        const uMenu = [];
        menu.forEach(val => {
            let intr = val.roles.filter(x => this.user.roles.includes(x));
            if (intr.length)
                uMenu.push(val);
        });
        $('#uMenu').html(uMenu.map(el => util.parse(util.tpl.menu, el)).join(''));
        setTimeout(() => {
            $('#uMenu a')[0].click();
        }, 10);
    };
    this.loadModel = name => {
        this.active = name;
        if (this.model[name]) return this.model[name].js.view(this.model[name].tpl).then(tpl=>this.view(tpl));
        this.model[name] = {};
        util.ajax('GET','/js/'+name+'.js',null,false).then(sc => {
            this.model[name].js = (new Function('return new '+sc))();
            this.model[name].js.name = name;
            if(this.model[name].tpl) this.model[name].js.view(this.model[name].tpl).then(tpl=>this.view(tpl));
        });
        util.ajax('GET','/tpl/'+name+'.html',null,false).then(html => {
            this.model[name].tpl = html;
            if(this.model[name].js) this.model[name].js.view(html).then(tpl=>this.view(tpl));
        });
    };
    this.active = null;
    this.view = (tpl)=>{
        $('#main').html(tpl);
    };
    $('#uMenu').on('click', 'a', function (event) {
        event.preventDefault();
        self.loadModel(this.dataset.action);
    });
    util.on(info => {
        console.log("route",info.href,this.model[this.active].js[info.href]);
        if(info.href && typeof this.model[this.active].js[info.href]==="function") {
            this.model[this.active].js[info.href](info);
        }
    })


})(new Utils());


function Utils() {
    this.ajax = (type, url, data, isJson = true) => {
        const promise = new Promise((resp, rej) => {
            const options = {
                method: type,
                cache: 'no-cache',
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            if (data!=null && data!=undefined) {
                options.body = typeof data == 'string' ? data : JSON.stringify(data);
            }
            let response;
            fetch(url, options).then(r => {
                response = r;
                if(response.status == 409) {
                    return this.ajax(type, url, data, isJson);
                }
                return isJson && type!='DELETE' ? r.json() : r.text()
            }).then(data => {
                if(isJson)
                    console.info("Ajax", url, type, response, data);
                resp(data);
            }).catch((err) => {
                rej(err)
            });
        });
        return promise;
    };
    this.on = callback => {
        $('#main').on('click', '.action', function (event) {
            event.preventDefault();
            let _this = $(this);
            let data = _this.data();
            let self = Object.assign({}, data);
            if (_this.attr('href'))
                self.href = _this.attr('href');
            else
                self.href = data.action;
            callback(self);
            self = null;
        });
    };
    this.parse = (tpl, map) => {
        let param = Object.assign({}, map);
        return ("" + tpl).replace(/{{(\w+)}}/g, (str) => {
            str = str.substring(2, str.length - 2);
            if (param[str] == undefined)
                return '';
            return param[str];
        });
    };
    this.dateFormat = (date) => {
        if(typeof date === 'string')
            date = new Date(date);
        return date.toLocaleDateString();
    }
    this.tpl = {
        menu: `<li class="nav-item">
                <a href="{{url}}" data-action="{{url}}" class="nav-link">{{label}}</a>
            </li>`,
        edit: `<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
</svg>`,
        remove: `<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-x-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd" d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
  </svg>`
    };
}

const proto = new (function(util,url) {
    this.util = util;
    this.name = '';
    this.url = url;
    this.view = (tpl)=>{return Promise.resolve(tpl);}
})(new Utils(),URL);
