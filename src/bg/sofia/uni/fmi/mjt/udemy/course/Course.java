package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class Course implements Completable, Purchasable {

    private final String name;
    private final String description;
    private final double price;
    private final Resource[] content;
    private final Category category;
    private final CourseDuration totalTime;
    private boolean isPurchased;
    private boolean isCompleted;
    private double grade;

    public Course(String name, String description, double price, Resource[] content, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.content = content;
        this.category = category;
        this.totalTime = CourseDuration.of(content);
        this.isPurchased = false;
        this.isCompleted = false;
        this.grade = 0.0;
    }

    public Course(Course otherCourse) {
        this.name = otherCourse.name;
        this.description = otherCourse.description;
        this.price = otherCourse.price;
        this.content = Arrays.copyOf(otherCourse.content, otherCourse.content.length);
        this.category = otherCourse.category;
        this.totalTime = otherCourse.totalTime;
        this.isPurchased = otherCourse.isPurchased;
        this.isCompleted = otherCourse.isCompleted;
        this.grade = otherCourse.grade;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Resource[] getContent() {
        return content;
    }

    public CourseDuration getTotalTime() {
        return totalTime;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
        this.isCompleted = true;
    }

    @Override
    public boolean isCompleted() {
        if (this.isCompleted) {
            return true;
        }

        for (Resource resource : content) {
            if (!resource.isCompleted()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getCompletionPercentage() {
        int totalCourses = content.length;
        int completedCourses = 0;

        int completionPercentage = 0;
        for (Resource resource : content) {
            if (resource.isCompleted()) {
                completedCourses++;
                completionPercentage += resource.getCompletionPercentage();
            }
        }

        return completedCourses == 0 ? 0 : (int) Math.round((double) completionPercentage / totalCourses);
    }

    @Override
    public void purchase() {
        this.isPurchased = true;
    }

    @Override
    public boolean isPurchased() {
        return isPurchased;
    }

    public void completeResource(Resource resourceToComplete) throws ResourceNotFoundException {
        for (Resource resource : this.content) {
            if (resource.equals(resourceToComplete)) {
                resource.complete();
                return;
            }
        }
        throw new ResourceNotFoundException(
            "Resource with name %s could not be found in course %s".formatted(resourceToComplete.getName(), name));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Course other) {
            return this.name.equals(other.name) && this.description.equals(other.description) &&
                this.price == other.price && Arrays.equals(this.content, other.content) &&
                this.category.equals(other.category) && this.totalTime.equals(other.totalTime);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, Arrays.hashCode(content), category, totalTime);
    }
}