import java.util.Scanner;
import java.util.Random;

public class NumGuess
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        
        System.out.println("Welcome to the Number Guessing Game!\n");
        System.out.print("Enter the range for the random number (1 to N): ");
        
        int range = scan.nextInt();
        if(range<10)
        {
            System.out.println("Please enter a number greater than or equal to 10.");
            return;
        }
        else if(range>1000)
        {
            System.out.println("Please enter a number less than or equal to 1000.");
            return;
        }
        System.out.println("The target number is between 1 and " + range);

        int target = rand.nextInt(range) + 1;

        System.out.println("Enter the level of difficulty (1-Easy, 2-Medium, 3-Hard): ");
        int difficulty = scan.nextInt();

        int attempts;
        if(difficulty == 1) attempts = 10;
        else if(difficulty == 2)  attempts = 5;
        else attempts = 3;
        System.out.println("You have " + attempts + " attempts to guess the number!");

        int guess;

        do
        {
            System.out.print("Enter your guess: ");
            guess = scan.nextInt();
            if (guess < target)
                System.out.println("Too low! Try again.");
            else if (guess > target)
                System.out.println("Too high! Try again.");
            else
                System.out.println("Congratulations! You've guessed the number " + target + " correctly!");
            attempts--;
        } while (guess != target && attempts > 0);
        if (guess != target)
        {
            System.out.println("Sorry! You've used all your attempts. The correct number was " + target + ".");
        }
    }
}