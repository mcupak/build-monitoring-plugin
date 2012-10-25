function() {
	emit({year: this.timestamp.getFullYear(), month: this.timestamp.getMonth(), day: this.timestamp.getDate()}, this.numberOfOfflineSlaves);
}