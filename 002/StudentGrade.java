import java.util.Scanner;

public class StudentGrade
{
    public char getGrade(int score, int total)
    {
        double percentage = (double) score / total * 100;
        if(percentage >= 90)
            return 'A';
        else if(percentage >= 80)
            return 'B';
        else if(percentage >= 70)
            return 'C';
        else if(percentage >= 60)
            return 'D';
        else
            return 'F';
    }
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        StudentGrade sg = new StudentGrade();

        System.out.print("Enter the score: ");
        int score = scan.nextInt();

        System.out.print("Enter the total possible score: ");
        int total = scan.nextInt();
        
        char grade = sg.getGrade(score, total);
        System.out.println("The grade is: " + grade);   
    }


}