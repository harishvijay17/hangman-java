import java.util.Scanner;
import java.awt.*;
import javax.swing.JFrame;

public class WordGuesser extends Canvas {
	private static int missesLeft = 6; // number of misses remaining

	public void paint(Graphics g) {
		setBackground(Color.decode("#87CEEB")); // set colors
		setForeground(Color.decode("#9b7653"));

		g.fillRect(0, 250, 400, 160); // ground
		g.setColor(Color.BLACK);

		g.fillRect(20, 245, 50, 5); // hanging stuff
		g.fillRect(43, 5, 5, 240);
		g.fillRect(43, 5, 112, 5);
		g.fillRect(155, 5, 5, 30);

		if (missesLeft < 6) g.fillOval(130,32,50, 60); // draw head if they miss
		if (missesLeft < 5) g.fillRect(145, 90, 20, 100); // draw body if they have 2 misses

		if (missesLeft < 4) g.fillRect(165, 110, 45, 10); // draw arm if they have 3 misses
		if (missesLeft < 3) g.fillRect(100, 110, 45, 10); // draw arm if they have 4 misses

		if (missesLeft < 2) g.fillRect(157, 190, 8, 50); // draw leg if they have 5 misses
		if (missesLeft < 1) g.fillRect(145, 190, 8, 50); // draw leg if they have 6 misses
	}

	public static void main(String[] args) {
		WordGuesser a = new WordGuesser(); // graphics
		JFrame frame = new JFrame();

		frame.add(a);
		frame.setSize(400, 400);
        frame.setVisible(true); // draw graphics

		Scanner reader = new Scanner(System.in);

		// word list
		String[] words = {"cousin", "generate", "suntan", "mutual", "bounce", "worker", "tongue", "harass", "entry", "extent", "cluster", "peach", "display", "behead", "cunning", "infection", "mother", "suitcase", "tragedy", "wander", "doubt", "ignorant", "detail", "carve", "amuse"};
		String[] hardWords = {"abruptly", "absurd", "abyss","affix","askew","avenue","awkward","axiom","azure","bagpipes","bandwagon","banjo","bayou","beekeeper","bikini","blitz","blizzard","boggle","bookworm" ,"boxcar","boxful"
							,"buckaroo","buffalo","buffoon","buzzard","crypt","curacao","cycle","daiquiri","euouae","exodus","faking","fishhook","flopping","fluffiness","frazzled","galaxy","gazebo","giaour","gizmo"
							,"glowworm","glyph","gnarly","gnostic","gossip","grogginess","haiku","haphazard","hyphen","icebox","injury","ivory","ivy","jackpot","jaundice","jawbreaker","jaywalk","jazziest","jazzy","jelly","jigsaw"
							,"jinx","jiujitsu","jockey","jogging","juicy","khaki","kilobyte","kiosk","larynx","lengths","lucky","luxury","lymph","marquis","matrix","psyche","puppy","puzzling","quartz","queue","quixotic"
							,"rhubarb","rhythm","strength","subway","swivel","syndrome","thriftless","thumbscrew","topaz","transcript","twelfths","unknown","unworthy","unzip","uptown","vaporize","vixen","vodka","voodoo"
							,"vortex","walkway","zipper","zodiac","zombie"};

		System.out.println("Welcome to WordGuesser! In this game you need to guess a randomly chosen word letter by letter.");
		System.out.println("If you guess a letter that is in the word, that letter will be shown so you can see where it is in the word.\nIf that letter is not in the word then a body part is added to the hangman.");
		System.out.println("If 6 of your guessed letters are not in the word, you lose.");
		System.out.println("There is a normal and a hard difficulty.");
		System.out.println("The normal difficulty chooses a random word from a list of 25 random words which were randomly selected.");
		System.out.print("The hard difficulty will choose from a list of words that were specifically chosen to be difficult to guess.\nWould you like the normal difficulty (1) or the hard difficulty (2)? ");

		int difficulty = reader.nextInt(); // difficulty selection
		reader.nextLine();

		while (difficulty != 1 && difficulty != 2) { // check invalid input
			System.out.print("Invalid input. Please enter again (1 or 2). ");
			difficulty = reader.nextInt();
			reader.nextLine();
		}

		String playAgain = "y";
		int score = 0;

		while (playAgain.equals("y")) { // loop while they want to keep playing
			String word = (difficulty == 1 ? words[(int)(Math.random() * words.length)] : hardWords[(int)(Math.random() * hardWords.length)]); // choose random word
			String[] wordArr = word.split("");

			String[] guesses = new String[0]; // keep track of letters guessed
			missesLeft = 6;

			String printStr = "";
			boolean found = false;

			while (true) {
				for (int i = 0; i < word.length(); i++) printStr += (inArr(wordArr[i], guesses) ? wordArr[i] : "_") + " "; // create string to print (print _ if that letter is not guessed yet)
				System.out.println(printStr);

				if (printStr.indexOf("_") == -1) { // if none of the letters are blank they have won
					found = true;
					break;
				}

				frame.add(a); // update graphics
				if (missesLeft == 0) break; // if they have used all misses then they lose
				System.out.println("Misses Left: " + missesLeft);

				System.out.print("Letters guessed so far: ");
				for (int i = 0; i < guesses.length; i++) System.out.print(guesses[i] + (i == guesses.length - 1 ? "" : ", ")); // print letter guessed
				System.out.println();

				System.out.print("Guess letter: ");
				String guess = reader.nextLine().toLowerCase(); // ask to guess a letter

				boolean reguess = false;
				if (inArr(guess, guesses)) reguess = true;
				while (guess.replaceFirst("[a-z]", "").length() != 0 || reguess) { // check if input is invalid or letter has already been guessed
					if (inArr(guess, guesses)) {
						reguess = false;
						System.out.print("You have already guessed this letter. ");
					}

					System.out.print("Invalid, try again.\nGuess letter: ");
					guess = reader.nextLine().toLowerCase();
				}

				guesses = append(guess, guesses); // add guess to array of guessed letters
				if (word.indexOf(guess) == -1) { // if the letter is not in the word then missesLeft is reduced
					missesLeft--;
					System.out.println("No " + guess + "!");
				} else System.out.println("Found " + guess + "!"); // print that the letter is found if guess is there

				System.out.println();
				printStr = "";
			}

			if (found) { // if they have guessed the word increase their score
				System.out.println("You guessed the word with " + missesLeft + " misses left!");
				score++;
			} else System.out.println("You lost! The word was " + word + "."); // tell them word if they lose

			System.out.println("Your score is " + score + "."); // tell them their score

			System.out.print("\nWould you like to play again? ((Y)es or (N)o) "); // ask to play again
			playAgain = reader.nextLine().toLowerCase();

			while (playAgain.replaceFirst("[ynYN]", "").length() != 0) { // check for invalid input
				System.out.print("Invalid input. Please enter again. (Y or N)");
				playAgain = reader.nextLine().toLowerCase();
			}

			System.out.println();
		}

		System.out.println("Your final score is " + score + "!"); // tell them final score at the end
	}

	public static boolean inArr(String elem, String[] arr) { // check if elem is in arr
		for (int i = 0; i < arr.length; i++) {
			if (elem.equals(arr[i])) return true; // if the element is equal to the current element in the loop then return true
		}

		return false; // if not found then return false
	}

	public static String[] append(String elem, String[] arr) { // append elem to arr
		String[] newArr = new String[arr.length + 1]; // make a new array which is one longer than current array

		for (int i = 0; i < arr.length; i++) newArr[i] = arr[i]; // put all elements into new array
		newArr[arr.length] = elem; // set the last element

		return newArr; // return array
	}
}