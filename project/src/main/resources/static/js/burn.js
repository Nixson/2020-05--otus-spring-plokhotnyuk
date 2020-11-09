(function () {
    this.__proto__ = proto;
    this.name = '';
    this.data = null;
    this.view = (tpl) => {
        return new Promise((resolve, reject) => {
            this.util.ajax('GET', this.url + '/api/'+this.name).then(data => {
                this.data = data;
                resolve(this.util.parse(tpl, {
                    sprintName: form.user.command.name,
                    sprintStart: this.util.dateFormat(data.sprint.startday),
                    sprintEnd: this.util.dateFormat(data.sprint.endday)
                }));
                setTimeout(()=>{this.chart();},10);
            }).catch(err => {
                reject(err);
            });
        });
    };
    this.chart = ()=>{
        anychart.theme(anychart.themes.darkTurquoise);

        let chart = anychart.area();
        const unitData = [];
        let summ = this.data.max;
        this.data.burndown.forEach(val => {unitData.push([this.util.dateFormat(val.bdate),summ - val.dtime]); summ -= val.dtime});
        chart.data(unitData);
        console.log(unitData);
        chart.container("burnDown");
        chart.draw();

/*
        let chart = anychart.area();
        chart.padding(15, 0, 0, 0);
        chart.title().enabled(false);
        var xAxis = chart.xAxis();
        xAxis.title(false);
        xAxis.staggerMode(false);
        chart
            .yAxis()
            .labels()
            .format(function () {
                return parseInt(this.value).toLocaleString();
            });
        unitSeries = chart.area();
        unitSeries.tooltip().titleFormat(function () {
            return this.x;
        });
        unitSeries.tooltip().format(function () {
            return this.value().toLocaleString();
        });
        const unitData = [];
        let summ = 0;
        this.data.burndown.forEach(val => summ+=val);
        this.data.burndown.forEach(val => unitData.push([val.bdate,summ - val.bdate]));
        unitSeries.data(unitData);

        unitSeries.container("burnDown");
        chart.draw();
 */
    }
})();
