import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ExpenseTracker {

    private static final String FILE_NAME = "expenses.csv";

    // Expense Model
    static class Expense {
        private final int id;
        private final LocalDate date;
        private final String category;
        private final double amount;
        private final String description;

        public Expense(int id, LocalDate date, String category, double amount, String description) {
            this.id = id;
            this.date = date;
            this.category = category;
            this.amount = amount;
            this.description = description;
        }

        public int getId() { return id; }
        public LocalDate getDate() { return date; }
        public String getCategory() { return category; }
        public double getAmount() { return amount; }
        public String getDescription() { return description; }

        public String toCsv() {
            return String.format("%d,%s,%s,%.2f,%s", id, date, category, amount, description);
        }

        public static Expense fromCsv(String line) {
            String[] parts = line.split(",", 5);
            if (parts.length < 5) return null;
            return new Expense(
                    Integer.parseInt(parts[0]),
                    LocalDate.parse(parts[1]),
                    parts[2],
                    Double.parseDouble(parts[3]),
                    parts[4]
            );
        }
    }

    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public ExpenseTracker() {
        loadExpenses();
    }

    public void addExpense(LocalDate date, String category, double amount, String description) {
        Expense expense = new Expense(nextId++, date, category, amount, description);
        expenses.add(expense);
        saveExpenses();
        System.out.println("Expense added successfully with ID: " + expense.getId());
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("\nNo expenses recorded yet.");
            return;
        }

        System.out.println("\n-------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-12s | %-15s | %-10s | %-20s%n", "ID", "Date", "Category", "Amount", "Description");
        System.out.println("-------------------------------------------------------------------------------");

        for (Expense e : expenses) {
            System.out.printf("%-5d | %-12s | %-15s | $%-9.2f | %-20s%n",
                    e.getId(), e.getDate(), e.getCategory(), e.getAmount(), e.getDescription());
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    public void showTotal() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("%nTotal Expenditure: $%.2f%n", total);
    }

    public void deleteExpense(int id) {
        boolean removed = expenses.removeIf(e -> e.getId() == id);
        if (removed) {
            saveExpenses();
            System.out.println("Expense ID " + id + " deleted successfully.");
        } else {
            System.out.println("Expense ID " + id + " not found.");
        }
    }

    private void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.println(e.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Expense expense = Expense.fromCsv(line);
                if (expense != null) {
                    expenses.add(expense);
                    if (expense.getId() >= nextId) {
                        nextId = expense.getId() + 1;
                    }
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error reading expenses: " + e.getMessage());
        }
    }

    // CLI Interface
    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== EXPENSE TRACKER ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Total Expenses");
            System.out.println("4. Delete Expense");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAdd(tracker, scanner);
                    break;
                case "2":
                    tracker.viewExpenses();
                    break;
                case "3":
                    tracker.showTotal();
                    break;
                case "4":
                    handleDelete(tracker, scanner);
                    break;
                case "5":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid selection. Please choose 1 to 5.");
            }
        }
    }

    private static void handleAdd(ExpenseTracker tracker, Scanner scanner) {
        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter date (YYYY-MM-DD) or press Enter for today: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                date = LocalDate.now();
            } else {
                try {
                    date = LocalDate.parse(input);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                }
            }
        }

        System.out.print("Enter category (e.g., Food, Travel, Utilities): ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) category = "General";

        double amount = -1;
        while (amount < 0) {
            System.out.print("Enter amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount < 0) System.out.println("Amount cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Enter a numeric value.");
            }
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        tracker.addExpense(date, category, amount, description);
    }

    private static void handleDelete(ExpenseTracker tracker, Scanner scanner) {
        System.out.print("Enter expense ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            tracker.deleteExpense(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter an integer.");
        }
    }
}