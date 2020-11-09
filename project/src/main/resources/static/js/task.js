(function () {
    this.__proto__ = proto;
    this.name = '';
    this.data = {};
    this.view = (tpl) => {
        return new Promise((resolve, reject) => {
            this.util.ajax('GET', this.url + '/api/' + this.name).then(data => {
                this.data = data;
                const mapData = {
                    commandName: form.user.command.name,
                    sprintStartday: this.util.dateFormat(data.sprint.startday),
                    sprintEndday: this.util.dateFormat(data.sprint.endday),
                    tasks: this.taskListView()
                };
                resolve(this.util.parse(tpl, mapData));
            }).catch(err => {
                reject(err);
            });
        });
    };
    this.taskListView = () => {
        return this.data.tasks.map(task => {
            return this.util.parse(this.tpl.taskTd, Object.assign({}, task, {
                burnSumm: this.burnSumm(task),
                edit: this.util.tpl.edit,
                remove: this.util.tpl.remove
            }))
        }).join('');
    };
    this.burnSumm = task => {
        let summ = 0;
        if (this.data.burnings) {
            this.data.burnings.filter(burn => burn.task === task.id).map(burn => summ += burn.dtime);
        }
        return summ;
    };
    this.burn = data => {
        $('#burning').removeClass('hidden').addClass('col');
        const id = data.id;
        active = id;
        const lst = [];
        this.data.burnings
            .filter(burn => burn.task === id)
            .map(burn =>
                lst.push(this.util.parse(this.tpl.burn, this.burnInfo(burn)))
            );
        $('#burning tbody').html(lst.join(''));
    };
    this.burnInfo = burn => {
        const map = Object.assign({}, burn);
        map.date = this.util.dateFormat(map.bdate);
        let user = this.data.users.filter(user => user.login === map.user)[0];
        if(user)
            map.user = user.name;
        map.remove = this.util.tpl.remove;
        return map;
    };
    this.edit = data => {
        active = data.id;
        $('#burning').removeClass('col').addClass('hidden');
        const task = this.data.tasks.find(task => task.id === active);
        $('#addTask .modal-title').text(task.name);
        $('#addTask form')[0].reset();
        $('#taskDtime').val(task.dtime);
        $('#taskName').val(task.name);
        $('#addTask').modal('show');
    };
    this.addTask = () => {
        active = 0;
        $('#burning').removeClass('col').addClass('hidden');
        $('#addTask .modal-title').text('Добавить задачу');
        $('#addTask form')[0].reset();
        $('#addTask').modal('show');
    };
    this.addBurn = () => {
        $('#addBurn form')[0].reset();
        $('#addBurn').modal('show');
    }
    let active = 0;
    this.saveTask = () => {
        $('#addTask').modal('hide');
        const data = {
            "command": form.user.command.id,
            "dtime": parseInt($('#taskDtime').val()),
            "name": $('#taskName').val(),
            "sprint": this.data.sprint.id
        }
        if (active) {
            data.id = active;
            this.util.ajax('PUT', this.url + '/data/task/' + active, data).then(() => {
                form.loadModel(this.name);
            });
        } else {
            this.util.ajax('POST', this.url + '/data/task', data).then(() => {
                form.loadModel(this.name);
            });
        }
        ;
    };
    this.rm = data =>{
        this.util.ajax('DELETE', this.url + '/data/task/'+data.id).then(() => {
            form.loadModel(this.name);
        });
    }
    this.rmBurn = data =>{
        this.util.ajax('DELETE', this.url + '/data/burn/'+data.id).then(() => {
            form.loadModel(this.name);
        });
    }
    this.saveBurn = ()=>{
        $('#addBurn').modal('hide');
        const data = {
            "task": active,
            "dtime": parseInt($('#burnDtime').val()),
            "bdate": new Date($('#burnDate').val()),
            "user": form.user.login
        }
        this.util.ajax('POST', this.url + '/data/burn', data).then(() => {
            form.loadModel(this.name);
        });
    }
    this.tpl = {
        taskTd: `<tr>
                <td>{{id}}</td>
                <td>{{name}}</td>
                <td>{{dtime}}</td>
                <td><a href="burn" class="action" data-action="burn" data-id="{{id}}">{{burnSumm}}</a></td>
                <td>
                <div class="btn-group btn-group-sm" role="group">
                  <button type="button" class="btn btn-outline-primary action" data-action="edit" data-id="{{id}}">{{edit}}</button>
                  <button type="button" class="btn btn-outline-danger action" data-action="rm" data-id="{{id}}">{{remove}}</button>
                </div>
                </td>
            </tr>`,
        burn: `<tr>
                <td>{{date}}</td>
                <td>{{user}}</td>
                <td>{{dtime}}</td>
                <td>
                  <button type="button" class="btn btn-outline-danger btn-sm action" data-action="rmBurn" data-id="{{id}}">{{remove}}</button>
                </td>
                </tr>
        `
    };
})();
