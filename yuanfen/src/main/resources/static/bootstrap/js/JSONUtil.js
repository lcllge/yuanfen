var JSONUtil = {

    parseObj: function (text) {
        if (text == null || text.length < 0) return null;
        if (typeof (text) !== 'object') {
            return JSON.parse(text);
        } else {
            return text;
        }
    },
    toJSONString: function (obj) {
        if (typeof (obj) === 'object') {
            return JSON.stringify(text);
        } else {
            return obj;
        }
    },

    /**
     * JQ serialize之后的数据转换成JSON
     * @param data
     * @returns {string}
     */
    serializeFormToJson: function (data) {
        let newData = decodeURIComponent(data);
        newData = newData.replace(/&/g, "\",\"").replace(/=/g, "\":\"").replace(/\+/g, " ").replace(/[\r\n]/g, "<br>");
        newData = "{\"" + newData + "\"}";
        return JSON.parse(newData);
    },
};