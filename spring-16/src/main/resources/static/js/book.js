'use strict';

function Book() {
    const self = this;
    const name = "book";
    const url = "/" + name;
    self.data = null;
    self.init = () => {
        $('#container').off('keyup', '#productFilter');
        self.set(name, self);
        return self;
    };
    self.afterView = () => {
        self.active = null;
    };
    self.parse = (data) => {
        self.data = data;
        const view = [];
        data.forEach((value) => {
            const val = Object.assign({}, value);
            val.Authors = val.authors!=null ? self.getAuthors(val.authors) : "";
            val.Genres = val.genres!=null ? self.getGenres(val.genres) : "";
            val.cnt = val.comments!=null ? val.comments.length : 0;
            view.push(util.parse(self.tplBookList, val));
        });
        return {
            bookList: view.join('')
        }
    };
    self.getListNames = (lst) => {
        const resp = [];
        lst.forEach((val) => resp.push(val.name));
        return resp.join(', ');
    };
    self.active = null;

    self.viewBook = (el) => {
        if (typeof el !== 'boolean') {
            $('#bookList a').removeClass('active');
            if (self.active === el.data('value')) {
                $('#bookControl').addClass('d-none');
                $('#bookView').text('');
                self.active = null;
                return false;
            }
            el.addClass('active');
            self.active = el.data('value');
        }
        let info = self.loadControl(self.data.find((val) => val.id === self.active));
        $('#bookControl').removeClass('d-none');
        $('#bookView').html(util.parse(self.tplView, info)).removeClass('d-none');
        $('#bookView textarea').each((function () {
            this.setAttribute('style', 'height: ' + (this.scrollHeight + 10) + 'px;overflow-y:hidden;');
        }));
    };
    self.loadControl = (inf) => {
        const attrs = Object.assign({}, inf);
        attrs.genres = attrs.genres != null ? attrs.genres.map((val) => util.parse(self.tplSubelement, Object.assign({}, val, {type: "genre"}))).join('') : "";
        attrs.authors = attrs.authors != null ? attrs.authors.map((val) => util.parse(self.tplSubelement, Object.assign({}, val, {type: "author"}))).join('') : "";
        attrs.commentList = attrs.comments != null ? attrs.comments.map((val) => util.parse(self.groupInfo, val)).join('') : "";
        return attrs;
    };
    self.getAuthors = self.getListNames;
    self.getGenres = self.getListNames;
    self.edit = () => {
        $('#bookControl').addClass('d-none');
        $('#bookView .form-control-plaintext').addClass('form-control').removeClass("form-control-plaintext").prop("readonly", false);
        $('#bookView textarea').each((function () {
            this.setAttribute('style', 'height: 200px; overflow-y:auto;');
        }));
        $('#bookView .d-none').removeClass("d-none");
    };
    self.create = ()=>{
        self.active = null;
        $('#bookList a').removeClass('active');
        $('#bookView').html(util.parse(self.tplView, {})).removeClass('d-none');
        self.edit();
        $('button[data-action=addAuthor]').addClass('d-none');
        $('button[data-action=addGenre]').addClass('d-none');
    };
    self.addType = null;
    self.addAuthor = () => {
        self.addType = 'author';
        $('#bookAddSubElement .modal-title').text('Add author');
        $('#bookAddSubElement #bookAddNameLabel').text('Full name');
        $('#bookAddSubElement form')[0].reset();
        $('#bookAddSubElement').modal('show');
    };
    self.addGenre = () => {
        self.addType = 'genre';
        $('#bookAddSubElement .modal-title').text('Add genre');
        $('#bookAddSubElement #bookAddNameLabel').text('Title');
        $('#bookAddSubElement form')[0].reset();
        $('#bookAddSubElement').modal('show');
    };
    self.submit = () => {
        const form = new FormData($('#bookView')[0]);
        const attr = {};
        form.forEach((val, key) => attr[key] = val);
        if(self.active!=null) {
            util.put('/book/' + self.active, attr).then(book => self.reloadBook(book));
        } else {
            util.post('/book/', attr).then(() => self.reload());
        }
    };
    self.reloadBook = (book) => {
        self.data.some((val, num) => {
            if (val.id === self.active) {
                self.data[num] = book;
                return true;
            }
        });
        self.viewBook(true);
        self.edit();
    };
    self.addSubelement = () => {
        $('#bookAddSubElement').modal('hide');
        util.post('/' + self.addType + '/', {bookId: self.active, name: $('#bookAddName').val()})
            .then(() => util.get('/book/' + self.active)).then(book => self.reloadBook(book));
    };
    self.rmSub = (map) => {
        if (confirm("Are you sure?")) {
            util.delete('/' + map.type + "/" + map.id).then(() => {
                $('#bookView [data-id=' + map.id + ']').parent().remove();
            });
        }
    };
    self.remove = () => {
        if (confirm("Are you sure?")) {
            util.delete(self.url + "/" + self.active).then(() => self.reload());
        }
    };
    self.tplView = `
        <div class="d-flex justify-content-end mb-3">
            <button type="button" class="btn btn-success action d-none" data-action="submit">Save</button>
        </div>
        <div class="form-group row">
            <label for="bookViewName" class="col-sm-3 col-form-label">Title</label>
            <div class="col-sm-9"><input type="text" id="bookViewName" name="name" readonly class="form-control-plaintext" value="{{name}}" /></div>
        </div>
        <div class="form-group row">
            <label for="bookViewYear" class="col-sm-3 col-form-label">Date of writing</label>
            <div class="col-sm-9"><input type="text" id="bookViewYear" name="year" readonly class="form-control-plaintext" value="{{year}}" /></div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3 col-form-label">Authors</div>
            <div class="col-sm-7">{{authors}}</div>
            <div class="col-sm-2 text-right d-none">
                <button class="btn btn-success btn-sm action" type="button" data-action="addAuthor"><span aria-hidden="true">&#43;</span></button>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-3 col-form-label">Genres</div>
            <div class="col-sm-7">{{genres}}</div>
            <div class="col-sm-2 text-right d-none">
                <button class="btn btn-success btn-sm action" type="button" data-action="addGenre" data-value="{{id}}">
                <span aria-hidden="true">&#43;</span></button>
            </div>
        </div>
        <div class="form-group row">
            <label for="bookViewDescription" class="col-sm-3 col-form-label">Description</label>
            <div class="col-sm-9">
                <textarea id="bookViewDescription" name="description" readonly class="form-control-plaintext">{{description}}</textarea>
            </div>
        </div>
        <button class="btn btn-primary btn-sm" type="button" data-toggle="collapse" href="#collapsedComments{{id}}" role="button" aria-expanded="false">Comments</button>
        <div class="collapse" id="collapsedComments{{id}}">
            <ul class="list-group list-group-flush">{{commentList}}</ul>
        </div>
        <div class="d-flex justify-content-end mb-3">
            <button type="button" class="btn btn-success action d-none" data-action="submit">Save</button>
        </div>
    `;
    self.tplSubelement = `<span class="badge badge-pill badge-light">{{name}} 
    <button class="btn btn-danger btn-sm d-none action" type="button" data-action="rmSub" data-type="{{type}}" data-id="{{id}}">&times;</button></span>`;
    self.groupInfo = `<li class="list-group-item d-flex justify-content-between align-items-center">{{content}}
    <button class="btn btn-danger btn-sm action d-none" type="button" data-action="rmSub" data-type="comment" data-id="{{id}}">&times;</button></li>`;
    self.tplBookList = `
        <a class="list-group-item list-group-item-action" href="viewBook" data-value="{{id}}">
        <div class="d-flex w-100 justify-content-between">
            <h5>[{{year}}] {{name}}</h5>
            <small>comments: {{cnt}}</small>
        </div>
        <p class="mb-1">{{Authors}}</p>
        <p class="mb-1">{{Genres}}</p>
</a>
    `;
    return self;
}

Book.prototype = core;
util.modules["book"] = new Book();
