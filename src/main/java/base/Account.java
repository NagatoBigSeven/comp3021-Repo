package base;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
public class Account{
	public int id;
	public int balance;
	public Account(int id, int balance){
		this.id = id;
		this.balance = balance;
	}
	// TODO: Task1
	// replace the null with a lambda expression
	public static Consumer<Account> add100 = account -> account.balance += 100;
	// TODO: Task2
	/**
	*	define checkBound using lowerBound and upperBound
	 *  lowerBound is 0, upperBound is 10000
	 *  We want checkBound to check BOTH lowerBound AND upperBound.
	*/
	public static Predicate<Account> lowerBound = account -> account.balance >= 0;
	public static Predicate<Account> upperBound = account -> account.balance <= 10000;
	public static Predicate<Account> checkBound = lowerBound.and(upperBound);
	// TODO: Task3
	// replace the null with a lambda expression
	// implement a public interface AddMaker that contains single abstract method make(int N)
	// make(int N) returns a Consumer<Account> that adds N to the balance of the account
	// implements make(int N) to return a lambda expression
	// TODO
	@FunctionalInterface
	public interface AddMaker{
		Consumer<Account> make(int N);
	}
	// TODO
	public static AddMaker maker = N -> account -> account.balance += N;
	// You can assume that all the Account in accounts have positive balances.
	public static int getMaxAccountID(List<Account> accounts){
		// TODO: Task4
		// replace the null with a lambda expression
		Account maxOne = accounts.stream().max(Comparator.comparingInt(account -> account.balance)).get();
		return maxOne.id;
	}
}
