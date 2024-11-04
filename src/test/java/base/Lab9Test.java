package base;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class Lab9Test{
	@Test
	public void test1(){
		Account a1 = new Account(1, 500);
		Account.add100.accept(a1);
		assertEquals(600, a1.balance);
	}
	@Test
	public void test2(){
		Account a1 = new Account(1, 500);
		Account a2 = new Account(2, -100);
		Account a3 = new Account(3, 12000);
		boolean b1 = Account.checkBound.test(a1);
		boolean b2 = Account.checkBound.test(a2);
		boolean b3 = Account.checkBound.test(a3);
		assertTrue(b1 && !b2 && !b3);
	}
	@Test
	public void test3() {
		Account a1 = new Account(1, 500);
		Consumer<Account> add1000 = Account.maker.make(1000);
		Consumer<Account> sub1000 = Account.maker.make(-1000);
		add1000.accept(a1);
		int b1 = a1.balance;
		sub1000.accept(a1);
		int b2 = a1.balance;
		assertEquals(1500, b1);
		assertEquals(500, b2);
	}
	@Test
	public void test4() {
		int[] arrInt = {100, 200, 300, 400, 500, 600, 500, 400, 300, 700, 200};
		ArrayList<Account> accounts = new ArrayList<>();
		int id = 0;
		for(Integer balance: arrInt)accounts.add(new Account(id++, balance));
		int maxID = Account.getMaxAccountID(accounts);
		assertEquals(9, maxID);
	}
}
