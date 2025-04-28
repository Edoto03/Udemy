package bg.sofia.uni.fmi.mjt.udemy.course.duration;

import bg.sofia.uni.fmi.mjt.udemy.course.Resource;

public record CourseDuration(int hours, int minutes) {

    public CourseDuration {

        if (hours < 0 || hours > 24) {
            throw new IllegalArgumentException("Invalid amount of hours in CourseDuration!");
        }
        if (minutes < 0 || minutes > 60) {
            throw new IllegalArgumentException("Invalid amount of minutes in CourseDuration!");
        }
    }

    public static CourseDuration of(Resource[] content) {
        int minutes = 0;

        for (Resource resource : content) {
            minutes += resource.getDuration().minutes();
        }

        int hours = minutes / 60;
        minutes = minutes - hours * 60;
        return new CourseDuration(hours, minutes);
    }

    public boolean isLongerThan(CourseDuration otherDuration) {
        int totalMinutes = this.hours * 60 + this.minutes;
        int otherTotalMinutes = otherDuration.hours * 60 + otherDuration.minutes;

        return totalMinutes > otherTotalMinutes;
    }
}
