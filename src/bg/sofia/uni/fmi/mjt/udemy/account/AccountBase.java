package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.course.Completable;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.exception.*;

public abstract class AccountBase implements Account {
    private static final int MAX_CAPACITY = 100;
    private static final double MIN_GRADE = 2.0;
    private static final double MAX_GRADE = 6.0;

    private final String username;
    private double balance;
    private final Course[] courses;
    private int courseCounter;

    public AccountBase(String username, double balance) {
        this.username = username;
        this.balance = balance;
        this.courses = new Course[MAX_CAPACITY];
        this.courseCounter = 0;
    }

    public AccountBase(String username, double balance, Course[] courses, int courseCounter) {
        this.username = username;
        this.balance = balance;
        this.courses = courses;
        this.courseCounter = courseCounter;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void addToBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be a negative number!");
        }

        this.balance += amount;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    protected abstract double applyDiscount(Course course);

    @Override
    public void buyCourse(Course course)
        throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {

        if (this.balance < course.getPrice()) {
            throw new InsufficientBalanceException(
                "Account %s cannot buy course %s due to insufficient balance.".formatted(username, course));
        }

        if (course.isPurchased()) {
            throw new CourseAlreadyPurchasedException("Course %s is already purchased.".formatted(course));
        }

        if (this.courseCounter == MAX_CAPACITY) {
            throw new MaxCourseCapacityReachedException("Maximum capacity reached.");
        }

        double newPrice = applyDiscount(course);
        this.balance -= newPrice;

        Course courseToAdd = new Course(course);
        this.courses[this.courseCounter++] = courseToAdd;
        courseToAdd.purchase();
    }

    @Override
    public void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete)
        throws CourseNotPurchasedException, ResourceNotFoundException {
        if (course == null || resourcesToComplete.length == 0) {
            throw new IllegalArgumentException("Course or resources are null!");
        }

        if (!course.isPurchased()) {
            throw new CourseNotPurchasedException("Course %s is not purchased.".formatted(course.getName()));
        }

        for (Resource resourceToComplete : resourcesToComplete) {
            course.completeResource(resourceToComplete);
        }
    }

    @Override
    public void completeCourse(Course course, double grade)
        throws CourseNotPurchasedException, CourseNotCompletedException, ResourceNotFoundException {
        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new IllegalArgumentException("Invalid grade!");
        }

        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null!");
        }

        if (!course.isPurchased()) {
            throw new CourseNotPurchasedException("Course %s is not yet purchased.".formatted(course));
        }

        completeResourcesFromCourse(course, course.getContent());

        if (!course.isCompleted()) {
            throw new CourseNotCompletedException("Course %s is not yet completed.".formatted(course));
        }

        course.setGrade(grade);
    }

    @Override
    public Course getLeastCompletedCourse() {
        int mimCompletionPercentage = 101;
        Course leastCompletedCourse = null;

        for (Course course : this.courses) {
            if (course.getCompletionPercentage() < mimCompletionPercentage) {
                mimCompletionPercentage = course.getCompletionPercentage();
                leastCompletedCourse = course;
            }
        }

        return leastCompletedCourse;
    }
}
