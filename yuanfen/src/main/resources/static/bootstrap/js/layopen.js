function layopen(option, init, data, endFun, shadeClose) {
    //{title: "标题", area: 6, url: ctxPath + ''}
    let area;
    if (option.area) {
        area = layArea[option.area]
    } else if (option.x && option.y) {
        area = [option.x, option.y];
    } else {
        area = layArea['4'];
    }
    let index = layer.open({
        type: 2,
        title: option.title,
        shadeClose: shadeClose,
        area: area,
        content: option.url,
        success: function (layero, index) {
            $(':focus').blur();
            if (!init) {
                init = 'init';
            }
            if (init) {
                if (!data) {
                    data = {};
                }
                if (option.close) {
                    data.close = function () {
                        layer.close(index)
                    };
                }
                let obj = $(layero).find("iframe")[0].contentWindow;
                if (typeof obj[init] === "function") {
                    obj[init](data);
                }
            }
        },
        end: function () {
            if (typeof endFun === "function") {
                endFun();
            }
        }
    });
}

var layArea = {
    "1": ["350px", "300px"],
    "2": ["400px", "300px"],
    "3": ["600px", "400px"],
    "4": ["800px", "600px"],
    "5": ["800px", "800px"],
    "6": ["1000px", "800px"],
    "7": ["1000px", "600px"],
    "8": ["1208px", "800px"],
    "9": ["100%", "100%"],
    "10": ["800px", "500px"]
};



