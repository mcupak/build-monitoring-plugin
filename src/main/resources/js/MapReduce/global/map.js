function() {
    emit({year: this.timestamp.getFullYear(), month: this.timestamp.getMonth(), day: this.timestamp.getDate()},
    {projects: this.numberOfProjetcs, builds: this.numberOfBuilds, slaves: this.numberOfSlaves, offline: this.numberOfOfflineSlaves, idle: this.numberOfIdleSlaves});
}