package helloworld;
import java.util.HashMap;


public class Enemy {
	public HashMap<String, Integer> items = new  HashMap<String, Integer>();
	public int health = 3;

	public void init() {
		items.put("Spyglass", 0);
		items.put("Cigarettes", 0);
		items.put("Knife", 0);
		items.put("Beer", 0);
		items.put("Handcuffs", 0);

	}
	
	public float liveprobability() {
		int live = 0;
		int blank = 0;
	    for (int i = 0; i < HelloWorld.gbullets.size(); i++) {
	        if (HelloWorld.gbullets.get(i)) {
	        	live++;
	        }
	        else {
	        	blank++;
	        }
	    }
	    if (blank > 0) {
	    	return (float) Math.round((live / blank) * 100) / 100;
	    }
	    else {
	    	return 1;
	    }
	}
	
	public char ai(float probability, boolean issaw, boolean ishandcuffed, int isshown) {
		char input = 'q';
		if (HelloWorld.gbullets.size() < 4 && items.get("Handcuffs") > 0 && !ishandcuffed) {
			input = 'h';
		}
		else if (health < 3 && items.get("Cigarettes") > 0) {
			input = 'c';
		}
		else if (isshown == 1) {
			if (items.get("Knife") > 0 && !issaw) {
				input = 'k';
			}
			else {
				input = 'q';
			}
		}
		else if (probability > 0.9) {
			if (items.get("Knife") > 0 && !issaw) {
				input = 'k';
			}
			else {
				input = 'q';
			}
		}
		else if (probability < 0.4) {
			if (isshown == -1) {
				if (items.get("Spyglass") > 0) {
					input = 's';
				}
				else {
					if (probability < 0.2) {
						input = 'w';
					}
					else {
						if (HelloWorld.random(1, 2) == 1) {
							input = 'w';
						}
						else {
							input = 'q';
						}
					}			
				}
			}
		}
		else {
			if (items.get("Beer") > 0) {
				input = 'b';
			}
			else {
				input = 'q';
			}
		}
		return input;
	}
	
	public int taketurn(int opponenthealth) {
		boolean turn = true;
		while (turn) {
			int isshown = -1;
			boolean spyglassused = false;
			boolean knifeused = false;
			boolean handcuffsused = false;
			boolean phase = true;
			int damage = 1;
			int totaldamage = 0;
			float probability = liveprobability();
			System.out.println("\n\n\nOpponent's turn.\nOpponent's Health: " + health + "\nYour Health: " + opponenthealth + "\nBullets Left: " + HelloWorld.gbullets.size());
			while (phase) {
				System.out.print("Opponent's Items: ");
				for (String i : items.keySet()) {
					if (items.get(i) > 0) {
						System.out.print(i + " = " + items.get(i) + ", ");
					}
				}	
				System.out.println("\nOpponent selecting move.");
				HelloWorld.wait(500);
				char input = ai(probability, knifeused, handcuffsused, isshown);
				switch (input) {
					case 'q':
						System.out.println("The opponent shot at you...");
						HelloWorld.wait(750);
						if (HelloWorld.gbullets.getFirst()) {
							HelloWorld.gbullets.remove(0);
							totaldamage += damage;
							System.out.println("It was a live! your's Health: " + (opponenthealth - totaldamage));
							if (!handcuffsused) {return totaldamage;} else {handcuffsused = false;}
						}
						else {
							HelloWorld.gbullets.remove(0);
							System.out.println("It was a blank.");
							if (!handcuffsused) {return totaldamage;} else {handcuffsused = false;}
						}
					case 'w':
						System.out.println("Opponent shot at itself...");
						HelloWorld.wait(750);
						if (HelloWorld.gbullets.getFirst()) {
							HelloWorld.gbullets.remove(0);
							health -= damage;
							System.out.println("It was a live. Opponent's Health: " + health);
							if (!handcuffsused) {return totaldamage;} else {handcuffsused = false;}
						}
						else {
							HelloWorld.gbullets.remove(0);
							System.out.println("It was a blank! Opponent goes again.");
						}
					break;
					case 's':
						if (!spyglassused) {
							items.replace("Spyglass", items.get("Spyglass") - 1);
							System.out.println("Opponent used a spyglass...");
							HelloWorld.wait(250);
							if (HelloWorld.gbullets.getFirst()) {
								isshown = 1;
							}
							else {
								isshown = 0;
							}
						}
					break;
					case 'c':
						if (health < 3) {
							items.replace("Cigarettes", items.get("Cigarettes") - 1);
							System.out.println("Opponent used a cigarette...");
							HelloWorld.wait(100);
							health++;
							System.out.println("Opponent +1 health. Opponent's health: " + health);
						}
					break;
					case 'k':
						if (!knifeused) {
							items.replace("Knife", items.get("Knife") - 1);
							System.out.println("Opponent used a knife...");
							HelloWorld.wait(250);
							System.out.println("Next shot will do 2x damage");
							knifeused = true;
							damage = 2;
						}
					break;
					case 'b':
						items.replace("Beer", items.get("Beer") - 1);
						System.out.println("Opponent chugged a beer...");
						HelloWorld.wait(250);
						if (HelloWorld.gbullets.getFirst()) {
							System.out.println("Opponent removed a live.");
						}
						else {
							System.out.println("Opponent removed a blank.");
						}
						HelloWorld.gbullets.remove(0);
					break;
					case 'h':
						if (!handcuffsused) {
							items.replace("Handcuffs", items.get("Handcuffs") - 1);
							System.out.println("Opponent used handcuffs...");
							HelloWorld.wait(250);
							System.out.println("Your turn will be skipped.");
							handcuffsused = true;
						}
					break;			
					}
					if (HelloWorld.gbullets.size() <= 0) {
						return totaldamage;
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
