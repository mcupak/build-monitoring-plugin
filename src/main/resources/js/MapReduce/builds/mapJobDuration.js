 function() {
    // if('finished_time' in this && 'started_time' in this) {
      var delta = this.finished_time.getTime() - this.started_time.getTime();
      emit(this.name,delta);
    // }
  }