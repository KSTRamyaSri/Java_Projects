import java.util.Scanner;

class AllPrimeOperations
{
    public boolean isPrime(int num)
    {
        if(num<=1)  return false;
        for(int i=2; i<=Math.sqrt(num); i++)
            if(num%i==0) return false;
        return true;
    }

    public void printPrimes(int limit)
    {
        System.out.println("Prime numbers up to " + limit + ":");
        for(int i=2; i<=limit; i++)
            if(isPrime(i))
                System.out.print(i + " ");
        
        System.out.println();
    }

    public int countPrimes(int limit)
    {
        int count = 0;
        for(int i=2; i<=limit; i++)
            if(isPrime(i))
                count++;
        return count;
    }

}

public class Prime
{
    public static void main(String[] args)
    {
        int option;
        Scanner scanner = new Scanner(System.in);
        AllPrimeOperations primeOps = new AllPrimeOperations();
        System.out.println("Choose an option:");
        System.out.println("1. Print prime numbers");
        System.out.println("2. Count prime numbers");
        System.out.println("3. Check if a number is prime");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        option = scanner.nextInt();
        switch(option)
        {
            case 1:
                System.out.print("Enter the limit: ");
                int limit1 = scanner.nextInt();
                primeOps.printPrimes(limit1);
                break;
            case 2:
                System.out.print("Enter the limit: ");
                int limit2 = scanner.nextInt();
                int count = primeOps.countPrimes(limit2);
                System.out.println("Number of prime numbers up to " + limit2 + " is: " + count);
                break;
            case 3:
                System.out.print("Enter a number: ");
                int num = scanner.nextInt();
                if(primeOps.isPrime(num))
                    System.out.println(num + " is a prime number.");
                else
                    System.out.println(num + " is not a prime number.");
                break;
            case 4:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option");
                
        }
    }
}