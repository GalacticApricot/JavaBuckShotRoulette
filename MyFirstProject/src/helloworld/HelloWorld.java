package helloworld;
import java.util.Scanner;
import java.util.ArrayList;

public class HelloWorld {
	
	public static void wait(int ms) {
	    try {
	        Thread.sleep(ms);
	    } catch (InterruptedException e) {
	        System.err.format("InterruptedException : %s%n", e);
	    }
	}
	
	public static ArrayList<Boolean> gbullets = new ArrayList<Boolean>();
	
	public static Scanner inp = new Scanner(System.in);
	
	public static int random(int min, int max) {
		return (int) Math.round(Math.random()*(max-min)) + min;
	}
	
	public static void newbullets() {
		int length = random(5, 10);
		gbullets.clear();
		for (int i = 0; i < length; i++) {
			if (random(1, 2) == 1) {
				gbullets.add(false);
			}
			else {
				gbullets.add(true);
			}
		}
		int live = 0;
		int blank = 0;
	    for (int i = 0; i < gbullets.size(); i++) {
	        if (gbullets.get(i)) {
	        	live++;
	        }
	        else {
	        	blank++;
	        }
	    }
		System.out.println("Chamber: " + live + " Lives, " + blank + " Blanks.");
	}
	
	public static boolean newgame() {
		// TODO Auto-generated method stub
		Player player = new Player();
		player.init();
		Enemy enemy = new Enemy();
		enemy.init();
		int gamestatus = -1;
		while (gamestatus == -1) {
			if (gbullets.size() <= 0) {
				System.out.println("New Round. +2 more items each.");
				player.takeitems(2);
				enemy.takeitems(2);
				System.out.println("Reloading chamber...");
				wait(500);
				newbullets();
			}
			wait(1000);
			if (enemy.kapow(player.taketurn(enemy.health))) {
				gamestatus = 1;
			}
			wait(1000);
			if (gbullets.size() <= 0) {
				System.out.println("New Round. +2 more items each.");
				player.takeitems(2);
				enemy.takeitems(2);
				System.out.println("Reloading chamber...");
				wait(500);
				newbullets();
			}
			if (player.kapow(enemy.taketurn(player.health))) {
				gamestatus = 0;
			}
		}
		if (gamestatus == 1) {
			System.out.println("You won");
		}
		else {
			System.out.println("You lost.");
		}
		return true;
	}
	
	
	public static boolean newmultigame() {
		// TODO Auto-generated method stub
		Player player1 = new Player();
		player1.init();
		Player player2 = new Player();
		player2.init();
		int gamestatus = -1;
		while (gamestatus == -1) {
			if (gbullets.size() <= 0) {
				System.out.println("New Round. +2 more items each.");
				player1.takeitems(2);
				player2.takeitems(2);
				System.out.println("Reloading chamber...");
				wait(500);
				newbullets();
			}
			wait(1000);
			System.out.println("Player 1's turn");
			if (player2.kapow(player1.taketurn(player2.health))) {
				gamestatus = 1;
			}
			wait(1000);
			if (gbullets.size() <= 0) {
				System.out.println("New Round. +2 more items each.");
				player1.takeitems(2);
				player2.takeitems(2);
				System.out.println("Reloading chamber...");
				wait(500);
				newbullets();
			}
			System.out.println("Player 2's turn");
			if (player1.kapow(player2.taketurn(player1.health))) {
				gamestatus = 0;
			}
		}
		if (gamestatus == 1) {
			System.out.println("Player 1 wins");
		}
		else {
			System.out.println("Player 2 wins");
		}
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player = new Player();
		player.init();
		System.out.println("Russian Roulette");
		boolean isnodone = true;
		while (isnodone) {
			System.out.println("\nSelect game mode:\n1) Multiplayer\n2) VS AI");
			int input = inp.nextInt();
			switch (input) {
			case 1:
				newmultigame();
				break;
			case 2:
				newgame();
				break;
			default:
				System.out.println("Invalid input, please enter 1 or 2");
			}
			
			
		}
		newgame();
	}

}
