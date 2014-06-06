function(keys, values) {

var sum = 0;
        values.forEach(function(value) {
            sum += value.count;
        });
        return sum;
}