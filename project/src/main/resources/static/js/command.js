(function () {
    this.__proto__ = proto;
    this.name = '';
    this.view = (tpl) => {
        return new Promise((resolve, reject) => {
            this.util.ajax('GET', this.url + '/api/' + this.name + 's').then(data => {
                this.data = data;
                const mapData = {
                    commands: this.commandListView()
                };
                resolve(this.util.parse(tpl, mapData));
            }).catch(err => {
                reject(err);
            });
        });
    };
    this.commandListView = () => {
        return this.data.map(command => {
            return this.util.parse(this.tpl, Object.assign({}, command, {
                edit: this.util.tpl.edit,
                remove: this.util.tpl.remove
            }))
        }).join('');
    };
    this.add = ()=>{
        active = 0;
        $('#add .modal-title').text('Добавить команду');
        $('#add form')[0].reset();
        $('#add').modal('show');
    }
    let active = 0;
    this.edit = data =>{
        active = data.id;
        $('#add .modal-title').text('Изменить команду');
        $('#add form')[0].reset();
        const command = this.data.find(command => command.id === active);
        $('#commandName').val(command.name);
        $('#add').modal('show');
    };


    this.save = ()=>{
        $('#add').modal('hide');
        const data = {
            name: $('#commandName').val()
        }
        if (active) {
            data.id = active;
            this.util.ajax('PUT', this.url + '/data/command/' + active, data).then(() => {
                form.loadModel(this.name);
            });
        } else {
            this.util.ajax('POST', this.url + '/data/command', data).then(() => {
                form.loadModel(this.name);
            });
        }

        };
    this.rm = data =>{
        this.util.ajax('DELETE', this.url + '/data/command/'+data.id).then(() => {
            form.loadModel(this.name);
        });
    };

    this.tpl = `<tr>
                    <td>{{id}}</td>
                    <td>{{name}}</td>
                    <td>{{countUser}}</td>
                    <td>
                        <div class="btn-group btn-group-sm" role="group">
                          <button type="button" class="btn btn-outline-primary action" data-action="edit" data-id="{{id}}">{{edit}}</button>
                          <button type="button" class="btn btn-outline-danger action" data-action="rm" data-id="{{id}}">{{remove}}</button>
                        </div>
                    </td>
            </tr>`;
})();
