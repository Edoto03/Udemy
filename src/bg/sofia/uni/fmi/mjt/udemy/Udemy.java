package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.Account;
import bg.sofia.uni.fmi.mjt.udemy.account.AccountBase;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Udemy implements LearningPlatform {
    private AccountBase[] accounts;
    private Course[] courses;

    public Udemy(AccountBase[] accounts, Course[] courses) {
        this.accounts = accounts;
        this.courses = courses;
    }

    @Override
    public Course findByName(String name) throws CourseNotFoundException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Course name you are trying to find is blank.");
        }

        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }

        throw new CourseNotFoundException("No course with name %s in this Udemy".formatted(name));
    }

    public Course[] findByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            throw new IllegalArgumentException("Keyword you are trying to find is blank.");
        }

        if (!keyword.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Keyword should contain only small and capital latin letters.");
        }

        List<Course> matchingList = new ArrayList<>();

        for (Course course : courses) {
            if (course != null && course.getName() != null && course.getDescription() != null) {
                if (course.getName().contains(keyword) || course.getDescription().contains(keyword)) {
                    matchingList.add(course);
                }
            }
        }

        if (matchingList.isEmpty()) {
            return null;
        } else {
            return matchingList.toArray(new Course[0]);
        }
    }

    @Override
    public Course[] getAllCoursesByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }

        List<Course> coursesMatchingCategory = new ArrayList<>();
        for (Course course : this.courses) {
            if (course != null && course.getCategory() != null) {
                if (course.getCategory().equals(category)) {
                    coursesMatchingCategory.add(course);
                }
            }
        }

        return coursesMatchingCategory.toArray(new Course[0]);
    }

    @Override
    public AccountBase getAccount(String name) throws AccountNotFoundException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be null or blank.");
        }

        for (AccountBase account : accounts) {
            if (account.getUsername().equals(name)) {
                return account;
            }
        }

        throw new AccountNotFoundException("No account with name %s was found.".formatted(name));
    }

    @Override
    public Course getLongestCourse() {
        CourseDuration maxCourseDuration = new CourseDuration(0, 0);
        Course longestCourse = null;

        for (Course course : courses) {
            if (course.getTotalTime().isLongerThan(maxCourseDuration)) {
                maxCourseDuration = course.getTotalTime();
                longestCourse = course;
            }
        }

        return longestCourse;
    }

    @Override
    public Course getCheapestByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }

        double cheapestPrice = Double.MAX_VALUE;
        Course cheapestCourse = null;
        for (Course course : this.courses) {
            if (course != null && course.getCategory() != null) {
                if (course.getCategory().equals(category)) {
                    if (course.getPrice() < cheapestPrice) {
                        cheapestCourse = course;
                        cheapestPrice = cheapestCourse.getPrice();
                    }
                }
            }
        }

        return cheapestCourse;
    }
}
