package blackJack;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BlowJow {

	String[] deck = {
			"2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AH", // Hearts
			"2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD", "AD", // Diamonds
			"2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC", // Clubs
			"2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS"  // Spades
	};

	String[] playerHand = new String[10];
	String[] dealerHand = new String[10];
	String[] onTable = new String[20];
	int playerCount = 0;
	int dealerCount = 0;
	int tableCount = 0;
	int playerValue = 0;
	int dealerValue = 0;
	boolean win = false; //1
	boolean lose = false; //2
	boolean blackJ = false; //3
	
	String amount = null;

	int valueStorage = 0;

	boolean stand = false;
	boolean playerBust = false;
	boolean dealerBust = false;
	boolean playerBJ = false;
	boolean dealerBJ = false;
	Set<String> cardsInPlay = new HashSet<>();

	Scanner scanner = new Scanner(System.in);

	// Method to pick a random card for a player
	int cardForPlayer() {
		Random random = new Random();

		String randomCard;
		do {
			int randomIndex = random.nextInt(deck.length);
			randomCard = deck[randomIndex];
		} while (cardsInPlay.contains(randomCard));

		playerHand[playerCount] = randomCard;
		onTable[tableCount] = randomCard;
		playerCount++;
		tableCount++;
		cardsInPlay.add(randomCard);

		valueStorage = getRank(randomCard);

		playerValue += valueStorage;

		if (playerValue == 21) {
			playerBJ = true;
		}
		if (playerValue > 21) {
			playerBust = true;
		}

		return playerValue;

	}

	// Method to pick a random card for a dealer
	int cardForDealer() {
		int valueStorage = 0;
		Random random = new Random();

		String randomCard;
		do {
			int randomIndex = random.nextInt(deck.length);
			randomCard = deck[randomIndex];
		} while (cardsInPlay.contains(randomCard));

		dealerHand[dealerCount] = randomCard;
		onTable[tableCount] = randomCard;
		dealerCount++;
		tableCount++;
		cardsInPlay.add(randomCard);

		valueStorage = getRank(randomCard);

		dealerValue += valueStorage;

		if (dealerValue == 21 && dealerCount == 2) {
			dealerBJ = true;
		}

		if (dealerValue > 21) {
			dealerBust = true;
		}

		return dealerValue;

	}

	void Start() throws InterruptedException {
		System.out.println("Hello, Welcome to blackJack");
		System.out.println("How much would you like to bet?");
		amount = scanner.nextLine();
		
		

		Thread.sleep(500);
		
		cardForPlayer(); 
		System.out.println("Player: " + playerValue + " Dealer: " + dealerValue);
		Thread.sleep(500);
		cardForDealer();
		System.out.println("Player: " + playerValue + " Dealer: " + dealerValue);
		Thread.sleep(500);
		cardForPlayer();
		System.out.println("Player: " + playerValue + " Dealer: " + dealerValue);
		Thread.sleep(500);

		if (playerBJ) {
			blackJ = true;
			return;
		}

		cardForDealer();
		System.out.println("Player: " + playerValue + " Dealer: " + dealerValue);
		Thread.sleep(500);

		if (dealerBJ) {
			lose = true;
			return;
		}

		// now player turn

		playerTurn();
		if (playerBust) {
			lose = true;
			return;
		}
		
		if (dealerValue < 17) {
			dealerTurn();
		}
		
		if (dealerValue > playerValue && !dealerBust && !playerBust) {
			lose = true;
			
		}
		
		if (playerValue > dealerValue && !dealerBust & !playerBust) {
			win = true;
			
		}

	}
	
	public void bet() {
		if (win) {
			int amountInt = Integer.parseInt(amount);
			amountInt = (int) (amountInt * 2.5);
			System.out.println("You win " + amountInt);
		}
		
		if (blackJ) {
			int amountInt = Integer.parseInt(amount);
			amountInt = amountInt * 2;
			System.out.println("BlackJack, You win " + amountInt);
		}
		
		if (lose) {
			System.out.println("You lose");
		}
		
		
	}

	public int hit() {
		cardForPlayer();
		return playerValue;
	}

	public void stand() {
		stand = true;
		return;
	}

	public void playerTurn() {
		System.out.println("Player: " + playerValue);
		System.out.println("choose an Option: Hit or Stand");
		String choice = scanner.nextLine();

		if (choice.equals("1")) { // hit
			if (hit() > 21) {
				//bust
				System.out.println("You drew a(n) " + valueStorage);
				System.out.println("Bust");
				return;
			}
			playerTurn();
		}

		if (choice == "2") {// stand
			stand();
			System.out.println("Stand");
			return;
		}

	}

	public void dealerTurn() throws InterruptedException {
		System.out.println("Dealer : " + dealerValue);

		while (dealerValue < 17) {
			System.out.println("Dealer draws");
			Thread.sleep(1000);

			cardForDealer();
			System.out.println("Dealer has " + dealerValue + "");

		}
		
		if (dealerBust) {
			System.out.println("Dealer Busts");
			win = true;
			return;
		}

		return;

	}

	int getRank(String card) {
		int cardValue = 0;
		if (card.contains("A")) {
			cardValue = 11;
			return cardValue;

		} else if (card.contains("2")) {
			cardValue = 2;
			return cardValue;

		} else if (card.contains("3")) {
			cardValue = 3;
			return cardValue;

		} else if (card.contains("4")) {
			cardValue = 4;
			return cardValue;

		} else if (card.contains("5")) {
			cardValue = 5;
			return cardValue;

		} else if (card.contains("6")) {
			cardValue = 6;
			return cardValue;

		} else if (card.contains("7")) {
			cardValue = 7;
			return cardValue;

		} else if (card.contains("8")) {
			cardValue = 8;
			return cardValue;

		} else if (card.contains("9")) {
			cardValue = 9;
			return cardValue;

		} else if (card.contains("10") || card.contains("J") 
				|| card.contains("Q") || card.contains("K")) {
			cardValue = 10;
			return cardValue;
		} 
		return -1;
	}

}
