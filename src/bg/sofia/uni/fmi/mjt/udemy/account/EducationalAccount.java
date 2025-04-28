package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotCompletedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

public class EducationalAccount extends AccountBase {

    private final int PREVIOUS_GRADES_COUNT = 5;
    private final double MINIMAL_GRADE_FOR_DISCOUNT = 4.50;

    private final double[] grades;
    private int gradesCounter;
    private boolean discountEligibilityCount;

    public EducationalAccount(String username, double balance) {
        super(username, balance);
        this.grades = new double[PREVIOUS_GRADES_COUNT];
        this.gradesCounter = 0;
        this.discountEligibilityCount = false;
    }

    @Override
    public double applyDiscount(Course course) {
        double coursePrice = course.getPrice();

        if (discountEligibilityCount) {
            discountEligibilityCount = false;
            return coursePrice - coursePrice * AccountType.STANDARD.getDiscount();
        }

        return coursePrice;
    }

    @Override
    public void completeCourse(Course course, double grade)
        throws CourseNotPurchasedException, CourseNotCompletedException, ResourceNotFoundException {

        super.completeCourse(course, grade);

        this.grades[gradesCounter++] = grade;

        if (gradesCounter == PREVIOUS_GRADES_COUNT) {
            if (getAvarageGradeFromPreviousCourse() >= MINIMAL_GRADE_FOR_DISCOUNT) {
                discountEligibilityCount = true;
            }

            gradesCounter = 0;
        }
    }

    public double getAvarageGradeFromPreviousCourse() {
        double sum = 0;

        for (double grade : grades) {
            sum += grade;
        }

        return sum / PREVIOUS_GRADES_COUNT;
    }
}
