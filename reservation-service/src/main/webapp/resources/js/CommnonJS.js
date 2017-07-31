var ConvertTimestamp = eg.Class({
  "construct" : function(timestamp){
    this.timestamp = timestamp;
  },
  "convert" : function(timestamp){
    var time;
    var d = new Date(timestamp);
    var yyyy = d.getFullYear();
    var mm = ('0' + (d.getMonth() + 1)).slice(-2); // Months are zero based. Add leading 0.
    var dd = ('0' + d.getDate()).slice(-2); // Add leading 0.
    var hh = d.getHours();
    var h = hh;
    var min = ('0' + d.getMinutes()).slice(-2); // Add leading 0.
    var ampm = 'AM';
    if (hh > 12) {
      h = hh - 12;
      ampm = 'PM';
    } else if (hh === 12) {
      h = 12;
      ampm = 'PM';
    } else if (hh == 0) {
      h = 12;
    }
    time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;
    return time;
  }
});
