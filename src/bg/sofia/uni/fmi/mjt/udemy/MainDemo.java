package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.AccountBase;
import bg.sofia.uni.fmi.mjt.udemy.account.BusinessAccount;
import bg.sofia.uni.fmi.mjt.udemy.account.EducationalAccount;
import bg.sofia.uni.fmi.mjt.udemy.account.StandartAccount;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.*;

public class MainDemo {
    public static void main(String[] args) {
        System.out.println("Starting Udemy Learning Platform Demo...");

        // Create resources and courses
        Resource[] javaResources = createJavaResources();
        Resource[] pythonResources = createPythonResources();
        Resource[] marketingResources = createMarketingResources();

        Course javaCourse = new Course(
            "Java Masterclass",
            "Complete Java programming from basics to advanced topics",
            99.99,
            javaResources,
            Category.SOFTWARE_ENGINEERING
        );

        Course pythonCourse = new Course(
            "Python for Beginners",
            "Learn Python programming from scratch",
            79.99,
            pythonResources,
            Category.DEVELOPMENT
        );

        Course marketingCourse = new Course(
            "Digital Marketing Essentials",
            "Master the art of digital marketing strategies",
            69.99,
            marketingResources,
            Category.MARKETING
        );

        // Create accounts
        StandartAccount standardAccount = new StandartAccount("john_doe", 200.0);
        EducationalAccount eduAccount = new EducationalAccount("student_alice", 150.0);
        BusinessAccount businessAccount = new BusinessAccount(
            "tech_company",
            500.0,
            new Category[] {Category.SOFTWARE_ENGINEERING, Category.DEVELOPMENT}
        );

        // Gather all accounts and courses
        AccountBase[] accounts = new AccountBase[] {
            standardAccount, eduAccount, businessAccount
        };

        Course[] courses = new Course[] {
            javaCourse, pythonCourse, marketingCourse
        };

        // Create Udemy platform instance
        Udemy udemy = new Udemy(accounts, courses);

        // Demonstrate finding a course by name
        System.out.println("\n--- Finding Courses by Name ---");
        findCourseByName(udemy, "Java Masterclass");
        findCourseByName(udemy, "Course That Doesn't Exist");

        // Demonstrate finding courses by keyword
        System.out.println("\n--- Finding Courses by Keyword ---");
        findCoursesByKeyword(udemy, "programming");
        findCoursesByKeyword(udemy, "nonexistent");

        // Exception would be thrown for invalid keywords
        findCoursesByKeyword(udemy, "");  // Should throw IllegalArgumentException
        findCoursesByKeyword(udemy, "keyword123");  // Should throw IllegalArgumentException

        // Demonstrate getting courses by category
        System.out.println("\n--- Getting Courses by Category ---");
        getCoursesByCategory(udemy, Category.DEVELOPMENT);
        getCoursesByCategory(udemy, Category.MUSIC);

        // Demonstrate buying courses with different account types
        System.out.println("\n--- Purchasing Courses ---");
        purchaseCourse(standardAccount, javaCourse);
        purchaseCourse(eduAccount, pythonCourse);
        purchaseCourse(businessAccount, javaCourse);

        // Try to buy a course with incompatible category for business account
        purchaseCourse(businessAccount, marketingCourse);

        // Demonstrate completing resources
        System.out.println("\n--- Completing Resources ---");
        System.out.println("Initial Java course completion percentage: " +
            javaCourse.getCompletionPercentage() + "%");

        // Complete some resources in Java course for standard account
        completeResources(standardAccount, javaCourse,
            new Resource[] {javaResources[0], javaResources[1]});

        System.out.println("After completing 2 resources, Java course completion percentage: " +
            javaCourse.getCompletionPercentage() + "%");

        // Complete all resources and finish the course
        completeResources(standardAccount, javaCourse,
            new Resource[] {javaResources[2], javaResources[3]});

        // Complete the course with a grade
        completeCourse(standardAccount, javaCourse, 5.5);

        // Find longest course
        System.out.println("\n--- Finding Longest Course ---");
        Course longestCourse = udemy.getLongestCourse();
        if (longestCourse != null) {
            System.out.println("Longest course: " + longestCourse.getName() +
                " with duration " + longestCourse.getTotalTime().hours() +
                " hours and " + longestCourse.getTotalTime().minutes() + " minutes");
        } else {
            System.out.println("No courses found in the platform.");
        }

        // Find cheapest course by category
        System.out.println("\n--- Finding Cheapest Course by Category ---");
        findCheapestCourseByCategory(udemy, Category.DEVELOPMENT);
        findCheapestCourseByCategory(udemy, Category.MUSIC);
    }

    private static Resource[] createJavaResources() {
        return new Resource[] {
            new Resource("Java Basics", new ResourceDuration(45)),
            new Resource("OOP Concepts", new ResourceDuration(60)),
            new Resource("Java Collections", new ResourceDuration(55)),
            new Resource("Exceptions Handling", new ResourceDuration(40))
        };
    }

    private static Resource[] createPythonResources() {
        return new Resource[] {
            new Resource("Python Basics", new ResourceDuration(50)),
            new Resource("Python Functions", new ResourceDuration(45)),
            new Resource("Data Structures", new ResourceDuration(55))
        };
    }

    private static Resource[] createMarketingResources() {
        return new Resource[] {
            new Resource("Digital Marketing Intro", new ResourceDuration(30)),
            new Resource("Social Media Marketing", new ResourceDuration(50)),
            new Resource("SEO Fundamentals", new ResourceDuration(45))
        };
    }

    private static void findCourseByName(LearningPlatform platform, String courseName) {
        try {
            Course course = platform.findByName(courseName);
            System.out.println("Found course: " + course.getName() +
                " with price: $" + course.getPrice());
        } catch (CourseNotFoundException e) {
            System.out.println("Could not find course: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid course name: " + e.getMessage());
        }
    }

    private static void findCoursesByKeyword(LearningPlatform platform, String keyword) {
        try {
            Course[] courses = platform.findByKeyword(keyword);
            if (courses != null && courses.length > 0) {
                System.out.println("Found " + courses.length + " courses with keyword '" + keyword + "':");
                for (Course course : courses) {
                    System.out.println("- " + course.getName());
                }
            } else {
                System.out.println("No courses found with keyword '" + keyword + "'");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid keyword '" + keyword + "': " + e.getMessage());
        }
    }

    private static void getCoursesByCategory(LearningPlatform platform, Category category) {
        try {
            Course[] courses = platform.getAllCoursesByCategory(category);
            if (courses.length > 0) {
                System.out.println("Found " + courses.length + " courses in " + category + " category:");
                for (Course course : courses) {
                    System.out.println("- " + course.getName());
                }
            } else {
                System.out.println("No courses found in " + category + " category");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category: " + e.getMessage());
        }
    }

    private static void purchaseCourse(AccountBase account, Course course) {
        System.out.println(account.getUsername() + " balance before purchase: $" + account.getBalance());
        try {
            account.buyCourse(course);
            System.out.println(account.getUsername() + " purchased " + course.getName() +
                ". New balance: $" + account.getBalance());
        } catch (InsufficientBalanceException e) {
            System.out.println("Insufficient balance: " + e.getMessage());
        } catch (CourseAlreadyPurchasedException e) {
            System.out.println("Course already purchased: " + e.getMessage());
        } catch (MaxCourseCapacityReachedException e) {
            System.out.println("Maximum course capacity reached: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot purchase course: " + e.getMessage());
        }
    }

    private static void completeResources(AccountBase account, Course course, Resource[] resources) {
        try {
            account.completeResourcesFromCourse(course, resources);
            System.out.println("Completed " + resources.length + " resources in course " + course.getName());
        } catch (CourseNotPurchasedException e) {
            System.out.println("Course not purchased: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            System.out.println("Resource not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments: " + e.getMessage());
        }
    }

    private static void completeCourse(AccountBase account, Course course, double grade) {
        try {
            account.completeCourse(course, grade);
            System.out.println(course.getName() + " completed with grade: " + course.getGrade());
            System.out.println("Is " + course.getName() + " course completed? " + course.isCompleted());
        } catch (CourseNotPurchasedException e) {
            System.out.println("Course not purchased: " + e.getMessage());
        } catch (CourseNotCompletedException e) {
            System.out.println("Course not completed: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            System.out.println("Resource not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments: " + e.getMessage());
        }
    }

    private static void findCheapestCourseByCategory(LearningPlatform platform, Category category) {
        try {
            Course cheapestCourse = platform.getCheapestByCategory(category);
            if (cheapestCourse != null) {
                System.out.println("Cheapest course in " + category + " category: " +
                    cheapestCourse.getName() + " ($" + cheapestCourse.getPrice() + ")");
            } else {
                System.out.println("No courses found in " + category + " category");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category: " + e.getMessage());
        }
    }
}