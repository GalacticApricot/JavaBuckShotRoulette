package helloworld;
import java.util.Scanner;
import java.util.HashMap;


public class Player {
	public HashMap<String, Integer> items = new  HashMap<String, Integer>();
	public int health = 3;
	public Scanner inp = new Scanner(System.in);

	public void init() {
		items.put("Spyglass", 0);
		items.put("Cigarettes", 0);
		items.put("Knife", 0);
		items.put("Beer", 0);
		items.put("Handcuffs", 0);

	}
	
	public int taketurn(int opponenthealth) {
		boolean turn = true;
		while (turn) {
			boolean spyglassused = false;
			boolean knifeused = false;
			boolean handcuffsused = false;
			boolean phase = true;
			int damage = 1;
			int totaldamage = 0;
			System.out.println("\n\n\nYour turn.\nYour Health: " + health + "\nOpponent's Health: " + opponenthealth + "\nBullets Left: " + HelloWorld.gbullets.size());
			while (phase) {
				System.out.print("Your Items: ");
				for (String i : items.keySet()) {
					if (items.get(i) > 0) {
						System.out.print(i + " = " + items.get(i) + ", ");
					}
				}				
				System.out.println("\nSelect Move:\nQ) Fire at Opponent\nW)Fire at yourself");
				for (String i : items.keySet()) {
					if (items.get(i) > 0) {
						System.out.println(i.charAt(0) + ") " + i);
					}
				}
				System.out.print(">");
				char input = inp.nextLine().toLowerCase().charAt(0);
				boolean isok = false;
				for (String i : items.keySet()) {
					if ((items.get(i) > 0 && i.toLowerCase().charAt(0) == input) || input == 'q' || input == 'w') {
						isok = true;
					}
				}
				
				if (isok) {
					
					
					
					switch (input) {
					case 'q':
						System.out.println("You fired a shot at the opponent...");
						HelloWorld.wait(750);
						if (HelloWorld.gbullets.getFirst()) {
							HelloWorld.gbullets.remove(0);
							totaldamage += damage;
							System.out.println("It was a live! opponent's Health: " + (opponenthealth - totaldamage));
							if (!handcuffsused) {return totaldamage;} else {handcuffsused = false;}
						}
						else {
							HelloWorld.gbullets.remove(0);
							System.out.println("It was a blank.");
							if (!handcuffsused) {return totaldamage;} else {handcuffsused = false;}
						}
					case 'w':
						System.out.println("You fired a shot at yourself...");
						HelloWorld.wait(750);
						if (HelloWorld.gbullets.getFirst()) {
							HelloWorld.gbullets.remove(0);
							health -= damage;
							System.out.println("It was a live. Your Health: " + health);
							if (!handcuffsused) {return totaldamage;} else {handcuffsused = false;}
						}
						else {
							HelloWorld.gbullets.remove(0);
							System.out.println("It was a blank! Go Again.");
						}
					break;
					case 's':
						if (!spyglassused) {
							items.replace("Spyglass", items.get("Spyglass") - 1);
							System.out.println("You used a spyglass...");
							HelloWorld.wait(250);
							if (HelloWorld.gbullets.getFirst()) {
								System.out.println("It's a live.");
							}
							else {
								System.out.println("It's a blank.");
							}
						}
						else {
							System.out.println("You have already used a spyglass this round.");
						}
					break;
					case 'c':
						if (health < 3) {
							items.replace("Cigarettes", items.get("Cigarettes") - 1);
							System.out.println("You used a cigarette...");
							HelloWorld.wait(100);
							health++;
							System.out.println("+1 health. Your health: " + health);
						}
						else {
							System.out.println("You are already full health.");
						}
					break;
					case 'k':
						if (!knifeused) {
							items.replace("Knife", items.get("Knife") - 1);
							System.out.println("You used a knife...");
							HelloWorld.wait(250);
							System.out.println("Next shot will do 2x damage");
							knifeused = true;
							damage = 2;
						}
						else {
							System.out.println("You have already used a spyglass this round.");
						}
					break;
					case 'b':
						items.replace("Beer", items.get("Beer") - 1);
						System.out.println("You chugged a beer...");
						HelloWorld.wait(250);
						if (HelloWorld.gbullets.getFirst()) {
							System.out.println("You removed a live.");
						}
						else {
							System.out.println("You removed a blank.");
						}
						HelloWorld.gbullets.remove(0);
					break;
					case 'h':
						if (!handcuffsused) {
							items.replace("Handcuffs", items.get("Handcuffs") - 1);
							System.out.println("You used handcuffs...");
							HelloWorld.wait(250);
							System.out.println("You will skip the opponent's turn.");
							handcuffsused = true;
						}
						else {
							System.out.println("You have already used handcuffs this round.");
						}
					break;
					
					
					
					}
					if (HelloWorld.gbullets.size() <= 0) {
						return totaldamage;
					}
				}
				else {
					System.out.println("Invalid Input.");
				}
			}
		}
	return 0;
}
	
	public boolean kapow(int didtakedamage) {
		if (didtakedamage > 0) {
			health--;
			if (health <= 0) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public void takeitems(int n) {
		while (n > 0) {
			int i = HelloWorld.random(0, 4);
			String k = items.keySet().toArray()[i].toString();
			items.replace(k, items.get(k) + 1);
			n--;
		}
	}

}
