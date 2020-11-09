(function () {
    this.__proto__ = proto;
    this.name = '';
    this.view = (tpl) => {
        return new Promise((resolve, reject) => {
            this.util.ajax('GET', this.url + '/api/' + this.name + 's').then(data => {
                this.data = data;
                const mapData = {
                    users: this.userListView(),
                    commands: this.commandList()
                };
                resolve(this.util.parse(tpl, mapData));
            }).catch(err => {
                reject(err);
            });
        });
    };
    this.commandList = () => {
        return this.data.commands.map(command => {
            command = Object.assign({}, command);
            return this.util.parse(this.tpl.command, command);
        }).join('');

    };
    this.userListView = () => {
        return this.data.users.map(user => {
            user = Object.assign({}, user);
            user.commandName = user.command.name;
            user.roles = user.roles.join(', ');
            return this.util.parse(this.tpl.user, Object.assign({}, user, {
                edit: this.util.tpl.edit,
                remove: this.util.tpl.remove
            }))
        }).join('');

    };
    this.add = () => {
        active = 0;
        $('#add .modal-title').text('Добавить сотрудника');
        $('#add form')[0].reset();
        $('#add').modal('show');
    }
    let active = 0;
    this.edit = data => {
        active = data.id;
        $('#add .modal-title').text('Изменить сотрудника');
        $('#add form')[0].reset();
        const user = this.data.users.find(user => user.login === active);
        $('#usrLogin').prop('disabled',true).val(user.login);
        $('#usrName').val(user.name);
        $('#usrCommand').val(user.command.id);
        $('#usrRoles').val(user.roles.join(','));
        $('#add').modal('show');
    };
    this.save = () => {
        $('#add').modal('hide');
        const data = {
            "login": $('#usrLogin').val(),
            'name': $('#usrName').val(),
            "command": this.data.commands.find(command=> command.id == $('#usrCommand').val()),
            'roles': $('#usrRoles').val().split(',').map(el=>el.trim())
        }
        if($('#usrPassword').val()!==''){
            data.password = $('#usrPassword').val();
        }
        if (active) {
            data.id = active;
            this.util.ajax('PUT', this.url + '/api/user/' + active, data).then(() => {
                form.loadModel(this.name);
            });
        } else {
            this.util.ajax('POST', this.url + '/api/user', data).then(() => {
                form.loadModel(this.name);
            });
        }

    };
    this.rm = data => {
        this.util.ajax('DELETE', this.url + '/api/user/' + data.id).then(() => {
            form.loadModel(this.name);
        });
    };
    this.tpl = {
        user: `<tr>
                    <td>{{login}}</td>
                    <td>{{name}}</td>
                    <td>{{commandName}}</td>
                    <td>{{roles}}</td>
                    <td>
                        <div class="btn-group btn-group-sm" role="group">
                          <button type="button" class="btn btn-outline-primary action" data-action="edit" data-id="{{login}}">{{edit}}</button>
                          <button type="button" class="btn btn-outline-danger action" data-action="rm" data-id="{{login}}">{{remove}}</button>
                        </div>
                    </td>
            </tr>`,
        command: `<option value="{{id}}">{{name}}</option>`
    }
})();
