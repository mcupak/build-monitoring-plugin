function() {
    emit({year: this.timestamp.getFullYear(), month: this.timestamp.getMonth(), day: this.timestamp.getDate()},
    {queueSize: this.queueSize, buildableSize: this.buildableSize, pendingSize: this.pendingSize, blockedSize: this.blockedSize, waitingSize: this.waitingSize});
}