/*字典 AgvInfoList*/
function AgvInfoList() {
    this.add = add;
    this.datastore = new Array();
    this.find = find;
    this.remove = remove;
    this.count = count;
    this.clear = clear;
    this.containsKey = containsKey;
}

function add(key, value) {
    this.datastore[key] = value;
}

function find(key) {
    var value = "";
    try {
        value = this.datastore[key];
    } catch (e) {
    }
    return value;
}

function containsKey(key) {
    return find(key) != undefined;
}

function remove(key) {
    try {
        delete this.datastore[key];
    } catch (e) {
    }
}

function count() {
    var n = 0;
    for (var key in Object.keys(this.datastore)) {
        ++n;
    }
    return n;
}

function clear() {
    for (var key in this.datastore) {
        delete this.datastore[key];
    }
}
