package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;

public class StandartAccount extends AccountBase {

    public StandartAccount(String username, double balance) {
        super(username, balance);
    }

    @Override
    public double applyDiscount(Course course) {
        return course.getPrice() - course.getPrice() * AccountType.STANDARD.getDiscount();
    }
}
