(function () {
    this.__proto__ = proto;
    this.name = '';
    this.view = (tpl) => {
        return new Promise((resolve, reject) => {
            this.util.ajax('GET', this.url + '/api/'+this.name+'s').then(data => {
                this.data = data;
                const mapData = {
                    commandName: form.user.command.name,
                    sprints: this.sprintListView()
                };
                resolve(this.util.parse(tpl, mapData));
            }).catch(err => {
                reject(err);
            });
        });
    };
    this.sprintListView = ()=>{
        return this.data.map(sprint => {
            sprint = Object.assign({}, sprint);
            sprint.startDay = this.util.dateFormat(sprint.startday);
            sprint.endDay = this.util.dateFormat(sprint.endday);
            return this.util.parse(this.tpl.sprint, Object.assign({}, sprint, {
                edit: this.util.tpl.edit,
                remove: this.util.tpl.remove
            }))
        }).join('');

    };
    this.add = ()=>{
        active = 0;
        $('#add .modal-title').text('Добавить спринт');
        $('#add form')[0].reset();
        $('#add').modal('show');
    }
    let active = 0;
    this.edit = data =>{
        active = data.id;
        $('#add .modal-title').text('Изменить спринт');
        $('#add form')[0].reset();
        const sprint = this.data.find(sprint => sprint.id === active);
        $('#sprintStartDay').val(sprint.startday);
        $('#sprintEndDay').val(sprint.endday);
        $('#add').modal('show');
    };
    this.save = ()=>{
        $('#add').modal('hide');
        const data = {
            "command": form.user.command.id,
            "startday": new Date($('#sprintStartDay').val()),
            "endday": new Date($('#sprintEndDay').val())
        }
        if (active) {
            data.id = active;
            this.util.ajax('PUT', this.url + '/data/sprint/' + active, data).then(() => {
                form.loadModel(this.name);
            });
        } else {
            this.util.ajax('POST', this.url + '/data/sprint', data).then(() => {
                form.loadModel(this.name);
            });
        }

    };
    this.rm = data =>{
        this.util.ajax('DELETE', this.url + '/data/sprint/'+data.id).then(() => {
            form.loadModel(this.name);
        });
    };
    this.tpl = {
        sprint: `<tr>
                    <td>{{id}}</td>
                    <td>{{startDay}}</td>
                    <td>{{endDay}}</td>
                    <td>
                        <div class="btn-group btn-group-sm" role="group">
                          <button type="button" class="btn btn-outline-primary action" data-action="edit" data-id="{{id}}">{{edit}}</button>
                          <button type="button" class="btn btn-outline-danger action" data-action="rm" data-id="{{id}}">{{remove}}</button>
                        </div>
                    </td>
            </tr>`
    }
})();
