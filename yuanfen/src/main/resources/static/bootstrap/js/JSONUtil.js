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
    parseForm: function (text) {
        let entry = text.split('&'), result = {};
        for (var i = 0; i < entry.length; i++) {
            let kv = entry[i];
            if (kv) {
                let map = kv.split('=');
                if (map.length === 2) {
                    if (map[1]) {
                        result[map[0]] = map[1];
                    }
                }
            }
        }
        return result;
    }
};