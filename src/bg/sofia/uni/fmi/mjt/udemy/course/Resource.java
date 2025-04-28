package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;

public class Resource implements Completable {

    private final String name;
    private final ResourceDuration duration;
    private boolean isCompleted;

    public Resource(String name, ResourceDuration duration) {
        this.name = name;
        this.duration = duration;
        this.isCompleted = false;
    }

    public Resource(Resource other) {
        this.name = other.name;
        this.duration = other.duration;
        this.isCompleted = other.isCompleted;
    }

    public String getName() {
        return name;
    }

    public ResourceDuration getDuration() {
        return duration;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public int getCompletionPercentage() {
        return isCompleted ? 100 : 0;
    }

    public void complete() {
        this.isCompleted = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Resource other) {
            return this.name.equals(other.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
