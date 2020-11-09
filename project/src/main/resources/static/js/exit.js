(function () {
    this.__proto__ = proto;
    this.name = '';
    this.data = {};
    this.view = (tpl) => {
        return new Promise((resolve, reject) => {
            fetch(this.url+'/api/user',{
                method: "GET",
                credentials: 'include',
                headers: {
                    'Authorization': 'Basic ' + btoa('none:none'),
                    'Content-Type': 'application/json'
                }
            }).then(() => {
                window.location.assign(this.url);
            });
        });
    };
})();
